package kanta;

import java.time.LocalDate;

/**
 * Oikeellisuustarkistukset kaikelle tekstinsyötölle
 * @author Akseli Jaara
 * @version 18 Apr 2021
 *
 */
public class Tarkistus {
    private static final String merkit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ.,() ";
    private static final String numerotDouble = "0123456789.";
    private static final String numerotInteger = "0123456789";
    private static final String pvmMerkit = "0123456789.";
    private static final int[] kuukaudet = {31,29,31,30,31,30,31,31,30,31,30,31};
    
    /**
     * Tarkistetaan merkkijono, ja halutessaan parametrina myös merkkijono niistä merkeistä joita saa käyttää.
     * @param jono merkkijono, joka halutaan tarkistaa
     * @param oikeat oikeat merkit, joita vasten tarkistetaan. Jos asetetaan null, käytetään automaattisia.
     * @return palauttaa virheilmoituksen jos muita kuin sallittuja merkkejä. Palauttaa null, jos tarkistettava merkkijono on oikeellinen.
     */
    public String tarkista(String jono, String oikeat) {
        String tarkistettavat = oikeat;
        if(oikeat == null) {tarkistettavat = merkit;}
        if(etsi(jono, tarkistettavat) == false) {return "Käytä vain sallittuja merkkejä! Sallitut merkit: " + merkit;}
        return null;
    }
    
    /**
     * Tarkistetaan merkkijono, onko merkkijonossa kiellettyjä merkkejä
     * @param jono merkkijono joka halutaan tarkistaa
     * @param oikeat merkkijono, jossa hyväksytyt merkit
     * @return palauttaa totuusarvon, onko tarkistus hyväksytty
     * 
     * 
     */
    private boolean etsi(String jono, String oikeat) {
        String tarkistus = jono.toUpperCase();
        for (int i=0; i<jono.length(); i++)
            if ( oikeat.indexOf(tarkistus.charAt(i)) < 0 ) return false;
        return true;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa
     * @return palauttaa virheviestin, null jos virhettä ei ole
     * @example
     * <pre name="test">
     * tarkistaInteger(1243) === null;
     * tarkistaInteger("21.12") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaInteger("21.12") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaInteger("21.1e") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaInteger("2f.12") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaInteger("2.2.2") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * </pre>
     */
    public String tarkistaInteger(String jono) {
        if(etsi(jono, numerotInteger) == false) return "Käytä numeroarvojen kohdalla vain numeroita!";
        return null;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa
     * @return palauttaa virheviestin, null jos virhettä ei ole
     * @example
     * <pre name="test">
     * tarkistaDouble("21.12") === null;
     * tarkistaDouble("21.12") === null;
     * tarkistaDouble("21.1e") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaDouble("2f.12") === "Käytä numeroarvojen kohdalla vain numeroita!";
     * tarkistaDouble("2.2.2") === "Erottele numerot desimaaleista vain yhdellä pisteellä!";
     * </pre>
     */
    public String tarkistaDouble(String jono) {
        if(etsi(jono, numerotDouble) == false) return "Käytä numeroarvojen kohdalla vain numeroita!";
        if(jono.split(".").length > 2) return "Erottele numerot desimaaleista vain yhdellä pisteellä!";
        return null;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa että onko toimiva päivämääräksi
     * @return palauttaa virheilmoituksen merkkijonona, palauttaa null jos toimii
     * @example
     * <pre name="test">
     * tarkistaPvm("1.1.2021") === null;
     * tarkistaPvm("1.2.2019") === null;
     * tarkistaPvm("2.3.2000") === null;
     * tarkistaPvm("1.1.1.2021") === "Tarkista arvojen erotukset ja että kaikki arvot on täytetty!";
     * tarkistaPvm("2.12.2000") === "Kuukauden pitää olla alle 13!";
     * tarkistaPvm("30.2.2021") === "Päivämäärä liian suuri asettamallesi kuukaudelle!";
     * tarkistaPvm("30.2.2121") === "Et voi kirjata merkintöjä tulevaisuuteen!";
     * </pre>
     */
    public String tarkistaPvm(String jono) {
        String tarkistus = tarkista(jono, pvmMerkit);
        if(tarkistus != null) {return tarkistus;}
        String[] arvot = jono.split("\\.");
        if(arvot.length != 3) return "Tarkista arvojen erotukset ja että kaikki arvot on täytetty!";
        int pv = Integer.parseInt(arvot[0]);
        int kk = Integer.parseInt(arvot[1]);
        int vv = Integer.parseInt(arvot[2]);
        if(kk > 12) {return "Kuukauden pitää olla alle 13!";}
        if(pv > kuukaudet[kk]) {return "Päivämäärä liian suuri asettamallesi kuukaudelle!";}
        if(vv > LocalDate.now().getYear() || kk > LocalDate.now().getMonthValue()) {return "Et voi kirjata merkintöjä tulevaisuuteen!";}
        if(kk == LocalDate.now().getMonthValue() && pv > LocalDate.now().getDayOfMonth()) {return "Et voi kirjata merkintöjä tulevaisuuteen!";}
        return null;
    }
    
    /**
     * @param jono hakuehto
     * @param maski mihin verrataan
     * @return palauttaa totuusarvon, onko sama merkkijono
     */
    public boolean vertaa(String jono, String maski) {
        if(jono.equalsIgnoreCase(maski)) {
            return true;
        }else if(jono.contains("\s")) {
            return vertaa(jono.replaceFirst("\s", maski.substring(jono.indexOf("\s"))), maski);
        }
        return false;
    }
}
