# Dockerfile para EcoHouse Backend

# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

# Copiar código fuente y compilar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 9000

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
