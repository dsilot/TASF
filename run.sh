#!/bin/bash
start(){
cd ctrlventas
./mvnw spring-boot:run
}

startapi(){
cd ctrlventas
./mvnw spring-boot:run
}
start &
startapi &

echo "web y api iniciados"
