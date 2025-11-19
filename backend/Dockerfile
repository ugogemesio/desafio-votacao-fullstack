FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app


COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .


RUN ./gradlew dependencies --no-daemon || true


COPY . .


RUN ./gradlew clean build -x test --no-daemon


FROM eclipse-temurin:17-jdk

WORKDIR /app


COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
