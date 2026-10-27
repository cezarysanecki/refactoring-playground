FROM openjdk:17.0.2-slim
VOLUME /tmp
COPY target/*SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 18080
