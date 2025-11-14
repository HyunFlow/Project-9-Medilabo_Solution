FROM maven:3.9.6-eclipse-temurin-21 AS build
LABEL authors="Yonghyun LEE"
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine
WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /app/target/patient-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8090
