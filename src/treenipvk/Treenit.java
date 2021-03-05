/**
 * 
 */
package Treenipvk;

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
        if(lkm <= treenit.length) {
            //Exception tähän
        }
       this. treenit[lkm] = treeni;
       this.lkm++;
    }
    
    /**
     * @param i halutun treenin indeksinumero.
     * @return palauttaa treenit-taulukosta indeksin mukaisen treenin.
     */
    public Treeni getTreeni(int i) {
        if(i < 0 || i < lkm) {
            //Exception tähän
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
     * Järjestää treenit päivämäärän eli LocalDate-olion mukaisesti laskevaan järjestykseen.
     * @param pvm LocalDate-olio, jonka mukaan halutaan järjestää
     * @return palauttaa listan Treenejä.
     */
    public List<Treeni> jarjestaTreeni(LocalDate pvm){
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
     * Testataan Treeni-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
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
