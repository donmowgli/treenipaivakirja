/**
 * 
 */
package Treenipvk;
import java.io.PrintStream;

/**
 * @author Akseli Jaara
 * @version 3 Mar 2021
 * Harjoite-luokka, jossa säilötään Sarja-olioita ja muita tärkeitä harjoitteeseen liittyviä muuttujia.
 */
public class Harjoite {

    private String nimi;
    private int harid;
    private int trid;
    private int sarlkm;
    
    private int seuraavaNro;
    
    /**
     * @param nimi harjoitteen nimi, joka sille halutaan asettaa
     * @param trid treenin ID, joka yksilöi treenin
     * @param sarlkm sarjojen lukumäärä, joka perusharjoituksessa sarjalle halutaan
     */
    public Harjoite(String nimi, int trid, int sarlkm) {
        this.nimi = nimi;
        this.trid = trid;
        this.sarlkm = sarlkm;
    }
    
    /**
     *  Harjoite-olion parametriton muodostaja.
     */
    public Harjoite() {
        this("", 0, 0);
    }
    
    /**
     * Nimen asettaminen treenille.
     * @param nimi nimi joka halutaan treenille asetettavaksi.
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    /**
     * Haetaan harjoitteen nimi.
     * @return palauttaa harjoitteen nimen.
     */
    public String getNimi() {
        return this.nimi;
    }
    
    /**
     * Haetaan harjoituksen yksilöivä id, harid
     * @return palauttaa yksilöivän id:n harid-numeron.
     */
    public int getHarid() {
        return this.harid;
    }
    
    /**
     * Treenin ID:n asettaminen
     * @param trid treenin id.
     */
    public void setTrid(int trid) {
        this.trid = trid;
    }
    
    /** Haetaan treenin yksilöivä trid-tunnus.
     * @return palauttaa treenin yksilöivän trid-tunnuksen.
     */
    public int getTrid() {
        return this.trid;
    }
    
    /**
     * Aseta sarjojen lukumäärä, joka halutaan perusharjoituksessa olevan. Käytetään haluttua sarjaa monistaessa automaattisesti harjoitteeseen.
     * @param sarlkm haluttujen perussarjojen lukumäärä.
     */
    public void setSarlkm(int sarlkm) {
        this.sarlkm = sarlkm;
    }
    
    /**
     * Tulostetaan harjoite kokonaisudessaan.
     * @param out tietovirta ulos
     */
    public void tulosta(PrintStream out) {
        out.println(this.nimi + " " + this.harid + " " + this.trid + " " + this.sarlkm);
    }
    
    /**
     * Asetetaan harjoitukselle oma id.
     * @return harjoituksen uuden harjoitusid:n eli harid:n.
     */
    public int rekisteroi() {
        this.harid = seuraavaNro;
        seuraavaNro++;
        return harid;
    }
    
    /**
     * Testataan Harjoite-luokkaa.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //Testataan harjoite-luokkaa
        
        Harjoite harjoite = new Harjoite();
        harjoite.setNimi("Penkki");
        harjoite.setSarlkm(3);
        harjoite.rekisteroi();
        harjoite.tulosta(System.out);
    }

}
