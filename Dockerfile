FROM openjdk:11
MAINTAINER shazam.org
COPY build/libs/application.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
