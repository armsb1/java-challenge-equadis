# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory for Maven
WORKDIR /app

# Copy the project source code into the container
COPY . .

# Build the project with Maven (skip tests to speed up the build)
RUN mvn clean install -DskipTests

# Stage 2: Run with OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/customer-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8081 (port for customer service)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
