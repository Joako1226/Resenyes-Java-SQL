/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import static CONTROLLER.Main.password;
import static CONTROLLER.Main.url;
import static CONTROLLER.Main.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;

/**
 *
 * @author Rger Trulls
 */
public class frmMainClient extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmMainClient.class.getName());
    private ButtonGroup chkOpciocontingut = new ButtonGroup();

    /**
     * Creates new form frmMainClient
     */
    public frmMainClient() {
        initComponents();
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

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
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

        rdoSerie.setText("Serie");

        rdoVideojocs.setText("Videojocs");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rdoVideojocs, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdoSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdoPelicula, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                                .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(lblRating))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(233, 233, 233)
                                        .addComponent(lblGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblComentari))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblRating))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGenere)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbGenere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdoPelicula)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoSerie)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoVideojocs)))
                .addGap(45, 45, 45)
                .addComponent(lblBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(lblComentari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
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
        java.awt.EventQueue.invokeLater(() -> new frmMainClient().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbGenere;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblComentari;
    private javax.swing.JLabel lblGenere;
    private javax.swing.JLabel lblRating;
    private javax.swing.JRadioButton rdoPelicula;
    private javax.swing.JRadioButton rdoSerie;
    private javax.swing.JRadioButton rdoVideojocs;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtComentari;
    // End of variables declaration//GEN-END:variables
}
