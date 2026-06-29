# Proyecto de Microservicios: RRHH - Microservicio de Colaboradores
Evaluación Parcial 3 - Desarrollo FullStack 1

### 1. Descripción del Contexto
Este proyecto forma parte de una arquitectura distribuida orientada a la gestión de capital humano. Su objetivo principal es administrar la información de los Colaboradores y sus relaciones (con Sucursales, Regiones, Comunas,etc.), asegurando la integridad referencial y aplicando reglas de negocio requeridas. Asi como tambien la interaccion con Laborales y RRHH.



### 2. Integrantes del Equipo y Desarrollo realizado:   

#### GusOporto (Gustavo Oporto):
- Gateway: Servidor de enlace y enrutamiento
- Eureka Server: Servidor de descubrimiento de servicios
- ColaboradoresSucursales: Gestión de personal por sucursal, permitiendo la busqueda de colaboradores ya sea por región, comuna o sucursal, así como tambien por run o id.
- README  

#### 17kjaja (Benjamin Saavedra):

#### kris-binimelis (Krishna Binimelis):

### 4. Rutas Principales del API Gateway
El sistema centraliza el tráfico a través de Spring Cloud Gateway. Las rutas configuradas en el archivo YAML son:   
- GET /api/v1/sucursales/** -> Enrutado a ColaboradoresSucursales
- GET /api/v1/laborales/** -> Enrutado a Laborales
- GET /api/v1/rrhh/** -> Enrutado a RRHH

### 5. Documentación Swagger/OpenAPI
Cada microservicio cuenta con documentación técnica accesible mediante la UI de Swagger. Los endpoints incluyen descripciones, parámetros y códigos de respuesta HTTP. Documentación Local: http://localhost:8080/swagger-ui.html.

### 6. Instrucciones de Ejecución
Ejecución Local
Para arrancar el ecosistema completo de forma local, puedes utilizar el script automatizado incluido en la raíz:
Asegurarse de tener Docker Desktop o MySQL local operativo.
Ejecutar el archivo iniciar-todo.bat.
Alternativamente, compilar y ejecutar cada servicio con Maven: mvn spring-boot:run.

### 7. Pruebas Unitarias y Calidad
Cobertura: El proyecto asegura al menos un 80% de cobertura en las pruebas unitarias de lógica de negocio.
Tecnologías: Implementadas con JUnit 5 y Mockito, siguiendo la estructura Given-When-Then.
Estado: Todas las pruebas pasan exitosamente antes de la entrega.
