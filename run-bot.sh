#! /bin/bash

docker-compose run -e JAVA_HOME=/root/.sdkman/candidates/java/current base ./gradlew run

