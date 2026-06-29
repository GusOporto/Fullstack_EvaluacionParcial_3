@echo off

echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 12 segundos a que Eureka se estabilice...
timeout /t 12 /nobreak > nul

echo Iniciando API Gateway...
cd ../gateway
start cmd /k "mvnw spring-boot:run"

echo Esperando 5 segundos...
timeout /t 5 /nobreak > nul

echo Iniciando Microservicio ColaboradoresSucursales...
cd ../ColaboradoresSucursales
start cmd /k "mvnw spring-boot:run"

echo Ecosistema lanzado. 
echo - Dashboard disponible en http://localhost:8761
echo - Gateway / Swagger: http://localhost:8080/swagger-ui/index.html