FROM openjdk:8-jdk-alpine
MAINTAINER mail@ludeo.net
ADD target/recikligi.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]