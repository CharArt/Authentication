FROM openjdk:18
MAINTAINER charartpav@gmail.com
EXPOSE 8080
COPY target/authentication-0.0.1-SNAPSHOT.jar authentication-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/authentication-0.0.1-SNAPSHOT.jar"]