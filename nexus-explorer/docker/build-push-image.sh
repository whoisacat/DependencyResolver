#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/nexus-explorer:init .
docker push whoisacat/nexus-explorer:init
