# Using Java 25 as the base image
FROM eclipse-temurin:25-jdk-jammy

# Setting the working directory
WORKDIR /app

# Copying project files
COPY pom.xml .
COPY src ./src

# Installing Maven
RUN apt-get update && apt-get install -y maven curl

# Building the project
RUN mvn clean package -DskipTests

# Download wait-for-it.sh
RUN curl -o wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Exposing the Spring Boot port
EXPOSE 8080

# Running the application and waiting for Redis
CMD ["./wait-for-it.sh", "redis:6379", "--timeout=30", "--strict", "--", "java", "-jar", "target/tarot-online-0.0.1-SNAPSHOT.jar"]