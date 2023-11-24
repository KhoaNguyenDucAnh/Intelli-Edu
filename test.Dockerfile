FROM winamd64/eclipse-temurin:17
COPY Backend/target/intelliedu-0.0.1-SNAPSHOT.jar intelliedu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "intelliedu.jar"]
