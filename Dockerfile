FROM maven:3.9.9-eclipse-temurin-21-alpine
COPY  **/target/*.jar saidikapp.jar
ENTRYPOINT ["java","-jar","saidikapp.jar"]
EXPOSE 8080
