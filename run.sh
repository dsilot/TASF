#!/bin/bash

docker-entrypoint.sh
mariadbd
start(){
echo "ctrlventas start"
cd /ctrlventas/
ls -l
./mvnw spring-boot:run
echo "ctrlventas after start"
}

startapi(){
echo "ctrlventasapi start"
cd /ctrlventasapi/
ls -l
./mvnw spring-boot:run
echo "ctrlventasapi after start"
}

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
start &
startapi &

echo "web y api iniciados"
