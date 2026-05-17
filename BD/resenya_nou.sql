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
(1, 'Interstellar', 'Viatge espacial a través d un forat de cuc', 7, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/interestellar.jpg')),
(2, 'The Godfather', 'Crònica de la família Corleone', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/godfather.jpg')),
(3, 'Spirited Away', 'Una nena s endinsa en un món màgic', 0, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/spirited_away.jpg')),
(4, 'The Dark Knight', 'Batman s enfronta al Joker a Gotham', 13, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/the_dark_knight.jpg')),
(5, 'Parasite', 'Dues famílies de classes diferents s entrellacen', 16, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/parasite.jpg')),
(6, 'Breaking Bad', 'Professor de química es torna narcotraficant', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/breaking_bad.jpg')),
(7, 'Stranger Things', 'Nens s enfronten a misteris sobrenaturals', 13, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/stranger_things.jpg')),
(8, 'The Crown', 'Vida i regnat de la reina Isabel II', 7, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/the_crown.png')),
(9, 'Black Mirror', 'Antologia sobre el costat fosc de la tecnologia', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/black_mirror.jpg')),
(10, 'Succession', 'Lluita pel control d un imperi mediàtic', 16, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/succession.jpg')),
(11, 'The Legend of Zelda: Tears of the Kingdom', 'Aventura èpica al regne d Hyrule', 12, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/zelda.jpg')),
(12, 'God of War Ragnarok', 'Kratos i Atreus s enfronten al destí nòrdic', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/god_of_war_ragnarok.jpg')),
(13, 'Cyberpunk 2077', 'RPG en una metròpoli futurista corrupta', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/cyberpunk_2077.jpg')),
(14, 'Minecraft', 'Món obert de construcció i supervivència', 7, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/minecraft.jpg')),
(15, 'Red Dead Redemption 2', 'Vida de fugitius al salvatge oest', 18, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/red_dead_redemption_2.jpg')),
(16, 'Inception', 'Lladres de somnis en una missió impossible', 13, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/inception.jpg')),
(17, 'Toy Story', 'La vida secreta de les joguines', 0, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/toy_story.jpg')),
(18, 'Better Call Saul', 'L evolució de l advocat Saul Goodman', 16, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/better_call_saul.jpg')),
(19, 'Hollow Knight', 'Aventura metroidvania en un regne d insectes', 7, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/hollow_knight.jpg')),
(20, 'Forrest Gump', 'La història dels EUA a través d un home', 7, LOAD_FILE('C:/Users/Joaquin.DESKTOP-1SH4RD0/Desktop/Fotos_Contingut/forrest_gump.jpg'));
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

INSERT INTO usuari (nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin) VALUES 
('maria23', 'Maria', 'pass123', '2005-06-14', 10, 'active', NULL, false),
('alexdev', 'Alex', 'pass123', '2004-11-02', 25, 'active', NULL, false),
('laurap', 'Laura', 'pass123', '2006-01-19', 5, 'active', NULL, false),
('diegor', 'Diego', 'pass123', '2003-09-30', 40, 'active', NULL, false),
('sara99', 'Sara', 'pass123', '2007-02-10', 15, 'warned', NULL, false),
('pau_cat', 'Pau', 'pass123', '2002-07-21', 60, 'active', NULL, false);

INSERT INTO resenya (id_usuari, id_contingut, descripcio, nota, spoiler, data_resenya) VALUES

-- Pel·lícules
('maria23', 1, 'Visualment espectacular però una mica lenta en alguns moments', 88.0, false, '2025-01-10'),
('alexdev', 2, 'Molt bona història però ritme massa antic', 92.0, false, '2025-01-11'),
('laurap', 3, 'Màgica i emotiva, molt recomanable', 85.0, false, '2025-01-12'),
('diegor', 4, 'Gran Joker però massa hype', 80.0, false, '2025-01-13'),
('sara99', 5, 'Interessant però massa lenta i previsible en alguns punts', 72.0, false, '2025-01-14'),

-- Sèries
('pau_cat', 6, 'Excel·lent evolució del protagonista', 95.0, false, '2025-01-15'),
('maria23', 7, 'Addictiva però repetitiva en temporades avançades', 78.0, false, '2025-01-16'),
('alexdev', 8, 'Correcta però molt lenta', 70.0, false, '2025-01-17'),
('laurap', 9, 'Episodis molt irregulars, alguns brillants i altres dolents', 74.0, false, '2025-01-18'),
('diegor', 10, 'Bona actuació però història poc emocionant', 76.0, false, '2025-01-19'),

-- Videojocs
('sara99', 11, 'Exploració increïble però controls millorables', 90.0, false, '2025-01-20'),
('pau_cat', 12, 'Joc espectacular i brutal en tots els sentits', 96.0, false, '2025-01-21'),
('maria23', 13, 'Gran ambientació però ple de bugs', 65.0, false, '2025-01-22'),
('alexdev', 14, 'Creatiu però molt repetitiu al cap de poques hores', 75.0, false, '2025-01-23'),
('laurap', 15, 'Història molt bona però gameplay lent', 89.0, false, '2025-01-24'),
('diegor', 19, 'Art preciós però massa difícil i frustrant', 68.0, false, '2025-01-25'),

-- Altres
('sara99', 16, 'Molt bona idea però final confús', 83.0, false, '2025-01-26'),
('pau_cat', 17, 'Divertida però massa infantil per adults', 70.0, false, '2025-01-27'),
('maria23', 18, 'Escriptura excel·lent però ritme irregular', 87.0, false, '2025-01-28'),
('alexdev', 20, 'Clàssic emotiu però una mica sobrevalorat', 78.0, false, '2025-01-29');
SELECT id, nom FROM genere;
SELECT * FROM resenya;
show tables;
Select * from usuari;

