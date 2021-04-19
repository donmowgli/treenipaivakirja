/**
 * 
 */
package Treenipvk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
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
     * Haetaan id-arvon mukaisesti vastaava harjoite
     * @param id harjoitteen id-numero, jota vastaava harjoite haetaan
     * @return palauttaa id-arvoa vastaavan harjoitteen
     */
    public Harjoite getHarjoite(int id) {
        for (Harjoite harjoite : this.harjoitteet) {
            if (harjoite.getId() == id) {
                return harjoite;
            }
        }
        return null;
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
     * Haetaan kaikki Harjoitteet-olion harjoitteet
     * @return palauttaa kaikki harjoitteet listana
     */
    public ArrayList<Harjoite> getHarjoitteet(){
        return this.harjoitteet;
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
     * @param hakuehto merkkijono, jonka mukaan haetaan ja johon harjoitteiden nimiä verrataan
     * @return palautetaan lista harjoitteista, joiden nimet vastaavat hakuehtoa
     */
    public ArrayList<Harjoite> etsi(String hakuehto){
        ArrayList<Harjoite> ret = new ArrayList<Harjoite>();
        for(Harjoite harjoite : this.harjoitteet){
            if (harjoite.getNimi().equals(hakuehto)) {ret.add(harjoite);}
        }
        Collections.sort(ret);
        return ret;
    }
    
    /**
     * Lukee harjoitteet tiedostosta Harjoite-olioon.
     * @throws SailoException jos ei tiedostosta lukeminen onnistu
     */
    public void lueTiedostosta() throws SailoException {
        this.harjoitteet = new ArrayList<Harjoite>();
        File tiedosto = new File(this.tiedostonimi);
        try(BufferedReader lukija = new BufferedReader(new FileReader(tiedosto))){
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
    @SuppressWarnings("resource")
    public void tallenna() throws SailoException {
        File tiedosto = new File(this.getTiedostoNimi());
        try {
            tiedosto.createNewFile();
            PrintStream stream = new PrintStream(tiedosto);
            for(Harjoite harjoite : this.harjoitteet) {
                stream.println(harjoite.toString());
            }
            stream.close();
        } catch(IOException e) {
            throw new SailoException("Tiedosto ei aukea");
        }
    }
    
    /**
     * Poistetaan haluttu Harjoite-olio
     * @param id Harjoite-olion id, joka halutaan poistaa
     */
    public void poista(int id) {
        Iterator<Harjoite> iter = this.harjoitteet.iterator();
        while(iter.hasNext()) {
            Harjoite harjoite = iter.next();
            if(harjoite.getId() == id) iter.remove();
        }
    }

    /**
     * Iteraattori kaikkien harjoitteiden läpikäymiseen
     * @return harjoiteiteraattori
     * 
     * @example
     * <pre name= "test">
     * #PACKAGEIMPORT
     * #import java.util*;
     *  
     *  Harjoite harjoitteet = new Harjoitteet();
     *  Harjoite harjoite1 = new Harjoite("testi1", 3); harjoite1.rekisteroi(); harjoitteet.lisaa(harjoite1);
     *  Harjoite harjoite2 = new Harjoite("testi2", 4); harjoite2.rekisteroi(); harjoitteet.lisaa(harjoite2);
     *  Harjoite harjoite3 = new Harjoite("testi3", 5); harjoite3.rekisteroi(); harjoitteet.lisaa(harjoite3);
     *  Harjoite harjoite4 = new Harjoite("testi4", 6); harjoite4.rekisteroi(); harjoitteet.lisaa(harjoite4);
     *  Harjoite harjoite5 = new Harjoite("testi5", 7); harjoite5.rekisteroi(); harjoitteet.lisaa(harjoite5);
     *  
     *  Iterator<Harjoite> i = harjoitteet.iterator();
     *  i.next() === harjoite1;
     *  i.next() === harjoite2;
     *  i.next() === harjoite3;
     *  i.next() === harjoite4;
     *  i.next() === harjoite5;
     *  i.next() === harjoite4; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int harids [] = {0, 1, 2, 3, 4};
     *  
     *  for(Sarja sarja : sarjat){
     *      sarja.getSarid() === sarids[n]; n++;
     *  }
     *  
     *  </pre>
     */
    @Override
    public Iterator<Harjoite> iterator() {
        return harjoitteet.iterator();
    }


    /**
     * Testataan harjoitteet-luokkaa
     * @param args ei käytössä
     * @throws SailoException jos tallentamisen kanssa ongelmia
     */
    public static void main(String[] args) throws SailoException {
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
        
        Harjoite harjoite1 = new Harjoite("penkki", 0, 3);
        Harjoite harjoite2 = new Harjoite("työntö", 0, 3);
        Harjoite harjoite3 = new Harjoite("Punnerrus", 0, 3);
        
        harjoite1.rekisteroi();
        harjoite2.rekisteroi();
        harjoite3.rekisteroi();
        
        Harjoitteet harjoitteet = new Harjoitteet();
        harjoitteet.lisaaHarjoite(harjoite1);
        harjoitteet.lisaaHarjoite(harjoite2);
        harjoitteet.lisaaHarjoite(harjoite3);
        
        harjoitteet.setTiedostonNimi("harjoitteet.dat");
        
        for(Harjoite harjoite : harjoitteet) {
            harjoite.tulosta(System.out);
        }
        
        harjoitteet.tallenna();
        harjoitteet.lueTiedostosta();
        
        for(Harjoite harjoite : harjoitteet) {
            harjoite.tulosta(System.out);
        }
    }
}
