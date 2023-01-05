#!/bin/bash

#mysql -uroot -proot ventas < ventas.sql;

start(){
echo "ctrlventas start"
cd /ctrlventas/
pwd
./mvnw spring-boot:run
echo "ctrlventas after start"
}

startapi(){
echo "ctrlventasapi start"
cd /ctrlventasapi/
pwd
./mvnw spring-boot:run
echo "ctrlventasapi after start"
}

chmod +x /ctrlventas/mvnw
chmod +x /ctrlventasapi/mvnw
start &
startapi &

echo "web y api iniciados"
