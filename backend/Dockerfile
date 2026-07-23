# Sử dụng Java 17
FROM eclipse-temurin:17-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar vào container
COPY target/pavu-shop-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]