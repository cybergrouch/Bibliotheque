FROM gradle:8.6.0-jdk17 as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build --stacktrace

FROM openjdk
WORKDIR /app
COPY --from=builder /app/build/libs/biblioteque-all.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]