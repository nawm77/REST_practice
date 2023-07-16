FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY target/bike-sharing.jar /app
EXPOSE 8080
CMD ["java", "-jar", "bike-sharing.jar"]