FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ChurnGym/pom.xml ./pom.xml
RUN mvn dependency:go-offline -q
COPY ChurnGym/src ./src
RUN mvn clean package -DskipTests --no-transfer-progress

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
