CREATE TABLE regiones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL
);

CREATE TABLE comunas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL
    id_region_fk BIGINT,
    CONSTRAINT fk_comuna_region FOREIGN KEY (id_region_fk) 
        REFERENCES regiones(id) ON DELETE SET NULL
);

CREATE TABLE sucursales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    id_comuna_fk BIGINT,
    CONSTRAINT fk_sucursal_comuna FOREIGN KEY (id_comuna_fk) 
        REFERENCES comunas(id) ON DELETE SET NULL
);

CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(9) NOT NULL,
    nombres VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(11) NOT NULL,
    correo VARCHAR(30) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    id_sucursal_fk BIGINT,
    CONSTRAINT fk_colaborador_sucursal FOREIGN KEY (id_sucursal_fk) 
        REFERENCES sucursales(id) ON DELETE SET NULL
);


INSERT INTO regiones (nombre) VALUES 
('Metropolitana'),
('Valparaiso');

INSERT INTO comunas (nombre, id_region_fk) VALUES 
('Santiago', 1),
('Maipu', 1),
('Viña del Mar', 2);

INSERT INTO sucursales (nombre, direccion, id_comuna_fk) VALUES 
('Sucursal Providencia', 'Av. Providencia 1234', 1),
('Sucursal Maipu', 'Av. Maipu 5678', 2),
('Sucursal Viña del Mar', 'Av. Viña del Mar 91011', 3);

INSERT INTO colaboradores (run, nombres, apellidos, fecha_nacimiento, telefono, correo, direccion, id_sucursal_fk) VALUES 
('123456789', 'Juan Andres', 'Parada Gonzalez', '1990-01-01', '56912345678', 'juan.parada@example.com', 'Av. Americo Vespucio 01021', 1);
('234567891', 'Patricio Ignacio', 'Perez Leiva', '1995-05-05', '56987456123', 'patricio.perez@example.com', 'Av. Libertador 123', 2);
('345678902', 'Sebastian Patricio', 'Hidalgo Ruiz', '1999-02-02', '56923456789', 'sebastian.hidalgo@example.com', 'Av. Las Condes 0102', 3);