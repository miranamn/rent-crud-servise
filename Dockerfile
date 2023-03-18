FROM maven:3.5-jdk-11 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn package

FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:1.10

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

COPY --from=build /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /build/target/quarkus-app/*.jar /deployments/
COPY --from=build /build/target/quarkus-app/app/ /deployments/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENTRYPOINT [ "java", "-jar", "/deployments/quarkus-run.jar" ]