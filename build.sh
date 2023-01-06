#!/bin/bash

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
cd /ctrlventas/
tar --extract --file ctrlventa.zip
ls -l
./mvnw package

cd /ctrlventasapi/
tar --extract --file ctrlventasapi.zip
ls -l
./mvnw package

docker-entrypoint.sh
