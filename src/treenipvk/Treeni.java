/**
 * 
 */
package Treenipvk;

import java.io.PrintStream;
import java.time.LocalDate;

/**
 * @author Akseli Jaara
 * @version 3 Mar 2021
 * 
 */
public class Treeni {
    
    private String nimi;
    private int trid;
    private LocalDate pvm;
    
    private static int seuraavaNro;
    
    /**
     * Treeni-olion muodostaja. Säilöö Harjoite-olioita ja edelleen Sarja-olioita, ja muodostaa siten täyden harjoitteen.
     * @param nimi nimi joka asetetaan harjoitteelle
     * @param pvm LocalDate-olio, joka määrittää lokimerkinnän harjoitteelle.
     */
    public Treeni(String nimi, LocalDate pvm) {
        this.nimi = nimi;
        this.pvm = pvm;
    }
    
    /**
     * Treeni-olion parametriton muodostaja.
     */
    public Treeni() {
        this("", null);
    }
    
    /** Asettaa treenille nimen.
     * @param nimi nimi, joka treenille halutaan antaa.
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    /**
     * Asetetaan päivämäärä treenille.
     * @param pvm LocalDate-olio, joka asetetaan pvm:ksi Treeniin.
     */
    public void setPvm(LocalDate pvm) {
        this.pvm = pvm;
    }
    /**
     * Haetaan Harjoite-olion Pvm-merkintä
     * @return palauttaa Harjoite-olion Pvm-olion.
     */
    public LocalDate getPvm() {
        return this.pvm;
    }
    
    /**
     * Haetaan treenin id
     * @return palautetaan Treeni-olion trid integer-numerona.
     */
    public int getTrid() {
        return this.trid;
    }
    
    /**
     * Rekisteröidään treeni
     * @return palauttaa treenin uuden trid:n 
     */
    public int rekisteroi() {
        this.trid = seuraavaNro;
        seuraavaNro++;
        return trid;
    }
    
    /**
     * Tulostetaan Treenin tiedot 
     * @param out tietovirta, josta halutaan tulostaa
     */
    public void tulosta(PrintStream out) {
        out.println(this.nimi + " " + this.trid + " " + this.pvm);
    }
    
    @Override
    public String toString() {
        return this.nimi + "|" + this.trid + "|" + this.pvm.toString();
    }
    
    /**
     * String-oliota luettaessa alustetaan tiedot halutulle treenille.
     * @param jono josta halutaan tarvittavat tiedot treenille
     */
    public void parse(String jono) {
        String[] arvot = jono.split("//|");
        this.nimi = arvot[0];
        this.trid = Integer.parseInt(arvot[1]);
        this.pvm = LocalDate.parse(arvot[2]);
    }
    
    /**
     * Testataan treeni-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //Testataan Treeni-luokkaa
        Treeni treeni = new Treeni();
        treeni.setNimi("Työntävä");
        treeni.rekisteroi();
        
        treeni.tulosta(System.out);
        
        Treeni treeni2 = new Treeni();
        treeni2.setNimi("Vetävä");
        treeni2.rekisteroi();
        
        treeni2.tulosta(System.out);
    }

}
