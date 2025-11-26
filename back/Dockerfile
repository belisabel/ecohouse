# Dockerfile para EcoHouse Backend
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# Ejecutar aplicación

ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"
# Variables de entorno por defecto

EXPOSE 9000
# Exponer puerto

COPY --from=build /app/target/*.jar app.jar
# Copiar el JAR compilado

WORKDIR /app
FROM eclipse-temurin:21-jre-alpine
# Etapa de producción

RUN ./mvnw clean package -DskipTests
COPY src ./src
# Copiar código fuente y compilar

RUN ./mvnw dependency:go-offline
# Descargar dependencias

RUN chmod +x ./mvnw
COPY mvnw pom.xml ./
COPY .mvn/ .mvn
# Copiar archivos de Maven

WORKDIR /app
FROM eclipse-temurin:21-jdk-alpine AS build

