        # Use a modern, slim base image for Java 17
        FROM eclipse-temurin:17-jdk-jammy

        # Set a working directory inside the container
        WORKDIR /app

        # Copy the Gradle wrapper files
        COPY gradlew .
        COPY gradle gradle

        # Copy the build configuration file
        COPY build.gradle.kts .
        COPY settings.gradle.kts .

        # Copy the source code
        COPY src src

        # Make the gradlew script executable
        RUN chmod +x ./gradlew

        # Build the application and create the executable JAR.
        # The -x test flag skips running tests during the build.
        RUN ./gradlew build -x test

        # The final stage for a slim image
        # Expose the port your Spring Boot app runs on
        EXPOSE 8080

        # Command to run the application
        ENTRYPOINT ["java", "-jar", "build/libs/DriveBuyBE-0.0.1-SNAPSHOT.jar"]