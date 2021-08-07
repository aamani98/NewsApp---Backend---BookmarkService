FROM openjdk:11-jdk-slim
ADD target/springbootdockerdeploy-bookmark.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]