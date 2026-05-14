/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DADES;

import CONTROLLER.Main;
import static CONTROLLER.Main.password;
import static CONTROLLER.Main.pelicules;
import static CONTROLLER.Main.series;
import static CONTROLLER.Main.url;
import static CONTROLLER.Main.user;
import static CONTROLLER.Main.usuaris;
import static CONTROLLER.Main.videojocs;
import static DADES.Connexio.connectar;
import MODEL.Contingut;
import MODEL.Pelicula;
import MODEL.Resenya;
import MODEL.Serie;
import MODEL.Usuari;
import MODEL.Videojoc;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Joaquin
 */
public class gestioSQL {

    public static void insertUsuari(Usuari usuari) throws SQLException {
        String sql = "INSERT INTO usuari (nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin) VALUES (?, ?, ?, ?, ?, ?, ?,false)";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuari.getNom_usuari());
            ps.setString(2, usuari.getNom());
            ps.setString(3, usuari.getContrasenya());

            if (usuari.getData_naixament() != null) {
                ps.setDate(4, java.sql.Date.valueOf(usuari.getData_naixament()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setInt(5, usuari.getPunts());

            if (usuari.getEstat() != null) {
                ps.setString(6, usuari.getEstat().name());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            if (usuari.getData_ban() != null) {
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(usuari.getData_ban()));
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en fer l'insert: " + e.getMessage());
            throw e;
        }
    }

    public static void modificarUsuari(Usuari usuari) throws SQLException {
        String sql = "UPDATE usuari SET nom_usuari = ?, nom = ?, contrasenya = ?, data_naixement = ?, punts = ?, estat = ?, data_ban = ?, admin = ? WHERE nom_usuari = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuari.getNom_usuari());
            ps.setString(2, usuari.getNom());
            ps.setString(3, usuari.getContrasenya());

            if (usuari.getData_naixament() != null) {
                ps.setDate(4, java.sql.Date.valueOf(usuari.getData_naixament()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setInt(5, usuari.getPunts());

            if (usuari.getEstat() != null) {
                ps.setString(6, usuari.getEstat().name());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            if (usuari.getData_ban() != null) {
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(usuari.getData_ban()));
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }

            ps.setBoolean(8, usuari.isAdmin());

            ps.setString(9, usuari.getNom_usuari());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en fer l'update: " + e.getMessage());
            throw e;
        }
    }

    public static ArrayList<Usuari> carregarUsuari() {

        ArrayList<Usuari> usuaris = new ArrayList<>();

        String sql = "SELECT nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin FROM usuari";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String nom_usuari = rs.getString("nom_usuari");
                String nom = rs.getString("nom");
                String contrasenya = rs.getString("contrasenya");

                LocalDate data_naixement = null;
                java.sql.Date sqlDate = rs.getDate("data_naixement");
                if (sqlDate != null) {
                    data_naixement = sqlDate.toLocalDate();
                }

                int punts = rs.getInt("punts");

                Usuari.TipusBan estat = null;
                String estatString = rs.getString("estat");
                if (estatString != null) {
                    try {
                        estat = Usuari.TipusBan.valueOf(estatString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Valor d'estat desconegut: " + estatString);
                    }
                }

                LocalDateTime data_ban = null;
                java.sql.Timestamp ts = rs.getTimestamp("data_ban");
                if (ts != null) {
                    data_ban = ts.toLocalDateTime();
                }

                boolean admin = rs.getBoolean("admin");

                usuaris.add(new Usuari(
                        nom_usuari, nom, contrasenya,
                        data_naixement, punts, estat, data_ban, admin
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }

        return usuaris;
    }

    public static Usuari carregarUsuariPerNom(String nomUsuari) {

        String sql = "SELECT nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin " + "FROM usuari WHERE nom_usuari = ?";

        try (
                Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomUsuari);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String nom_usuari = rs.getString("nom_usuari");
                    String nom = rs.getString("nom");
                    String contrasenya = rs.getString("contrasenya");

                    java.sql.Date sqlDate = rs.getDate("data_naixement");
                    LocalDate data_naixement
                            = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    int punts = rs.getInt("punts");

                    String estatString = rs.getString("estat");
                    Usuari.TipusBan estat = null;

                    if (estatString != null) {
                        try {
                            estat = Usuari.TipusBan.valueOf(estatString);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Valor d'estat desconegut: " + estatString);
                        }
                    }

                    java.sql.Timestamp sqlTimestamp = rs.getTimestamp("data_ban");

                    LocalDateTime data_ban
                            = (sqlTimestamp != null)
                                    ? sqlTimestamp.toLocalDateTime()
                                    : null;

                    boolean admin = rs.getBoolean("admin");

                    Usuari u = new Usuari(
                            nom_usuari,
                            nom,
                            contrasenya,
                            data_naixement,
                            punts,
                            estat,
                            data_ban,
                            admin
                    );

                    System.out.println("Usuari carregat: " + nom_usuari);

                    return u;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }

        return null;
    }

    public static Usuari login(String userIntent, String passIntent) {
        String sql = "SELECT nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin FROM usuari WHERE nom_usuari = ? AND contrasenya = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userIntent);
            ps.setString(2, passIntent);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");

                    java.sql.Date sqlDate = rs.getDate("data_naixement");
                    LocalDate dataN = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    int punts = rs.getInt("punts");

                    String estatStr = rs.getString("estat");
                    Usuari.TipusBan estat = (estatStr != null) ? Usuari.TipusBan.valueOf(estatStr) : null;

                    java.sql.Timestamp sqlTime = rs.getTimestamp("data_ban");
                    LocalDateTime dataB = (sqlTime != null) ? sqlTime.toLocalDateTime() : null;

                    boolean esAdmin = rs.getBoolean("admin");

                    return new Usuari(userIntent, nom, passIntent, dataN, punts, estat, dataB, esAdmin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pelicula> CarregarPelicules() {
        ArrayList<Pelicula> llista = new ArrayList<>();
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, p.director, p.duracio "
                + "FROM contingut c "
                + "INNER JOIN pelicula p ON c.id = p.idPelicula";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String descripcio = rs.getString("descripcio");
                int classificacio = rs.getInt("classificacio");
                byte[] imatge = rs.getBytes("imatge");
                String director = rs.getString("director");

                java.sql.Time sqlTime = rs.getTime("duracio");
                java.time.LocalTime duracio = (sqlTime != null) ? sqlTime.toLocalTime() : java.time.LocalTime.of(0, 0);

                Pelicula p = new Pelicula(duracio, director, id, titol, descripcio, classificacio, imatge);
                llista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al carregar pel·lícules: " + e.getMessage());
            return null;
        }
        return llista;
    }

    public Pelicula carregarPeliculaAleatoria() {

        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, "
                + "p.director, p.duracio "
                + "FROM contingut c "
                + "INNER JOIN pelicula p ON c.id = p.idPelicula "
                + "WHERE c.imatge IS NOT NULL "
                + "AND LENGTH(c.imatge) > 0 "
                + "ORDER BY RAND()";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String descripcio = rs.getString("descripcio");
                int classificacio = rs.getInt("classificacio");
                byte[] imatge = rs.getBytes("imatge");
                String director = rs.getString("director");

                java.sql.Time sqlTime = rs.getTime("duracio");

                java.time.LocalTime duracio
                        = (sqlTime != null)
                                ? sqlTime.toLocalTime()
                                : java.time.LocalTime.of(0, 0);

                Pelicula pelicula = new Pelicula(
                        duracio,
                        director,
                        id,
                        titol,
                        descripcio,
                        classificacio,
                        imatge
                );

                double nota = obtenirNotaContingut(pelicula);

                if (nota > 0) {

                    try {

                        ByteArrayInputStream bis = new ByteArrayInputStream(imatge);
                        BufferedImage bufferedImage = ImageIO.read(bis);

                        if (bufferedImage != null) {
                            return pelicula;
                        }

                    } catch (Exception e) {
                        System.out.println("Imatge corrupta: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtenir pel·lícula aleatòria: " + e.getMessage());
        }

        return null;
    }

    public static void BuscarPelicules(String cadena) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, p.director, p.duracio "
                + "FROM contingut c "
                + "INNER JOIN pelicula p ON c.id = p.idPelicula "
                + "WHERE c.titol LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + cadena + "%");

            pelicules.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    String director = rs.getString("director");

                    java.sql.Time sqlTime = rs.getTime("duracio");
                    java.time.LocalTime duracio = (sqlTime != null) ? sqlTime.toLocalTime() : java.time.LocalTime.of(0, 0);

                    Pelicula p = new Pelicula(duracio, director, id, titol, descripcio, classificacio, imatge);
                    pelicules.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Pelicules: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarPeliculesPerGenere(String nomGenere) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, p.director, p.duracio "
                + "FROM contingut c "
                + "INNER JOIN pelicula p ON c.id = p.idPelicula "
                + "INNER JOIN genere_contingut gc ON c.id = gc.idContingut "
                + "INNER JOIN genere g ON gc.idGenere = g.id "
                + "WHERE g.nom = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomGenere);
            pelicules.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    String director = rs.getString("director");

                    java.sql.Time sqlTime = rs.getTime("duracio");
                    java.time.LocalTime duracio = (sqlTime != null) ? sqlTime.toLocalTime() : java.time.LocalTime.of(0, 0);

                    Pelicula p = new Pelicula(duracio, director, id, titol, descripcio, classificacio, imatge);
                    pelicules.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar per Gènere: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<Resenya> carregarResenyes(int id) {

        ArrayList<Resenya> llista = new ArrayList<>();

        String sql = "SELECT r.id_usuari, r.id_contingut, r.descripcio, r.nota, r.spoiler, r.data_resenya "
                + "FROM resenya r "
                + "WHERE r.id_contingut = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    String usuari = rs.getString("id_usuari");
                    int idContingut = rs.getInt("id_contingut");
                    String descripcio = rs.getString("descripcio");
                    double nota = rs.getDouble("nota");
                    boolean spoiler = rs.getBoolean("spoiler");

                    LocalDate dataResenya = null;
                    java.sql.Date sqlDate = rs.getDate("data_resenya");
                    if (sqlDate != null) {
                        dataResenya = sqlDate.toLocalDate();
                    }

                    Resenya r = new Resenya(
                            usuari,
                            idContingut,
                            descripcio,
                            nota,
                            spoiler,
                            dataResenya
                    );

                    llista.add(r);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al carregar ressenyes: " + e.getMessage());
            return new ArrayList<>();
        }

        return llista;
    }

    public ArrayList<Serie> CarregarSeries() {
        ArrayList<Serie> llista = new ArrayList<>();
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, s.capitols, s.temporada "
                + "FROM contingut c "
                + "INNER JOIN serie s ON c.id = s.idSerie";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String descripcio = rs.getString("descripcio");
                int classificacio = rs.getInt("classificacio");
                byte[] imatge = rs.getBytes("imatge");
                int capitols = rs.getInt("capitols");
                int temporada = rs.getInt("temporada");
                Serie s = new Serie(capitols, temporada, id, titol, descripcio, classificacio, imatge);
                series.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Error al carregar pel·lícules: " + e.getMessage());
            return null;
        }
        return llista;
    }

    public static void BuscarSerie(String cadena) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, s.capitols, s.temporada "
                + "FROM contingut c "
                + "INNER JOIN serie s ON c.id = s.idSerie "
                + "WHERE c.titol LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + cadena + "%");

            series.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    int capitols = rs.getInt("capitols");
                    int temporada = rs.getInt("temporada");
                    Serie s = new Serie(capitols, temporada, id, titol, descripcio, classificacio, imatge);
                    series.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Pelicules: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarSeriePerGenere(String nomGenere) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, s.capitols, s.temporada "
                + "FROM contingut c "
                + "INNER JOIN serie s ON c.id = s.idSerie "
                + "INNER JOIN genere_contingut gc ON c.id = gc.idContingut "
                + "INNER JOIN genere g ON gc.idGenere = g.id "
                + "WHERE g.nom = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomGenere);
            series.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    int capitols = rs.getInt("capitols");
                    int temporada = rs.getInt("temporada");

                    Serie s = new Serie(capitols, temporada, id, titol, descripcio, classificacio, imatge);
                    series.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar per Gènere: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<Videojoc> CarregarVideojocs() {
        ArrayList<Videojoc> llista = new ArrayList<>();
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, v.preu "
                + "FROM contingut c "
                + "INNER JOIN videojoc v ON c.id = v.idJoc";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String descripcio = rs.getString("descripcio");
                int classificacio = rs.getInt("classificacio");
                byte[] imatge = rs.getBytes("imatge");
                double preu = rs.getDouble("preu");
                Videojoc v = new Videojoc(preu, id, titol, descripcio, classificacio, imatge);
                videojocs.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error al carregar pel·lícules: " + e.getMessage());
            return null;
        }
        return llista;
    }

    public static void BuscarVideojoc(String cadena) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, v.preu "
                + "FROM contingut c "
                + "INNER JOIN videojoc v ON c.id = v.idJoc "
                + "WHERE c.titol LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + cadena + "%");

            videojocs.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    double preu = rs.getDouble("preu");
                    Videojoc v = new Videojoc(preu, id, titol, descripcio, classificacio, imatge);
                    videojocs.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Videojocs: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarVideojocPerGenere(String nomGenere) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, v.preu "
                + "FROM contingut c "
                + "INNER JOIN videojoc v ON c.id = v.idJoc "
                + "INNER JOIN genere_contingut gc ON c.id = gc.idContingut "
                + "INNER JOIN genere g ON gc.idGenere = g.id "
                + "WHERE g.nom = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomGenere);
            videojocs.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    double preu = rs.getDouble("preu");
                    Videojoc v = new Videojoc(preu, id, titol, descripcio, classificacio, imatge);
                    videojocs.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar per Gènere: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarVideojocPerValoracio(double puntuacio) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, v.preu "
                + "FROM contingut c "
                + "INNER JOIN videojoc v ON c.id = v.idJoc "
                + "LEFT JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, v.preu "
                + "HAVING IFNULL(AVG(r.nota), 0) >= ?";
        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, puntuacio);
            videojocs.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    double preu = rs.getDouble("preu");

                    // Creem l'objecte Videojoc amb el teu ordre de paràmetres
                    Videojoc v = new Videojoc(preu, id, titol, descripcio, classificacio, imatge);
                    videojocs.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Videojoc per Valoració: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarPeliculaPerValoracio(double puntuacio) throws SQLException {
        // Utilitzem GROUP BY per agrupar el contingut i HAVING per filtrar la mitjana de notes
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, p.director, p.duracio "
                + "FROM contingut c "
                + "INNER JOIN pelicula p ON c.id = p.idPelicula "
                + "LEFT JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, p.director, p.duracio "
                + "HAVING IFNULL(AVG(r.nota), 0) >= ?";
        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, puntuacio);
            pelicules.clear();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    String director = rs.getString("director");

                    java.sql.Time sqlTime = rs.getTime("duracio");
                    java.time.LocalTime duracio = (sqlTime != null) ? sqlTime.toLocalTime() : java.time.LocalTime.of(0, 0);

                    Pelicula p = new Pelicula(duracio, director, id, titol, descripcio, classificacio, imatge);
                    pelicules.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Pelicula per Valoració: " + e.getMessage());
            throw e;
        }
    }

    public static void BuscarSeriePerValoracio(double puntuacio) throws SQLException {
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, s.capitols, s.temporada "
                + "FROM contingut c "
                + "INNER JOIN serie s ON c.id = s.idSerie "
                + "LEFT JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, s.capitols, s.temporada "
                + "HAVING IFNULL(AVG(r.nota), 0) >= ?";
        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, puntuacio);
            series.clear(); //

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titol = rs.getString("titol");
                    String descripcio = rs.getString("descripcio");
                    int classificacio = rs.getInt("classificacio");
                    byte[] imatge = rs.getBytes("imatge");
                    int capitols = rs.getInt("capitols");
                    int temporades = rs.getInt("temporada");

                    Serie s = new Serie(capitols, temporades, id, titol, descripcio, classificacio, imatge);
                    series.add(s); //
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Serie per Valoració: " + e.getMessage());
            throw e;
        }
    }

    public double obtenirNotaContingut(Contingut c) {

        String sql = "SELECT IFNULL(AVG(r.nota), 0) AS mitjana "
                + "FROM resenya r "
                + "WHERE r.id_contingut = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("mitjana");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obtenint nota mitjana: " + e.getMessage());

        }

        return 0.0;
    }

    public void AgregarVideojoc(Videojoc videojoc) {
        String sqlContingut = "INSERT INTO contingut (titol, descripcio, classificacio, imatge) VALUES (?,?,?,?)";
        String sqlVideojoc = "INSERT INTO videojoc (idJoc, preu) VALUES (?,?)";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            if (con == null || con.isClosed()) {
                connectar();
            }

            con.setAutoCommit(false);

            PreparedStatement psC = con.prepareStatement(sqlContingut, Statement.RETURN_GENERATED_KEYS);
            psC.setString(1, videojoc.getTitol());
            psC.setString(2, videojoc.getDescripcio());
            psC.setInt(3, videojoc.getClassificacio());
            psC.setBytes(4, videojoc.getImatge());
            psC.executeUpdate();

            ResultSet rs = psC.getGeneratedKeys();
            int idGenerat = 0;
            if (rs.next()) {
                idGenerat = rs.getInt(1);
            }

            if (idGenerat > 0) {
                PreparedStatement psV = con.prepareStatement(sqlVideojoc);
                psV.setInt(1, idGenerat); // L'ID de contingut
                psV.setDouble(2, videojoc.getPreu());
                psV.executeUpdate();

                con.commit();
                System.out.println("Videojoc insertat correctament amb ID: " + idGenerat);
            }

        } catch (SQLException ex) {
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                con.rollback();
                System.err.println("Error, fent rollback...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarPelicula(Pelicula pelicula) {
        String sqlContingut = "INSERT INTO contingut (titol, descripcio, classificacio, imatge) VALUES (?,?,?,?)";
        String sqlPelicula = "INSERT INTO pelicula (idPelicula, duracio, director) VALUES (?,?,?)";

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // Iniciem la transacció

            // 1. Insert a la taula genèrica 'contingut'
            PreparedStatement psC = con.prepareStatement(sqlContingut, Statement.RETURN_GENERATED_KEYS);
            psC.setString(1, pelicula.getTitol());
            psC.setString(2, pelicula.getDescripcio());
            psC.setInt(3, pelicula.getClassificacio());
            psC.setBytes(4, pelicula.getImatge());
            psC.executeUpdate();

            // Recuperem l'ID generat automàticament
            ResultSet rs = psC.getGeneratedKeys();
            int idGenerat = 0;
            if (rs.next()) {
                idGenerat = rs.getInt(1);
            }

            if (idGenerat > 0) {
                // 2. Insert a la taula específica 'pelicula'
                PreparedStatement psP = con.prepareStatement(sqlPelicula);
                psP.setInt(1, idGenerat);
                psP.setTime(2, java.sql.Time.valueOf(pelicula.getDuracio())); // LocalTime a SQL Time
                psP.setString(3, pelicula.getDirector());
                psP.executeUpdate();

                con.commit(); // Tot correcte, guardem canvis
                System.out.println("Pel·lícula guardada amb ID: " + idGenerat);
            }
        } catch (SQLException ex) {
            gestionarRollback(con, ex);
        } finally {
            tancarConnexio(con);
        }
    }

    public void agregarSerie(Serie serie) {
        String sqlContingut = "INSERT INTO contingut (titol, descripcio, classificacio, imatge) VALUES (?,?,?,?)";
        String sqlSerie = "INSERT INTO serie (idSerie, capitols, temporada) VALUES (?,?,?)";

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);

            PreparedStatement psC = con.prepareStatement(sqlContingut, Statement.RETURN_GENERATED_KEYS);
            psC.setString(1, serie.getTitol());
            psC.setString(2, serie.getDescripcio());
            psC.setInt(3, serie.getClassificacio());
            psC.setBytes(4, serie.getImatge());
            psC.executeUpdate();

            ResultSet rs = psC.getGeneratedKeys();
            int idGenerat = 0;
            if (rs.next()) {
                idGenerat = rs.getInt(1);
            }

            if (idGenerat > 0) {
                PreparedStatement psS = con.prepareStatement(sqlSerie);
                psS.setInt(1, idGenerat);
                psS.setInt(2, serie.getCapitols());
                psS.setInt(3, serie.getTemporada());
                psS.executeUpdate();

                con.commit();
                System.out.println("Sèrie guardada amb ID: " + idGenerat);
            }
        } catch (SQLException ex) {
            gestionarRollback(con, ex);
        } finally {
            tancarConnexio(con);
        }
    }

    public static void insertResenya(Resenya resenya) throws SQLException {
        String sql = "INSERT INTO resenya (id_usuari, id_contingut, descripcio, nota, spoiler, data_resenya) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, resenya.getUsuari());
            ps.setInt(2, resenya.getIdContingut());
            ps.setString(3, resenya.getDescripcio());
            ps.setDouble(4, resenya.getNota());
            ps.setBoolean(5, resenya.isSpoiler());

            LocalDate dataPerInsertar = (resenya.getDataResenya() != null)
                    ? resenya.getDataResenya()
                    : LocalDate.now();

            ps.setDate(6, java.sql.Date.valueOf(dataPerInsertar));

            ps.executeUpdate();
            System.out.println("Resenya guardada correctament!");

        } catch (SQLException e) {
            System.err.println("Error en fer l'insert de la resenya: " + e.getMessage());
            throw e;
        }
    }

    private void gestionarRollback(Connection con, SQLException ex) {
        System.err.println("Error en la transacció. Desfent canvis (Rollback)...");
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error critic fent rollback: " + e.getMessage());
        }
        ex.printStackTrace();
    }

    private void tancarConnexio(Connection con) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int obtenirNumBans(String nomUsuari) throws SQLException {
        String sql = "SELECT COUNT(*) FROM resenya WHERE id_usuari = ? AND descripcio LIKE '%[COMENTARI BLOQUEJAT]%'";
        try (Connection conn = connectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomUsuari);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public static void actualitzarEstatUsuari(String usuari, MODEL.Usuari.TipusBan nouEstat, LocalDateTime dataFi) throws SQLException {
        String sql = "UPDATE usuari SET estat = ?, data_ban = ? WHERE nom_usuari = ?";

        try (Connection conn = connectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nouEstat.name());

            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(dataFi));

            pstmt.setString(3, usuari);
            pstmt.executeUpdate();
        }
    }

    public static ArrayList<Resenya> obtenirTotesLesResenyes() {
        ArrayList<Resenya> llista = new ArrayList<>();

        String sql = "SELECT r.id_usuari, r.id_contingut, r.descripcio, r.nota, r.spoiler, r.data_resenya, c.titol "
                + "FROM resenya r "
                + "INNER JOIN contingut c ON r.id_contingut = c.id "
                + "ORDER BY r.data_resenya DESC";

        try (java.sql.Connection conn = connectar(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); java.sql.ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String usuari = rs.getString("id_usuari");
                int idContingut = rs.getInt("id_contingut");
                String comentari = rs.getString("descripcio");
                double nota = rs.getDouble("nota");
                boolean esSpoiler = rs.getBoolean("spoiler");
                java.time.LocalDate data = rs.getDate("data_resenya").toLocalDate();

                MODEL.Resenya r = new MODEL.Resenya(usuari, idContingut, comentari, nota, esSpoiler, data);

                r.setTitolContingut(rs.getString("titol"));

                llista.add(r);
            }

        } catch (java.sql.SQLException ex) {
            System.err.println("Error en obtenirTotesLesResenyes: " + ex.getMessage());
        }

        return llista;
    }

    public static boolean eliminarResenya(String usuari, int idContingut) {
        String sql = "DELETE FROM resenya WHERE id_usuari = ? AND id_contingut = ?";
        try (java.sql.Connection conn = connectar(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuari);
            pstmt.setInt(2, idContingut);
            return pstmt.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            return false;
        }
    }

    public static boolean modificarComentari(String usuari, int idContingut, String nouText) {
        String sql = "UPDATE resenya SET descripcio = ? WHERE id_usuari = ? AND id_contingut = ?";
        try (java.sql.Connection conn = connectar(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nouText);
            pstmt.setString(2, usuari);
            pstmt.setInt(3, idContingut);
            return pstmt.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            return false;
        }
    }

    public static String obtenirResumCriminal(String nomUsuari) {
        StringBuilder resum = new StringBuilder();
        String sql = "SELECT descripcio FROM resenya WHERE id_usuari = ? AND descripcio LIKE '[COMENTARI BLOQUEJAT]%'";

        try (Connection conn = connectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomUsuari);
            try (ResultSet rs = pstmt.executeQuery()) {
                int comptador = 0;
                resum.append("Historial de ").append(nomUsuari).append(":\n\n");
                while (rs.next()) {
                    comptador++;
                    resum.append("- ").append(rs.getString("descripcio")).append("\n");
                }
                if (comptador == 0) {
                    return "L'usuari no té comentaris bloquejats actualment.";
                }
                resum.insert(0, "TOTAL BLOQUEJOS: " + comptador + "\n");
            }
        } catch (SQLException e) {
            return "No s'ha pogut carregar l'historial.";
        }
        return resum.toString();
    }

    public static void eliminarContingutPerTitol(String titol) {

        String sql = "DELETE FROM contingut WHERE titol = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titol);

            int filesAfectades = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error eliminant contingut per títol: " + e.getMessage());
        }
    }

}
