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
import MODEL.Pelicula;
import MODEL.Serie;
import MODEL.Usuari;
import MODEL.Videojoc;
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
        // SQL corregit amb GROUP BY per poder calcular la mitjana de les resenyes
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, v.preu "
                + "FROM contingut c "
                + "INNER JOIN videojoc v ON c.id = v.idJoc "
                + "INNER JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, v.preu "
                + "HAVING AVG(r.nota) >= ?";

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
                + "INNER JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, p.director, p.duracio "
                + "HAVING AVG(r.nota) >= ?";

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
        String sql = "SELECT c.id, c.titol, c.descripcio, c.classificacio, c.imatge, s.capitols, s.temporades "
                + "FROM contingut c "
                + "INNER JOIN serie s ON c.id = s.idSerie "
                + "INNER JOIN resenya r ON c.id = r.id_contingut "
                + "GROUP BY c.id, s.capitols, s.temporades "
                + "HAVING AVG(r.nota) >= ?";

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
                    int temporades = rs.getInt("temporades");

                    Serie s = new Serie(capitols, temporades, id, titol, descripcio, classificacio, imatge);
                    series.add(s); //
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a Buscar Serie per Valoració: " + e.getMessage());
            throw e;
        }
    }
}
