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
import static CONTROLLER.Main.videojocs;
import DADES.Connexio;
import DADES.gestioSQL;
import MODEL.Contingut;
import MODEL.Pelicula;
import MODEL.RenderImg;
import MODEL.Serie;
import MODEL.Videojoc;
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

    /**
     * Creates new form frmMainClient
     */
    public frmMainClient() {
        initComponents();
        tblContinguts.setVisible(false);

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

    }

    private void limpiar() {
        for (int i = mModelTaula.getRowCount() - 1; i >= 0; i--) {
            mModelTaula.removeRow(i);
        }
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
        try {
            if (imatgeBytes != null && imatgeBytes.length > 0) {
                InputStream inputStream = new ByteArrayInputStream(imatgeBytes);
                BufferedImage bufferedImage = ImageIO.read(inputStream);

                ImageIcon mIcon = new ImageIcon(
                        bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                );

                return new JLabel(mIcon);
            } else {
                throw new Exception("Sense dades d'imatge");
            }
        } catch (Exception e) {
            JLabel placeholder = new JLabel("");
            try {
                placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
            } catch (Exception ex) {
                placeholder.setText("No Img");
            }
            return placeholder;
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentari = new javax.swing.JTextArea();
        lblRating = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        lblGenere = new javax.swing.JLabel();
        lblComentari = new javax.swing.JLabel();
        rdoPelicula = new javax.swing.JRadioButton();
        rdoSerie = new javax.swing.JRadioButton();
        rdoVideojocs = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContinguts = new javax.swing.JTable();
        sldValoracio = new javax.swing.JSlider();
        lblPuntuacio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        txtComentari.setColumns(20);
        txtComentari.setRows(5);
        jScrollPane1.setViewportView(txtComentari);

        lblRating.setFont(new java.awt.Font("Liberation Serif", 1, 48)); // NOI18N
        lblRating.setForeground(new java.awt.Color(102, 102, 255));
        lblRating.setText("RATING");

        lblBuscar.setText("Buscar");

        lblGenere.setText("Genere");

        lblComentari.setText("Comentari");

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
        jScrollPane2.setViewportView(tblContinguts);

        sldValoracio.setMaximum(1000);
        sldValoracio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sldValoracioMouseDragged(evt);
            }
        });

        lblPuntuacio.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(rdoVideojocs, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComentari)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPuntuacio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(lblRating)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblGenere)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(rdoPelicula)
                .addGap(18, 18, 18)
                .addComponent(rdoSerie)
                .addGap(18, 18, 18)
                .addComponent(rdoVideojocs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPuntuacio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sldValoracio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblComentari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblRating)
                .addGap(57, 57, 57)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
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
        frmRegister register = new frmRegister();
        register.setLocationRelativeTo(this);
        register.setFocusable(false);
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
        tblContinguts.setVisible(false);
        carregarPelicules();
    }//GEN-LAST:event_rdoPeliculaActionPerformed

    private void txtBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseEntered
        // TODO add your handling code here:
        tblContinguts.setVisible(true);

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
        tblContinguts.setVisible(false);
        carregarSeries();
    }//GEN-LAST:event_rdoSerieActionPerformed

    private void rdoVideojocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoVideojocsActionPerformed
        // TODO add your handling code here:
        tblContinguts.setVisible(false);
        carregarVideojocs();

    }//GEN-LAST:event_rdoVideojocsActionPerformed

    private void sldValoracioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldValoracioMouseDragged
        // TODO add your handling code here:
        int valor = sldValoracio.getValue();

        lblPuntuacio.setText(String.valueOf(valor/10));
    }//GEN-LAST:event_sldValoracioMouseDragged

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
    private javax.swing.JLabel imgLogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblComentari;
    private javax.swing.JLabel lblGenere;
    private javax.swing.JLabel lblPuntuacio;
    private javax.swing.JLabel lblRating;
    private javax.swing.JRadioButton rdoPelicula;
    private javax.swing.JRadioButton rdoSerie;
    private javax.swing.JRadioButton rdoVideojocs;
    private javax.swing.JSlider sldValoracio;
    private javax.swing.JTable tblContinguts;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtComentari;
    // End of variables declaration//GEN-END:variables
}
