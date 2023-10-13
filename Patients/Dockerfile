FROM openjdk:17-jdk as patients-build

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=patients-build /app/target/Patients-0.0.1-SNAPSHOT.jar patients.jar

CMD ["java", "-jar", "patients.jar"]