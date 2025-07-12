FROM maven:3.9.9-eclipse-temurin-23 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM eclipse-temurin:23-jre-alpine

COPY --from=build /app/target/health-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE="prod"

EXPOSE 8081

CMD ["java","-jar","app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]