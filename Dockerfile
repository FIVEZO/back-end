FROM openjdk:17.0.2-jdk-slim-buster AS builder

WORKDIR /app

COPY build/libs/toogo-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]