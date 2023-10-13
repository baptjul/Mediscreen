FROM openjdk:17-jdk as history-build

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=history-build /app/target/History-0.0.1-SNAPSHOT.jar history.jar

CMD ["java", "-jar", "history.jar"]
