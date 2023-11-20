FROM adoptopenjdk:11-jre-hotspot
COPY target/phone_demo-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]