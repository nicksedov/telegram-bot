FROM gradle:jdk11-alpine AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
RUN gradle build || return 1

FROM openjdk:11.0.11-jre
EXPOSE 8080
ENV ARTIFACT_NAME=sbconn-bot.jar
ENV APP_HOME=/app
RUN mkdir $APP_HOME
COPY --from=BUILD_STAGE /home/gradle/build/libs/*.jar $APP_HOME/$ARTIFACT_NAME
WORKDIR $APP_HOME
ENTRYPOINT ["java", "-jar", "$APP_HOME/$ARTIFACT_NAME"]