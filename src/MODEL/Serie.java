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
public class Serie extends Contingut{
    private int capitols;
    private int temporada;

    public Serie(int capitols, int temporada, int id, String titol, String descripcio, int classificacio, byte[] imatge) {
        super(id, titol, descripcio, classificacio, imatge);
        this.capitols = capitols;
        this.temporada = temporada;
    }

    public int getCapitols() {
        return capitols;
    }

    public void setCapitols(int capitols) {
        this.capitols = capitols;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }
    
}
