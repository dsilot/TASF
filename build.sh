#!/bin/bash

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
ls -l
cd /
./mvnw package
cd /ctrlventasapi/
./mvnw package
docker-entrypoint.sh
