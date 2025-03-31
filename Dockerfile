FROM node:21-alpine AS node

WORKDIR /frontend

COPY ./frontend .
RUN npm install && npm run build

FROM maven:3-amazoncorretto-21-alpine AS serverBuilder

WORKDIR /server

COPY ./bsc-registration-server .

COPY --from=node ./frontend/dist/assets ./src/main/resources/static
COPY --from=node ./frontend/dist/BSCSpear.ico ./src/main/resources/static
COPY --from=node ./frontend/dist/index.html ./src/main/resources/templates/index.html

RUN mvn package

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=serverBuilder ./server/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080