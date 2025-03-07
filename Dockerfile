#Build aşaması
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# pom.xml i dependency ler için kopyala
COPY pom.xml .

# dependency leri docker içinde çalıştırıyoruz
RUN mvn dependency:go-offline

# Source code u docker içinde çalıştır
COPY src ./src

# Program run komutu (testleri pas geçtim)
RUN mvn clean package -DskipTests=true -Dmaven.test.skip=true


FROM openjdk:17-jdk-slim

VOLUME /tmp

# jar dosyasını copyala docker bununla çalışıyor
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
