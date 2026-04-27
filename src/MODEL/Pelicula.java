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
public class Pelicula extends Contingut{
    
    private LocalTime duracio;
    private String director;
    

    public Pelicula(LocalTime duracio, String director, int id, String titol, String descripcio, int classificacio, byte [] imatge) {
        super(id, titol, descripcio, classificacio, imatge);
        this.duracio = duracio;
        this.director = director;
    }

    public LocalTime getDuracio() {
        return duracio;
    }

    public void setDuracio(LocalTime duracio) {
        this.duracio = duracio;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    
}
