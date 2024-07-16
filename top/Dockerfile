# Usa una imagen de base de OpenJDK
FROM openjdk:17

# run
RUN mkdir app


# Copia el archivo JAR de tu aplicación al contenedor
COPY top-0.0.1-SNAPSHOT.jar /app/top-0.0.1-SNAPSHOT.jar

# Establece el directorio de trabajo
WORKDIR /app

# Expone el puerto
# EXPOSE 9080

# Define el comando para ejecutar la aplicación
CMD ["java", "-jar", "top-0.0.1-SNAPSHOT.jar"]
