# telegram-bot

## How to build and deploy

* Create runnable jar file with embedded Jetty and all necessary deps:
```
./gradlew bootJar
```

* Smoke test jar file by running
```
java -jar ./build/libs/telegram-bot-1.0.0.jar
```

* Create Docker image for application
```
docker build -t nsedov/sbconn-bot .
```

* Run Docker image
```
docker run -p 8080:8080 nsedov/sbconn-bot
```