/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Rger Trulls
 */
public class GenereContingut {

    private int idGenere;
    private int idContingut;

    public GenereContingut(int idGenere, int idContingut) {
        this.idGenere = idGenere;
        this.idContingut = idContingut;
    }

    public int getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(int idGenere) {
        this.idGenere = idGenere;
    }

    public int getIdContingut() {
        return idContingut;
    }

    public void setIdContingut(int idContingut) {
        this.idContingut = idContingut;
    }

}
