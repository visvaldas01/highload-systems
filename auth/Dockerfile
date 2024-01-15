FROM openjdk:17-jdk-slim-buster AS builder

RUN apt-get update -y
RUN apt-get install -y binutils

WORKDIR /auth

COPY . .

RUN ./gradlew build -i --stacktrace -x test

ENTRYPOINT ["java", "-jar", "/auth/build/libs/auth-0.0.1-SNAPSHOT.jar"]