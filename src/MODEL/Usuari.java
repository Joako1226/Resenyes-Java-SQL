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

    private String nom_usuari;
    private String nom;
    private String contrasenya;
    private Date data_naixament;
    private int punts;

    private enum tipus_ban {
        active, warned, soft_ban, hard_ban
    };
    private LocalDateTime data_ban;

    public Usuari(String nom_usuari, String nom, String contrasenya, Date data_naixament, int punts, LocalDateTime data_ban) {
        this.nom_usuari = nom_usuari;
        this.nom = nom;
        this.contrasenya = contrasenya;
        this.data_naixament = data_naixament;
        this.punts = punts;
        this.data_ban = data_ban;
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

    public Date getData_naixament() {
        return data_naixament;
    }

    public void setData_naixament(Date data_naixament) {
        this.data_naixament = data_naixament;
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

}
