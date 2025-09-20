FROM node:23-alpine AS node

WORKDIR /frontend

COPY ./frontend ./
RUN npm install && npm run build

 FROM maven:3-amazoncorretto-21-alpine AS serverbuilder

WORKDIR /server

COPY ./bsc-registration-server ./

COPY --from=node ./frontend/dist/assets ./src/main/resources/static/assets
COPY --from=node ./frontend/dist/BSCSpear.ico ./src/main/resources/static/BSCSpear.ico
COPY --from=node ./frontend/dist/*.html ./src/main/resources/templates/index.html

RUN mvn package

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY ./bsc-registration-server/src/main/resources/bscConf.json ./config/bscConf.json

COPY --from=serverbuilder ./server/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080