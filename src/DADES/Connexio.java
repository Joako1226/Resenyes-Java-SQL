/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DADES;
import CONTROLLER.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Joaquin
 */

public class Connexio {
    public Connection connectar() {

    Connection con = null;

    String url = Main.url;
    String user = Main.user;
    String password = Main.password;

    try {
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Connexió OK");
    } catch (SQLException e) {
        System.out.println("No s'ha pogut establir la Connexio");
        e.printStackTrace();
    }

    return con;
}
}
