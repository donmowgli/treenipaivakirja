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
import java.util.Iterator;
import java.util.List;

/**
 * Sarjat, joka osaa mm. lisätä uuden sarjan.
 * 
 * @author Akseli jaara
 * @version 4 Mar 2021
 *
 */
public class Sarjat implements Iterable<Sarja>{
    
    private String tiedostonimi;
    private ArrayList<Sarja> sarjat;
    
    /**
     * @param tiedostonimi tiedoston nimi, johon tiedot halutaan tallettaa
     * @param sarjat sarjat, joita halutaan tiedostoon tallettaa.
     */
    public Sarjat(String tiedostonimi, ArrayList<Sarja> sarjat) {
        this.tiedostonimi = tiedostonimi;
        this.sarjat = sarjat;
    }
    
    /**
     *  Parametriton muodostaja Sarjat-oliolle.
     */
    public Sarjat() {
        this("", new ArrayList<Sarja>());
    }
    
    /**
     * @param sarja Sarja-olio, joka halutaan sarjoihin lisätä.
     */
    public void lisaaSarja(Sarja sarja) {
        this.sarjat.add(sarja);
    }
    
    /**
     * Palauttaa viitteen i Sarjan sarjat-ArrayLististä.
     * @param i monennenko sarjan viiten halutaan
     * @return palauttaa halutun sarjan
     * @throws IndexOutOfBoundsException jos indeksi i ei ole sallitulla alueella.
     */
    public Sarja getSarja(int i) throws IndexOutOfBoundsException  {
        if(i < 0 || i > this.sarjat.size()) {
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        }
        return this.sarjat.get(i);
    }
    
    /**
     * Etsitään harjoitteen sarjat vertailemalla harjoitteen yksilöivää harid-tunnusta sarjan harid-tunnukseen.
     * @param harid harjoituksen yksilöivä harid tunnus.
     * @return palauttaa listana vastaavat sarjat.
     */
    public List<Sarja> getSarjat(int harid){
        List<Sarja> loydetyt = new ArrayList<Sarja>();
        for(Sarja sarja : this.sarjat) {
            if(sarja.getHarid() == harid) {
                loydetyt.add(sarja);
            }
        }
        return loydetyt;
    }
    
    /**
     * Sarjojen lukumäärän palauttaminen.
     * @return palauttaa sarjat-taulukon koon eli sarjojen lukumäärän.
     */
    public int getSarjatLkm() {
        return this.sarjat.size();
    }
    
    /**
     * Asetetaan Sarjat-oliolle tiedostonimi
     * @param nimi tiedoston nimi, joka asetetaan
     */
    public void setTiedostonNimi(String nimi) {
        this.tiedostonimi = nimi;
    }
    
    /**
     * Haetaan Sarjat-olion tiedoston nimi
     * @return palauttaa Sarjat-olion tiedoston nimen
     */
    public String getTiedostoNimi() {
        return this.tiedostonimi;
    }
    
    /**
     * Lukee sarjat tiedostosta Sarjat-olioon.
     * @throws SailoException jos ei tiedostosta lukeminen onnistu
     */
    public void lueTiedostosta() throws SailoException {
        this.sarjat = new ArrayList<Sarja>();
        File tiedosto = new File(this.tiedostonimi);
        try(BufferedReader lukija = new BufferedReader(new FileReader(tiedosto))){
            String rivi;
            while((rivi = lukija.readLine()) != null) {
                Sarja sarja = new Sarja();
                sarja.parse(rivi);
                this.lisaaSarja(sarja);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedoston kanssa on ongelmia!");
        }
    }
    
    /**
     * Tallentaa sarjat tiedostoon.
     * @throws SailoException jos tallentaminen ei onnistu
     */
    @SuppressWarnings("resource")
    public void tallenna() throws SailoException {
        File tiedosto = new File(this.getTiedostoNimi());
        try {
            tiedosto.createNewFile();
            PrintStream stream = new PrintStream(tiedosto);
            for(Sarja sarja : this.sarjat) {
                stream.println(sarja.toString());
            }
            stream.close();
        } catch(IOException e) {
            throw new SailoException("Tiedosto ei aukea");
        }
    }

    /**
     * Iteraattori kaikkien sarjojen läpikäymiseen
     * @return sarjaiteraattori
     * 
     * @example
     * <pre name= "test">
     * #PACKAGEIMPORT
     * #import java.util*;
     *  
     *  Sarjat sarjat = new Sarjat();
     *  Sarja sarja1 = new Sarja(80, 6); sarja1.rekisteroi(); sarjat.lisaa(sarja1);
     *  Sarja sarja2 = new Sarja(70, 8); sarja2.rekisteroi(); sarjat.lisaa(sarja2);
     *  Sarja sarja3 = new Sarja(60, 10); sarja3.rekisteroi(); sarjat.lisaa(sarja3);
     *  Sarja sarja4 = new Sarja(50, 12); sarja4.rekisteroi(); sarjat.lisaa(sarja4);
     *  Sarja sarja5 = new Sarja(40, 16); sarja5.rekisteroi(); sarjat.lisaa(sarja5);
     *  
     *  Iterator<Sarja> i = harrasteet.iterator();
     *  i.next() === sarja1;
     *  i.next() === sarja2;
     *  i.next() === sarja3;
     *  i.next() === sarja4;
     *  i.next() === sarja5;
     *  i.next() === sarja4; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int sarids [] = {0, 1, 2, 3, 4};
     *  
     *  for(Sarja sarja : sarjat){
     *      sarja.getSarid() === sarids[n]; n++;
     *  }
     *  
     *  </pre>
     */
    @Override
    public Iterator<Sarja> iterator() {
        return sarjat.iterator();
    }

    
    /**
     * Testataan sarjat-luokkaa
     * @param args ei käytössä
     * @throws SailoException jos tallentamisen kanssa ongelmia
     */
    public static void main(String[] args) throws SailoException {
        //Testataan Sarjat-luokkaa
        Sarja sarja1 = new Sarja(80, 4);
        Sarja sarja2 = new Sarja(70, 6);
        Sarja sarja3 = new Sarja(60, 8);
        
        sarja1.setToteutuneet(5);
        sarja2.setToteutuneet(6);
        sarja3.setToteutuneet(8);
        
        sarja1.rekisteroi();
        sarja2.rekisteroi();
        sarja3.rekisteroi();
        
        sarja1.setHarid(0);
        sarja2.setHarid(1);
        sarja3.setHarid(2);
        
        Sarjat sarjat = new Sarjat();
        
        sarjat.lisaaSarja(sarja1);
        sarjat.lisaaSarja(sarja2);
        sarjat.lisaaSarja(sarja3);
        
        sarjat.setTiedostonNimi("sarjat.dat");
        sarjat.tallenna();
        sarjat.lueTiedostosta();
        
        for(Sarja sarja : sarjat) {
            sarja.tulosta(System.out);
        }
    }
}
