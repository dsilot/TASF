#!/bin/bash

#mysql -uroot -proot ventas < ventas.sql;

start(){
cd /ctrlventas
echo "ctrlventas"
./mvnw spring-boot:run
}

startapi(){
cd /ctrlventasapi
echo "ctrlventasapi"
./mvnw spring-boot:run
}

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
start &
startapi &

echo "web y api iniciados"
