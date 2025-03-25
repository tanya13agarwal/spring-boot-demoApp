# Use Eclipse Temurin JDK 17 as the base image
FROM eclipse-temurin:21-alpine

# Set the working directory inside the container
WORKDIR /springBootApp

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the port that the application runs on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
