FROM maven:3.9.4-eclipse-temurin-17 as builder
WORKDIR /app
COPY Backend/pom.xml .
COPY Backend/src ./src
RUN mvn clean install package -q -Dspring.profiles.active=prod

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "intelliedu-0.0.1-SNAPSHOT.jar"]
