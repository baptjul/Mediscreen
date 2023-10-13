FROM openjdk:17-jdk as users-build

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=users-build /app/target/Users-0.0.1-SNAPSHOT.jar users.jar

CMD ["java", "-jar", "users.jar"]
