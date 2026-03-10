FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradle gradle
COPY gradlew build.gradle.kts settings.gradle.kts google-style.xml ./
RUN ./gradlew --no-daemon dependencies || true
COPY src src
RUN ./gradlew --no-daemon bootJar -x test -x asciidoctor

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
