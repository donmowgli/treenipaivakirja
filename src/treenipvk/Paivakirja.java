/**
 * 
 */
package Treenipvk;

/**
 * Päiväkirja-luokka, joka huolehtii treeneistä. Tulee huolehtimaan luokkien välisestä yhteistyöstä.
 * 
 * @author Akseli Jaara
 * @version 4 Mar 2021
 *
 */
public class Paivakirja {
    private Treenit treenit = new Treenit();
    private Harjoitteet harjoitteet = new Harjoitteet();
    private Sarjat sarjat = new Sarjat();
    
    /**
     * Treenien kokonaislukumäärän palauttaminen.
     * @return palauttaa treenien lukumäärän
     */
    public int getTreeneja() {
        return treenit.getTreeniLkm();
    }
    
    /**
     * Lisää sarjan sarjoihin.
     * @param sarja lisättävä sarja-olio
     * @example
     * <pre name="test">
     *  Paivakirja paivakirja = new Paivakirja();
     *  Sarja sarja1 = new Sarja(); sarja1.rekisteroi();
     *  Sarja sarja2 = new Sarja(); sarja2.rekisteroi();
     *  paivakirja.lisaa(sarja1); kerho.getSarjoja() === 1;
     *  paivakirja.lisaa(sarja2); kerho.getSarjoja() === 2;
     *  </pre>
     */
    public void lisaa(Sarja sarja) {
        sarjat.lisaaSarja(sarja);
    }
    
    /**
     * Sarjojen kokonaislukumäärän palauttaminen
     * @return palauttaa sarjojen kokonaislukumäärän.
     */
    public int getSarjoja() {
        return sarjat.getSarjatLkm();
    }
    
    /**
     * Lisää harjoitteen harjoitteisiin.
     * @param harjoite lisättävä harjoite-olio
     * @example
     * <pre name="test">
     *  Paivakirja paivakirja = new Paivakirja();
     *  Harjoite harjoite1 = new Harjoite(); harjoite1.rekisteroi();
     *  Harjoite harjoite2 = new Harjoite(); harjoite2.rekisteroi();
     *  paivakirja.lisaa(harjoite1); kerho.getHarjoitteita() === 1;
     *  paivakirja.lisaa(harjoite2); kerho.getHarjoitteita() === 2;
     *  </pre>
     */
    public void lisaa(Harjoite harjoite) {
        harjoitteet.lisaaHarjoite(harjoite);
    }
    
    /**
     * Harjoitteiden kokonaislukumäärän palauttaminen.
     * @return palauttaa harjoitteiden kokonaislukumäärän
     */
    public int getHarjoitteita() {
        return harjoitteet.getHarjoitteetLkm();
    }
    
    /**
     * Lisää treenin treeneihin.
     * @param treeni lisättävä treeni-olio.
     * @throws SailoException jos tietorakenteen kanssa ongelmia
     * @example
     * <pre name="test">
     *  Paivakirja paivakirja = new Paivakirja();
     *  Treeni treeni1 = new Treeni(); treeni1.rekisteroi();
     *  Treeni treeni2 = new Treeni(); treeni2.rekisteroi();
     *  paivakirja.lisaa(treeni1); kerho.getTreeneja() === 1;
     *  paivakirja.lisaa(treeni2); kerho.getTreeneja() === 2;
     *  </pre>
     */
    public void lisaa(Treeni treeni) throws SailoException {
        treenit.lisaaTreeni(treeni);
    }
    
    /**
     * Poistaa halutulla numerolla löytyvän treenin, harjoitteen tai sarjan. Kesken!
     * TODO täydennä!
     * @param id jonka mukaisesti halutaan poistaa
     * @return palauttaa poistettujen alkioiden lukumäärän
     */
    public int poista (@SuppressWarnings("unused") int id) {
        return 0;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    }

}
