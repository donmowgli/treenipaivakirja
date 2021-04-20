/**
 * 
 */
package Treenipvk;
import java.io.PrintStream;
import java.util.ArrayList;

import kanta.Muokattava;

/**
 *  Sarja-luokka, jolla tärkeimmät sarjan ominaisuudet. Osaa huolehtia omasta sarid-numerostaan.
 * 
 * @author Akseli Jaara
 * @version 3 Mar 2021
 *
 */
public class Sarja implements Muokattava, Cloneable, Comparable<Sarja> {

    private int sarid;
    private int harid;
    private double tyopaino;
    private int toistot;
    private int toteutuneet;
    
    private static int seuraavaNro = 1;
    
    /**
     * Sarja-olion parametrillinen muodostaja.
     * @param tyopaino on tyopaino sarjassa.
     * @param toistot tavoiteltava toistojen määrä sarjassa.
     */
    public Sarja(double tyopaino, int toistot) {
        this.tyopaino = tyopaino;
        this.toistot = toistot;
    }
    
    /**
     * Sarja-olion parametriton muodostaja. Alustaa sarjan.
     */
    public Sarja() {
        this( 0, 0);
    }
    
    /**
     * Lisää sarjalle Harjotiuksen id:n, johon sarja on kytkettynä.
     * @param harid Harjoituksen ID, johon sarja kuuluu.
     */
    public void setHarid(int harid) {
        this.harid = harid;
    }
    
    /**
     * Palauttaa harjoituksen yksilöivän harid-tunnuksen.
     * @return Harjoituksen yksilöivä harid-tunnus.
     */
    public int getHarid() {
        return this.harid;
    }
    
    /**Sarjan uniikki sarid-muuttuja.
     * @return palauttaa uniikin sarid-muuttujan.
     */
    public int getSarid() {
        return this.sarid;
    }

    /**
     * Työpainon lisääminen Sarja-olioon
     * @param paino joka halutaan lisätä työpainoksi
     */
    public void setTyopaino(double paino) {
        this.tyopaino = paino;
    }
    
    /**
     * Työpainojen palauttaminen
     * @return palauttaa työpainon
     */
    public double getTyopaino() {
        return this.tyopaino;
    }
    
    /**
     * Toteutuneiden toistojen lisääminen Sarja-olioon
     * @param toistot jotka halutaan lisätä sarjaan
     */
    public void setToistot(int toistot) {
        this.toistot = toistot;
    }
    
    /**
     * Haetaan sarjan toistot
     * @return palauttaa sarjan toistot
     */
    public int getToistot() {
        return this.toistot;
    }
    
    /**
     * @return palautetaan toteutuneet toistot
     */
    public int getToteutuneet() {
        return this.toteutuneet;
    }
    
    /**
     * Asetetaan toteutuneiden toistojen lukumäärä sarjassa.
     * @param toteutuneet toteutuneiden toistojen lukumäärä sarjassa.
     */
    public void setToteutuneet(int toteutuneet) {
        this.toteutuneet = toteutuneet;
    }
    
    /**
     * Antaa Sarja-oliolle sen uniikin sarid-numeron.
     * @return palauttaa sarjan id:n eli sarid-oliomuuttujan.
     * @example
     * <pre name="test">
     *      Sarja sarja1 = new sarja();
     *      Sarja sarja2 = new sarja();
     *      sarja1.rekisteroi();
     *      sarja2.rekisteroi();
     *      sarja1.sarid === sarja2.sarid - 1;
     * </pre>
     */
    public int rekisteroi() {
        this.sarid = seuraavaNro;
        seuraavaNro++;
        return sarid;
    }
    
    /**
     * @param out mihin halutaan printata
     */
    public void tulosta(PrintStream out) {
        out.println("Sarjan ID: " + this.sarid + ", Työpaino: " + this.tyopaino  + ", Toistojen lukumäärä: " +this.toistot + ", Toteutuneiden toistojen lukumäärä:  " + this.toteutuneet);
    }
    
    /**
     * @return String-olio, jolla näytölle tulostettavat tiedot
     */
    @Override
    public String getTiedot() {
        return "Työpaino: " + this.tyopaino + ", toistot: " + this.toistot + ", ja toteutuneet: " + this.toteutuneet;
    }
    
    @Override
    public ArrayList<String> getArvot() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(String.valueOf(this.tyopaino));
        ret.add(String.valueOf(this.toistot));
        ret.add(String.valueOf(this.toteutuneet));
        return ret;
    }
    
    /**
     * Haetaan sarjan yksilöivä id
     * @return palautetaan Sarja-olion sarid-muuttuja
     */
    @Override
    public int getId() {
        return this.sarid;
    }
    
    @Override
    public int getViite() {
        return this.harid;
    }
    
    /**
     * Kloonataan haluttu Sarja-olio
     * @return palauttaa kloonin halutusta sarjasta
     * @example
     * <pre name="test">
     * Sarja sarja1 = new Sarja(80, 5);
     * Sarja sarja2 = sarja1.clone();
     * sarja1.tyopaino === sarja2.tyopaino;
     * sarja1.toistot === sarja2.toistot;
     * </pre>
     */
    @Override
    public Sarja clone() {
        Sarja sarja = new Sarja(this.getTyopaino(), this.getToteutuneet());
        sarja.toistot = this.getToistot();
        sarja.harid = this.getHarid();
        return sarja;
    }
    
    @Override
    public int compareTo(Sarja sarja) {
        if (this.sarid < sarja.getSarid()) {return -1;}
        if (this.sarid == sarja.getSarid()) {return 0;}
        if (this.sarid > sarja.getSarid()) {return 1;}
        return 0;
    }

    @Override
    public String toString() {
        return this.sarid + "|" + this.harid + "|" + this.tyopaino  + "|" + this.toistot  + "|" + this.toteutuneet;
    }
    
    /**
     * String-oliota luettaessa alustetaan tiedot halutulle sarjalle.
     * @param jono josta halutaan tarvittavat tiedot sarjalle
     */
    public void parse(String jono) {
        String[] arvot = jono.split("\\|");
        this.sarid = Integer.parseInt(arvot[0]);
        this.harid = Integer.parseInt(arvot[1]);
        this.tyopaino = Double.parseDouble(arvot[2]);
        this.toistot = Integer.parseInt(arvot[3]);
        this.toteutuneet = Integer.parseInt(arvot[4]);
    }
    
    /** Pääohjelma testaamaan luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //testataan sarja-luokkaa
        Sarja sarja = new Sarja(80, 5);
        sarja.setToteutuneet(6);
        sarja.rekisteroi();
        sarja.tulosta(System.out);
        sarja.parse(sarja.toString());
    }
}
