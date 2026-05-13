/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import DADES.Connexio;
import MODEL.Contingut;
import MODEL.Genere;
import MODEL.GenereContingut;
import MODEL.Pelicula;
import MODEL.RenderImg;
import MODEL.Serie;
import MODEL.Videojoc;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joaquin
 */
public class frmContingutPagina extends javax.swing.JFrame {
    
    Connexio mConnexio;
    DefaultTableModel mModelTaula = new DefaultTableModel();
    String ruta = "";
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmContingutPagina.class.getName());
    private ButtonGroup btnTipus = new ButtonGroup();
    private ButtonGroup btnEdicio = new ButtonGroup();

    /**
     * Creates new form frmContingutPagina
     */
    public frmContingutPagina() {
        initComponents();
        mConnexio = new Connexio();
        mModelTaula.addColumn("ID");
        mModelTaula.addColumn("Títol");
        mModelTaula.addColumn("Descripció");
        mModelTaula.addColumn("Classificació");
        mModelTaula.addColumn("Imatge");
        mModelTaula.addColumn("Tipus");
        
        btnTipus.add(btnSerie);
        btnTipus.add(btnPelicula);
        btnTipus.add(btnVideojoc);
        btnPelicula.setActionCommand("PELICULA");
        btnSerie.setActionCommand("SERIE");
        btnVideojoc.setActionCommand("VIDEOJOC");
        
        btnEdicio.add(btnAfegir);
        btnEdicio.add(btnModificar);
        
        carregarContinguts();
        ompleComboGenere();
        
    }
    
    private void carregarContinguts() {
        limpiar();
        tblContinguts.setDefaultRenderer(Object.class, new RenderImg());
        
        ArrayList imatges;
        Contingut mContingut;
        
        if (mConnexio.connectarCon()) {
            
            mModelTaula.setRowCount(0);
            
            Object[] Dades = new Object[6];
            imatges = mConnexio.CarregarImg();
            
            if (imatges != null) {
                for (int i = imatges.size() - 1; i >= 0; i--) {
                    
                    mContingut = (Contingut) imatges.get(i);
                    
                    Dades[0] = String.valueOf(mContingut.getId());
                    Dades[1] = mContingut.getTitol();
                    Dades[2] = mContingut.getDescripcio();
                    Dades[3] = mContingut.getClassificacio();
                    
                    try {
                        byte[] imatge = mContingut.getImatge();
                        BufferedImage bufferedImage = null;
                        InputStream inputStream = new ByteArrayInputStream(imatge);
                        bufferedImage = ImageIO.read(inputStream);
                        
                        ImageIcon mIcon = new ImageIcon(
                                bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                        );
                        
                        Dades[4] = new JLabel(mIcon);
                        
                    } catch (Exception e) {
                        JLabel placeholder = new JLabel("");
                        placeholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/placeHolderImg.jpg")));
                        Dades[4] = placeholder;
                    }
                    Dades[5] = mContingut.getClass().getSimpleName();
                    mModelTaula.addRow(Dades);
                }
                
                tblContinguts.setModel(mModelTaula);
                tblContinguts.setRowHeight(60);
                
                tblContinguts.getColumnModel().getColumn(0).setPreferredWidth(40);
                tblContinguts.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblContinguts.getColumnModel().getColumn(2).setPreferredWidth(200);
                tblContinguts.getColumnModel().getColumn(3).setPreferredWidth(80);
                tblContinguts.getColumnModel().getColumn(4).setPreferredWidth(80);
                tblContinguts.getColumnModel().getColumn(5).setPreferredWidth(100);
            }
        }
    }
    
    private void limpiar() {
        for (int i = mModelTaula.getRowCount() - 1; i >= 0; i--) {
            mModelTaula.removeRow(i);
        }
    }
    
    private void ompleComboGenere() {
        cmbGenere.removeAllItems();
        mConnexio = new Connexio();
        ArrayList<Genere> arrayGeneres = new ArrayList<Genere>();
        if (mConnexio != null) {
            try {
                
                mConnexio = new Connexio();
                Connection conn = mConnexio.connectar();
                Statement stmt = conn.createStatement();
                String sql;
                String tipus = btnTipus.getSelection().getActionCommand();
                
                ResultSet rs = stmt.executeQuery(tipusSeleccionat());
                while (rs.next()) {
                    Genere genere = new Genere(
                            rs.getInt("id"),
                            rs.getString("nom")
                    );
                    arrayGeneres.add(genere);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: connexio null");
        }
        for (Genere g : arrayGeneres) {
            cmbGenere.addItem(g.getNom());
        }
        
    }
    
    private String tipusSeleccionat() {
        
        if (btnTipus.getSelection() == null) {
            return "SELECT id, nom FROM genere ORDER BY nom ASC";
        }
        
        String seleccio = btnTipus.getSelection().getActionCommand();
        
        switch (seleccio) {
            case "PELICULA":
                numCapitols.setEnabled(false);
                numTemporades.setEnabled(false);
                numPreu.setEnabled(false);
                txtDuracio.setEnabled(true);
                txtDirector.setEnabled(true);
                return "SELECT DISTINCT g.id, g.nom FROM genere g "
                        + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                        + "INNER JOIN serie s ON gc.idContingut = s.idSerie "
                        + "ORDER BY g.nom ASC";
            
            case "SERIE":
                numCapitols.setEnabled(true);
                numTemporades.setEnabled(true);
                numPreu.setEnabled(false);
                txtDuracio.setEnabled(false);
                txtDirector.setEnabled(false);
                return "SELECT DISTINCT g.id, g.nom FROM genere g "
                        + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                        + "INNER JOIN serie s ON gc.idContingut = s.idSerie "
                        + "ORDER BY g.nom ASC";
            
            case "VIDEOJOC":
                numCapitols.setEnabled(false);
                numTemporades.setEnabled(false);
                numPreu.setEnabled(true);
                txtDuracio.setEnabled(false);
                txtDirector.setEnabled(false);
                return "SELECT DISTINCT g.id, g.nom FROM genere g "
                        + "INNER JOIN genere_contingut gc ON g.id = gc.idGenere "
                        + "INNER JOIN videojoc v ON gc.idContingut = v.idJoc "
                        + "ORDER BY g.nom ASC";
            
            default:
                return "SELECT id, nom FROM genere ORDER BY nom ASC";
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblContinguts = new javax.swing.JTable();
        txtTitol = new javax.swing.JTextField();
        btnExaminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbGenere = new javax.swing.JComboBox<>();
        btnPelicula = new javax.swing.JCheckBox();
        btnSerie = new javax.swing.JCheckBox();
        btnVideojoc = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cmbClassificacio = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        lblImg = new javax.swing.JLabel();
        numCapitols = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        numTemporades = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDirector = new javax.swing.JTextField();
        txtDuracio = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        numPreu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        imgLogo = new javax.swing.JLabel();
        btnAfegir = new javax.swing.JRadioButton();
        btnModificar = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcio = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jScrollPane1.setViewportView(tblContinguts);

        txtTitol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTitolActionPerformed(evt);
            }
        });
        txtTitol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTitolKeyTyped(evt);
            }
        });

        btnExaminar.setText("Examinar");
        btnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel1.setText("Titol");

        jLabel2.setText("Descripcio");

        jLabel3.setText("Clasificacio Per edats");

        cmbGenere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGenereActionPerformed(evt);
            }
        });

        btnPelicula.setSelected(true);
        btnPelicula.setText("Pelicula");
        btnPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeliculaActionPerformed(evt);
            }
        });

        btnSerie.setText("Serie");
        btnSerie.setToolTipText("");
        btnSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSerieActionPerformed(evt);
            }
        });

        btnVideojoc.setText("Videojoc");
        btnVideojoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVideojocActionPerformed(evt);
            }
        });

        jLabel4.setText("Genere");

        cmbClassificacio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "7", "12", "16", "18", "21" }));
        cmbClassificacio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClassificacioActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(200, 200, 200));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
        );

        jLabel5.setText("Capitols");

        jLabel6.setText("Temporades");

        jLabel7.setText("Duració");

        jLabel8.setText("Director");

        txtDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirectorActionPerformed(evt);
            }
        });
        txtDirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDirectorKeyTyped(evt);
            }
        });

        txtDuracio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuracioActionPerformed(evt);
            }
        });
        txtDuracio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDuracioKeyTyped(evt);
            }
        });

        numPreu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numPreuActionPerformed(evt);
            }
        });
        numPreu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numPreuKeyTyped(evt);
            }
        });

        jLabel9.setText("Preu");

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/logoCriticFy128p.png"))); // NOI18N
        imgLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgLogo.setMaximumSize(new java.awt.Dimension(50, 50));
        imgLogo.setMinimumSize(new java.awt.Dimension(50, 50));
        imgLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgLogoMousePressed(evt);
            }
        });

        btnAfegir.setSelected(true);
        btnAfegir.setText("Afegir");

        btnModificar.setText("Modificar");

        txtDescripcio.setColumns(20);
        txtDescripcio.setRows(5);
        jScrollPane2.setViewportView(txtDescripcio);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTitol, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVideojoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cmbClassificacio, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(btnSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(145, 145, 145))
                    .addComponent(jScrollPane2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(txtDuracio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(numPreu, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numTemporades, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(numCapitols, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExaminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                        .addGap(0, 142, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAfegir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTitol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jLabel6))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(numCapitols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(numTemporades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel5))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(numPreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbClassificacio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(btnPelicula)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSerie)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnVideojoc)
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel4)
                                        .addGap(16, 16, 16)
                                        .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnExaminar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnGuardar)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDuracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)))
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(btnAfegir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificar)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private byte[] getImatge(String ruta) {
        File imatge = new File(ruta);
        try {
            byte[] icona = new byte[(int) imatge.length()];
            InputStream input = new FileInputStream(imatge);
            input.read(icona);
            return icona;
        } catch (Exception ex) {
            return null;
        }
    }
    

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String tipus = btnTipus.getSelection().getActionCommand();

        Contingut mContingut;

        switch (tipus) {
            case "PELICULA":
                mContingut = new Pelicula(); 
                ((Pelicula) mContingut).setDirector(txtDirector.getText());
                String textDuracio = txtDuracio.getText(); 
                ((Pelicula) mContingut).setDuracio(java.time.LocalTime.parse(textDuracio));
                break;

            case "SERIE":
                mContingut = new Serie();
                ((Serie) mContingut).setCapitols((int) numCapitols.getValue());
                ((Serie) mContingut).setTemporada((int) numTemporades.getValue());
                break;

            case "VIDEOJOC":
                mContingut = new Videojoc();
                ((Videojoc) mContingut).setPreu(Double.parseDouble(numPreu.getText()));
                break;

            default:
                mContingut = new Contingut();
                break;
        }

        mContingut.setTitol(txtTitol.getText());
        mContingut.setDescripcio(txtDescripcio.getText());
        int classificacioVal = Integer.parseInt(cmbClassificacio.getSelectedItem().toString());
        mContingut.setClassificacio(classificacioVal);
        if (!ruta.equals("")) {
            mContingut.setImatge(getImatge(ruta));
        }

        if (mConnexio.connectarCon()) {
            if (mConnexio.guardarContingut(mContingut)) {
                carregarContinguts();
            }
        }
        }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(extensionFilter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ruta = fileChooser.getSelectedFile().getAbsolutePath();
            Image mImatge = new ImageIcon(ruta).getImage();
            ImageIcon mIcona = new ImageIcon(mImatge.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), 0));
            lblImg.setIcon(mIcona);
        }
    }//GEN-LAST:event_btnExaminarActionPerformed

    private void txtTitolKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTitolKeyTyped
        if (txtTitol.getText().length() >= 40) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTitolKeyTyped

    private void txtTitolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitolActionPerformed

    private void btnPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculaActionPerformed
        ompleComboGenere();
    }//GEN-LAST:event_btnPeliculaActionPerformed

    private void cmbGenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGenereActionPerformed
        

    }//GEN-LAST:event_cmbGenereActionPerformed

    private void btnSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSerieActionPerformed
        ompleComboGenere();
    }//GEN-LAST:event_btnSerieActionPerformed

    private void btnVideojocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVideojocActionPerformed
        ompleComboGenere();
    }//GEN-LAST:event_btnVideojocActionPerformed

    private void txtDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirectorActionPerformed

    private void txtDirectorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDirectorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirectorKeyTyped

    private void txtDuracioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuracioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuracioActionPerformed

    private void txtDuracioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDuracioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuracioKeyTyped

    private void numPreuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numPreuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numPreuActionPerformed

    private void numPreuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numPreuKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_numPreuKeyTyped

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

    private void cmbClassificacioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClassificacioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbClassificacioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new frmContingutPagina().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnAfegir;
    private javax.swing.JButton btnExaminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JRadioButton btnModificar;
    private javax.swing.JCheckBox btnPelicula;
    private javax.swing.JCheckBox btnSerie;
    private javax.swing.JCheckBox btnVideojoc;
    private javax.swing.JComboBox<String> cmbClassificacio;
    private javax.swing.JComboBox<String> cmbGenere;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblImg;
    private javax.swing.JSpinner numCapitols;
    private javax.swing.JTextField numPreu;
    private javax.swing.JSpinner numTemporades;
    private javax.swing.JTable tblContinguts;
    private javax.swing.JTextArea txtDescripcio;
    private javax.swing.JTextField txtDirector;
    private javax.swing.JTextField txtDuracio;
    private javax.swing.JTextField txtTitol;
    // End of variables declaration//GEN-END:variables
}
