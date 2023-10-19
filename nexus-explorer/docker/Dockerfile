FROM openjdk:17.0.1-jdk-slim as nexus_explorer_app

RUN apt-get --yes update
RUN apt-get --yes install git

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

RUN mkdir /files
VOLUME /files

#ARG JAR_FILE=$PROJECT_DIR/commentsBlockerAdmin/comments-blocker-admin/target/*.jar
COPY ./build/libs/*.jar $PROJECT_DIR/app.jar

ENTRYPOINT ["java", "-jar", "/opt/project/app.jar"]
