/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.time.LocalDate;

/**
 *
 * @author Rger Trulls
 */
public class Resenya {

    private String usuari;
    private int idContingut;
    private String titolContingut; 
    private String descripcio;
    private double nota;
    private boolean spoiler;
    private LocalDate dataResenya;

    public Resenya(String usuari, int idContingut, String descripcio, double nota, boolean spoiler, LocalDate dataResenya) {
        this.usuari = usuari;
        this.idContingut = idContingut;
        this.descripcio = descripcio;
        this.nota = nota;
        this.spoiler = spoiler;
        this.dataResenya = dataResenya;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public int getIdContingut() {
        return idContingut;
    }

    public void setIdContingut(int idCOntingut) {
        this.idContingut = idCOntingut;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public LocalDate getDataResenya() {
        return dataResenya;
    }

    public void setDataResenya(LocalDate dataResenya) {
        this.dataResenya = dataResenya;
    }

    public String getTitolContingut() {
        return titolContingut;
    }

    public void setTitolContingut(String titolContingut) {
        this.titolContingut = titolContingut;
    }

}
