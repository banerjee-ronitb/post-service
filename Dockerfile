FROM openjdk:11
ADD target/post-service-0.0.1-SNAPSHOT.jar post-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "post-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080