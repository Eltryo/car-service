#
# Build stage
#
FROM openjdk:11 AS build
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM openjdk:11
ARG JAR_FILE=/app/target/*.jar
COPY --from=build $JAR_FILE /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
