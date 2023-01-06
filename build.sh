#!/bin/bash

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
ls -l
./mvnw package

cd /ctrlventasapi/
ls -l
./mvnw package

docker-entrypoint.sh
