#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/nexus-explorer:init.arm64 .
docker push whoisacat/nexus-explorer:init.arm64
