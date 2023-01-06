#!/bin/bash

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
tar xvf ctrlventas.tar
ls -l
./mvnw package

cd /ctrlventasapi/
tar xvf ctrlventasapi.tar
ls -l
./mvnw package

docker-entrypoint.sh
