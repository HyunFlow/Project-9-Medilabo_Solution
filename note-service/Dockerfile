FROM maven:3.9.6-eclipse-temurin-21 AS build
LABEL authors="Yonghyun LEE"
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-alpine
EXPOSE 8083
WORKDIR /app

RUN apk add --no-cache curl
COPY --from=build /app/target/note-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
