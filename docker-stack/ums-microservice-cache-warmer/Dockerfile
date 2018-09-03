FROM java:8
WORKDIR /app
RUN mkdir ums
COPY ums-dist-trunk-SNAPSHOT.zip /app/ums
WORKDIR /app/ums
RUN mkdir ums-dist
RUN unzip ums-dist-trunk-SNAPSHOT.zip -d ./ums-dist
RUN cp -R ums-dist/microservice/*.jar /app/ums
RUN mkdir config
COPY config /app/ums/config
ENV UMS_CONFIG file:/app/ums/config
CMD ["java", "-jar", "/app/ums/microservice-trunk-SNAPSHOT.jar", "cacheWarmerManager"]
