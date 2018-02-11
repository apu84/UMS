#!/bin/bash

# description: IUMS deployment script 

# processname: tomcat

# chkconfig: 234 20 80
set -e

source "/opt/config/iums.conf"

pid_file="$RUN_DIR/microservie-$PROJECT_VERSION.pid"
out_log="${LOG_DIR}/microservie-$PROJECT_VERSION.out"

restart_service_if_not_running() {
	if P=$(pgrep $1)
	then
			echo "$1 is running, PID is $P"
	else
			echo "$1 is not running, restarting"
			service $1 restart
	fi
}

sync_repo() {
	cd $UMS_SRC
	git fetch
	git checkout $1
	git pull
}

build_src() {
	cd $UMS_SRC
	
	sync_repo $1
	
	PATH=$MAVEN_HOME/bin:$PATH
  
	export PATH
	
	mvn clean install -Ptest-server
	
	cd $UMS_SRC/ums-webapps
	
	mvn clean install
}

deploy() {
cd $APP_SERVER_HOME
for instance in $(echo $APP_SERVER_INSTANCES | sed "s/,/ /g")
do
		set_env $instance
		echo "Shutting down $instance"
		stop_tomcat $instance
		
		rm -rf $instance/webapps/*
		rm -rf $instance/ums/lib/*.*
		cp -R $UMS_SRC/ums-dist/target/ums-dist-$PROJECT_VERSION/lib/*.jar $instance/ums/lib/
		cp -R $UMS_SRC/ums-dist/target/ums-dist-$PROJECT_VERSION/webapps/*.war $instance/webapps/
		let "port=port+1"
        echo "Starting up $instance at port : $port"
		start_tomcat $instance $port
done

yes | cp -R $UMS_SRC/ums-webapps/ums-web/target/ums-web-$PROJECT_VERSION/* /opt/ums-web
yes | cp -R $UMS_SRC/ums-webapps/ums-registrar-web/target/ums-registrar-web-$PROJECT_VERSION/* /opt/ums-registrar-web
yes | cp -R $UMS_SRC/ums-webapps/ums-library-web/target/ums-library-web-$PROJECT_VERSION/* /opt/ums-library-web
yes | cp -R $UMS_SRC/ums-webapps/ums-account-web/target/ums-account-web-$PROJECT_VERSION/* /opt/ums-account-web
}

get_micro_service_pids() {
  ps aux | grep microservice-$PROJECT_VERSION.jar |grep -v grep| awk '{print $2}'
}

status_of_micro_service() {
 if [ -r "${pid_file}" ]; then
    local pid=
    pid=$(cat "${pid_file}")
    if [ "$(ps -p "${pid}" | wc -l)" -eq 2 ]; then
      echo "Microservice is running with PID ${pid}"
    else
      echo "Microservice is NOT running but has a dangling PID file ${pid_file}"
    fi
  else
    local micro_service_pids=
    micro_service_pids=$(get_micro_service_pids)
    if [ -n "${micro_service_pids:-}" ]; then
      cat <<EOF
There's rogue Microservice process running on $HOSTNAME
It wasn't started with ${0}, it has PID ${micro_service_pids}
EOF
      exit 1
    fi
    echo "Microservice is NOT running"
  fi

  exit 0
}

stop_micro_service() {
  if [ -r "${pid_file}" ]; then
    local pid=
    pid=$(cat "${pid_file}")

    ## ps -p will output two lines if the process is running
    if [ "$(ps -p "${pid}" | wc -l)" -eq 2 ]; then
      echo "Stopping Microservice running with PID ${pid} ..."
      kill "${pid}"

      echo -n "Waiting for Microservice to shut down "
      for i in {0..10}; do
        echo -n "."
        sleep 1
        if [ "$(ps -p "${pid}" | wc -l)" -eq 1 ]; then
          break
        fi
      done
      echo ""

      if [ "$(ps -p "${pid}" | wc -l)" -eq 2 ]; then
        echo "Gracefully stopping Microservice failed, will now use force ..."
        kill -9 "${pid}"
      fi

      rm "${pid_file}"
    fi
  else
    echo "PID file doesn't not exist ${pid_file}"
    exit 1
  fi
}

start_micro_service() {
  local pid=
  local running_micro_services=
  running_micro_services=$(get_micro_service_pids)
  if [ ! -z "${running_micro_services}" ]; then
    echo "Microservice already running with PID ${running_micro_services}"
    exit 1
  fi

  export PATH=$PATH:$MAVEN_HOME/bin:$UMS_CONFIG:$JAVA_HOME/bin
  UMS_CONFIG=$UMS_CONFIG
  export UMS_CONFIG
  java -jar $UMS_SRC/microservice/target/microservice-$PROJECT_VERSION.jar &>> "${out_log}" & pid=$!
  echo "${pid}" > "${pid_file}"
  printf "%s" "Starting Microservice "

  ## Wait for few seconds to check if user-manager has started properly
  for i in {0..5}; do
    echo -n "."
    sleep 1
    if [ "$(ps -p "${pid}" | wc -l)" -eq 1 ]; then
      rm "${pid_file}"
      cat <<EOF

Failed to start Microservice.
Please check ${out_log} for further details.
EOF
       exit 1
    fi
    done
    echo ""
    echo "Started Microservice with PID ${pid}"
}

restart_web_server() {
	service solr restart -e schemaless
	service nginx restart
}

restart_cache_server() {
	service memcached restart
	service memcached2 restart
}

restart_app_servers() {
	stop_all
	start_all
}

stop_all() {
cd $APP_SERVER_HOME

for instance in $(echo $APP_SERVER_INSTANCES | sed "s/,/ /g")
do
		set_env $instance
		echo "Shutting down $instance"
		stop_tomcat $instance
done
}

start_all() {
cd $APP_SERVER_HOME

for instance in $(echo $APP_SERVER_INSTANCES | sed "s/,/ /g")
do
		set_env $instance
		let "port=port+1"
		echo "Starting up $instance"
		start_tomcat $instance $port
done
}

set_env() {
	CATALINA_HOME=$APP_SERVER_HOME/$1
  export CATALINA_HOME
	export PATH=$PATH:$MAVEN_HOME/bin:$UMS_CONFIG:$JAVA_HOME/bin:$CATALINA_HOME:$PATH
	UMS_CONFIG=$UMS_CONFIG
	export UMS_CONFIG
}

stop_remote_debug() {
	for instance in $(echo $APP_SERVER_INSTANCES | sed "s/,/ /g")
	do
		let "port=$REMOTE_DEBUG_PORT+1"
		echo "Stopping remote debug: $port"
		fuser -k $port/tcp
	done
}

stop_tomcat() {
        #Finds the tomcat process id
        ps aux | grep $1 |grep -v grep | awk -F  " " '{print $2}' > tomcatProcessID

        #Kills the process id returned from above mentioned command.
        kill -9 `cat tomcatProcessID` && tput setaf 3 && echo "Tomcat $1 killed Successfully" ;rm -rf tomcatProcessID


	#sh $1/bin/shutdown.sh
	if [ "$ENABLE_REMOTE_DEBUGGING" == "true" ]
	then
		stop_remote_debug
  fi
}

start_tomcat() {
	if [ "$ENABLE_REMOTE_DEBUGGING" == "true" ]
	then
		let "remote_port=$REMOTE_DEBUG_PORT+1"
		export CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,address=$remote_port,server=y,suspend=n -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true"
		$1/bin/catalina.sh jpda start
  fi
	sh $1/bin/startup.sh
}

restart_tomcat() {
	stop_tomcat $1
	start_tomcat $1
}

usage() {
	echo "-rb | --remote-branch name to checkout"
	echo "-d  | --deploy Deploy in application and web server"
	echo "-b  | --build Build distribution"
    echo "-m  | --mservice Run microservice"
	echo "-bd | --build-deploy Build distribution and deploy in application and web server"
	echo "      --stop Stops a particular tomcat instance / microservice"
	echo "      --start Start a particular tomcat instance/ microservice"
	echo "      --restart Restart a particular tomcat instance"
	echo "      --status Status a particular tomcat instance/ microservice"
	echo "      --applog Show applicaiton logs of a particular tomcat instance"
	echo "      --restart-app-servers Restarts all tomcat instances"
	echo "      --restart-web-server Restarts web server and solar (apache/nginx)"
	echo "      --restart-cache-server Restarts cache server (memcached)"
}

#restart_service_if_not_running nginx

ARGUMENTS=0
BRANCH="master"
DEPLOY=""
BUILD_DEPLOY=""
BUILD=""
MSERVICE=""
STOP=""
START=""
RESTART=""
STATUS=""
TOMCAT_INSTANCE=""
APP_LOG=""
RESTART_APP_SERVERS=""
RESTART_WEB_SERVER=""
RESTART_CACHE_SERVER=""

while [[ $# -gt 0 ]]
do
key="$1"
ARGUMENTS=1

case $key in
		-rb|--repo-branch)
    BRANCH=$2
    ;;

		-b|--build)
		BUILD="build"
    ;;

		-d|--deploy)
		DEPLOY="deploy"

    ;;
		-m|--mservice)
		MSERVICE="mservice"
    ;;

    -bd|--build-deploy)
		BUILD_DEPLOY="build_deploy"
    ;;

		--stop)
	if [ -z "$MSERVICE" ]; then
      STOP="stop"
	  TOMCAT_INSTANCE=$2
    else
	  stop_micro_service
	fi
    ;;

		--start)
	if [ -z "$MSERVICE" ]; then
	  START="start"
      TOMCAT_INSTANCE=$2
    else
	  start_micro_service
	fi
    ;;

		--restart)
		RESTART="restart"
		TOMCAT_INSTANCE=$2
    ;;

		--status)
	if [ -z "$MSERVICE" ]; then
      STATUS="status"
	  TOMCAT_INSTANCE=$2
    else
	  status_of_micro_service
	fi
    ;;

		--applog)
		APP_LOG="applog"
		TOMCAT_INSTANCE=$2
    ;;

    --restart-app-servers)
    RESTART_APP_SERVERS="restart-app-servers"
    ;;

		--restart-web-server)
    RESTART_WEB_SERVER="restart-web-server"
    ;;

    --restart-cache-server)
		RESTART_CACHE_SERVER="restart-cache-server"
		;;

    *)

    ;;
esac
shift
done

if [ ! -z "$BUILD" ]
then
	build_src $BRANCH
fi

if [ ! -z "$DEPLOY" ]
then
	deploy
fi

if [ ! -z "$BUILD_DEPLOY" ]
then
	build_src $BRANCH		
	deploy
fi

if [ ! -z "$START" ]
then
	if [ "$TOMCAT_INSTANCE" == "all" ]
	then
		start_all
	else
		start_tomcat $APP_SERVER_HOME/$TOMCAT_INSTANCE		
	fi
fi

if [ ! -z "$STOP" ]
then
	if [ "$TOMCAT_INSTANCE" == "all" ]
	then
		stop_all
	else
		stop_tomcat $APP_SERVER_HOME/$TOMCAT_INSTANCE		
	fi
fi


if [ ! -z "$RESTART" ]
then
	stop_tomcat $APP_SERVER_HOME/$TOMCAT_INSTANCE
	start_tomcat $APP_SERVER_HOME/$TOMCAT_INSTANCE	
fi

if [ ! -z "$STATUS" ]
then
	if [ -z "$TOMCAT_INSTANCE" ]
	then
		for instance in $(echo $APP_SERVER_INSTANCES | sed "s/,/ /g")
		do
			pid=$(ps axuw | grep "$APP_SERVER_HOME/$instance" | grep -v grep | cut -d ' ' -f 1)
			if [ "${pid}" ]
			then
				echo "$instance is Running"
			else
				echo "$instance is Stopped"
			fi	
		done
	else 
		pid=$(ps axuw | grep "$APP_SERVER_HOME/$TOMCAT_INSTANCE" | grep -v grep | cut -d ' ' -f 1)
		if [ "${pid}" ]
		then
			echo "Running"
		else
			echo "Stopped"
		fi
	fi
fi

if [ ! -z "$APP_LOG" ]
then
	tail -f $APP_SERVER_HOME/$TOMCAT_INSTANCE/$APPLOG		
fi

if [ ! -z "$RESTART_APP_SERVERS" ]
then
	restart_app_servers		
fi

if [ ! -z "$RESTART_WEB_SERVER" ]
then
	restart_web_server		
fi

if [ ! -z "$RESTART_CACHE_SERVER" ]
then
	restart_cache_server
fi


if [[ $ARGUMENTS -eq 1 ]]
then 
	echo ""
else
	usage
fi
	
exit 0
