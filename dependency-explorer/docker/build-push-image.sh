#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/dependency-explorer:entrypoint-script .
docker push whoisacat/dependency-explorer:entrypoint-script
