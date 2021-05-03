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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import kanta.Tarkistus;

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
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * treenit.getTreeniLkm() === 0;
     * Treeni treeni1 = new Treeni("testi1", null); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1);
     * treenit.getTreeniLkm() === 1;
     * Treeni treeni2 = new Treeni("testi2", null); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2);
     * treenit.getTreeniLkm() === 2;
     * Treeni treeni3 = new Treeni("testi3", null); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3);
     * treenit.getTreeniLkm() === 3;
     * </pre>
     */
    public void lisaaTreeni(Treeni treeni) {
        if (lkm >= treenit.length) {
            int uusiKoko = lkm + 1;
            Treeni[] uudet = new Treeni[uusiKoko];
            for (int i = 0; i < uusiKoko - 1; i++) {
                uudet[i] = treenit[i];
            }
            this.treenit = uudet;
        }
        treenit[lkm] = treeni;
        lkm++;
    }
    
    /**
     * Haetaan id-arvoa vastaava Treeni-olio
     * @param id id-arvo, jonka mukaan Treeniä haetaan
     * @return palauttaa id-arvoa vastaavan treenin
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni("testi1", null); testi1.rekisteroi(); treenit.lisaa(treeni1);
     * Treeni treeni2 = new Treeni("testi2", null); testi2.rekisteroi(); treenit.lisaa(treeni2);
     * Treeni treeni3 = new Treeni("testi3", null); testi3.rekisteroi(); treenit.lisaa(treeni3);
     * treenit.getTreeni(1).getId() === 1;
     * treenit.getTreeni(2).getId() === 2;
     * treenit.getTreeni(3).getId() === 3;
     * treenit.getTreeni(4).getId() === null;
     * </pre>
     */
    public Treeni getTreeni(int id) {
        ArrayList<Treeni> trnt = this.getTreenit();
        for (Treeni trn : trnt) {
            if (trn.getId() == id) {
                return trn;
            }
        }
        return null;
    }
    
    /**
     * Haetaan treenien lukumäärä suoraa oliomuuttujasta lkm.
     * @return palauttaa treenien lukumäärän.
     */
    public int getTreeniLkm() {
        return this.lkm;
    }
    
    /**
     * @return palauttaa Treenit-olion treenit-taulukon ArrayListinä.
     */
    public ArrayList<Treeni> getTreenit() {
        ArrayList<Treeni> palautettava = new ArrayList<Treeni>();
        for (int i = 0; i < this.lkm; i++) {
            palautettava.add(treenit[i]);
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
     * @param hakuehto merkkijono, jonka mukaan haetaan
     * @return palauttaa listan alkioista, joiden nimi vastaa hakuehtoa
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni("testi1", null); testi1.rekisteroi(); treenit.lisaa(treeni1);
     * Treeni treeni2 = new Treeni("testi2", null); testi2.rekisteroi(); treenit.lisaa(treeni2);
     * Treeni treeni3 = new Treeni("testi3", null); testi3.rekisteroi(); treenit.lisaa(treeni3);
     * treenit.etsi("testi1").get(0).getNimi() === "testi1";
     * treenit.etsi("testi2").get(1).getNimi() === "testi2";
     * treenit.etsi("testi3").get(2).getNimi() === "testi3";
     * treenit.etsi("testi").isEmpty() === true;
     * </pre>
     */
    public ArrayList<Treeni> etsi(String hakuehto){
        ArrayList<Treeni> ret = new ArrayList<Treeni>();
        ArrayList<Treeni> alkiot = this.getTreenit();
        Tarkistus tarkistus = new Tarkistus();
        for (Treeni treeni : alkiot) {
            if (tarkistus.vertaa(hakuehto, treeni.getNimi())) {ret.add(treeni);}
        }
        Collections.sort(ret);
        return ret;
    }
    
    /**
     * @param hakuehto merkkijono, jonka mukaan haetaan
     * @return palauttaa listan kaikista Treeneistä, joiden Pvm-arvo vastaa hakuehtoa
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni("testi1", LocalDate.now()); testi1.rekisteroi(); treenit.lisaa(treeni1);
     * Treeni treeni2 = new Treeni("testi2", LocalDate.now()); testi2.rekisteroi(); treenit.lisaa(treeni2);
     * Treeni treeni3 = new Treeni("testi3", LocalDate.now()); testi3.rekisteroi(); treenit.lisaa(treeni3);
     * treenit.etsiPvm(treenit.pvmToString()).get(0).getNimi() === "testi1";
     * </pre>
     */
    public ArrayList<Treeni> etsiPvm(String hakuehto){
        ArrayList<Treeni> ret = new ArrayList<Treeni>();
        ArrayList<Treeni> alkiot = this.getTreenit();
        Tarkistus tarkistus = new Tarkistus();
        for (Treeni treeni : alkiot) {
            if (treeni.getPvm() == null) {continue;}
            if (tarkistus.vertaa(hakuehto, treeni.pvmToString())) {ret.add(treeni);}
        }
        Collections.sort(ret);
        return ret;
    }
    
    /**
     * Poistetaan Treenit-luokan Treeni-taulukosta haluttu treeni Trid-oliomuuttujan mukaisesti.
     * @param id id, jonka mukaan Treeni-olio poistetaan
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni("testi1", null); testi1.rekisteroi(); treenit.lisaa(treeni1);
     * Treeni treeni2 = new Treeni("testi2", null); testi2.rekisteroi(); treenit.lisaa(treeni2);
     * Treeni treeni3 = new Treeni("testi3", null); testi3.rekisteroi(); treenit.lisaa(treeni3);
     * treenit.poista(1);
     * treenit.getTreeni(1) === null; 
     * </pre>
     */
    public void poista(int id) {
        Treeni[] vanha = this.treenit;
        int vLkm = this.lkm;
        this.treenit = new Treeni[0];
        this.lkm = 0;
        for (int i = 0; i < vLkm; i++) {
            if (vanha[i].getTrid() == id) {
                continue;
            }
            this.lisaaTreeni(vanha[i]);
        }
    }
    
    /**
     * Lukee treenit tiedostosta Treenit-olioon.
     * @throws SailoException jos ei tiedostosta lukeminen onnistu
     */
    public void lueTiedostosta() throws SailoException {
        this.treenit = new Treeni[this.lkm];
        this.lkm = 0;
        File tiedosto = new File(this.tiedostonimi);
        try(BufferedReader lukija = new BufferedReader(new FileReader(tiedosto))){
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
    @SuppressWarnings("resource")
    public void tallenna() throws SailoException {
        File tiedosto = new File(this.getTiedostoNimi());
        try {
            tiedosto.createNewFile();
            PrintStream stream = new PrintStream(tiedosto);
            for(int i = 0; i < this.lkm; i++) {
                stream.println(this.treenit[i].toString());
            }
            stream.close();
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
        treeni2.setPvm(LocalDate.now());
        
        treeni.rekisteroi();
        treeni2.rekisteroi();
        
        treenit.lisaaTreeni(treeni);
        treenit.lisaaTreeni(treeni2);
        treenit.lisaaTreeni(treeni2);
        treenit.lisaaTreeni(treeni2);
        treenit.lisaaTreeni(treeni2);
        
        treenit.poista(1);
        
        treenit.setTiedostonNimi("treenit.dat");
        
        for(int i = 0; i < treenit.getTreeniLkm(); i++) {
            treenit.treenit[i].tulosta(System.out);
        }
        
        treenit.tallenna();
        treenit.lueTiedostosta();
        
        for(int i = 0; i < treenit.getTreeniLkm(); i++) {
            treenit.treenit[i].tulosta(System.out);
        }
        
    }

}
