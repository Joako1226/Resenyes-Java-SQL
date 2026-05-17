/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import static CONTROLLER.Main.password;
import static CONTROLLER.Main.pelicules;
import static CONTROLLER.Main.series;
import static CONTROLLER.Main.url;
import static CONTROLLER.Main.user;
import static CONTROLLER.Main.usuariActual;
import static CONTROLLER.Main.videojocs;
import DADES.Connexio;
import DADES.gestioSQL;
import static DADES.gestioSQL.BuscarPeliculaPerValoracio;
import static DADES.gestioSQL.BuscarSeriePerValoracio;
import static DADES.gestioSQL.BuscarVideojocPerValoracio;
import MODEL.Contingut;
import MODEL.Pelicula;
import MODEL.RenderImg;
import MODEL.Serie;
import MODEL.Videojoc;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rger Trulls
 */
public class frmMainClient extends javax.swing.JFrame {

    Connexio mConnexio = new Connexio();
    gestioSQL gestioSQL = new gestioSQL();
    DefaultTableModel mModelTaula = new DefaultTableModel();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmMainClient.class.getName());
    private ButtonGroup chkOpciocontingut = new ButtonGroup();

    private static final Color COLOR_FONS = new Color(15, 23, 42);
    private static final Color COLOR_CARD = new Color(30, 41, 59);
    private static final Color COLOR_ACCENT = new Color(99, 102, 241);
    private static final Color COLOR_TEXT = new Color(248, 250, 252);
    private static final Color COLOR_TEXT2 = new Color(148, 163, 184);

    /**
     * Creates new form frmMainClient
     */
    public frmMainClient() {
        initComponents();
        txtUsuari.setText("Usuari actual: " + usuariActual.getNom());

        chkOpciocontingut.add(rdoSerie);
        chkOpciocontingut.add(rdoPelicula);
        chkOpciocontingut.add(rdoVideojocs);

        rdoSerie.setActionCommand("SERIE");
        rdoPelicula.setActionCommand("PELICULA");
        rdoVideojocs.setActionCommand("VIDEOJOC");

        rdoSerie.addActionListener(e -> actualitzarInterficieSegonsTipus());
        rdoPelicula.addActionListener(e -> actualitzarInterficieSegonsTipus());
        rdoVideojocs.addActionListener(e -> actualitzarInterficieSegonsTipus());

        actualitzarInterficieSegonsTipus();
        carregarPelicules();

        /*aplicarEstilModern();
        estilitzarBotons();*/
        this.setLocationRelativeTo(null);

        this.setTitle("CriticFy - Busqueda");


    }

    /* private void aplicarEstilModern() {
        this.getContentPane().setBackground(COLOR_FONS);

        tblContinguts.setBackground(COLOR_CARD);
        tblContinguts.setForeground(COLOR_TEXT);
        tblContinguts.setGridColor(new Color(51, 65, 85));
        tblContinguts.setSelectionBackground(COLOR_ACCENT);
        tblContinguts.setSelectionForeground(Color.WHITE);
        tblContinguts.setRowHeight(80);
        tblContinguts.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(new BordeRodo(new Color(51, 65, 85), 15));

        tblContinguts.getTableHeader().setBackground(COLOR_FONS);
        tblContinguts.getTableHeader().setForeground(COLOR_ACCENT);
        tblContinguts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblContinguts.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder());

        txtBuscar.setBackground(COLOR_CARD);
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(new javax.swing.border.CompoundBorder(
                new BordeRodo(COLOR_ACCENT, 20),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtValoracio.setBackground(COLOR_CARD);
        txtValoracio.setForeground(Color.WHITE);
        txtValoracio.setCaretColor(Color.WHITE);
        txtValoracio.setBorder(new javax.swing.border.CompoundBorder(
                new BordeRodo(COLOR_ACCENT, 20),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        lblRating.setForeground(COLOR_ACCENT);
        txtUsuari.setForeground(COLOR_TEXT2);
        lblBuscar.setForeground(COLOR_TEXT2);
        lblGenere.setForeground(COLOR_TEXT2);

        rdoPelicula.setForeground(COLOR_TEXT);
        rdoPelicula.setBackground(COLOR_FONS);
        rdoPelicula.setFocusPainted(false); 

        rdoSerie.setForeground(COLOR_TEXT);
        rdoSerie.setBackground(COLOR_FONS);
        rdoSerie.setFocusPainted(false);

        rdoVideojocs.setForeground(COLOR_TEXT);
        rdoVideojocs.setBackground(COLOR_FONS);
        rdoVideojocs.setFocusPainted(false);

    }    
    private void estilitzarBotons() {
        rdoPelicula.setFocusPainted(false);
        rdoPelicula.setContentAreaFilled(false); 
    }
     */
    private void limpiar() {
        DefaultTableModel tb = (DefaultTableModel) tblContinguts.getModel();
        tb.setRowCount(0);
    }

    public void estrelles() {
        String full = "/IMAGES/estrellaFull.png";
        String mitja = "/IMAGES/estrellaMitja.png";

        if (comprovarIcona(imgEstrella5, full) || comprovarIcona(imgEstrella5, mitja)) {
            imgEstrella4.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        }

        if (comprovarIcona(imgEstrella4, full) || comprovarIcona(imgEstrella4, mitja)) {
            imgEstrella3.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        }

        if (comprovarIcona(imgEstrella3, full) || comprovarIcona(imgEstrella3, mitja)) {
            imgEstrella2.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        }

        if (comprovarIcona(imgEstrella2, full) || comprovarIcona(imgEstrella2, mitja)) {
            imgEstrella1.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        }
    }

    private boolean comprovarIcona(javax.swing.JLabel label, String rutaRecurs) {
        if (label.getIcon() == null) {
            return false;
        }
        String iconPath = label.getIcon().toString();
        return iconPath.contains(rutaRecurs);
    }

    private void actualitzarPerClicEstrella(double valor) throws SQLException {
        sldValoracio.setValue((int) (valor * 10));
        txtValoracio.setText(String.valueOf(valor));
        actualitzarEstrelles(valor);

        filtrarDadesPerNota(valor);
    }
    
    private void filtrarDadesPerNota(double nota) throws SQLException {
        if (chkOpciocontingut.getSelection() == null) {
            return;
        }

        String seleccio = chkOpciocontingut.getSelection().getActionCommand();

        switch (seleccio) {
            case "VIDEOJOC":
                gestioSQL.BuscarVideojocPerValoracio(nota); 
                refrescarTaulaVideojoc(); 
                break;
            case "PELICULA":
                gestioSQL.BuscarPeliculaPerValoracio(nota);
                refrescarTaulaPelicules();
                break;
            case "SERIE":
                gestioSQL.BuscarSeriePerValoracio(nota);
                refrescarTaulaSeries();                break;
        }
    }
    public void actualitzarEstrelles(double valor) {
        String full = "/IMAGES/estrellaFull.png";
        String mitja = "/IMAGES/estrellaMitja.png";
        String buida = "/IMAGES/estrellaGris.png"; // O com es digui la teva estrella buida

        javax.swing.JLabel[] estrelles = {imgEstrella1, imgEstrella2, imgEstrella3, imgEstrella4, imgEstrella5};
        for (javax.swing.JLabel s : estrelles) {
            s.setIcon(new javax.swing.ImageIcon(getClass().getResource(buida)));
        }

        if (valor >= 20) {
            imgEstrella1.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        } else if (valor >= 10) {
            imgEstrella1.setIcon(new javax.swing.ImageIcon(getClass().getResource(mitja)));
        }

        if (valor >= 40) {
            imgEstrella2.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        } else if (valor >= 30) {
            imgEstrella2.setIcon(new javax.swing.ImageIcon(getClass().getResource(mitja)));
        }

        if (valor >= 60) {
            imgEstrella3.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        } else if (valor >= 50) {
            imgEstrella3.setIcon(new javax.swing.ImageIcon(getClass().getResource(mitja)));
        }

        if (valor >= 80) {
            imgEstrella4.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        } else if (valor >= 70) {
            imgEstrella4.setIcon(new javax.swing.ImageIcon(getClass().getResource(mitja)));
        }

        if (valor >= 100) {
            imgEstrella5.setIcon(new javax.swing.ImageIcon(getClass().getResource(full)));
        } else if (valor >= 90) {
            imgEstrella5.setIcon(new javax.swing.ImageIcon(getClass().getResource(mitja)));
        }

        estrelles();
    }

    private void carregarPelicules() {
        limpiar();
        String[] columnes = {"ID", "Títol", "Descripció", "Class.", "Imatge", "Tipus", "Director", "Duració"};
        mModelTaula.setColumnIdentifiers(columnes);
        tblContinguts.setDefaultRenderer(Object.class, new RenderImg());

        ArrayList<Pelicula> llistaPelicules = new ArrayList();
        Pelicula mPelicula;

        if (mConnexio.connectarCon()) {

            mModelTaula.setRowCount(0);

            Object[] Dades = new Object[8];

            llistaPelicules = gestioSQL.CarregarPelicules();

            if (llistaPelicules != null) {
                for (int i = llistaPelicules.size() - 1; i >= 0; i--) {

                    mPelicula = llistaPelicules.get(i);

                    Dades[0] = String.valueOf(mPelicula.getId());
                    Dades[1] = mPelicula.getTitol();
                    Dades[2] = mPelicula.getDescripcio();
                    Dades[3] = mPelicula.getClassificacio();

                    try {
                        byte[] imatge = mPelicula.getImatge();
                        if (imatge != null) {
                            InputStream inputStream = new ByteArrayInputStream(imatge);
                            BufferedImage bufferedImage = ImageIO.read(inputStream);
                            ImageIcon mIcon = new ImageIcon(
                                    bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                            );
                            Dades[4] = new JLabel(mIcon);
                        } else {
                            throw new Exception("Sense imatge");
                        }
                    } catch (Exception e) {
                        JLabel placeholder = new JLabel("");
                        placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
                        Dades[4] = placeholder;
                    }

                    Dades[5] = "Pelicula";
                    Dades[6] = mPelicula.getDirector();
                    Dades[7] = mPelicula.getDuracio().toString();

                    mModelTaula.addRow(Dades);
                }

                tblContinguts.setModel(mModelTaula);
                tblContinguts.setRowHeight(60);

                tblContinguts.getColumnModel().getColumn(6).setPreferredWidth(100);
                tblContinguts.getColumnModel().getColumn(7).setPreferredWidth(80);
            }
        }
    }

    private void refrescarTaulaPelicules() {
        limpiar();

        Object[] fila = new Object[8];

        for (Pelicula p : pelicules) {
            fila[0] = p.getId();
            fila[1] = p.getTitol();
            fila[2] = p.getDescripcio();
            fila[3] = p.getClassificacio();
            fila[4] = generarLabelImatge(p.getImatge());
            fila[5] = "Pelicula";
            fila[6] = p.getDirector();
            fila[7] = p.getDuracio();

            mModelTaula.addRow(fila);
        }
    }

    private void carregarSeries() {
        limpiar();
        String[] columnes = {"ID", "Títol", "Descripció", "Class.", "Imatge", "Tipus", "Capitols", "Temporades"};
        mModelTaula.setColumnIdentifiers(columnes);
        tblContinguts.setDefaultRenderer(Object.class, new RenderImg());

        ArrayList<Serie> llistaPelicules = new ArrayList();
        Serie mSerie;

        if (mConnexio.connectarCon()) {

            mModelTaula.setRowCount(0);

            Object[] Dades = new Object[8];

            llistaPelicules = gestioSQL.CarregarSeries();

            if (llistaPelicules != null) {
                for (int i = llistaPelicules.size() - 1; i >= 0; i--) {

                    mSerie = llistaPelicules.get(i);

                    Dades[0] = String.valueOf(mSerie.getId());
                    Dades[1] = mSerie.getTitol();
                    Dades[2] = mSerie.getDescripcio();
                    Dades[3] = mSerie.getClassificacio();

                    try {
                        byte[] imatge = mSerie.getImatge();
                        if (imatge != null) {
                            InputStream inputStream = new ByteArrayInputStream(imatge);
                            BufferedImage bufferedImage = ImageIO.read(inputStream);
                            ImageIcon mIcon = new ImageIcon(
                                    bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                            );
                            Dades[4] = new JLabel(mIcon);
                        } else {
                            throw new Exception("Sense imatge");
                        }
                    } catch (Exception e) {
                        JLabel placeholder = new JLabel("");
                        placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
                        Dades[4] = placeholder;
                    }

                    Dades[5] = "Serie";
                    Dades[6] = mSerie.getCapitols();
                    Dades[7] = mSerie.getTemporada();

                    mModelTaula.addRow(Dades);
                }

                tblContinguts.setModel(mModelTaula);
                tblContinguts.setRowHeight(60);

                tblContinguts.getColumnModel().getColumn(6).setPreferredWidth(100);
                tblContinguts.getColumnModel().getColumn(7).setPreferredWidth(80);
            }
        }
    }

    private void refrescarTaulaSeries() {
        limpiar();

        Object[] fila = new Object[8];

        for (Serie s : series) {
            fila[0] = s.getId();
            fila[1] = s.getTitol();
            fila[2] = s.getDescripcio();
            fila[3] = s.getClassificacio();
            fila[4] = generarLabelImatge(s.getImatge());
            fila[5] = "Serie";
            fila[6] = s.getCapitols();
            fila[7] = s.getTemporada();

            mModelTaula.addRow(fila);
        }
    }

    private void carregarVideojocs() {
        limpiar();
        String[] columnes = {"ID", "Títol", "Descripció", "Class.", "Imatge", "Tipus", "Preu"};
        mModelTaula.setColumnIdentifiers(columnes);
        tblContinguts.setDefaultRenderer(Object.class, new RenderImg());

        ArrayList<Videojoc> llistaVideojoc = new ArrayList();
        Videojoc mVideojoc;

        if (mConnexio.connectarCon()) {

            mModelTaula.setRowCount(0);

            Object[] Dades = new Object[8];

            llistaVideojoc = gestioSQL.CarregarVideojocs();

            if (llistaVideojoc != null) {
                for (int i = llistaVideojoc.size() - 1; i >= 0; i--) {

                    mVideojoc = llistaVideojoc.get(i);

                    Dades[0] = String.valueOf(mVideojoc.getId());
                    Dades[1] = mVideojoc.getTitol();
                    Dades[2] = mVideojoc.getDescripcio();
                    Dades[3] = mVideojoc.getClassificacio();

                    try {
                        byte[] imatge = mVideojoc.getImatge();
                        if (imatge != null) {
                            InputStream inputStream = new ByteArrayInputStream(imatge);
                            BufferedImage bufferedImage = ImageIO.read(inputStream);
                            ImageIcon mIcon = new ImageIcon(
                                    bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                            );
                            Dades[4] = new JLabel(mIcon);
                        } else {
                            throw new Exception("Sense imatge");
                        }
                    } catch (Exception e) {
                        JLabel placeholder = new JLabel("");
                        placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
                        Dades[4] = placeholder;
                    }

                    Dades[5] = "Videojoc";
                    Dades[6] = mVideojoc.getPreu();

                    mModelTaula.addRow(Dades);
                }

                tblContinguts.setModel(mModelTaula);
                tblContinguts.setRowHeight(60);

                tblContinguts.getColumnModel().getColumn(0).setPreferredWidth(40);
                tblContinguts.getColumnModel().getColumn(1).setPreferredWidth(150);
                tblContinguts.getColumnModel().getColumn(2).setPreferredWidth(150);
                //tblContinguts.getColumnModel().getColumn(7).setPreferredWidth(80);
            }
        }
    }

    private void refrescarTaulaVideojoc() {
        limpiar();

        Object[] fila = new Object[8];

        for (Videojoc v : videojocs) {
            fila[0] = v.getId();
            fila[1] = v.getTitol();
            fila[2] = v.getDescripcio();
            fila[3] = v.getClassificacio();
            fila[4] = generarLabelImatge(v.getImatge());
            fila[5] = "Videojoc";
            fila[6] = v.getPreu();

            mModelTaula.addRow(fila);
        }
    }

    private JLabel generarLabelImatge(byte[] imatgeBytes) {
        if (imatgeBytes == null) {
            return crearPlaceholder();
        }
        try {
            InputStream is = new ByteArrayInputStream(imatgeBytes);
            BufferedImage bi = ImageIO.read(is);
            Image dimg = bi.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(dimg));
        } catch (Exception e) {
            return crearPlaceholder();
        }
    }
    private JLabel crearPlaceholder() {
        JLabel placeholder = new JLabel("");
        try {
            placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
        } catch (Exception ex) {
            placeholder.setText("No Img");
        }
        return placeholder;
    }

    public void omplirComboGeneresContingut() {
        String sql = "SELECT DISTINCT g.nom FROM genere g "
                + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                + "ORDER BY g.nom ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            cmbGenere.removeAllItems();
            while (rs.next()) {
                cmbGenere.addItem(rs.getString("nom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void omplirComboGeneresVideojocs() {
        String sql = "SELECT DISTINCT g.nom FROM genere g "
                + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                + "INNER JOIN videojoc v ON gc.idContingut = v.idJoc "
                + "ORDER BY g.nom ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            cmbGenere.removeAllItems();
            while (rs.next()) {
                cmbGenere.addItem(rs.getString("nom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void omplirComboGeneresSeries() {
        String sql = "SELECT DISTINCT g.nom FROM genere g "
                + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                + "INNER JOIN serie s ON gc.idContingut = s.idSerie "
                + "ORDER BY g.nom ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            cmbGenere.removeAllItems();
            while (rs.next()) {
                cmbGenere.addItem(rs.getString("nom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void omplirComboGeneresPelicules() {
        String sql = "SELECT DISTINCT g.nom FROM genere g "
                + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                + "INNER JOIN pelicula p ON gc.idContingut = p.idPelicula "
                + "ORDER BY g.nom ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            cmbGenere.removeAllItems();
            while (rs.next()) {
                cmbGenere.addItem(rs.getString("nom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualitzarInterficieSegonsTipus() {
        if (chkOpciocontingut.getSelection() == null) {
            return;
        }
        String seleccio = chkOpciocontingut.getSelection().getActionCommand();

        switch (seleccio) {
            case "VIDEOJOC":
                omplirComboGeneresVideojocs();

                break;

            case "PELICULA":
                omplirComboGeneresPelicules();

                break;

            case "SERIE":
                omplirComboGeneresSeries();
                break;
            default:
                omplirComboGeneresContingut();
                break;
        }
    }

    public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgLogo = new javax.swing.JLabel();
        cmbGenere = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        lblRating = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        lblGenere = new javax.swing.JLabel();
        rdoPelicula = new javax.swing.JRadioButton();
        rdoSerie = new javax.swing.JRadioButton();
        rdoVideojocs = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContinguts = new javax.swing.JTable();
        sldValoracio = new javax.swing.JSlider();
        txtUsuari = new javax.swing.JLabel();
        imgEstrella1 = new javax.swing.JLabel();
        imgEstrella2 = new javax.swing.JLabel();
        imgEstrella3 = new javax.swing.JLabel();
        imgEstrella4 = new javax.swing.JLabel();
        imgEstrella5 = new javax.swing.JLabel();
        txtValoracio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/logoCriticFy128p.png"))); // NOI18N
        imgLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgLogo.setMaximumSize(new java.awt.Dimension(50, 50));
        imgLogo.setMinimumSize(new java.awt.Dimension(50, 50));
        imgLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgLogoMousePressed(evt);
            }
        });

        cmbGenere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGenereActionPerformed(evt);
            }
        });

        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtBuscarMouseEntered(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        lblRating.setFont(new java.awt.Font("Liberation Serif", 1, 48)); // NOI18N
        lblRating.setForeground(new java.awt.Color(102, 102, 255));
        lblRating.setText("RATING");

        lblBuscar.setText("Buscar");

        lblGenere.setText("Genere");

        rdoPelicula.setSelected(true);
        rdoPelicula.setText("Pelicula");
        rdoPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPeliculaActionPerformed(evt);
            }
        });

        rdoSerie.setText("Serie");
        rdoSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSerieActionPerformed(evt);
            }
        });

        rdoVideojocs.setText("Videojocs");
        rdoVideojocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoVideojocsActionPerformed(evt);
            }
        });

        tblContinguts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblContinguts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblContingutsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblContinguts);

        sldValoracio.setMaximum(1000);
        sldValoracio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sldValoracioMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sldValoracioMouseMoved(evt);
            }
        });

        txtUsuari.setText("Usuari");

        imgEstrella1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella1.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella1.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella1MousePressed(evt);
            }
        });

        imgEstrella2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella2.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella2.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella2MousePressed(evt);
            }
        });

        imgEstrella3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella3.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella3.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella3MousePressed(evt);
            }
        });

        imgEstrella4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella4.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella4.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella4MousePressed(evt);
            }
        });

        imgEstrella5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella5.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella5.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella5MousePressed(evt);
            }
        });

        txtValoracio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtValoracioMouseEntered(evt);
            }
        });
        txtValoracio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValoracioActionPerformed(evt);
            }
        });
        txtValoracio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValoracioKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoVideojocs, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(rdoSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(rdoPelicula, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(cmbGenere, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(lblBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imgEstrella1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgEstrella2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgEstrella3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgEstrella4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgEstrella5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRating)
                        .addGap(120, 120, 120)
                        .addComponent(txtUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(356, 356, 356))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblGenere)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoPelicula)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoSerie)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoVideojocs)
                        .addGap(18, 18, 18)))
                .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgEstrella1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblRating))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(txtUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imgLogoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgLogoMousePressed
        // TODO add your handling code here:
        frmLogin login = new frmLogin();
        login.setVisible(true);
        login.setLocationRelativeTo(this);
        login.toFront();
        this.dispose();
        frmMainClient main = new frmMainClient();
        main.setLocationRelativeTo(this);
        main.setFocusable(false);
    }//GEN-LAST:event_imgLogoMousePressed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cmbGenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGenereActionPerformed
        // TODO add your handling code here:
        String genereSeleccionat = (String) cmbGenere.getSelectedItem();

        if (genereSeleccionat != null) {
            try {
                tblContinguts.setVisible(true);
                if (rdoPelicula.isSelected()) {
                    gestioSQL.BuscarPeliculesPerGenere(genereSeleccionat);
                    refrescarTaulaPelicules();
                } else if (rdoSerie.isSelected()) {
                    gestioSQL.BuscarSeriePerGenere(genereSeleccionat);
                    refrescarTaulaSeries();
                } else if (rdoVideojocs.isSelected()) {
                    gestioSQL.BuscarVideojocPerGenere(genereSeleccionat);
                    refrescarTaulaVideojoc();
                }
            } catch (SQLException ex) {
                System.err.println("Error al filtrar: " + ex.getMessage());
            }
        }

    }//GEN-LAST:event_cmbGenereActionPerformed

    private void rdoPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPeliculaActionPerformed
        // TODO add your handling code here:
        carregarPelicules();
    }//GEN-LAST:event_rdoPeliculaActionPerformed

    private void txtBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscarMouseEntered

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        try {
            if (rdoPelicula.isSelected()) {
                gestioSQL.BuscarPelicules(txtBuscar.getText());
                refrescarTaulaPelicules();
            } else if (rdoSerie.isSelected()) {
                gestioSQL.BuscarSerie(txtBuscar.getText());
                refrescarTaulaSeries();
            } else if (rdoVideojocs.isSelected()) {
                gestioSQL.BuscarVideojoc(txtBuscar.getText());
                refrescarTaulaVideojoc();
            }
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void rdoSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSerieActionPerformed
        // TODO add your handling code here:
        carregarSeries();
    }//GEN-LAST:event_rdoSerieActionPerformed

    private void rdoVideojocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoVideojocsActionPerformed
        // TODO add your handling code here:
        carregarVideojocs();

    }//GEN-LAST:event_rdoVideojocsActionPerformed

    private void sldValoracioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldValoracioMouseDragged
        // TODO add your handling code here:
        double valor = sldValoracio.getValue() / 10;
        actualitzarEstrelles(valor);
        txtValoracio.setText(String.valueOf(valor));
        try {
            if (rdoPelicula.isSelected()) {
                gestioSQL.BuscarPeliculaPerValoracio(valor);
                refrescarTaulaPelicules();
            } else if (rdoSerie.isSelected()) {
                gestioSQL.BuscarSeriePerValoracio(valor);
                refrescarTaulaSeries();
            } else if (rdoVideojocs.isSelected()) {
                gestioSQL.BuscarVideojocPerValoracio(valor);
                refrescarTaulaVideojoc();
            }
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_sldValoracioMouseDragged

    private void imgEstrella1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella1MousePressed
        // TODO add your handling code here:
        sldValoracio.setValue(200);
        try {
            actualitzarPerClicEstrella(20.0);
            
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_imgEstrella1MousePressed

    private void imgEstrella2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella2MousePressed
        // TODO add your handling code here:
        sldValoracio.setValue(400);
        try {
            actualitzarPerClicEstrella(40.0);
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_imgEstrella2MousePressed

    private void imgEstrella3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella3MousePressed
        // TODO add your handling code here:
        sldValoracio.setValue(600);
        try {
            actualitzarPerClicEstrella(60.0);
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_imgEstrella3MousePressed

    private void imgEstrella4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella4MousePressed
        // TODO add your handling code here:
        sldValoracio.setValue(800);
        try {
            actualitzarPerClicEstrella(80.0);
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_imgEstrella4MousePressed

    private void imgEstrella5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella5MousePressed
        // TODO add your handling code here:
        sldValoracio.setValue(1000);
        try {
            actualitzarPerClicEstrella(100.0);
        } catch (SQLException ex) {
            System.getLogger(frmMainClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }//GEN-LAST:event_imgEstrella5MousePressed

    private void sldValoracioMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldValoracioMouseMoved
        // TODO add your handling code here:
        double valor = sldValoracio.getValue();
        actualitzarEstrelles(valor / 10);
        txtValoracio.setText(String.valueOf(valor / 10));


    }//GEN-LAST:event_sldValoracioMouseMoved

    private void txtValoracioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtValoracioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValoracioMouseEntered

    private void txtValoracioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValoracioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValoracioActionPerformed

    private void txtValoracioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValoracioKeyReleased
        try {
            String text = txtValoracio.getText().replace(',', '.');
            if (text.isEmpty()) {
                sldValoracio.setValue(0);
                actualitzarEstrelles(0);
                return;
            }

            double valorNota = Double.parseDouble(text);

            if (valorNota >= 0 && valorNota <= 100) {
                sldValoracio.setValue((int) (valorNota * 10));

                actualitzarEstrelles(valorNota);

                this.repaint();
            }
        } catch (NumberFormatException e) {
        }
    }//GEN-LAST:event_txtValoracioKeyReleased

    private void tblContingutsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblContingutsMouseClicked
        // TODO add your handling code here:
        int fila = tblContinguts.getSelectedRow();

        if (fila != -1) {
            Object objecteSeleccionat = null;

            String seleccio = chkOpciocontingut.getSelection().getActionCommand();

            switch (seleccio) {
                case "PELICULA":
                objecteSeleccionat = pelicules.get(fila);
                break;
                case "SERIE":
                objecteSeleccionat = series.get(fila);
                break;
                case "VIDEOJOC":
                objecteSeleccionat = videojocs.get(fila);
                break;
            }

            if (objecteSeleccionat != null) {
                frmValoracio finestraValorar = new frmValoracio(objecteSeleccionat);
                finestraValorar.setVisible(true);
                finestraValorar.setLocationRelativeTo(null);
                frmMainClient fc = new frmMainClient();
                fc.setVisible(false);
            }
        }
    }//GEN-LAST:event_tblContingutsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frmMainClient().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbGenere;
    private javax.swing.JLabel imgEstrella1;
    private javax.swing.JLabel imgEstrella2;
    private javax.swing.JLabel imgEstrella3;
    private javax.swing.JLabel imgEstrella4;
    private javax.swing.JLabel imgEstrella5;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblGenere;
    private javax.swing.JLabel lblRating;
    private javax.swing.JRadioButton rdoPelicula;
    private javax.swing.JRadioButton rdoSerie;
    private javax.swing.JRadioButton rdoVideojocs;
    private javax.swing.JSlider sldValoracio;
    private javax.swing.JTable tblContinguts;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtUsuari;
    private javax.swing.JTextField txtValoracio;
    // End of variables declaration//GEN-END:variables
}
