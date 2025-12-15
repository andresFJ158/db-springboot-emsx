# -------------------------
#   1) COMPILAR LA APP
# -------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn -q -e dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto (genera el jar)
RUN mvn clean package -DskipTests


# -------------------------
#   2) IMAGEN FINAL
# -------------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el JAR compilado desde la imagen builder
COPY --from=builder /app/target/*.jar app.jar

# Puerto donde correrá Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]