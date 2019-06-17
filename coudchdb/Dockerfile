FROM couchdb:latest

RUN apt update

RUN mkdir /root/database
RUN chmod 777 /root/database

COPY local.ini /opt/couchdb/local.ini