/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import MODEL.Resenya;
import java.awt.Color;
import java.awt.Component;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rger Trulls
 */
public class frmLlistaResenyes extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmLlistaResenyes.class.getName());

    /**
     * Creates new form frmLlistaResenyes
     */
    private ArrayList<Resenya> totesLesResenyes;
    private DefaultTableModel modelTaula;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public frmLlistaResenyes() {
        initComponents();
        configurarTaula();
        carregarDadesBD();
        this.setLocationRelativeTo(null);
    }

    private void configurarTaula() {
        modelTaula = new DefaultTableModel(
                new Object[]{"Usuari", "Contingut", "Comentari", "Nota", "Data"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblResenyes.setModel(modelTaula);
        tblResenyes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // --- CONFIGURACIÓ DE LES COLUMNES ---
        // Usuari
        tblResenyes.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblResenyes.getColumnModel().getColumn(0).setMaxWidth(120);

        // Contingut (Títol) - Ara li donem una mica més d'espai que abans
        tblResenyes.getColumnModel().getColumn(1).setPreferredWidth(170);
        tblResenyes.getColumnModel().getColumn(1).setMaxWidth(250);

        // Comentari 
        tblResenyes.getColumnModel().getColumn(2).setPreferredWidth(350);

        // Nota
        tblResenyes.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblResenyes.getColumnModel().getColumn(3).setMaxWidth(70);

        // Data
        tblResenyes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblResenyes.getColumnModel().getColumn(4).setMaxWidth(120);

        // --- RENDERITZADOR (COLORS) ---
        tblResenyes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object valorCel = table.getValueAt(row, 2);
                String comentari = (valorCel != null) ? valorCel.toString() : "";

                if (comentari.startsWith("[COMENTARI BLOQUEJAT]")) {
                    c.setBackground(new Color(150, 40, 40)); // Vermell fosc
                    c.setForeground(Color.WHITE);
                } else if (comentari.startsWith("[USUARI BANEJAT")) {
                    c.setBackground(new Color(40, 40, 150)); // Blau fosc
                    c.setForeground(Color.WHITE);
                } else {
                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                        c.setForeground(table.getSelectionForeground());
                    } else {
                        c.setBackground(table.getBackground());
                        c.setForeground(table.getForeground());
                    }
                }
                return c;
            }
        });
    }

    private void carregarDadesBD() {
        try {
            totesLesResenyes = DADES.gestioSQL.obtenirTotesLesResenyes();
            actualitzarTaula(totesLesResenyes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al carregar ressenyes: " + e.getMessage());
        }
    }

    private void aplicarFiltres() {
        double notaMinima = jsFiltreNota.getValue() / 10.0;

        String usuariCercat = txtFiltreUsuari.getText().toLowerCase().trim();

        lblValorFiltre.setText("Nota mínima: " + notaMinima);

        ArrayList<Resenya> filtrades = new ArrayList<>();

        for (Resenya r : totesLesResenyes) {
            boolean compleixNota = r.getNota() >= notaMinima;
            boolean compleixUsuari = r.getUsuari().toLowerCase().contains(usuariCercat);

            if (compleixNota && compleixUsuari) {
                filtrades.add(r);
            }
        }

        actualitzarTaula(filtrades);
    }

    private void actualitzarTaula(ArrayList<Resenya> llista) {
        modelTaula.setRowCount(0);
        for (Resenya r : llista) {
            modelTaula.addRow(new Object[]{
                r.getUsuari(),
                r.getTitolContingut(),
                r.getDescripcio(),
                r.getNota(),
                r.getDataResenya().format(dtf)
            });
        }
        lblTotal.setText("Total: " + llista.size() + " ressenyes mostrades");
    }

    // <editor-fold defaultstate="collapsed" desc="Codi del Dissenyador de NetBeans">
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

    private void actualitzarDespresDeClic(double valor) {
        txtValoracio.setText(String.valueOf(valor));
        actualitzarEstrelles(valor);
        aplicarFiltres();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgEstrella3 = new javax.swing.JLabel();
        imgEstrella4 = new javax.swing.JLabel();
        imgEstrella5 = new javax.swing.JLabel();
        txtValoracio = new javax.swing.JTextField();
        jsFiltreNota = new javax.swing.JSlider();
        imgEstrella1 = new javax.swing.JLabel();
        imgEstrella2 = new javax.swing.JLabel();
        txtFiltreUsuari = new javax.swing.JTextField();
        lblValorFiltre = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResenyes = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnBan = new javax.swing.JButton();
        lblUsuari = new javax.swing.JLabel();
        imgLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jsFiltreNota.setMaximum(1000);
        jsFiltreNota.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jsFiltreNotaMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jsFiltreNotaMouseMoved(evt);
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

        txtFiltreUsuari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltreUsuariKeyReleased(evt);
            }
        });

        lblValorFiltre.setText("VALORACIO");

        lblTotal.setText("TOTAL");

        tblResenyes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblResenyes);

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBan.setText("BAN");
        btnBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanActionPerformed(evt);
            }
        });

        lblUsuari.setText("USUARI:");

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VIEW/logoCriticFy128p.png"))); // NOI18N
        imgLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgLogo.setMaximumSize(new java.awt.Dimension(50, 50));
        imgLogo.setMinimumSize(new java.awt.Dimension(50, 50));
        imgLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgLogoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(27, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblValorFiltre)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(jsFiltreNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(btnBan))
                            .addComponent(btnEliminar)
                            .addComponent(txtFiltreUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuari))
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFiltreUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(lblTotal)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnBan))
                .addGap(39, 39, 39)
                .addComponent(lblValorFiltre)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgEstrella1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgEstrella5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jsFiltreNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imgEstrella3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella3MousePressed

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
        try {
            String text = txtValoracio.getText().replace(',', '.');
            if (text.isEmpty()) {
                jsFiltreNota.setValue(0);
                actualitzarEstrelles(0);
                aplicarFiltres();
                return;
            }

            double valorNota = Double.parseDouble(text);
            if (valorNota >= 0 && valorNota <= 100) {
                jsFiltreNota.setValue((int) (valorNota * 10));
                actualitzarEstrelles(valorNota);
                aplicarFiltres();
            }
        } catch (NumberFormatException e) {
        }
    }//GEN-LAST:event_txtValoracioKeyReleased

    private void jsFiltreNotaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsFiltreNotaMouseDragged
        // TODO add your handling code here:
        double valor = jsFiltreNota.getValue() / 10.0;
        // Limitar a 1 decimal per evitar números llargs al text
        String valorFormatat = String.format(java.util.Locale.US, "%.1f", valor);
        txtValoracio.setText(valorFormatat);
        actualitzarEstrelles(valor);
        aplicarFiltres();
    }//GEN-LAST:event_jsFiltreNotaMouseDragged

    private void jsFiltreNotaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsFiltreNotaMouseMoved
        // TODO add your handling code here:
        double valor = jsFiltreNota.getValue() / 10.0;
        // Limitar a 1 decimal per evitar números llargs al text
        String valorFormatat = String.format(java.util.Locale.US, "%.1f", valor);
        txtValoracio.setText(valorFormatat);
        actualitzarEstrelles(valor);
        aplicarFiltres();

    }//GEN-LAST:event_jsFiltreNotaMouseMoved

    private void imgEstrella1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella1MousePressed

    private void imgEstrella2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgEstrella2MousePressed

    private void txtFiltreUsuariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltreUsuariKeyReleased
        // TODO add your handling code here:
        aplicarFiltres();
    }//GEN-LAST:event_txtFiltreUsuariKeyReleased

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int fila = tblResenyes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una ressenya primer");
            return;
        }

        String usuari = tblResenyes.getValueAt(fila, 0).toString();
        int idCont = (int) tblResenyes.getValueAt(fila, 1);

        int confirmar = JOptionPane.showConfirmDialog(this, "Segur que vols eliminar la ressenya de " + usuari + "?");

        if (confirmar == JOptionPane.YES_OPTION) {
            if (DADES.gestioSQL.eliminarResenya(usuari, idCont)) {
                carregarDadesBD();
                JOptionPane.showMessageDialog(this, "Eliminada correctament");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanActionPerformed
        // TODO add your handling code here:
        int fila = tblResenyes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona una ressenya.");
            return;
        }

        String nomUsuari = tblResenyes.getValueAt(fila, 0).toString();

        String historial = DADES.gestioSQL.obtenirResumCriminal(nomUsuari);

        MODEL.Usuari.TipusBan[] opcions = MODEL.Usuari.TipusBan.values();

        MODEL.Usuari.TipusBan estatEscollit = (MODEL.Usuari.TipusBan) JOptionPane.showInputDialog(
                this,
                historial + "\n\nTria el nou estat per a l'usuari:",
                "Gestió de Sancions",
                JOptionPane.WARNING_MESSAGE,
                null,
                opcions,
                opcions[0]
        );

        if (estatEscollit != null) {
            java.time.LocalDateTime dataFi;
            switch (estatEscollit) {
                case warned:
                    dataFi = java.time.LocalDateTime.now().plusDays(3);
                    break;
                case soft_ban:
                    dataFi = java.time.LocalDateTime.now().plusWeeks(1);
                    break;
                case hard_ban:
                    dataFi = java.time.LocalDateTime.now().plusYears(100);
                    break;
                default:
                    dataFi = java.time.LocalDateTime.now();
                    break; // active
            }

            try {
                // 5. Apliquem a la base de dades
                DADES.gestioSQL.actualitzarEstatUsuari(nomUsuari, estatEscollit, dataFi);
                JOptionPane.showMessageDialog(this, "Sanció aplicada: " + estatEscollit.name());
                carregarDadesBD();
            } catch (java.sql.SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de BD: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnBanActionPerformed

    private void imgEstrella1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella1MouseClicked
        // TODO add your handling code here:
        jsFiltreNota.setValue(200);
        actualitzarDespresDeClic(20.0);
    }//GEN-LAST:event_imgEstrella1MouseClicked

    private void imgEstrella2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella2MouseClicked
        // TODO add your handling code here:
        jsFiltreNota.setValue(400);
        actualitzarDespresDeClic(40.0);
    }//GEN-LAST:event_imgEstrella2MouseClicked

    private void imgEstrella3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella3MouseClicked
        // TODO add your handling code here:
        jsFiltreNota.setValue(600);
        actualitzarDespresDeClic(60.0);

    }//GEN-LAST:event_imgEstrella3MouseClicked

    private void imgEstrella4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella4MouseClicked
        // TODO add your handling code here:
        jsFiltreNota.setValue(800);
        actualitzarDespresDeClic(80.0);

    }//GEN-LAST:event_imgEstrella4MouseClicked

    private void imgEstrella5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEstrella5MouseClicked
        // TODO add your handling code here:
        jsFiltreNota.setValue(1000);
        actualitzarDespresDeClic(100.0);

    }//GEN-LAST:event_imgEstrella5MouseClicked

    private void imgLogoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgLogoMousePressed
        // TODO add your handling code here:
        frmPanelControl panel = new frmPanelControl();
        panel.setVisible(true);
        panel.setLocationRelativeTo(this);
        panel.toFront();
        this.dispose();
        frmLlistaResenyes resenyes = new frmLlistaResenyes();
        resenyes.setLocationRelativeTo(this);
        resenyes.setFocusable(false);
    }//GEN-LAST:event_imgLogoMousePressed

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
        java.awt.EventQueue.invokeLater(() -> new frmLlistaResenyes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBan;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel imgEstrella1;
    private javax.swing.JLabel imgEstrella2;
    private javax.swing.JLabel imgEstrella3;
    private javax.swing.JLabel imgEstrella4;
    private javax.swing.JLabel imgEstrella5;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jsFiltreNota;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuari;
    private javax.swing.JLabel lblValorFiltre;
    private javax.swing.JTable tblResenyes;
    private javax.swing.JTextField txtFiltreUsuari;
    private javax.swing.JTextField txtValoracio;
    // End of variables declaration//GEN-END:variables
}
