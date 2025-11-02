# 1. Aşama: Maven ile derle
FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

# 2. Aşama: Runtime imajı
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/recipefy-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]