#!/bin/bash

docker-entrypoint.sh
chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
#tar xvf ctrlventas.tar
ls -al
./mvnw package

cd /ctrlventasapi/
#tar xvf ctrlventasapi.tar
ls -al
./mvnw package


