FROM openjdk:8u212-jre-alpine
COPY ./target/netty-server-1.0-SNAPSHOT.jar server.jar
#RUN 'touch /server.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","server.jar"]