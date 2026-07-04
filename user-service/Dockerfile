# --- Stage 1: Build the application ---
FROM gradle:8.6-jdk17 AS build
WORKDIR /app

# Copy Gradle configuration files to cache dependencies
COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./
RUN ./gradlew build -x test --no-daemon || true

# Copy source code and build the final fat JAR
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# --- Stage 2: Create the runtime image ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a secure non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built bootJar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose default port
EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]
