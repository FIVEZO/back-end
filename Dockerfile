FROM openjdk:17.0.2-jdk-slim-buster AS builder

WORKDIR /app

COPY build/libs/toogo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]