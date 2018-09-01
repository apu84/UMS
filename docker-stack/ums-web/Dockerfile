FROM ubuntu:14.04 as build
WORKDIR /app
RUN mkdir ums
COPY ums-web-trunk-SNAPSHOT.war /app/ums
COPY ums-bank-web-trunk-SNAPSHOT.war /app/ums
COPY ums-registrar-web-trunk-SNAPSHOT.war /app/ums
COPY ums-library-web-trunk-SNAPSHOT.war /app/ums
COPY ums-account-web-trunk-SNAPSHOT.war /app/ums
WORKDIR /app/ums
RUN apt-get -yqq update
RUN apt-get -yqq install zip unzip
RUN unzip ums-web-trunk-SNAPSHOT.war -d ./ums-web
RUN unzip ums-bank-web-trunk-SNAPSHOT.war -d ./ums-bank-web
RUN unzip ums-registrar-web-trunk-SNAPSHOT.war -d ./ums-registrar-web
RUN unzip ums-library-web-trunk-SNAPSHOT.war -d ./ums-library-web
RUN unzip ums-account-web-trunk-SNAPSHOT.war -d ./ums-account-web

FROM nginx
COPY --from=build /app/ums/ums-web /app/ums/ums-web
COPY --from=build /app/ums/ums-bank-web /app/ums/ums-bank-web
COPY --from=build /app/ums/ums-registrar-web /app/ums/ums-registrar-web
COPY --from=build /app/ums/ums-library-web /app/ums/ums-library-web
COPY --from=build /app/ums/ums-account-web /app/ums/ums-account-web
COPY ums-nginx.conf /etc/nginx/conf.d
COPY wrapper.sh /
CMD ["./wrapper.sh"]

