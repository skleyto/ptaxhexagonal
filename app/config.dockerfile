FROM eclipse-temurin:21-jre
WORKDIR /app
COPY app/build/libs/app-all.jar app.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]