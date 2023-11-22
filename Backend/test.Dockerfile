FROM maven:3.9.4-eclipse-temurin-17 as backend_builder
WORKDIR /Backend
COPY Backend/pom.xml .
COPY Backend/src ./src
RUN mvn clean install package -q -Dspring.profiles.active=test

FROM eclipse-temurin:17.0.9_9-jre
WORKDIR /Backend
COPY --from=backend_builder /Backend/target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "intelliedu-0.0.1-SNAPSHOT.jar"]
