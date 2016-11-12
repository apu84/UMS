#!/bin/bash

# description: IUMS deployment script 

# processname: tomcat

# chkconfig: 234 20 80
set -e

source "/opt/config/iums.conf"

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
	
	mvn clean install
	
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
		echo "Starting up $instance"
		let "port=port+1"
		start_tomcat $instance $port
done

yes | cp -R $UMS_SRC/ums-webapps/ums-web/target/ums-web-$PROJECT_VERSION/* /opt/ums-web

}

restart_web_server() {
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
		let "port=port+1"
		echo "Stopping remote debug"
		fuser -k $port/tcp
	done
}

stop_tomcat() {
	sh $1/bin/shutdown.sh
	if [ "$ENABLE_REMOTE_DEBUGGING" == "true" ]
	then
		stop_remote_debug
  fi
}

start_tomcat() {
	if [ "$ENABLE_REMOTE_DEBUGGING" == "true" ]
	then
		let "remote_port=$REMOTE_DEBUG_PORT+$2"
		export CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,address=$remote_port,server=y,suspend=n"
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
	echo "-bd | --build-deploy Build distribution and deploy in application and web server"
	echo "      --stop Stops a particular tomcat instance"
	echo "      --start Start a particular tomcat instance"
	echo "      --restart Restart a particular tomcat instance"
	echo "      --status Status a particular tomcat instance"
	echo "      --applog Show applicaiton logs of a particular tomcat instance"
	echo "      --restart-app-servers Restarts all tomcat instances"
	echo "      --restart-web-server Restarts web server (apache/nginx)"
	echo "      --restart-cache-server Restarts cache server (memcached)"
}

#restart_service_if_not_running nginx	

ARGUMENTS=0
BRANCH="master"
DEPLOY=""
BUILD_DEPLOY=""
BUILD=""
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

    -bd|--build-deploy)
		BUILD_DEPLOY="build_deploy"
    ;;
		
		--stop)
		STOP="stop"
		TOMCAT_INSTANCE=$2
    ;;
		
		--start)
		START="start"
		TOMCAT_INSTANCE=$2
    ;;
		
		--restart)
		RESTART="restart"
		TOMCAT_INSTANCE=$2
    ;;
		
		--status)
		STATUS="status"
		TOMCAT_INSTANCE=$2
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
