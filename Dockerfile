FROM openjdk:8
MAINTAINER karimux
VOLUME /tmp
COPY target/dpshop-backend-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties
EXPOSE 8200
ENTRYPOINT ["java","-Xms500m","-Xmx1000m","-jar","/app.jar"]