#!/bin/bash

start(){
cd /ctrlventas/
./mvnw spring-boot:run
}

startapi(){
cd /ctrlventasapi/
./mvnw spring-boot:run
}

mariadbd
start &
startapi

echo "web y api iniciados"
