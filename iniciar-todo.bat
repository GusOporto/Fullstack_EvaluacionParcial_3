@echo off
cd gateway
start cmd /k "mvnw spring-boot:run"

cd ../ColaboradoresSucursales
start cmd /k "mvnw spring-boot:run"


