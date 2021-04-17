package kanta;

/**
 * 
 * Muokattavien tietorakenteiden tallentaminen olioon käsiteltäväksi kohdeluokan kanssa.
 * @author Akseli Jaara
 * @version 17 Apr 2021
 *
 * @param <T> Muokattava olio
 */
public class Muutettava <T extends Muokattava>{
    private T  muokattava;
    private Class<?> kohde;
    
    /**
     * @param muokattava olio, jota halutaan muokata
     * @param kohde kohdeluokka, johon muutos tehdään
     */
    @SuppressWarnings("unchecked")
    public Muutettava( Muokattava muokattava, Class <?> kohde) {
        this.muokattava = (T) muokattava;
        this.kohde = kohde;
    }
    
    /**
     * @return palauttaa muokattavan olion
     */
    public Muokattava getMuokattava() {
        return this.muokattava;
    }
    
    /**
     * @return palauttaa luokan, johon muokkaus tehdään
     */
    public Class<?> getKohde() {
        return this.kohde;
    }
}
