FROM eclipse-temurin:17
MAINTAINER intelli.edu
COPY target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "intelliedu-0.0.1-SNAPSHOT.jar"]
