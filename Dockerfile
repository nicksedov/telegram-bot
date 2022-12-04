FROM gradle:jdk11-alpine AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
RUN gradle build || return 1

FROM openjdk:11.0.11-jre
ENV ARTIFACT_NAME=sbconn-bot.jar
ENV APP_HOME=/tmp/app
COPY --from=BUILD_STAGE /home/gradle/build/libs/$ARTIFACT_NAME $APP_HOME/
WORKDIR $APP_HOME
RUN groupadd -r -g 1000 user && useradd -r -g user -u 1000 user
RUN chown -R user:user $APP_HOME
USER user
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}

#FROM openjdk:11
#VOLUME /tmp
#COPY build/libs/*.jar sbconn-bot.jar
#ENTRYPOINT ["java","-jar","/sbconn-bot.jar"]