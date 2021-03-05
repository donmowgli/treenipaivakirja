/**
 * 
 */
package Treenipvk;

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
     * Lukee sarjan tiedostosta. Ei vielä valmiina.
     * TODO Kesken!
     * @param hakemisto tiedoston hakemisto
     */
    public void lueTiedostosta(String hakemisto) {
        this.tiedostonimi = hakemisto + "/sarjat.dat";
        //Exception tähän
    }
    
    /**
     * Tallentaa sarjat tiedostoon. Ei vielä valmis.
     * TODO Kesken!
     */
    public void tallenna() {
        //Tähän exception
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
     */
    public static void main(String[] args) {
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
        sarja1.setHarid(2);
        
        Sarjat sarjat = new Sarjat();
        
        sarjat.lisaaSarja(sarja1);
        sarjat.lisaaSarja(sarja2);
        sarjat.lisaaSarja(sarja3);
        
        for(Sarja sarja : sarjat) {
            sarja.tulosta(System.out);
        }
        
        sarjat.getSarja(0).tulosta(System.out);
    }
}
