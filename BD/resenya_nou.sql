DROP DATABASE IF EXISTS resenyesBD;
CREATE DATABASE resenyesBD
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
DROP TABLE IF EXISTS contingut;

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

/*INSERT INTO contingut (titol,descripcio,classificacio,imatge) VALUES(
'CLub de la lucha', 'aaaaaaaaaaaaaaaaa', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\fondos\leopard.jpg') );*/
INSERT INTO genere (nom) VALUES 
('Acció'),
('Aventura'),
('Animació'),
('Comèdia'),
('Crim'),
('Documental'),
('Drama'),
('Familiar'),
('Fantasia'),
('Terror'),
('Musical'),
('Misteri'),
('Romanç'),
('Ciència-ficció'),
('Thriller'),
('Bèl·lic'),
('Western'),
('Estratègia'),
('RPG'),
('Plataformes'),
('Shooter'),
('Esports'),
('Simulació'),
('Survival Horror'),
('Sigil'),
('Puzzle'),
('Lluita'),
('Sandbox'),
('Roguelike'),
('Històric');
INSERT INTO contingut (id, titol, descripcio, classificacio, imatge) VALUES 
(1, 'Interstellar', 'Viatge espacial a través d un forat de cuc', 7, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\interestellar.jpg') ),
(2, 'The Godfather', 'Crònica de la família Corleone', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\godfather.jpg') ),
(3, 'Spirited Away', 'Una nena s endinsa en un món màgic', 0, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Spirited Away.jpg') ),
(4, 'The Dark Knight', 'Batman s enfronta al Joker a Gotham', 13, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\The Dark Knight.jpg') ),
(5, 'Parasite', 'Dues famílies de classes diferents s entrellacen', 16, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Parasite.jpg') ),
(6, 'Breaking Bad', 'Professor de química es torna narcotraficant', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Breaking Bad.jpg') ),
(7, 'Stranger Things', 'Nens s enfronten a misteris sobrenaturals', 13, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Stranger Things.jpg') ),
(8, 'The Crown', 'Vida i regnat de la reina Isabel II', 7, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\The Crown.png') ),
(9, 'Black Mirror', 'Antologia sobre el costat fosc de la tecnologia', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Black Mirror.jpg') ),
(10, 'Succession', 'Lluita pel control d un imperi mediàtic', 16, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Succession.jpg') ),
(11, 'The Legend of Zelda: Tears of the Kingdom', 'Aventura èpica al regne d Hyrule', 12, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\The Legend of Zelda.jpeg') ),
(12, 'God of War Ragnarok', 'Kratos i Atreus s enfronten al destí nòrdic', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\God of War Ragnarok.jpg') ),
(13, 'Cyberpunk 2077', 'RPG en una metròpoli futurista corrupta', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Cyberpunk 2077.jpg') ),
(14, 'Minecraft', 'Món obert de construcció i supervivència', 7, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Minecraft.jpg') ),
(15, 'Red Dead Redemption 2', 'Vida de fugitius al salvatge oest', 18, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Red Dead Redemption 2.jpeg') ),
(16, 'Inception', 'Lladres de somnis en una missió impossible', 13, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Inception.jpg') ),
(17, 'Toy Story', 'La vida secreta de les joguines', 0, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Toy Story.jpg') ),
(18, 'Better Call Saul', 'L evolució de l advocat Saul Goodman', 16, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Better Call Saul.jpeg') ),
(19, 'Hollow Knight', 'Aventura metroidvania en un regne d insectes', 7, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Hollow Knight.jpg') ),
(20, 'Forrest Gump', 'La història dels EUA a través d un home', 7, load_file('C:\Users\Rger Trulls\Desktop\Fotos\Cole\Programacio\Forrest Gump.jpg') );
INSERT INTO pelicula (idPelicula, duracio, director) VALUES 
(1, '02:49:00', 'Christopher Nolan'),
(2, '02:55:00', 'Francis Ford Coppola'),
(3, '02:05:00', 'Hayao Miyazaki'),
(4, '02:32:00', 'Christopher Nolan'),
(5, '02:12:00', 'Bong Joon-ho'),
(16, '02:28:00', 'Christopher Nolan'),
(17, '01:21:00', 'John Lasseter'),
(20, '02:22:00', 'Robert Zemeckis');
INSERT INTO serie (idSerie, capitols, temporada) VALUES 
(6, 62, 5),
(7, 34, 4),
(8, 60, 6),
(9, 27, 6),
(10, 39, 4),
(18, 63, 6);
INSERT INTO videojoc (idJoc, preu) VALUES 
(11, 69.99),
(12, 79.99),
(13, 59.99),
(14, 29.95),
(15, 59.99),
(19, 14.99);
INSERT INTO genere_contingut (idGenere, idContingut) VALUES 
(14, 1), (7, 1),   -- Interstellar: Ciència-ficció, Drama
(5, 2), (7, 2),    -- The Godfather: Crim, Drama
(3, 3), (9, 3),    -- Spirited Away: Animació, Fantasia
(1, 4), (15, 4),   -- The Dark Knight: Acció, Thriller
(15, 5), (7, 5),   -- Parasite: Thriller, Drama
(5, 6), (15, 6),   -- Breaking Bad: Crim, Thriller
(14, 7), (10, 7),  -- Stranger Things: Ciència-ficció, Terror
(7, 8), (30, 8),   -- The Crown: Drama, Històric
(14, 9), (15, 9),  -- Black Mirror: Ciència-ficció, Thriller
(7, 10), (4, 10),  -- Succession: Drama, Comèdia
(2, 11), (19, 11), -- Zelda: Aventura, RPG
(1, 12), (2, 12),  -- God of War: Acció, Aventura
(19, 13), (14, 13),-- Cyberpunk: RPG, Ciència-ficció
(28, 14), (2, 14), -- Minecraft: Sandbox, Aventura
(17, 15), (2, 15), -- RDR2: Western, Aventura
(14, 16), (1, 16), -- Inception: Ciència-ficció, Acció
(3, 17), (4, 17),  -- Toy Story: Animació, Comèdia
(5, 18), (7, 18),  -- Better Call Saul: Crim, Drama
(19, 19), (20, 19),-- Hollow Knight: RPG, Plataformes
(7, 20), (13, 20); -- Forrest Gump: Drama, Romanç
INSERT INTO usuari (nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin) VALUES 
("rtrulls", "roger", "1234", "2007-03-29", 0, null, null,true),
("joaquin", "joaquin", "1234", "2006-12-26", 0, null, null,true);

SELECT id, nom FROM genere;
show tables;
Select * from usuari;