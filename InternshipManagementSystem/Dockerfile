# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Java source code
COPY InternshipManagementSystem.java .

# Run the compiler & program when container starts
CMD ["sh", "-c", "javac InternshipManagementSystem.java && java InternshipManagementSystem."]
