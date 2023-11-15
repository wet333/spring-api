FROM openjdk:20

WORKDIR /usr/src/app

COPY target/spring-api-0.0.1-SNAPSHOT.jar /usr/src/app/spring-api.jar

EXPOSE 8080

CMD ["java", "-jar", "spring-api.jar"]