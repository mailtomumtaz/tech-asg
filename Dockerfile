FROM openjdk:8

ADD target/dev-test-app.jar dev-test-app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/dev-test-app.jar"]