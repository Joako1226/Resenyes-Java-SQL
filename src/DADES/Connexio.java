package DADES;

import MODEL.Contingut;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class Connexio {

    private static Connection con = null;

    public static String url = "jdbc:mysql://localhost:3306/resenyesBD";
    public static String user = "root";
    public static String password = "Roger2007";

 
    private String SQL_INSERTAR = "INSERT INTO contingut (titol, descripcio, classificacio, imatge) VALUES (?,?,?,?)";

    private String SQL_CONSULTA = "SELECT * FROM contingut";


    public static Connection connectar() {
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connexió OK");
        } catch (SQLException e) {
            System.out.println("Error de connexió");
            e.printStackTrace();
        }
        return con;
    }
    
    public boolean connectarCon() {
        try {
           
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connexió OK");
            return con != null;
        } catch (SQLException e) {
            System.out.println("Error de connexió");
            e.printStackTrace();
        }
        return false;
    }

   
    public void AgregarImg(Contingut contingut) {

        try {
            if (con == null) connectar();

            PreparedStatement ps = con.prepareStatement(SQL_INSERTAR);

            ps.setString(1, contingut.getTitol());
            ps.setString(2, contingut.getDescripcio());
            ps.setInt(3, contingut.getClassificacio());
            ps.setBytes(4, contingut.getImatge());

            ps.executeUpdate();

            System.out.println("Insert OK");

            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   
    public ArrayList<Contingut> CarregarImg() {

        ArrayList<Contingut> lista = new ArrayList<>();

        try {
            if (con == null) connectar();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_CONSULTA);

            while (rs.next()) {
                Contingut c = new Contingut();

                c.setId(rs.getInt("id"));
                c.setTitol(rs.getString("titol"));
                c.setDescripcio(rs.getString("descripcio"));
                c.setClassificacio(rs.getInt("classificacio"));
                c.setImatge(rs.getBytes("imatge"));

                lista.add(c);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}