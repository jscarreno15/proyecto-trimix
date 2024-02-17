FROM adoptopenjdk:11-jdk-hotspot as build

# Directorio de trabajo dentro del contenedor
WORKDIR /app/

# Actualiza e instala solo los paquetes necesarios, quitando git
RUN apt-get update && apt-get install -y findutils maven && rm -rf /var/cache/apt/archives /var/lib/apt/lists/*

# Copia el directorio actual al directorio de trabajo en el contenedor
COPY . /app/backend-personas

# Cambia el directorio de trabajo
WORKDIR /app/backend-personas

# Descarga las dependencias del proyecto
RUN mvn dependency:resolve --fail-never 

# Compila y empaqueta el proyecto sin ejecutar pruebas
RUN mvn clean package

# Cambia el directorio de trabajo al directorio target
WORKDIR /app/backend-personas/target

# Inicia una nueva etapa de construcción con una imagen base más ligera para ejecutar la aplicación
FROM adoptopenjdk:11-jre-hotspot

# Copia el archivo JAR generado en la etapa anterior al directorio de trabajo
COPY --from=build /app/backend-personas/target/backend-personas-0.0.1-SNAPSHOT.jar backend-personas-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que se ejecuta la aplicación Spring Boot
EXPOSE 8091

# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "backend-personas-0.0.1-SNAPSHOT.jar"]

