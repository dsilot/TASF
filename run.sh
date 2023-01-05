#!/bin/bash

#mysql -uroot -proot ventas < ventas.sql;
startdb(){
mariadb
}

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
startdb &
start &
//startapi &

echo "web y api iniciados"
