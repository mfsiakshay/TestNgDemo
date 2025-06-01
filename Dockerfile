# Dockerfile
FROM maven:3.9.9-eclipse-temurin-11

WORKDIR /testNg
COPY . /testNg

RUN mvn clean compile

CMD ["mvn", "test", "-Dremote=true", "-Dbrowser=chrome"]
