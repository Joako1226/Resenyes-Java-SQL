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
    data_ban DATETIME,
    admin BOOLEAN
);

CREATE TABLE contingut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(50) NOT NULL,
    descripcio VARCHAR(255),
    classificacio INT,
    imatge MEDIUMBLOB

);
CREATE TABLE serie (
	idSerie INT PRIMARY KEY,
    capitols INT,
    temporada INT,
    FOREIGN KEY (idSerie) REFERENCES contingut(id)
);
CREATE TABLE videojoc (
	idJoc INT PRIMARY KEY,
    preu DECIMAL (5,2),
    FOREIGN KEY (idJoc) REFERENCES contingut(id)
);
CREATE TABLE pelicula (
	idPelicula INT PRIMARY KEY,
    duracio TIME,
    director VARCHAR(125),
    FOREIGN KEY (idPelicula) REFERENCES contingut(id)
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
    id_contingut INT,
    descripcio VARCHAR(280),
    nota DECIMAL(3,1),
    spoiler BOOLEAN,
    data_resenya DATE,
    PRIMARY KEY (id_usuari, id_contingut),
    FOREIGN KEY (id_usuari) REFERENCES usuari(nom_usuari),
    FOREIGN KEY (id_contingut) REFERENCES contingut(id)
);

INSERT INTO contingut (titol,descripcio,classificacio,imatge) VALUES(
'CLub de la lucha', 'aaaaaaaaaaaaaaaaa', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\fondos\leopard.jpg') );
INSERT INTO genere (nom) VALUES 
('Acció'),
('Drama'),
('Ciència-ficció'),
('Thriller'),
('RPG'),
('Aventura');
INSERT INTO usuari (nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin) VALUES 
("rtrulls", "roger", "1234", "2007-03-29", 0, null, null,true),
("joaquin", "joaquin", "1234", "2006-12-26", 0, null, null,true);

SELECT id, nom FROM genere;
show tables;
Select * from contingut;