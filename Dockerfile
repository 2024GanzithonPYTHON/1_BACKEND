# Dockerfile
# Step 1: Use a base image with OpenJDK 17
FROM openjdk:17-jdk-alpine

# Step 2: Set working directory in the container
WORKDIR /app

# Step 3: Copy the JAR file into the container
COPY build/libs/go-farming.jar app.jar

# Step 4: Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
