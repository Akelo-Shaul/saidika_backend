FROM maven:3.9.9-eclipse-temurin-21-alpine
COPY  **/target/saidikaV3-0.0.1-SNAPSHOT.jar saidikapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","saidikapp.jar"]

