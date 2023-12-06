# stage1
# sets first stage and use maven version3.8.4 with java jdk17
FROM maven:3.8.4-openjdk-17 AS build

# set working direcotry inside container
WORKDIR /Test-Driven-Development

#copies the pom file and put into /PetAniTopia_Backend inside of the container
COPY pom.xml .

#copies the src directory and put into /PetAniTopia_Backend inside of the container
COPY src ./src

# runs and builds the artifacts of the app in the target folder and create jar
RUN mvn clean package

# stage2
# this sets up the second stage where we use Eclipse Temurin  image with jdk17 as base
FROM eclipse-temurin:17-jdk-alpine

# create a volume that persists data between container runs
VOLUME /tmp

# copies the jar file built in the first stage from target folder to the root and renames it
COPY --from=build /Test-Driven-Development/target/*.jar /Test-Driven-Development-0.0.1-SNAPSHOT.jar

# container will listen on port 79 at runtime. this will not publish the port
EXPOSE 8080

# this sets the default command to run when the container starts.
# it specifies to execute the JAR file using the Java runtime
ENTRYPOINT ["java","-jar","/Test-Driven-Development-0.0.1-SNAPSHOT.jar"]