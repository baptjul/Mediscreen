FROM openjdk:17-jdk as assessment-build

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=assessment-build /app/target/assessment-0.0.1-SNAPSHOT.jar assessment.jar

CMD ["java", "-jar", "assessment.jar"]
