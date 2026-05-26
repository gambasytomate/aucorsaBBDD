Create database AUCORSA;
use AUCORSA;

-- Tabla Bus
CREATE TABLE Bus (
    register VARCHAR(10) PRIMARY KEY,
    type VARCHAR(20),
    license VARCHAR(20)
);

-- Tabla Driver
CREATE TABLE Driver (
    numdriver INT PRIMARY KEY,
    name VARCHAR(50),
    surname VARCHAR(50)
);

-- Tabla Place
CREATE TABLE Place (
    idplace INT PRIMARY KEY,
    cp VARCHAR(10),
    city VARCHAR(50),
    site VARCHAR(50)
);
use aucorsa;

select * FROM BDP;
-- Tabla intermedia B-D-P (relación ternaria)
CREATE TABLE BDP (
    register VARCHAR(10),
    numdriver INT,
    idplace INT,
    day_of_week VARCHAR(15),
    PRIMARY KEY (register, numdriver, idplace),
    FOREIGN KEY (register) REFERENCES Bus(register),
    FOREIGN KEY (numdriver) REFERENCES Driver(numdriver),
    FOREIGN KEY (idplace) REFERENCES Place(idplace)
);

use AUCORSA;
ALTER TABLE Driver ADD COLUMN imagen varchar(200);
ALTER TABLE Bus ADD COLUMN imagen varchar(200);
ALTER TABLE BDP ADD column imagen varchar(200);

-- Insertamos buses
INSERT INTO Bus VALUES 
('B001', 'Urbano', 'LIC001'),
('B002', 'Interurbano', 'LIC002'),
('B003', 'Turismo', 'LIC003'),
('B004', 'Escolar', 'LIC004'),
('B005', 'Urbano', 'LIC005'),
('B006', 'Turismo', 'LIC006'),
('B007', 'Interurbano', 'LIC007'),
('B008', 'Urbano', 'LIC008'),
('B009', 'Escolar', 'LIC009'),
('B010', 'Turismo', 'LIC010');

-- Insertamos conductores
INSERT INTO Driver VALUES 
(101, 'Carlos', 'García'),
(102, 'Lucía', 'Pérez'),
(103, 'Manuel', 'Martín'),
(104, 'Laura', 'López'),
(105, 'Javier', 'Sánchez'),
(106, 'Marta', 'Fernández'),
(107, 'David', 'Ruiz'),
(108, 'Ana', 'Díaz'),
(109, 'Pablo', 'Gómez'),
(110, 'Elena', 'Navarro');

-- Insertamos lugares
INSERT INTO Place VALUES 
(1, '14001', 'Córdoba', 'Centro'),
(2, '28013', 'Madrid', 'Sol'),
(3, '41001', 'Sevilla', 'Triana'),
(4, '08001', 'Barcelona', 'Gótico'),
(5, '46001', 'Valencia', 'Carmen'),
(6, '29001', 'Málaga', 'Soho'),
(7, '03001', 'Alicante', 'Explanada'),
(8, '35001', 'Las Palmas', 'Vegueta'),
(9, '07001', 'Palma', 'Catedral'),
(10, '15001', 'A Coruña', 'Marina');

-- Insertamos datos en la relación BDP
INSERT INTO BDP VALUES 
('B001', 101, 1, 'Monday'),
('B002', 102, 2, 'Tuesday'),
('B003', 103, 3, 'Wednesday'),
('B004', 104, 4, 'Thursday'),
('B005', 105, 5, 'Friday'),
('B006', 106, 6, 'Monday'),
('B007', 107, 7, 'Tuesday'),
('B008', 108, 8, 'Wednesday'),
('B009', 109, 9, 'Thursday'),
('B010', 110, 10, 'Friday');