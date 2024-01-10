FROM openjdk:17-jdk-slim-buster AS builder

RUN apt-get update -y
RUN apt-get install -y binutils

WORKDIR /eureka-server

COPY . .

RUN ./gradlew build -i --stacktrace -x test

ENTRYPOINT ["java", "-jar", "/eureka-server/build/libs/eureka-server-0.0.1-SNAPSHOT.jar"]