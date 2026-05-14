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
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Rger Trulls
 */
public class frmValoracio extends javax.swing.JFrame {

    private Object contingut;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmValoracio.class.getName());

    /**
     * Creates new form frmValoracio
     */
    public frmValoracio(Object dades) {
        initComponents();
        this.contingut = dades;

        if (dades instanceof MODEL.Contingut) {
            MODEL.Contingut c = (MODEL.Contingut) dades;

            lblTitol.setText(c.getTitol());

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

        this.revalidate();
        this.repaint();
        this.pack();
        this.setLocationRelativeTo(null);
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

    private frmValoracio() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitol = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentari = new javax.swing.JTextArea();
        btnResenya = new javax.swing.JButton();
        imgEstrella4 = new javax.swing.JLabel();
        imgEstrella5 = new javax.swing.JLabel();
        txtValoracio = new javax.swing.JTextField();
        sldValoracio = new javax.swing.JSlider();
        imgEstrella1 = new javax.swing.JLabel();
        imgEstrella2 = new javax.swing.JLabel();
        imgEstrella3 = new javax.swing.JLabel();
        imgLogo = new javax.swing.JLabel();
        chkSpoiler = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitol.setFont(new java.awt.Font("Liberation Serif", 1, 48)); // NOI18N
        lblTitol.setForeground(new java.awt.Color(102, 102, 255));
        lblTitol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitol.setText("RATING");
        lblTitol.setMaximumSize(new java.awt.Dimension(2000, 999));
        lblTitol.setMinimumSize(new java.awt.Dimension(0, 0));

        txtComentari.setColumns(20);
        txtComentari.setRows(5);
        jScrollPane1.setViewportView(txtComentari);

        btnResenya.setText("ENTER");
        btnResenya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResenyaActionPerformed(evt);
            }
        });

        imgEstrella4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella4.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella4.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgEstrella4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella4MousePressed(evt);
            }
        });

        imgEstrella5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella5.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella5.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgEstrella5MouseClicked(evt);
            }
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

        sldValoracio.setMaximum(1000);
        sldValoracio.setValue(5);
        sldValoracio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sldValoracioMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sldValoracioMouseMoved(evt);
            }
        });

        imgEstrella1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella1.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella1.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgEstrella1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella1MousePressed(evt);
            }
        });

        imgEstrella2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella2.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella2.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgEstrella2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella2MousePressed(evt);
            }
        });

        imgEstrella3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/estrellaGris.PNG"))); // NOI18N
        imgEstrella3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgEstrella3.setMaximumSize(new java.awt.Dimension(50, 50));
        imgEstrella3.setMinimumSize(new java.awt.Dimension(50, 50));
        imgEstrella3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgEstrella3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEstrella3MousePressed(evt);
            }
        });

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/logoCriticFy128p.png"))); // NOI18N
        imgLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgLogo.setMaximumSize(new java.awt.Dimension(50, 50));
        imgLogo.setMinimumSize(new java.awt.Dimension(50, 50));
        imgLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgLogoMousePressed(evt);
            }
        });

        chkSpoiler.setText("Conte Spoilers?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(272, 272, 272)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(imgEstrella1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imgEstrella2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imgEstrella3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imgEstrella4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imgEstrella5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(103, 103, 103)
                                        .addComponent(chkSpoiler))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(lblTitol, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 138, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(357, 357, 357)
                .addComponent(btnResenya)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lblTitol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imgEstrella1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imgEstrella2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imgEstrella3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imgEstrella4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imgEstrella5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chkSpoiler)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnResenya)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

            int idContingut = 0;
            if (contingut instanceof Contingut) {
                idContingut = ((Contingut) contingut).getId();
            } else if (contingut instanceof Pelicula) {
                idContingut = ((Pelicula) contingut).getId();
            }

            String comentari = txtComentari.getText();
            String comentariBan = comentari;

            // 1. FILTRE I LÒGICA DE BANS
            if (Diccionari.esInadequat(comentariBan)) {
                int opcio = JOptionPane.showConfirmDialog(this,
                        "S'han detectat paraules inadequades. Vols continuar sota risc de sanció?",
                        "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (opcio == JOptionPane.YES_OPTION) {
                    int bansPrevis = DADES.gestioSQL.obtenirNumBans(usuariLoguejat);
                    int nivellSancio = bansPrevis + 1;

                    MODEL.Usuari.TipusBan nouEstat = MODEL.Usuari.TipusBan.active;
                    LocalDateTime dataFiBan = LocalDateTime.now();

                    if (nivellSancio == 1) {
                        nouEstat = MODEL.Usuari.TipusBan.warned;
                        JOptionPane.showMessageDialog(this, "Estat: WARNED.");
                        EscriureLog(mod + ":\t" + " Usuari " + usuariActual.getNom_usuari() + "\t\tIncompleix les normes de comportament;\t\t Estat del Ban: " + MODEL.Usuari.TipusBan.warned);
                    } else if (nivellSancio >= 2 && nivellSancio <= 4) {
                        nouEstat = MODEL.Usuari.TipusBan.soft_ban;
                        int dies = nivellSancio - 1;
                        dataFiBan = LocalDateTime.now().plusDays(dies);
                        JOptionPane.showMessageDialog(this, "SOFT_BAN: " + dies + " dia/es.");
                        EscriureLog(mod + ":\t" + " Usuari " + usuariActual.getNom_usuari() + "\t\tIncompleix les normes de comportament;\t\t Estat del Ban: " + MODEL.Usuari.TipusBan.warned);
                    } else {
                        nouEstat = MODEL.Usuari.TipusBan.hard_ban;
                        dataFiBan = LocalDateTime.now().plusDays(15);
                        JOptionPane.showMessageDialog(this, "HARD_BAN: 15 dies.");
                        EscriureLog(mod + ":\t" + " Usuari " + usuariActual.getNom_usuari() + "\t\tIncompleix les normes de comportament;\t\t Estat del Ban: " + MODEL.Usuari.TipusBan.warned);

                    }

                    DADES.gestioSQL.actualitzarEstatUsuari(usuariLoguejat, nouEstat, dataFiBan);
                    usuariActual.setEstat(nouEstat);
                    usuariActual.setData_ban(dataFiBan);

                    comentari = "[USUARI BANEJAT: no té permís per fer comentaris]";
                } else {
                    return; 
                }
            }

            if (usuariActual.getEstat() == MODEL.Usuari.TipusBan.soft_ban || usuariActual.getEstat() == MODEL.Usuari.TipusBan.hard_ban) {
                if (usuariActual.getData_ban() != null && usuariActual.getData_ban().isAfter(LocalDateTime.now())) {

                    if (usuariActual.getEstat() == MODEL.Usuari.TipusBan.soft_ban) {
                        txtComentari.setEnabled(false);
                        comentari = "[USUARI BANEJAT: no té permís per fer comentaris]";
                    } else {
                        JOptionPane.showMessageDialog(this, "No pots publicar fins a: "
                                + usuariActual.getData_ban().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                        return;
                    }
                }
                EscriureLog(mod + ":\t" + " Usuari " + usuariActual.getNom_usuari() + "\t\tValoracio del contingut:\t\t" + ((Contingut) contingut).getTitol());
            }

// 3. INSERCIÓ DE LA RESSENYA (Nota i Comentari filtrat/bloquejat)
            double nota = Double.parseDouble(txtValoracio.getText());
            boolean esSpoiler = chkSpoiler.isSelected();
            Resenya novaResenya = new Resenya(usuariLoguejat, idContingut, comentari, nota, esSpoiler, LocalDate.now());

            DADES.gestioSQL.insertResenya(novaResenya);
            JOptionPane.showMessageDialog(this, "Valoració enviada!");
            this.dispose();

            DADES.gestioSQL.insertResenya(novaResenya);
            JOptionPane.showMessageDialog(this, "Valoració enviada!");
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblTitol;
    private javax.swing.JSlider sldValoracio;
    private javax.swing.JTextArea txtComentari;
    private javax.swing.JTextField txtValoracio;
    // End of variables declaration//GEN-END:variables
}
