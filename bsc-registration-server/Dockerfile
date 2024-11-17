FROM openjdk:23-jdk

MAINTAINER "Kevin Krüger"

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bsc_registartion.jar
ENTRYPOINT ["java","-jar","/bsc_registartion.jar"]

EXPOSE 8080