#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/dependency-explorer:entrypoint-script.arm64 .
docker push whoisacat/dependency-explorer:entrypoint-script.arm64
