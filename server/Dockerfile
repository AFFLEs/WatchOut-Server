FROM openjdk:17-jdk-bullseye
WORKDIR /app

COPY server/gradlew .
COPY server/gradlew.bat .
COPY server/build.gradle .
COPY server/settings.gradle .
COPY server/gradle gradle
COPY server/src src

RUN apt-get update && apt-get install -y findutils curl bash

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

CMD ["java", "-jar", "build/libs/watchout-server-0.0.1-SNAPSHOT.jar"]