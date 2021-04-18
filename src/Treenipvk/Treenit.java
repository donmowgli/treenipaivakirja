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
     * Järjestää treenit päivämäärän eli LocalDate-olion mukaisesti laskevaan järjestykseen.
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
     * @param hakuehto merkkijono, jonka mukaan haetaan
     * @return palauttaa listan alkioista, joiden nimi vastaa hakuehtoa
     */
    public ArrayList<Treeni> etsi(String hakuehto){
        ArrayList<Treeni> ret = new ArrayList<Treeni>();
        ArrayList<Treeni> alkiot = this.getTreenit();
        for (Treeni treeni : alkiot) {
            if (treeni.getNimi().equals(hakuehto)) {ret.add(treeni);}
        }
        return ret;
    }
    
    /**
     * @param hakuehto merkkijono, jonka mukaan haetaan
     * @return palauttaa listan kaikista Treeneistä, joiden Pvm-arvo vastaa hakuehtoa
     */
    public ArrayList<Treeni> etsiPvm(String hakuehto){
        ArrayList<Treeni> ret = new ArrayList<Treeni>();
        ArrayList<Treeni> alkiot = this.getTreenit();
        for (Treeni treeni : alkiot) {
            if (treeni.getPvm() == null) {continue;}
            if (treeni.pvmToString().equals(hakuehto)) {ret.add(treeni);}
        }
        return ret;
    }
    
    /**
     * Poistetaan Treenit-luokan Treeni-taulukosta haluttu treeni Trid-oliomuuttujan mukaisesti.
     * @param id id, jonka mukaan Treeni-olio poistetaan
     */
    public void poista(int id) {
        Treeni[] vanha = this.treenit;
        int vLkm = this.lkm;
        this.treenit = new Treeni[0];
        this.lkm = 0;
        for (int i = 0; i < vLkm; i++) {
            if(vanha[i].getTrid() == id) {
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
