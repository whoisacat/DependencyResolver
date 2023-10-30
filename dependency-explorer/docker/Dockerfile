FROM gradle:7.6.3-jdk17-jammy as dependency_explorer_app

RUN apt-get --yes update
RUN apt-get --yes install git

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

RUN mkdir /files
VOLUME /files

COPY ./build/libs/*.jar $PROJECT_DIR/app.jar
COPY ./docker/entrypoint-script.sh $PROJECT_DIR/script.sh

ENTRYPOINT ["/opt/project/script.sh"]
