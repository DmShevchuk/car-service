FROM openjdk:17-alpine3.14
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/car-service.jar
ADD ${JAR_FILE} car-service.jar
ENTRYPOINT ["java", "-jar", "/car-service.jar"]