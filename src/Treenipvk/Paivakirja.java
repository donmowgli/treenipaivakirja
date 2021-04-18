/**
 * 
 */
package Treenipvk;

import java.util.ArrayList;

import kanta.Muokattava;

/**
 * Päiväkirja-luokka, joka huolehtii treeneistä, harjoitteista ja sarjoista. Tulee huolehtimaan luokkien välisestä yhteistyöstä.
 * 
 * @author Akseli Jaara
 * @version 4 Mar 2021
 *
 */
public class Paivakirja {
    private Treenit treenit;
    private Harjoitteet harjoitteet;
    private Sarjat sarjat;
    
    /**
     * Paivakirja-olion parametriton muodostaja
     */
    public Paivakirja() {
        this.treenit = new Treenit();
        this.harjoitteet = new Harjoitteet();
        this.sarjat = new Sarjat();
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
     * Poistetaan haluttu treeni treeneistä
     * @param treeni treeni-olio, joka halutaan poistaa
     */
    public void poista(Treeni treeni) {
        treenit.poista(treeni.getId());
    }
    
    /**
     * Treenien kokonaislukumäärän palauttaminen.
     * @return palauttaa treenien lukumäärän
     */
    public int getTreeneja() {
        return treenit.getTreeniLkm();
    }
    
    /**
     * Haetaan päiväkirjan Treenit-olio
     * @return palautetaan päiväkirjan treenit.
     */
    public Treenit getTreenit() {
        return this.treenit;
    }
    
    /**
     * Haetaan indeksin mukainen sarja taulukosta
     * @param i halutun sarjan indeksi
     * @return palauttaa indeksin mukaisen sarjan taulukosta
     */
    public Treeni getTreeni(int i) {
        return this.treenit.getTreeni(i);
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
     * Poistetaan harjoite harjoitteista
     * @param harjoite harjoite, joka halutaan poistaa
     */
    public void poista(Harjoite harjoite) {
        harjoitteet.poista(harjoite.getId());
    }
    
    /**
     * Harjoitteiden kokonaislukumäärän palauttaminen.
     * @return palauttaa harjoitteiden kokonaislukumäärän
     */
    public int getHarjoitteita() {
        return harjoitteet.getHarjoitteetLkm();
    }
    
    /**
     * Haetaan Paivakirja-olion harjoitteet-olio
     * @return Paivakirja-olion harjoitteet-olion
     */
    public Harjoitteet getHarjoitteet() {
        return this.harjoitteet;
    }
    
    /**
     * Haetaan indeksin mukainen sarja taulukosta
     * @param i halutun sarjan indeksi
     * @return palauttaa indeksin mukaisen sarjan taulukosta
     */
    public Harjoite getHarjoite(int i) {
        return this.harjoitteet.getHarjoite(i);
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
     * Poistetaan haluttu Sarja-olio sarjoista
     * @param sarja Sarja-olio joka halutaan poistaa
     */
    public void poista(Sarja sarja) {
        sarjat.poista(sarja.getId());
    }
    
    /**
     * Sarjojen kokonaislukumäärän palauttaminen
     * @return palauttaa sarjojen kokonaislukumäärän.
     */
    public int getSarjoja() {
        return sarjat.getSarjatLkm();
    }
    
    /**'
     * Palauttaa Paivakirja-olion Sarjat-olion.
     * @return palauttaa sarjat-olion.
     */
    public Sarjat getSarjat() {
        return this.sarjat;
    }
    
    /**
     * Haetaan indeksin mukainen sarja taulukosta
     * @param i halutun sarjan indeksi
     * @return palauttaa indeksin mukaisen sarjan taulukosta
     */
    public Sarja getSarja(int i) {
        return this.sarjat.getSarja(i);
    }
    
    /**
     * Luetaan tiedostosta tiedot koko Paivakirja-olioon, sarjoihin, harjoitteisiin, ja treeneihin. Alustettu perusnimillä, ei tarvetta toistaiseksi tarvetta nimetä tiedostoja erikseen, vaikka valmius luokissa on olemassa.
     * @throws SailoException jos ongelmia tiedoston ja siitä lukemisen kanssa.
     */
    public void lueTiedostosta() throws SailoException {
        
        this.sarjat.lueTiedostosta();
        this.harjoitteet.lueTiedostosta();
        this.treenit.lueTiedostosta();
    }
    
    /**
     * Tallennetaan Paivakirja-olion Treenit, Harjoitteet ja Sarjat-oliot erillisiin tiedostoihinsa luokkien omia apumetodeja hyödyntäen.
     * @throws SailoException jos tiedostoon tallentaminen ei onnistu.
     */
    public void tallenna() throws SailoException{
        this.sarjat.tallenna();
        this.harjoitteet.tallenna();
        this.treenit.tallenna();
    }
    
    /**
     * Alustetaan tallennustiedostojen nimet. Valmius myös muokkaamiselle ja voidaan muokata tarpeen vaatiessa esimerkiksi ohjelmaa laajentaessa entisestään treeniohjelmille.
     */
    public void alustaTiedostoNimet() {
        this.sarjat.setTiedostonNimi("sarjat.dat");
        this.harjoitteet.setTiedostonNimi("harjoitteet.dat");
        this.treenit.setTiedostonNimi("treenit.dat");
    }
    
    /**
     * @param <T> tyyppiparametri
     * @param kohde mistä halutaan poistaa
     * @param poistettava olio, joka halutaan poistaa
     */
    public <T> void poista(Class<T> kohde, Muokattava poistettava) {
        if (kohde == Sarjat.class) {this.sarjat.poista(poistettava.getId());}
        else if (kohde == Harjoitteet.class) {this.harjoitteet.poista(poistettava.getId());}
        else if (kohde == Treenit.class) {this.treenit.poista(poistettava.getId());}
        return;
    }
    
    /**
     * Etsitään haettavan olion mukaan 
     * @param haettava olio, jonka mukaan haetaan
     * @param hakulauseke merkkijono, jonka mukaan haetaan
     * @param pvm boolean-arvo, jolla määritetään onko haettava merkkijono pvm-merkkijono
     * @return palauttaa mahdollisen virheviestin. Jos ei virhettä, palauttaa null
     */
    public ArrayList<?> haku(Muokattava haettava, String hakulauseke, boolean pvm) {
        ArrayList<?> ret = new ArrayList<Muokattava>();
        if (haettava.getClass() == Harjoite.class) {ret = this.harjoitteet.etsi(hakulauseke);}
        else if (haettava.getClass() == Treeni.class && pvm == false) {ret = this.treenit.etsi(hakulauseke);}
        else if (haettava.getClass() == Treeni.class && pvm == true ) {ret = this.treenit.etsiPvm(hakulauseke);}
        return ret;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    // TODO testailua
    }
}
