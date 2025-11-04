# Dockerfile for Api Gateway Service
# Use Eclipse Temurin JRE 17 as the base image
FROM eclipse-temurin:17-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the target directory to the container
COPY target/*.jar /app/api-gateway.jar

# Expose port 8080 for the Api Gateway service
EXPOSE 8080

# Define the command to run the Api Gateway service
CMD ["java", "-jar", "/app/api-gateway.jar"]