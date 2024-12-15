FROM openjdk:25-slim


MAINTAINER "Kevin Krüger"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} bsc_registartion.jar
ENTRYPOINT ["java","-jar","/bsc_registartion.jar"]

EXPOSE 8080