# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local build into the container
COPY target/*.jar app.jar

# Expose port 8080 (or the one your Spring Boot app uses)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-XX:+UseG1GC", "-XX:+PrintFlagsFinal", "-jar", "app.jar"]
