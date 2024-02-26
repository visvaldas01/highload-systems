FROM openjdk:17-jdk-slim-buster AS builder
#
#RUN apt-get update -y
#RUN apt-get install -y binutils
#
#WORKDIR /gateway
#
#COPY . .
#
#RUN ./gradlew build -i --stacktrace -x test
#ENTRYPOINT ["java", "-jar", "/gateway/build/libs/auth-0.0.1-SNAPSHOT.jar"]
COPY build/libs/auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]