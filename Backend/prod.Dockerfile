FROM eclipse-temurin:17
COPY Backend/target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu.jar
EXPOSE 12345
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "intelliedu.jar"]
