# ============================
# 1) STAGE DE BUILD (Gradle)
# ============================
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copia arquivos Gradle primeiro (para melhor cache)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Baixa dependências (cache otimizado)
RUN ./gradlew dependencies --no-daemon || true

# Copia o restante do projeto
COPY . .

# Compila e cria o jar
RUN ./gradlew clean build -x test --no-daemon

# ============================
# 2) STAGE DE RUNTIME
# ============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o JAR gerado do stage anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
