#!/bin/sh
mkdir /tmp/dependency_explorer

docker-compose -p comments_blocker_by_ip_admin -f ./docker-compose.yml up -d
