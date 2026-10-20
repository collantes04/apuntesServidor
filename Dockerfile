# Fase 1: Compilar la aplicación usando Maven con JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Ejecutar la aplicación con una imagen ligera oficial de JDK 21
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar

# Render requiere obligatoriamente que tu app escuche en el puerto 10000
ENV PORT=10000
EXPOSE 10000

# Comando para arrancar el servidor de Spring Boot mapeando el puerto correcto
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]
