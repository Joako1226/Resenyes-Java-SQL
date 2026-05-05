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
import MODEL.Usuari;
import VIEW.frmLogin;
import VIEW.frmContingutPagina;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 *
 * @author Joaquin
 */
public class Main {
    public static String url = "jdbc:mysql://192.168.90.216:3306/resenyesBD";
    public static String user = "root";
    public static String password = "joaquin100";
    
    public static DateTimeFormatter logs = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static String mod = LocalDateTime.now().format(logs);

    public static ArrayList<Usuari>usuaris = new ArrayList();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
