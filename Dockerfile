FROM openjdk:8-jdk-alpine
ADD target/recikligi.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]