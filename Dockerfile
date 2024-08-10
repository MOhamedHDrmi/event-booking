FROM maven:3.8.4-openjdk-11-slim AS deps

WORKDIR /opt/app

COPY /event-booking-application/pom.xml /opt/app/event-booking-application/pom.xml
COPY /event-booking-core/pom.xml /opt/app/event-booking-core/pom.xml
COPY /event-booking-adapter-inbound-rest/pom.xml /opt/app/event-booking-adapter-inbound-rest/pom.xml
COPY /event-booking-adapter-outbound-sql/pom.xml /opt/app/event-booking-adapter-outbound-sql/pom.xml

COPY pom.xml .
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

FROM maven:3.8.4-openjdk-11-slim AS builder

WORKDIR /opt/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/app/ /opt/app
COPY event-booking-application/src /opt/app/event-booking-application/src
COPY event-booking-core/src /opt/app/event-booking-core/src
COPY event-booking-adapter-inbound-rest/src /opt/app/event-booking-adapter-inbound-rest/src
COPY event-booking-adapter-outbound-sql/src /opt/app/event-booking-adapter-outbound-sql/src

RUN mvn -B -e clean install -DskipTests=true

FROM openjdk:11-slim
WORKDIR /opt/app
COPY --from=builder /opt/app/event-booking-*/target/*.jar .
EXPOSE 8080
CMD [ "java", "-jar", "/opt/app/event-booking-application-0.0.1-SNAPSHOT.jar" ]