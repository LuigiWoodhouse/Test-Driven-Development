FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /Test-Driven-Development
COPY pom.xml .
COPY src ./src
RUN mvn clean package

 # stage2
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]