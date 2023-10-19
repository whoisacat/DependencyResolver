#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/dependency-explorer:init .
docker push whoisacat/dependency-explorer:init
