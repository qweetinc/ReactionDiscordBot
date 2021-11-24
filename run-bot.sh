#! /bin/bash

docker-compose run --rm -e JAVA_HOME=/root/.sdkman/candidates/java/current base ./gradlew --no-daemon run

