# Dockerfile
FROM maven:3.9.9-eclipse-temurin-11

WORKDIR /testNg
COPY . /testNg

RUN mvn clean compile

CMD ["sh", "-c", "mvn test -Dremote=true -Dbrowser=chrome -DsuiteXmlFile=$suiteXmlFile"]
