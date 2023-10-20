FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/highload-systems-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} highload-systems-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "highload-systems-0.0.1-SNAPSHOT.jar"]