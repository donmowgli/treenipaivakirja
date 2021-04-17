package kanta;

import java.util.ArrayList;

/**
 * Rajapinta muutettaville tietorakenteille.
 * @author Akseli Jaara
 * @version 9 Apr 2021
 *
 */
public  interface Muokattava {
    
    /**
     * toString-metodi, jolla tiedot tallennettavaksi String-olioksi
     * @return palauttaa tiedostoon tallennettavan String-olion
     */
    @Override
    String toString();
    
    /**
     * Haetaan näytöllä kerrottavat tiedot, joita halutaan näyttää.
     * @return palauttaa näytöllä näytettävät tiedot String-oliona
     */
    String getTiedot();
    
    /**
     * Haetaan olion tiedot String-taulukkona
     * @return palauttaa olion arvot String-taulukkona
     */
    ArrayList<String> getArvot();
    
    /**
     * Haetaan muokattavan tietorakenteen id
     * @return palauttaa muokattavan tietorakenteen id:n
     */
    int getId();
}
