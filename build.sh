#!/bin/bash

/usr/local/bin/docker-entrypoint.sh

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
ls -al
./mvnw package

#cd /ctrlventasapi/
#ls -al
#./mvnw package


