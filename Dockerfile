FROM openjdk:11
VOLUME /tmp
COPY build/libs/*.jar sbconn-bot.jar
ENTRYPOINT ["java","-jar","/sbconn-bot.jar"]