FROM openjdk:17-jdk as gateway-build

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=gateway-build /app/target/Gateway-0.0.1-SNAPSHOT.jar gateway.jar

CMD ["java", "-jar", "gateway.jar"]
