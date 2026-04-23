DROP DATABASE IF EXISTS resenyesBD;
CREATE DATABASE resenyesBD
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE resenyesBD;

CREATE TABLE genere (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(20)
);


CREATE TABLE usuari (
    nom_usuari VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(20),
    contrasenya VARCHAR(255),
    data_naixement DATE,
    punts INT,
    estat ENUM('active', 'warned', 'soft_ban', 'hard_ban'),
    data_ban DATETIME
);

CREATE TABLE contingut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(50) NOT NULL,
    descripcio VARCHAR(255),
    duracio TIME,
    tipus ENUM('Serie', 'Pelicula', 'Videojoc'),
    classificacio INT

);
CREATE TABLE genere_contingut (
	idGenere INT,
    idContingut INT,
    primary key (idGenere,idContingut),
    foreign key (idGenere) references genere(id),
    foreign key(idContingut) references contingut(id)
);

CREATE TABLE resenya (
    id_usuari VARCHAR(20),
    id_pelicula INT,
    descripcio VARCHAR(280),
    nota DECIMAL(3,1),
    spoiler BOOLEAN,
    PRIMARY KEY (id_usuari, id_pelicula),
    FOREIGN KEY (id_usuari) REFERENCES usuari(nom_usuari),
    FOREIGN KEY (id_pelicula) REFERENCES contingut(id)
);

INSERT INTO genere (nom) VALUES 
('Acció'),
('Drama'),
('Ciència-ficció'),
('Thriller'),
('RPG'),
('Aventura');

SELECT id, nom FROM genere;
show tables;