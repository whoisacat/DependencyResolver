#!/bin/sh

echo "start entrypoint-script.sh"
cd /files
rm ./gradle.build.log
gradle build --info >> ./gradle.build.log
java -jar /opt/project/app.jar
echo "result is in file dependencies.txt in your volume"