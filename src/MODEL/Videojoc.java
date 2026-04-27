/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.awt.image.BufferedImage;

/**
 *
 * @author Rger Trulls
 */
public class Videojoc extends Contingut{
    private double preu;

    public Videojoc(double preu, int id, String titol, String descripcio, int classificacio, BufferedImage imatge) {
        super(id, titol, descripcio, classificacio, imatge);
        this.preu = preu;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }
    
    
}
