# Dockerfile for User Service
# Use Eclipse Temurin JRE 17 as the base image
FROM eclipse-temurin:17-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the target directory to the container
COPY target/*.jar /app/user-service.jar

# Expose port 8081 for the User Service
EXPOSE 8081

# Define the command to run the User Service
CMD ["java", "-jar", "/app/user-service.jar"]