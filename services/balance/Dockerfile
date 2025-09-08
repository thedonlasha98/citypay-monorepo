# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21-alpine

# Set the working directory
WORKDIR /app ./gradlew build

# Copy the application JAR file
COPY services/balance/build/libs/balance-0.0.1.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]