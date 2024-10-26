FROM openjdk:17
EXPOSE 8087
ADD target/sprinprojet-2.0.0.jar sprinprojet-2.0.0.jar
ENTRYPOINT ["java","-jar","/sprinprojet-2.0.0.jar"]
