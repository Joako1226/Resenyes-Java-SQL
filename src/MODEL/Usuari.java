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
public class Usuari {

    private String nomUsuari;
    private String nom;
    private String contrasenya;
    private LocalDate dataNeixament;
    private int punts;

    private enum estat {
        active, warned, soft_ban, hard_ban
    }
    private LocalDate data_ban;

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public LocalDate getDataNeixament() {
        return dataNeixament;
    }

    public void setDataNeixament(LocalDate dataNeixament) {
        this.dataNeixament = dataNeixament;
    }

    public int getPunts() {
        return punts;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }

    public LocalDate getData_ban() {
        return data_ban;
    }

    public void setData_ban(LocalDate data_ban) {
        this.data_ban = data_ban;
    }

    public Usuari(String nomUsuari, String nom, String contrasenya, LocalDate dataNeixament, int punts, LocalDate data_ban) {
        this.nomUsuari = nomUsuari;
        this.nom = nom;
        this.contrasenya = contrasenya;
        this.dataNeixament = dataNeixament;
        this.punts = punts;
        this.data_ban = data_ban;
    }

}
