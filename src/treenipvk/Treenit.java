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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Akseli Jaara
 * @version 4 Mar 2021
 *
 */
public class Treenit {
    private static final int MAX_TREENIT = 6;
    private int lkm = 0;
    private String tiedostonimi;
    private Treeni [] treenit = new Treeni[MAX_TREENIT];
    
    /**
     * Treenit-mudostaja parametreillä.
     * @param tiedostonimi tiedostonimi, jolla treenien tiedot halutaan tallentaa.
     */
    public Treenit(String tiedostonimi) {
        this.tiedostonimi = tiedostonimi;
    }
    
    /**
     * Treenit-olion parametriton muodostaja.
     */
    public Treenit() {
        this("");
    }
    
    /**
     * 
     * @param treeni Treeni-olio, joka treenit-taulukkoon halutaan lisätä.
     * @throws SailoException SailoException jos tietorakenne on jo täynnä
     */
    public void lisaaTreeni(Treeni treeni) throws SailoException {
        if(lkm <= treenit.length) {
            throw new SailoException("Liikaa alkioita!");
        }
       this. treenit[lkm] = treeni;
       this.lkm++;
    }
    
    /**
     * @param i halutun treenin indeksinumero.
     * @return palauttaa treenit-taulukosta indeksin mukaisen treenin.
     * @throws IndexOutOfBoundsException jos haettu indeksi on olemassaolevan ulkopuolella, eli alle 0 tai tietorakenteen koon eli alkioiden lukumäärää suurempi
     */
    public Treeni getTreeni(int i) throws IndexOutOfBoundsException {
        if(i < 0 || i < lkm) {
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        }
        return this.treenit[i];
    }
    
    /**
     * Haetaan treenien lukumäärä suoraa oliomuuttujasta lkm.
     * @return palauttaa treenien lukumäärän.
     */
    public int getTreeniLkm() {
        return this.lkm;
    }
    
    /**
     * @return palauttaa Treeni-olion Treeni[] -taulukon
     */
    public Treeni[] getTreenit() {
        return this.treenit;
    }
    
    /**
     * Järjestää treenit päivämäärän eli LocalDate-olion mukaisesti laskevaan järjestykseen.
     * TODO lisää toinen metodi, jossa haku ja järjestäminen päivämäärän lisäämisen mukaisesti
     * @return palauttaa listan Treenejä.
     */
    public List<Treeni> jarjestaTreeni(){
        ArrayList<Treeni> palautettava = new ArrayList<Treeni>();
        ArrayList<LocalDate> paivamaarat = new ArrayList<LocalDate>();
        
        for(Treeni treeni : this.treenit) {
            if(treeni.getPvm() != null) {
                paivamaarat.add(treeni.getPvm());
            }
        }
        
        paivamaarat.sort(null);
        
        while(palautettava.size() != paivamaarat.size()) {
            for(int i = 0; i < this.treenit.length; i++) {
                if(treenit[i].getPvm() == paivamaarat.get(0)) {
                    palautettava.add(treenit[i]);
                    paivamaarat.remove(0);
                }
            }
        }
        
        return palautettava;
    }
    
    /**
     * Asetetaan Treenit-oliolle tiedostonimi
     * @param nimi tiedoston nimi, joka asetetaan
     */
    public void setTiedostonNimi(String nimi) {
        this.tiedostonimi = nimi;
    }
    
    /**
     * Haetaan Treenit-olion tiedoston nimi
     * @return palauttaa Harjoite-olion tiedoston nimen
     */
    public String getTiedostoNimi() {
        return this.tiedostonimi;
    }
    
    /**
     * Lukee treenit tiedostosta Treenit-olioon.
     * @param tiedNimi tiedoston nimi, josta halutaan lukea
     * @throws SailoException jos ei tiedostosta lukeminen onnistu
     */
    public void lueTiedostosta(String tiedNimi) throws SailoException {
        try(BufferedReader lukija = new BufferedReader(new FileReader(tiedNimi))){
            String rivi;
            while((rivi = lukija.readLine()) != null) {
                Treeni treeni = new Treeni();
                treeni.parse(rivi);
                this.lisaaTreeni(treeni);
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
            for(int i = 0; i < this.treenit.length; i++) {
                fo.println(this.treenit[i].toString());
            }
        } catch(IOException e) {
            throw new SailoException("Tiedosto ei aukea");
        }
    }

    /**
     * Testataan Treeni-luokkaa
     * @param args ei käytössä
     * @throws SailoException metodeissa ja funktioissa, joissa tietorakenteen kanssa ongelmia
     */
    public static void main(String[] args) throws SailoException {
        //Testataan Treenit-luokkaa
        Treenit treenit = new Treenit();
        
        Treeni treeni = new Treeni();
        treeni.setNimi("Työntävä");
        treeni.setPvm(LocalDate.now());
        
        Treeni treeni2 = new Treeni();
        treeni2.setNimi("Vetävä");
        
        treeni.rekisteroi();
        treeni2.rekisteroi();
        
        treenit.lisaaTreeni(treeni);
        treenit.lisaaTreeni(treeni2);
        
        for(Treeni trn : treenit.treenit) {
            trn.tulosta(System.out);
        }
        
    }

}
