# ---------------- Stage 1: Build the Application ----------------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy the rest of the source code.
COPY src ./src

# Build the application (you can skip tests if desired)
RUN mvn clean package -DskipTests=true -Dmaven.test.skip=true

# ---------------- Stage 2: Create the Final Image ----------------
FROM openjdk:17-jdk-slim

VOLUME /tmp

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
