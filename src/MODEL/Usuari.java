/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Rger Trulls
 */
public class Usuari {

    public enum TipusBan {
        active, warned, soft_ban, hard_ban
    }

    private String nom_usuari;
    private String nom;
    private String contrasenya;

    private LocalDate data_naixament;

    private int punts;

    private TipusBan estat;

    private LocalDateTime data_ban;

    private boolean admin;

    public Usuari(String nom_usuari, String nom, String contrasenya, LocalDate data_naixament, int punts, TipusBan estat, LocalDateTime data_ban, boolean admin) {
        this.nom_usuari = nom_usuari;
        this.nom = nom;
        this.contrasenya = contrasenya;
        this.data_naixament = data_naixament;
        this.punts = punts;
        this.estat = estat;
        this.data_ban = data_ban;
        this.admin = admin;
    }

    public TipusBan getEstat() {
        return estat;
    }

    public void setEstat(TipusBan estat) {
        this.estat = estat;
    }

    public LocalDate getData_naixament() {
        return data_naixament;
    }

    public void setData_naixament(LocalDate data_naixament) {
        this.data_naixament = data_naixament;
    }

    public String getNom_usuari() {
        return nom_usuari;
    }

    public void setNom_usuari(String nom_usuari) {
        this.nom_usuari = nom_usuari;
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

    public int getPunts() {
        return punts;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }

    public LocalDateTime getData_ban() {
        return data_ban;
    }

    public void setData_ban(LocalDateTime data_ban) {
        this.data_ban = data_ban;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
