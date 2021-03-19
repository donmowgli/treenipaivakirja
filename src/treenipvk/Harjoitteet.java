/**
 * 
 */
package Treenipvk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Akseli Jaara
 * @version 4 Mar 2021
 *
 */
public class Harjoitteet implements Iterable<Harjoite> {
    
    private String tiedostonimi;
    private ArrayList<Harjoite> harjoitteet;
    
    /**
     * Harjoitteiden parametrillinen muodostaja.
     * @param tiedostonimi tiedostonimi, johon tiedot harjoitteista tallennetaan
     * @param harjoitteet lista harjoitteista, jotka harjoitteisiin kuuluu
     */
    public Harjoitteet (String tiedostonimi, ArrayList<Harjoite> harjoitteet) {
        this.tiedostonimi = tiedostonimi;
        this.harjoitteet = harjoitteet;
    }
    
    /**
     * Harjoitteiden parametriton muodostaja
     */
    public Harjoitteet() {
        this("", new ArrayList<Harjoite>());
    }
    
    /**
     * Lisätään harjoite harjoitteisiin
     * @param harjoite harjoitteisiin lisättävä harjoite
     */
    public void lisaaHarjoite(Harjoite harjoite) {
        harjoitteet.add(harjoite);
    }
    
    /**
     * Palauttaa viitteen i Harjoite harjoitteet-ArrayLististä.
     * @param i monennenko harjoitteen viitteen halutaan
     * @return palauttaa halutun viitteen
     * @throws IndexOutOfBoundsException jos indeksi i ei ole sallitulla alueella.
     */
    public Harjoite getHarjoite(int i) throws IndexOutOfBoundsException  {
        if(i < 0 || i > this.harjoitteet.size()) {
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        }
        return this.harjoitteet.get(i);
    }
    
    /**
     * Haetaan harjoituksista haluttu harjoite nimen perusteella.
     * @param nimi nimi, jonka mukaan harjoitusta haetaan
     * @return palauttaa halutun Harjoite-olion. Paluttaa null-olion, jos ei ole olemassa.
     */
    public Harjoite getHarjoite(String nimi) {
        for(Harjoite harjoite : this.harjoitteet) {
            if(harjoite.getNimi().equals(nimi)) {
                return harjoite;
            }
        }
        return null;
    }
    
    /**
     * Etsitään treenin harjoitukset vertailemalla treenin yksilöivää trid-tunnusta harjoituksen trid-tunnukseen.
     * @param trid harjoituksen yksilöivä harid tunnus.
     * @return palauttaa listana vastaavat sarjat.
     */
    public List<Harjoite> getHarjoitteet(int trid){
        List<Harjoite> loydetyt = new ArrayList<Harjoite>();
        for(Harjoite harjoite : this.harjoitteet) {
            if(harjoite.getTrid() == trid) {
                loydetyt.add(harjoite);
            }
        }
        return loydetyt;
    }
    
    /**
     * Harjoitteiden lukumäärän palauttaminen
     * @return palauttaa harjoitteiden lukumäärän taulukossa
     */
    public int getHarjoitteetLkm() {
        return this.harjoitteet.size();
    }
    
    /**
     * Asetetaan Harjoitteet-oliolle tiedostonimi
     * @param nimi tiedoston nimi, joka asetetaan
     */
    public void setTiedostonNimi(String nimi) {
        this.tiedostonimi = nimi;
    }
    
    /**
     * Haetaan Harjoitteet-olion tiedoston nimi
     * @return palauttaa Harjoite-olion tiedoston nimen
     */
    public String getTiedostoNimi() {
        return this.tiedostonimi;
    }
    
    /**
     * Lukee harjoitteet tiedostosta Harjoite-olioon.
     * @param tiedNimi tiedoston nimi, josta halutaan lukea
     * @throws SailoException jos ei tiedostosta lukeminen onnistu
     */
    public void lueTiedostosta(String tiedNimi) throws SailoException {
        try(BufferedReader lukija = new BufferedReader(new FileReader(tiedNimi))){
            String rivi;
            while((rivi = lukija.readLine()) != null) {
                Harjoite harjoite = new Harjoite();
                harjoite.parse(rivi);
                this.lisaaHarjoite(harjoite);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedoston kanssa on ongelmia!");
        }
    }
    
    /**
     * Tallentaa harjoitteet tiedostoon.
     * @throws SailoException jos tallentaminen ei onnistu.
     */
    public void tallenna() throws SailoException {
        File tiedosto = new File(this.getTiedostoNimi());
        try {
            tiedosto.createNewFile();
            PrintWriter fo = new PrintWriter(new FileWriter(tiedosto.getCanonicalPath()));
            for(Harjoite harjoite : this) {
                fo.println(harjoite.toString());
            }
        } catch(IOException e) {
            throw new SailoException("Tiedosto ei aukea");
        }
    }
    

    /**
     * Iteraattori kaikkien harjoitteiden läpikäymiseen.
     */
    @Override
    public Iterator<Harjoite> iterator() {
        return harjoitteet.iterator();
    }


    /**
     * Testataan harjoitteet-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //Testataan Harjoitteet-luokkaa
        Sarja sarja1 = new Sarja(80, 4);
        Sarja sarja2 = new Sarja(70, 6);
        Sarja sarja3 = new Sarja(60, 8);
        
        sarja1.setToteutuneet(5);
        sarja2.setToteutuneet(6);
        sarja3.setToteutuneet(8);
        
        sarja1.setHarid(0);
        sarja2.setHarid(0);
        sarja3.setHarid(0);
        
        sarja1.rekisteroi();
        sarja2.rekisteroi();
        sarja3.rekisteroi();
        
        Sarjat sarjat = new Sarjat();
        
        sarjat.lisaaSarja(sarja1);
        sarjat.lisaaSarja(sarja2);
        sarjat.lisaaSarja(sarja3);
        
        Harjoite harjoite1 = new Harjoite();
        
        Harjoite harjoite2 = new Harjoite();
        Harjoite harjoite3 = new Harjoite();
        
        harjoite1.rekisteroi();
        harjoite2.rekisteroi();
        harjoite3.rekisteroi();
        
        Harjoitteet harjoitteet = new Harjoitteet();
        harjoitteet.lisaaHarjoite(harjoite1);
        harjoitteet.lisaaHarjoite(harjoite2);
        harjoitteet.lisaaHarjoite(harjoite3);
        
        for(Harjoite harjoite : harjoitteet) {
            harjoite.tulosta(System.out);
        }
    }
}
