FROM tomcat:7.0.90-jre8-alpine
WORKDIR /app
RUN mkdir ums
COPY ums-dist-trunk-SNAPSHOT.zip /app/ums
WORKDIR /app/ums
RUN mkdir ums-dist
RUN unzip ums-dist-trunk-SNAPSHOT.zip -d ./ums-dist
RUN cp -R ums-dist/lib/*.jar /usr/local/tomcat/lib
RUN cp -R ums-dist/webapps/ums-webservice-account.war /usr/local/tomcat/webapps
RUN rm -rf /usr/local/tomcat/webapps/examples
RUN rm -rf /usr/local/tomcat/webapps/docs
RUN rm -rf /usr/local/tomcat/webapps/manager
RUN rm -rf /usr/local/tomcat/webapps/host-manager
RUN rm -rf /usr/local/tomcat/webapps/ROOT
RUN mkdir config
COPY config /app/ums/config
ENV UMS_CONFIG file:/app/ums/config
