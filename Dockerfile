FROM maven:3.9.9-eclipse-temurin-21-alpine
COPY **/target/saidikaV3-0.0.1-SNAPSHOT.jar saidikaapp.jar
ENTRYPOINT ["java","-jar","/saidikaapp.jar"]
EXPOSE 8080
