package Treenipvk;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille
 * @author Akseli Jaara
 * @version 5 Mar 2021
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /** 
     * Poikkeuksen muodostaja, jolle tuodaan poikkeuksessa käytettävä viesti
     * @param viesti Poikkeuksen viesti, joka halutaan välittää
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
