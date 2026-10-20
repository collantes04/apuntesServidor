# Fase 1: Compilar la aplicación usando Maven
FROM maven:3.8.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Ejecutar la aplicación con una imagen ligera de Java
FROM openjdk:21-jdk-slim
COPY --from=build /target/*.jar app.jar

# Render requiere obligatoriamente que tu app escuche en el puerto 10000
ENV PORT=10000
EXPOSE 10000

# Comando para arrancar tu servidor de Spring Boot pasando el puerto dinámico
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]