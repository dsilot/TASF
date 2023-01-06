#!/bin/bash

/usr/local/bin/docker-entrypoint.sh

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw

cd /ctrlventas/
./mvnw package

cd /ctrlventasapi/
./mvnw package


