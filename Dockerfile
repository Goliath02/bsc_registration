FROM node:21-alpine AS node

WORKDIR /frontend

COPY ./frontend .
RUN npm install && npm run build

FROM maven:3-amazoncorretto-21-alpine AS serverBuilder

WORKDIR /server

COPY ./bsc-registration-server .

COPY --from=node ./frontend/dist/assets ./bsc-registration-server/src/main/resources/static
COPY --from=node ./frontend/dist/BSCSpear.ico ./bsc-registration-server/src/main/resources/static
COPY --from=node ./frontend/index.html ./bsc-registration-server/src/main/resources/templates

RUN cd /server && mvn package

FROM amazoncorretto:21-alpine

WORKDIR /app

MAINTAINER "Kevin Krüger"

COPY --from=serverBuilder ./server/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080