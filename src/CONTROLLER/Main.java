/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CONTROLLER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import DADES.Connexio;
import DADES.GestioLog;
import MODEL.Contingut;
import MODEL.Genere;
import MODEL.Pelicula;
import MODEL.Resenya;
import MODEL.Serie;
import static MODEL.Style.temaClar;
import static MODEL.Style.temaFosc;
import MODEL.Usuari;
import MODEL.Videojoc;
import VIEW.frmLogin;
import VIEW.frmContingutPagina;
import VIEW.frmHigherLower;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.UIManager;
/**
 *
 * @author Joaquin
 */
public class Main {
    public static String url ="jdbc:mysql://127.0.0.1:3306/resenyesbd";
    public static String user = "root";
    public static String password = "joaquin100";
    
    public static DateTimeFormatter logs = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static String mod = LocalDateTime.now().format(logs);

    public static ArrayList<Usuari>usuaris = new ArrayList();
    public static ArrayList<Contingut>continguts = new ArrayList();
    public static ArrayList<Pelicula>pelicules = new ArrayList();
    public static ArrayList<Serie>series = new ArrayList();
    public static ArrayList<Videojoc>videojocs = new ArrayList();
    public static ArrayList<Genere>generes = new ArrayList();
    public static ArrayList<Resenya>resenyes = new ArrayList();    
    
    public static Usuari usuariActual;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         try {
        UIManager.setLookAndFeel(new FlatDarkLaf());
    } catch (Exception e) {
        e.printStackTrace();
    }
        
        
       Connexio ccc = new Connexio();
        Connection conn = ccc.connectar();
        GestioLog.CrearLog();

        frmLogin Login = new frmLogin();
        Login.setVisible(true);
        
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, nom FROM genere");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    System.out.println(id + " - " + nom);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: connexio null");
        }
        
        
        
    }
        
    
}
