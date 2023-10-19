#!/bin/sh
#mkdir /tmp/nexus_explorer
#echo username:::username > /tmp/nexus_explorer/settings.txt
#echo url:::https://subdomain.domain.zone/repository/department-maven > /tmp/nexus_explorer/settings.txt
#echo password:::password >> /tmp/nexus_explorer/settings.txt
#echo ssl:::com.sun.security.enableAIAcaIssuer=true >> /tmp/nexus_explorer/settings.txt
#echo ssl:::javax.net.ssl.trustStore=<trust store file path> >> /tmp/nexus_explorer/settings.txt
#echo ssl:::javax.net.ssl.trustStorePassword=<trust store pass word> >> /tmp/nexus_explorer/settings.txt

docker-compose -p comments_blocker_by_ip_admin -f ./docker-compose.yml up -d
