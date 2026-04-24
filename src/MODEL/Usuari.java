/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

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
    private enum tipus_ban{active, warned, soft_ban, hard_ban};
    private LocalDateTime data_ban;

    public String getNom_usuari() {
        return nom_usuari;
    }

    public String getNom() {
        return nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public Date getData_naixament() {
        return data_naixament;
    }

    public int getPunts() {
        return punts;
    }

    public LocalDateTime getData_ban() {
        return data_ban;
    }

    public void setNom_usuari(String nom_usuari) {
        this.nom_usuari = nom_usuari;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public void setData_naixament(Date data_naixament) {
        this.data_naixament = data_naixament;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }

    public void setData_ban(LocalDateTime data_ban) {
        this.data_ban = data_ban;
    }
    
    
}
