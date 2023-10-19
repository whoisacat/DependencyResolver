#!/bin/sh
#mkdir /tmp/nexus_explorer

docker-compose -p dependency_explorer -f ./docker-compose.arm64.yml up -d
