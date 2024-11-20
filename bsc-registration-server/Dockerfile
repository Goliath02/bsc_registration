FROM openjdk:23-jdk

MAINTAINER "Kevin Krüger"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bsc_registartion.jar
ENTRYPOINT ["java","-jar","/bsc_registartion.jar"]