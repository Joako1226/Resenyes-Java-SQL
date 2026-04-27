/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

/**
 *
 * @author Rger Trulls
 */
public class Contingut {

    private int id;
    private String titol;
    private String descripcio;
    private int classificacio;
    private byte [] imatge;

    public Contingut(int id, String titol, String descripcio, int classificacio, byte[] imatge) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.classificacio = classificacio;
        this.imatge = imatge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getClassificacio() {
        return classificacio;
    }

    public void setClassificacio(int classificacio) {
        this.classificacio = classificacio;
    }

    public byte[] getImatge() {
        return imatge;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }

}
