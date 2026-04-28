/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DADES;

import CONTROLLER.Main;
import static CONTROLLER.Main.password;
import static CONTROLLER.Main.url;
import static CONTROLLER.Main.user;
import static CONTROLLER.Main.usuaris;
import MODEL.Usuari;
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
import java.util.Date;

/**
 *
 * @author Joaquin
 */
public class gestioSQL {

    public static void insertUsuari(Usuari usuari) throws SQLException {
        String sql = "INSERT INTO usuari (nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuari.getNom_usuari());
            ps.setString(2, usuari.getNom());
            ps.setString(3, usuari.getContrasenya());

            // 2. Convertir LocalDate a java.sql.Date
            if (usuari.getData_naixament() != null) {
                ps.setDate(4, java.sql.Date.valueOf(usuari.getData_naixament()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setInt(5, usuari.getPunts());

            // 3. Convertir l'Enum a String perquè MySQL l'entengui
            if (usuari.getEstat() != null) {
                ps.setString(6, usuari.getEstat().name());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            // 4. Convertir LocalDateTime a java.sql.Timestamp
            if (usuari.getData_ban() != null) {
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(usuari.getData_ban()));
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }

            // Executar la sentència
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en fer l'insert: " + e.getMessage());
            throw e; // Tornem a llançar l'excepció per gestionar-la a la UI si cal
        }
    }

    public static void carregarUsuari() {
        usuaris.clear();
        String sql = "SELECT nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin FROM usuari";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nom_usuari = rs.getString("nom_usuari");
                String nom = rs.getString("nom");
                String contrasenya = rs.getString("contrasenya");

                java.sql.Date sqlDate = rs.getDate("data_naixement");
                LocalDate data_naixement = (sqlDate != null) ? sqlDate.toLocalDate() : null;

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
                LocalDateTime data_ban = (sqlTimestamp != null) ? sqlTimestamp.toLocalDateTime() : null;

                boolean admin = rs.getBoolean("admin");
                usuaris.add(new Usuari(nom_usuari, nom, contrasenya, data_naixement, punts, estat, data_ban, admin));

                System.out.println("Usuari carregat: " + nom_usuari);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    }
}
