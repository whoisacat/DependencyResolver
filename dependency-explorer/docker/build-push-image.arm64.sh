#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/dependency-explorer:init.arm64 .
docker push whoisacat/dependency-explorer:init.arm64
