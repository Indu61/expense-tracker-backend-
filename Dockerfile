# FROM openjdk:21-jdk

# WORKDIR /app

# COPY target/expense_tracker-0.0.1-SNAPSHOT.jar app.jar

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "app.jar"]


FROM openjdk:21-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw clean package

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/expense_tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]