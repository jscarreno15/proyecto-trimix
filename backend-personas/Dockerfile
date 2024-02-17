FROM adoptopenjdk:17-jdk-hotspot as build

# Directorio de trabajo dentro del contenedor
WORKDIR /app/

ARG HTTP_PROXY=http://proxy.cab.cnea.gov.ar:3128
ARG HTTPS_PROXY=http://proxy.cab.cnea.gov.ar:3128
ARG http_proxy=http://proxy.cab.cnea.gov.ar:3128
ARG https_proxy=http://proxy.cab.cnea.gov.ar:3128
ARG no_proxy=gitlabcarem.cnea.gob.ar,carem-desarrollo
ARG NO_PROXY=gitlabcarem.cnea.gob.ar,carem-desarrollo


RUN apt-get update && apt-get install -y git findutils maven && rm -rf /var/cache/apt/archives /var/lib/apt/lists/*

RUN git clone https://carem-desarrollo:glpat-nnxppx3zc8NwiFKuZvCC@gitlabcarem.cnea.gob.ar/cnea/traful-refactor/backend-almacenes

WORKDIR /app/backend-almacenes

RUN mkdir -p /root/.m2/

# Copiar el archivo settings.xml
RUN cp /app/backend-almacenes/settings.xml /root/.m2/

# Copiar el archivo settings-security.xml
RUN cp /app/backend-almacenes/settings-security.xml /root/.m2/

# Descarga las dependencias del proyecto
RUN mvn dependency:resolve --fail-never 


# Compila y empaqueta el proyecto
RUN mvn clean package -DskipTests 


WORKDIR /app/backend-almacenes/target


# Utiliza una imagen base más ligera con Java 11 para ejecutar la aplicación
FROM adoptopenjdk:11-jre-hotspot


# Copia el archivo JAR generado en la etapa anterior
COPY --from=build /app/backend-almacenes/target/backend-almacenes-0.0.1-SNAPSHOT.jar backend-almacenes-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que se ejecuta la aplicación Spring Boot
EXPOSE 8091

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "backend-almacenes-0.0.1-SNAPSHOT.jar"]
