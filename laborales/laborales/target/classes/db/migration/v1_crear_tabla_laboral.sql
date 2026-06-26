CREATE TABLE Cargos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (45) NOT NULL,
    descripcion VARCHAR (100) NOT NULL,
    sueldo INT NOT NULL
);

INSERT INTO Cargos (nombre, descripcion, sueldo)
VALUES ('Benjamin Saavdra', 'Encargado Sitio web', 2350000);