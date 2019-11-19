FROM maven:latest

WORKDIR "/opt/app"

USER root

COPY . .

RUN mvn install -DskipTests

CMD ["mvn", "spring-boot:run"]