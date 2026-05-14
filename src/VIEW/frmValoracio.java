/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import static CONTROLLER.Main.mod;
import static DADES.GestioLog.EscriureLog;
import DADES.gestioSQL;
import MODEL.Contingut;
import MODEL.Pelicula;
import MODEL.Diccionari;
import MODEL.Resenya;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import java.util.ArrayList;

import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rger Trulls
 */
public class frmValoracio extends javax.swing.JFrame {

    private Object contingut;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmValoracio.class.getName());
    private static DefaultTableModel mModelTaula = new DefaultTableModel();

    /**
     * Creates new form frmValoracio
     */
    public frmValoracio(Object dades) {
        initComponents();
        this.contingut = dades;
        carregarResenyesTaula();

        if (dades instanceof MODEL.Contingut) {
            MODEL.Contingut c = (MODEL.Contingut) dades;

            lblTitol.setText(c.getTitol());
            txtDescripcio.setText(dividirText(c.getDescripcio(), 22));

            try {
                byte[] imatgeBytes = c.getImatge();

                if (imatgeBytes != null && imatgeBytes.length > 0) {
                    InputStream is = new ByteArrayInputStream(imatgeBytes);
                    BufferedImage img = ImageIO.read(is);

                    if (img != null) {
                        int maxAmple = 400;
                        int maxAlt = 300;

                        double ratio = Math.min((double) maxAmple / img.getWidth(), (double) maxAlt / img.getHeight());
                        int nouAmple = (int) (img.getWidth() * ratio);
                        int nouAlt = (int) (img.getHeight() * ratio);

                        Image escalada = img.getScaledInstance(nouAmple, nouAlt, Image.SCALE_SMOOTH);
                        lblImg.setIcon(new ImageIcon(escalada));
                        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        lblImg.setPreferredSize(new java.awt.Dimension(nouAmple, nouAlt));
                        lblImg.setText("");
                    } else {
                        lblImg.setText("Error en format d'imatge");
                    }
                } else {
                    lblImg.setText("Sense imatge disponible");
                }
            } catch (Exception e) {
                lblImg.setText("Error al carregar imatge");
                e.printStackTrace();
            }
        }

    }

    public String dividirText(String text, int maxChars) {
        StringBuilder resultat = new StringBuilder();
        int comptador = 0;

        for (String paraula : text.split(" ")) {

            if (comptador + paraula.length() > maxChars) {
                resultat.append("\n");
                comptador = 0;
            }

            resultat.append(paraula).append(" ");
            comptador += paraula.length() + 1;
        }

        return resultat.toString();

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

    private void actualitzarPerClicEstrella(double valor) {
        sldValoracio.setValue((int) (valor * 10));
        txtValoracio.setText(String.valueOf(valor));
        actualitzarEstrelles(valor);
    }

    public void actualitzarEstrelles(double valor) {
        String full = "/IMAGES/estrellaFull.png";
        String mitja = "/IMAGES/estrellaMitja.png";
        String buida = "/IMAGES/estrellaGris.png";

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

    private frmValoracio() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void limpiar() {
        DefaultTableModel tb = (DefaultTableModel) tblResenyes.getModel();
        tb.setRowCount(0);
    }

    private void carregarResenyesTaula() {

        mModelTaula.setRowCount(0);

        String[] columnes = {"Usuari", "Comentari", "Nota"};
        mModelTaula.setColumnIdentifiers(columnes);
        tblResenyes.setModel(mModelTaula);

        if (!(contingut instanceof MODEL.Contingut)) {
            return;
        }

        int idContingut = ((MODEL.Contingut) contingut).getId();

        System.out.println("ID contingut: " + idContingut);

        gestioSQL re = new gestioSQL();
        ArrayList<Resenya> resenyes = re.carregarResenyes(idContingut);

        for (Resenya r : resenyes) {
            mModelTaula.addRow(new Object[]{
                r.getUsuari(),
                r.getDescripcio(),
                r.getNota()
            });
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

        // --- 1. INICIALITZACIÓ DE COMPONENTS ---
        lblTitol = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        imgLogo = new javax.swing.JLabel();
        btnResenya = new javax.swing.JButton();
        chkSpoiler = new javax.swing.JCheckBox();
        txtValoracio = new javax.swing.JTextField();
        sldValoracio = new javax.swing.JSlider();
        
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentari = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResenyes = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcio = new javax.swing.JTextArea();

        imgEstrella1 = new javax.swing.JLabel();
        imgEstrella2 = new javax.swing.JLabel();
        imgEstrella3 = new javax.swing.JLabel();
        imgEstrella4 = new javax.swing.JLabel();
        imgEstrella5 = new javax.swing.JLabel();

        // --- 2. CONFIGURACIÓ DE PROPIETATS ---
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitol.setFont(new java.awt.Font("Liberation Serif", 1, 48)); 
        lblTitol.setForeground(new java.awt.Color(102, 102, 255));
        lblTitol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitol.setText("CRITICFY RATING");

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/logoCriticFy128p.png"))); 

        txtComentari.setColumns(20);
        txtComentari.setRows(5);
        jScrollPane1.setViewportView(txtComentari);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Escriu la teva ressenya"));

        txtDescripcio.setEditable(false);
        txtDescripcio.setColumns(20);
        txtDescripcio.setRows(5);
        txtDescripcio.setLineWrap(true);
        jScrollPane3.setViewportView(txtDescripcio);
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripció"));

        btnResenya.setText("ENTER");
        btnResenya.addActionListener(evt -> btnResenyaActionPerformed(evt));

        sldValoracio.setMaximum(10);
        sldValoracio.setValue(5);
        sldValoracio.addChangeListener(e -> txtValoracio.setText(String.valueOf(sldValoracio.getValue())));

        chkSpoiler.setText("Conte Spoilers?");

        // Configuració d'icones per estrelles (exempte d'una per estalviar espai, repeteix per a totes)
        javax.swing.JLabel[] estrelles = {imgEstrella1, imgEstrella2, imgEstrella3, imgEstrella4, imgEstrella5};
        for (int i = 0; i < estrelles.length; i++) {
            estrelles[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG")));
            final int index = i + 1;
            estrelles[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    // Aquí cridaries al teu mètode de gestió d'estrelles
                }
            });
        }

        tblResenyes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] { {null, null, null, null}, {null, null, null, null} },
            new String [] { "Usuari", "Nota", "Comentari", "Spoiler" }
        ));
        jScrollPane2.setViewportView(tblResenyes);

        // --- 3. DISSENY DEL LAYOUT (NET) ---
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    // Capçalera: Logo + Títol
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    // Cos principal
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(imgEstrella1).addGap(5).addComponent(imgEstrella2).addGap(5)
                                .addComponent(imgEstrella3).addGap(5).addComponent(imgEstrella4).addGap(5)
                                .addComponent(imgEstrella5))
                            .addComponent(sldValoracio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkSpoiler))
                            .addComponent(jScrollPane1)
                            .addComponent(btnResenya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imgLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(imgEstrella1).addComponent(imgEstrella2).addComponent(imgEstrella3)
                            .addComponent(imgEstrella4).addComponent(imgEstrella5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkSpoiler))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnResenya))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void imgEstrella4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella4MousePressed

    private void imgEstrella5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella5MousePressed

    private void txtValoracioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtValoracioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValoracioMouseEntered

    private void txtValoracioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValoracioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValoracioActionPerformed

    private void txtValoracioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValoracioKeyReleased
        /*  try {
            String text = txtValoracio.getText().replace(',', '.');
            if (text.isEmpty()) {
                sldValoracio.setValue(0);
                actualitzarEstrelles(0);
                return;
            }

            double valorNota = Double.parseDouble(text);

            if (valorNota >= 0 && valorNota <= 100) {
                sldValoracio.setValue((int) (valorNota * 10)); // L'slider es mou bé (x10)

                actualitzarEstrelles(valorNota);

                this.repaint();
            }
        } catch (NumberFormatException e) {
        }*/
    }//GEN-LAST:event_txtValoracioKeyReleased

    private void sldValoracioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldValoracioMouseDragged
        // TODO add your handling code here:
        double valor = sldValoracio.getValue() / 10;
        actualitzarEstrelles(valor);
        txtValoracio.setText(String.valueOf(valor));

    }//GEN-LAST:event_sldValoracioMouseDragged

    private void sldValoracioMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldValoracioMouseMoved
        // TODO add your handling code here:
        double valor = sldValoracio.getValue();
        actualitzarEstrelles(valor / 10);
        txtValoracio.setText(String.valueOf(valor / 10));

    }//GEN-LAST:event_sldValoracioMouseMoved

    private void imgEstrella1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella1MousePressed

    private void imgEstrella2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella2MousePressed

    private void imgEstrella3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella3MousePressed

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

    private void btnResenyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResenyaActionPerformed
        try {
            MODEL.Usuari usuariActual = CONTROLLER.Main.usuariActual;
            String usuariLoguejat = usuariActual.getNom_usuari();

            // 1. Obtenir ID del contingut de forma segura
            int idContingut = 0;
            if (contingut instanceof MODEL.Contingut) {
                idContingut = ((MODEL.Contingut) contingut).getId();
            } else if (contingut instanceof MODEL.Pelicula) {
                idContingut = ((MODEL.Pelicula) contingut).getId();
            }

            String comentari = txtComentari.getText();

            // 2. FILTRE I LÒGICA DE BANS
            if (MODEL.Diccionari.esInadequat(comentari)) {
                int opcio = JOptionPane.showConfirmDialog(this,
                        "S'han detectat paraules inadequades. Vols continuar sota risc de sanció?",
                        "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (opcio == JOptionPane.YES_OPTION) {
                    int bansPrevis = DADES.gestioSQL.obtenirNumBans(usuariLoguejat);
                    int nivellSancio = bansPrevis + 1;

                    MODEL.Usuari.TipusBan nouEstat;
                    LocalDateTime dataFiBan = LocalDateTime.now();

                    if (nivellSancio == 1) {
                        nouEstat = MODEL.Usuari.TipusBan.warned;
                        JOptionPane.showMessageDialog(this, "Estat: WARNED.");
                    } else if (nivellSancio >= 2 && nivellSancio <= 4) {
                        nouEstat = MODEL.Usuari.TipusBan.soft_ban;
                        int dies = nivellSancio - 1;
                        dataFiBan = LocalDateTime.now().plusDays(dies);
                        JOptionPane.showMessageDialog(this, "SOFT_BAN: " + dies + " dia/es.");
                    } else {
                        nouEstat = MODEL.Usuari.TipusBan.hard_ban;
                        dataFiBan = LocalDateTime.now().plusDays(15);
                        JOptionPane.showMessageDialog(this, "HARD_BAN: 15 dies.");
                    }

                    // Apliquem la sanció a la BBDD i a l'objecte actual
                    DADES.gestioSQL.actualitzarEstatUsuari(usuariLoguejat, nouEstat, dataFiBan);
                    usuariActual.setEstat(nouEstat);
                    usuariActual.setData_ban(dataFiBan);

                    EscriureLog(mod + ":\t Usuari " + usuariLoguejat + " sancionat a estat " + nouEstat);

                    // Forçem el text banejats
                    comentari = "[USUARI BANEJAT: contingut inadequat]";
                } else {
                    return; // L'usuari ha cancel·lat l'acció
                }
            }

            // 3. VERIFICACIÓ DE BAN ACTIU (Si ja estava banejat d'abans)
            if (usuariActual.getEstat() == MODEL.Usuari.TipusBan.soft_ban || usuariActual.getEstat() == MODEL.Usuari.TipusBan.hard_ban) {
                if (usuariActual.getData_ban() != null && usuariActual.getData_ban().isAfter(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(this, "No pots publicar fins a: "
                            + usuariActual.getData_ban().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    return;
                }
            }

            // 4. INSERCIÓ DE LA RESSENYA
            // Comprovem que el camp de valoració no estigui buit
            if (txtValoracio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Si us plau, posa una nota.");
                return;
            }

            double nota = Double.parseDouble(txtValoracio.getText());
            boolean esSpoiler = chkSpoiler.isSelected();
            Resenya novaResenya = new Resenya(usuariLoguejat, idContingut, comentari, nota, esSpoiler, LocalDate.now());

            DADES.gestioSQL.insertResenya(novaResenya);

            JOptionPane.showMessageDialog(this, "Valoració enviada amb èxit!");
            EscriureLog(mod + ":\t Usuari " + usuariLoguejat + " ha valorat l'ID " + idContingut);

            this.dispose();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "La nota ha de ser un número vàlid.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Ja tens una resenya per aquest contingut o hi ha un error de connexió.");
            e.printStackTrace();
        }    }//GEN-LAST:event_btnResenyaActionPerformed

    private void imgEstrella1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella1MouseClicked
        // TODO add your handling code here:
        sldValoracio.setValue(200);
        actualitzarPerClicEstrella(20.0);

    }//GEN-LAST:event_imgEstrella1MouseClicked

    private void imgEstrella2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella2MouseClicked
        // TODO add your handling code here:
        sldValoracio.setValue(400);
        actualitzarPerClicEstrella(40.0);

    }//GEN-LAST:event_imgEstrella2MouseClicked

    private void imgEstrella3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella3MouseClicked
        // TODO add your handling code here:
        sldValoracio.setValue(600);
        actualitzarPerClicEstrella(60.0);

    }//GEN-LAST:event_imgEstrella3MouseClicked

    private void imgEstrella4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella4MouseClicked
        // TODO add your handling code here:
        sldValoracio.setValue(800);
        actualitzarPerClicEstrella(80.0);

    }//GEN-LAST:event_imgEstrella4MouseClicked

    private void imgEstrella5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella5MouseClicked
        // TODO add your handling code here:
        sldValoracio.setValue(1000);
        actualitzarPerClicEstrella(100.0);

    }//GEN-LAST:event_imgEstrella5MouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new frmValoracio().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResenya;
    private javax.swing.JCheckBox chkSpoiler;
    private javax.swing.JLabel imgEstrella1;
    private javax.swing.JLabel imgEstrella2;
    private javax.swing.JLabel imgEstrella3;
    private javax.swing.JLabel imgEstrella4;
    private javax.swing.JLabel imgEstrella5;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblTitol;
    private javax.swing.JSlider sldValoracio;
    private javax.swing.JTable tblResenyes;
    private javax.swing.JTextArea txtComentari;
    private javax.swing.JTextArea txtDescripcio;
    private javax.swing.JTextField txtValoracio;
    // End of variables declaration//GEN-END:variables
}
