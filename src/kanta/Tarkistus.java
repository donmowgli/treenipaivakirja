package kanta;

import java.time.LocalDate;

/**
 * Oikeellisuustarkistukset kaikelle tekstinsyötölle
 * @author Akseli Jaara
 * @version 18 Apr 2021
 *
 */
public class Tarkistus {
    private static final String merkit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ.,()";
    private static final String numerotDouble = "0123456789.";
    private static final String numerotInteger = "0123456789";
    private static final String pvmMerkit = "0123456789.";
    private static final int[] kuukaudet = {31,29,31,30,31,30,31,31,30,31,30,31};
    
    /**
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
    
    private boolean etsi(String jono, String oikeat) {
        String tarkistus = jono.toUpperCase();
        for (int i=0; i<jono.length(); i++)
            if ( oikeat.indexOf(tarkistus.charAt(i)) < 0 ) return false;
        return true;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa
     * @return palauttaa virheviestin, null jos virhettä ei ole
     */
    public String tarkistaInteger(String jono) {
        if(etsi(jono, numerotInteger) == false) return "Käytä numeroarvojen kohdalla vain numeroita!";
        return null;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa
     * @return palauttaa virheviestin, null jos virhettä ei ole
     */
    public String tarkistaDouble(String jono) {
        if(etsi(jono, numerotDouble) == false) return "Käytä numeroarvojen kohdalla vain numeroita!";
        return null;
    }
    
    /**
     * @param jono merkkijono, joka halutaan tarkistaa että onko toimiva päivämääräksi
     * @return palauttaa virheilmoituksen merkkijonona, palauttaa null jos toimii
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
}
