INSERT INTO regiones (nombre) VALUES ('Metropolitana');
INSERT INTO regiones (nombre) VALUES ('Valparaiso');

INSERT INTO comunas (nombre, id_region_fk) VALUES ('Santiago', 1);
INSERT INTO comunas (nombre, id_region_fk) VALUES ('Maipu', 1);
INSERT INTO comunas (nombre, id_region_fk) VALUES ('Viña del Mar', 2);

INSERT INTO sucursales (nombre, direccion, id_comuna_fk) VALUES 
('Sucursal Providencia', 'Av. Providencia 1234', 1),
('Sucursal Maipu', 'Av. Maipu 5678', 2),
('Sucursal Viña del Mar', 'Av. Viña del Mar 91011', 3);

INSERT INTO colaboradores (run, nombres, apellidos, fecha_nacimiento, telefono, correo, direccion, id_sucursal_fk) VALUES 
('123456789', 'Juan Andres', 'Parada Gonzalez', '1990-01-01', '56912345678', 'juan.parada@example.com', 'Av. Americo Vespucio 01021', 1),
('234567891', 'Patricio Ignacio', 'Perez Leiva', '1995-05-05', '56987456123', 'patricio.perez@example.com', 'Av. Libertador 123', 2),
('345678902', 'Sebastian Patricio', 'Hidalgo Ruiz', '1999-02-02', '56923456789', 'sebastian.hidalgo@example.com', 'Av. Las Condes 0102', 3);