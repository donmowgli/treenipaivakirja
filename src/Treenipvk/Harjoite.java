/**
 * 
 */
package Treenipvk;
import java.io.PrintStream;
import java.util.ArrayList;

import kanta.Muokattava;

/**
 * @author Akseli Jaara
 * @version 3 Mar 2021
 * Harjoite-luokka, jossa säilötään Sarja-olioita ja muita tärkeitä harjoitteeseen liittyviä muuttujia.
 */
public class Harjoite implements Muokattava, Cloneable, Comparable<Harjoite> {

    private String nimi;
    private int harid;
    private int trid;
    private int sarlkm;
    private boolean kanta;
    
    private static int seuraavaNro = 0;
    
    /**
     * @param nimi harjoitteen nimi, joka sille halutaan asettaa
     * @param trid treenin ID, joka yksilöi treenin
     * @param sarlkm sarjojen lukumäärä, joka perusharjoituksessa sarjalle halutaan
     */
    public Harjoite(String nimi, int trid, int sarlkm) {
        this.nimi = nimi;
        this.trid = trid;
        this.sarlkm = sarlkm;
        this.kanta = false;
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
     * Asetetaan harjoitukselle id
     * @param harid harjoituksen id, joka sille halutaan asettaa.
     */
    public void setHarid(int harid) {
        this.harid = harid;
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
     * Haetaan sarjojen lukumäärä Sarja-oliolta
     * @return palautetaan Sarja-olion sarjojen lukumäärä
     */
    public int getSarlkm() {
        return this.sarlkm;
    }
    
    /**
     * Asetetaan totuusarvo sille, onko olio kantaolio
     * @param kanta asetettava totuusarvo
     */
    public void setKanta(boolean kanta) {
        this.kanta = kanta;
    }
    
    /**
     * Haetaan olion kanta-arvo
     * @return palauttaa kanta-arvon, eli totuusarvon onko kyseinen olio kantaolio
     */
    public boolean getKanta() {
        return this.kanta;
    }
    
    /**
     * Tulostetaan harjoite kokonaisudessaan.
     * @param out tietovirta ulos
     */
    public void tulosta(PrintStream out) {
        out.println(this.nimi + " " + this.harid + " " + this.trid + " " + this.sarlkm);
    }
    
    /**
     * Antaa Harjoite-oliolle sen uniikin sarid-numeron.
     * @return palauttaa harjoitteen id:n eli harid-oliomuuttujan.
     * @example
     * <pre name="test">
     *      Harjoite harjoite1 = new Harjoite();
     *      Harjoite harjoite2 ) new Harjoite();
     *      harjoite1.rekisteroi();
     *      harjoite2.rekisteroi();
     *      harjoite1.harid === harjoite2.harid - 1;
     * </pre>
     */
    public int rekisteroi() {
        this.harid = seuraavaNro;
        seuraavaNro++;
        return harid;
    }
    
    @Override
    public Harjoite clone() {
        Harjoite harjoite = new Harjoite(this.nimi, this.trid, this.sarlkm);
        return harjoite;
    }
    
    @Override
    public int compareTo(Harjoite harjoite) {
        return this.nimi.compareTo(harjoite.getNimi());
    }
    
    @Override
    public String toString() {
        return this.nimi + "|" + this.harid + "|" + this.trid  + "|" + this.sarlkm;
    }
    
    /**
     * String-oliota luettaessa alustetaan tiedot halutulle sarjalle.
     * @param jono josta halutaan tarvittavat tiedot sarjalle
     */
    public void parse(String jono) {
        String[] arvot = jono.split("\\|");
        this.nimi = arvot[0];
        this.harid = Integer.parseInt(arvot[1]);
        this.trid = Integer.parseInt(arvot[2]);
        this.sarlkm = Integer.parseInt(arvot[3]);
    }
    
    @Override
    public String getTiedot() {
        return this.nimi + ", sarjojen lukumäärä: " + String.valueOf(this.sarlkm);
    }

    @Override
    public ArrayList<String> getArvot() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(this.nimi);
        ret.add(String.valueOf(this.sarlkm));
        return ret;
    }

    @Override
    public int getId() {
        return this.harid;
    }
    
    @Override
    public int getViite() {
        return this.trid;
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
        
        Harjoite harjoite1 = new Harjoite();
        harjoite1.setNimi("Penkki");
        harjoite1.setSarlkm(3);
        harjoite1.rekisteroi();
        harjoite1.tulosta(System.out);
    }
}
