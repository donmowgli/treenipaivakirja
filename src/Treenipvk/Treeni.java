/**
 * 
 */
package Treenipvk;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import kanta.Muokattava;

/**
 * @author Akseli Jaara
 * @version 3 Mar 2021
 * 
 */
public class Treeni implements Muokattava, Cloneable, Comparable<Treeni> {
    
    private String nimi;
    private int trid;
    private LocalDate pvm;
    
    private static int seuraavaNro = 1;
    
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
     * Haetaan Treeni-olion nimi
     * @return palauttaa Treeni-olion nimen
     */
    public String getNimi() {
        return this.nimi;
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
     * @param trid treenin id, joka halutaan asettaa
     */
    public void setTrid(int trid) {
        this.trid = trid;
    }
    
    /**
     * Haetaan treenin id
     * @return palautetaan Treeni-olion trid integer-numerona.
     */
    public int getTrid() {
        return this.trid;
    }
    
    /**
     * Antaa Treeni-oliolle sen uniikin trid-numeron.
     * @return palauttaa treenin id:n eli trid-oliomuuttujan.
     * @example
     * <pre name="test">
     *      Treeni treeni1 = new Treeni();
     *      Treeni treeni2 = new Treeni();
     *      treeni1.rekisteroi();
     *      treeni2.rekisteroi();
     *      treeni1.trid === treeni2.trid - 1;
     * </pre>
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
    
    /**
     * Haetaan Treeni-olion Pvm(LocalDate) String-oliona näytettävässä muodossa
     * @return Pvm String-oliona
     */
    public String pvmToString() {
        return String.valueOf(this.pvm.getDayOfMonth()) + "." + String.valueOf(this.pvm.getMonthValue()) + "." + String.valueOf(this.pvm.getYear());
    }
    
    /**
     * ToString-metodi, joka palauttaa merkkijonon jossa arvot eroteltu "|"-merkinnällä.
     * Asettaa päivämäärälle merkkijonona "null" jos this.pvm == null.
     */
    @Override
    public String toString() {
        String sPvm;
        if (this.pvm == null) {
            sPvm = "null";
        } else sPvm = pvm.toString();
        return this.nimi + "|" + this.trid + "|" + sPvm;
    }
    
    /**
     * String-oliota luettaessa alustetaan tiedot halutulle treenille.
     * @param jono josta halutaan tarvittavat tiedot treenille
     */
    public void parse(String jono) {
        String[] arvot = jono.split("\\|");
        this.nimi = arvot[0];
        this.trid = Integer.parseInt(arvot[1]);
        if (arvot[2].equals("null")) {
            this.pvm = null;
        } else this.pvm = LocalDate.parse(arvot[2]);
    }
    
    /**
     * Kloonataan haluttu Treeni-olio
     * @return palauttaa kloonin halutusta Treenistä
     * @example
     * <pre name="test">
     * Treeni treeni1 = new Treeni("testi", LocalDate.now());
     * Treeni treeni2 = treeni1.clone();
     * treeni1.nimi === treeni2.nimi;
     * treeni1.pvm.equals(treeni2.pvm) === true;
     * </pre>
     */
    @Override
    public Treeni clone() {
        Treeni klooni = new Treeni(this.nimi, this.pvm);
         return klooni;
    }
    
    @Override
    public int compareTo(Treeni treeni) {
        return this.nimi.compareTo(treeni.getNimi());
    }
    
    @Override
    public String getTiedot() {
        StringBuilder ret = new StringBuilder();
        if(this.pvm != null) {ret.append(this.pvmToString()); ret.append(", ");}
        ret.append(this.nimi);
        return ret.toString();
    }
    
    @Override
    public ArrayList<String> getArvot() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(this.nimi);
        ret.add(this.pvmToString());
        return ret;
    }
    
    @Override
    public int getId() {
        return this.trid;
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
