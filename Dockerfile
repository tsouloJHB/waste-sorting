# Use an official Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an OpenJDK runtime image to run the application
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file from the build stage
COPY --from=build /app/target/waste-sorting-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
