FROM tomcat:9

MAINTAINER Alexey Buzevich

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/ChessGame-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY ./src/main/docker/wait-for-it.sh /wait-for-it.sh

ENV JPDA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
ENV JPDA_ADDRESS=*:8000
ENV JPDA_TRANSPORT="dt_socket"

RUN chmod +x /wait-for-it.sh