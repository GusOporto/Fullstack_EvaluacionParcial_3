CREATE TABLE regiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL
);

CREATE TABLE comunas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    id_region_fk INT,
    CONSTRAINT fk_comuna_region FOREIGN KEY (id_region_fk) 
        REFERENCES regiones(id) ON DELETE SET NULL
);

CREATE TABLE sucursales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    id_comuna_fk INT,
    CONSTRAINT fk_sucursal_comuna FOREIGN KEY (id_comuna_fk) 
        REFERENCES comunas(id) ON DELETE SET NULL
);

CREATE TABLE colaboradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(9) NOT NULL,
    nombres VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(11) NOT NULL,
    correo VARCHAR(30) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    id_sucursal_fk INT,
    CONSTRAINT fk_colaborador_sucursal FOREIGN KEY (id_sucursal_fk) 
        REFERENCES sucursales(id) ON DELETE SET NULL
);

