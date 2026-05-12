package DADES;

import MODEL.Contingut;
import MODEL.Pelicula;
import MODEL.Serie;
import MODEL.Videojoc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class Connexio {

    private static Connection con = null;

    public static String url = "jdbc:mysql://localhost:3306/resenyesBD";
    public static String user = "root";
    public static String password = "joaquin100";

 
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
    // Dins de la classe Connexio.java

    public boolean guardarContingut(Contingut c) {
        // Utilitzem la teva connexió existent
        Connection conn = connectar();
        if (conn == null) {
            return false;
        }

        try {
            String sqlBase = "INSERT INTO contingut (titol, descripcio, classificacio, imatge) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlBase, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, c.getTitol());
            pstmt.setString(2, c.getDescripcio());
            pstmt.setInt(3, c.getClassificacio());
            pstmt.setBytes(4, c.getImatge());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int idGenerada = rs.next() ? rs.getInt(1) : 0;

            if (c instanceof Pelicula) {
                Pelicula p = (Pelicula) c;
                String sqlPeli = "INSERT INTO pelicula (idPelicula, director, duracio) VALUES (?, ?, ?)";
                PreparedStatement pstmtPeli = conn.prepareStatement(sqlPeli);
                pstmtPeli.setInt(1, idGenerada);
                pstmtPeli.setString(2, p.getDirector());

                if (p.getDuracio() != null) {
                    pstmtPeli.setTime(3, java.sql.Time.valueOf(p.getDuracio()));
                } else {
                    pstmtPeli.setNull(3, java.sql.Types.TIME);
                }
                pstmtPeli.executeUpdate();

            } else if (c instanceof Serie) {
                Serie s = (Serie) c;
                // He posat 'idSerie' i 'temporada' segons el que veig al teu SELECT de CarregarSeries
                String sqlSerie = "INSERT INTO serie (idSerie, capitols, temporada) VALUES (?, ?, ?)";
                PreparedStatement pstmtSerie = conn.prepareStatement(sqlSerie);
                pstmtSerie.setInt(1, idGenerada);
                pstmtSerie.setInt(2, s.getCapitols());
                pstmtSerie.setInt(3, s.getTemporada());
                pstmtSerie.executeUpdate();

            } else if (c instanceof Videojoc) {
                Videojoc v = (Videojoc) c;
                // He posat 'idJoc' segons el teu SELECT de CarregarVideojocs
                String sqlJoc = "INSERT INTO videojoc (idJoc, preu) VALUES (?, ?)";
                PreparedStatement pstmtJoc = conn.prepareStatement(sqlJoc);
                pstmtJoc.setInt(1, idGenerada);
                pstmtJoc.setDouble(2, v.getPreu());
                pstmtJoc.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error a guardarContingut: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
