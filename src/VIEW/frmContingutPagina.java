/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import DADES.Connexio;
import MODEL.Contingut;
import MODEL.Genere;
import MODEL.RenderImg;
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

    /**
     * Creates new form frmContingutPagina
     */
    public frmContingutPagina() {

        mConnexio = new Connexio();
        mModelTaula.addColumn("ID");
        mModelTaula.addColumn("Títol");
        mModelTaula.addColumn("Descripció");
        mModelTaula.addColumn("Classificació");
        mModelTaula.addColumn("Imatge");
        mModelTaula.addColumn("Tipus");
        initComponents();

        btnTipus.add(btnSerie);
        btnTipus.add(btnPelicula);
        btnTipus.add(btnVideojoc);
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

                tblContinguts.getColumnModel().getColumn(0).setPreferredWidth(60);
                tblContinguts.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblContinguts.getColumnModel().getColumn(2).setPreferredWidth(200);
                tblContinguts.getColumnModel().getColumn(3).setPreferredWidth(80);
                tblContinguts.getColumnModel().getColumn(4).setPreferredWidth(80);
            }
        }
    }

    private void limpiar() {
        for (int i = mModelTaula.getRowCount() - 1; i >= 0; i--) {
            mModelTaula.removeRow(i);
        }
    }

    private void ompleComboGenere() {
        mConnexio = new Connexio();
        ArrayList<Genere> arrayGeneres = new ArrayList<Genere>();
        if (mConnexio != null) {
            try {

                mConnexio = new Connexio();
                Connection conn = mConnexio.connectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, nom FROM genere");
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

    private void limpiarCombo() {
        for (int i = cmbGenere.getItemCount() - 1; i >= 0; i--) {
            cmbGenere.remove(i);
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
        txtDescripcio = new javax.swing.JTextField();
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

        btnVideojoc.setText("Videojoc");

        jLabel4.setText("Genere");

        cmbClassificacio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "7", "12", "16", "18", "21" }));

        jPanel1.setBackground(new java.awt.Color(200, 200, 200));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbClassificacio, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVideojoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTitol, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                        .addComponent(txtDescripcio, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExaminar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTitol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(txtDescripcio, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(24, 24, 24)
                .addComponent(cmbClassificacio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnPelicula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSerie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVideojoc)
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExaminar)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        Contingut mContingut = new Contingut();
        if (mConnexio.connectarCon()) {
            mContingut.setTitol(txtTitol.getText());
            mContingut.setDescripcio(txtDescripcio.getText());
            mContingut.setClassificacio(Integer.parseInt(cmbClassificacio.getSelectedItem().toString()));
            mContingut.setImatge(getImatge(ruta));
            mConnexio.AgregarImg(mContingut);
            limpiar();
            carregarContinguts();
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
        if (txtTitol.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTitolKeyTyped

    private void txtTitolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitolActionPerformed

    private void btnPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeliculaActionPerformed

    private void cmbGenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGenereActionPerformed


    }//GEN-LAST:event_cmbGenereActionPerformed

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
    private javax.swing.JButton btnExaminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox btnPelicula;
    private javax.swing.JCheckBox btnSerie;
    private javax.swing.JCheckBox btnVideojoc;
    private javax.swing.JComboBox<String> cmbClassificacio;
    private javax.swing.JComboBox<String> cmbGenere;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImg;
    private javax.swing.JTable tblContinguts;
    private javax.swing.JTextField txtDescripcio;
    private javax.swing.JTextField txtTitol;
    // End of variables declaration//GEN-END:variables
}
