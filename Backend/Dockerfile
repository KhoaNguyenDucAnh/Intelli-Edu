FROM eclipse-temurin:17
MAINTAINER intelli.edu
COPY target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENV frontend_dir / 
ENTRYPOINT ["sh", "-c", "java","-jar", "-Dspring.profiles.active=prod", "-Dspring.web.resources.static-locations=file:${frontend_dir}", "intelliedu-0.0.1-SNAPSHOT.jar"]
