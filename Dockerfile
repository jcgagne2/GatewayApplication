# Utiliser une image Java officielle
FROM eclipse-temurin:21-jdk

# Ajouter le JAR dans l'image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Créer un dossier pour le javaagent jmx-exporter
#RUN mkdir -p /opt/jmx-exporter
# Copier le jar du javaagent JMX Prometheus
#COPY k8s/lib/jmx_prometheus_javaagent-1.4.0.jar /opt/jmx-exporter/jmx_prometheus_javaagent.jar
#COPY k8s/lib/config.yaml /opt/jmx-exporter/config.yaml

# Copier le jar du javaagent OpenTelemetry
#COPY ressource/otel/opentelemetry-javaagent.jar /opt/otlp/opentelemetry-javaagent.jar

# tomcat
EXPOSE 8080


# prometheus / jmx-exporter
#EXPOSE 9404

# Définir la commande de lancement
ENTRYPOINT ["java", \
            "-Dreactor.netty.http.server.accessLogEnabled=true", \
            "-jar", \
            "/app.jar"]

