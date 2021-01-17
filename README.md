# Treenipäiväkirja

Treenipäiväkirja treeniohjelman muodostamiseen, sekä treeniohjelman harjoitusten toteutumisen ja niiden tulosten dokumentointiin.

1.1. Mitä tietoja harjoitteista tarvitaan
- Harjoitteen nimi
- Käyttäjän maksimitulos harjoituksissa
- Sovelluksessa laskettu työpaino harjoitusta varten
- Sarjojen lukumäärä
- Toistojen lukumäärä

Joiden mukaan rakennetaan harjoite. Harjoitteiden pohjalta luodaan harjoitus, joka sisältää harjoitteita. Harjoitteista kootaan harjoitusohjelma, joka sisältää harjoituksia. Harjoitusohjelman mukaan edetessä, käyttäjä rekisteröi harjoituskertoja ja niiden tuloksia.

1.2. Mitä ominaisuuksia treenipäiväkirjalta halutaan?
- Harjoitteen lisääminen
- Harjoitteen muokkaaminen
- Harjoituksen lisääminen
- Harjoituksen muokkaaminen
- Harjoitusohjelman lisääminen
- Harjoitusohjelman muokkaaminen
- Harjoituksen rekisteröiminen tuloksineen
- Parhaiten kehittyvien liikkeiden jäsentely

1.3. Tallennustiedostojen muoto (voi olla myös tietokanta)
merkinnat.dat
; Kenttien järjestys tiedostossa on seuraava:
; päivämäärä | treeniohjelma | treeni | harjoite 1 nimi | harjoite 1 tulos | ... |harjoite n nimi | harjoite n tulos|

harjoite.dat
; Kenttien järjestys tiedostossa on seuraava:
; nimi | maksimipaino | työpaino | sarjat | toistot |

2.1 Ohjelman käynnistys
Ohjelma käynnistetään klikkaamalla kerho.jar-ikonia tai antamalla komentoriviltä komento
java -jar treenipaivakirja.jar

Kun ohjelma käynnistyy, tulostuu näyttöön ohjelman tiedot ja kaksi eri vaihtoehtoa, uuden treeniohjelman luominen tai vanhan etsiminen. Mikäli käyttäjä antaa vanhan treeniohjelman kohdalla nimen jota ei tunnisteta, tulostuu näyttöön "Tiedosto ei aukea, tarkista oikeinkirjoitus".

Jos käyttäjä luo uuden treeniohjelman, pääsee käyttäjä luomaan uutta treeniohjelmaa. Vaihtoehtoisesti avaa vanhan treeniohjelman.

2.2 Pääikkuna

Kun ohjelma on käynnistynyt, on näökyvillä ohjelman pääikkuna.

Pääikkunassa on seuraava valikkorakenne:

Tiedosto        Muokkaa                     Apua
========        =======                     ====
Tallenna        Lisää harjoite              Ohje
Avaa...         Lisää harjoitus             Tietoja...
Tulosta...      Lisää harjoitusohjelma
Lopeta          Poista harjoite
                Poista harjoitus
                Poista harjoitusohjelma

2.2.2 Hakeminen

Pääikkunan vasemmassa reunassa näkyy Hakuehto. Tästä voi valita, etsitäänkö harjoitusohjelmaa, harjoitusta, harjoitetta vai tiettyä päivämäärää. Tämän jälkeen tekstikenttään voi syöttää hakuehdon ja listaan tulee vain ne tietotyypit joille haku toteutuu. Hakutermi voi löytyä nimien kohdalla mistä kohden nimeä tahansa. Esimerkiksi jos kirjoitetaan hakuehtoon k, näytetään, kaikki, joissa nimessä on jossain kohtaa hakuehto k.

Tulokset lajitellaan valitun hakukentän perusteella.

2.2.2 Muokkaaminen

Valittua osiota voi muokata kirjoittamalla uuden arvon kyseiselle osiolle, esimerkiksi harjoitteelle muokaten sen maksimipainoa. Jos tietoon syötetään jotain, mikä ei kelpaa, tulee tästä ilmoitus:

Esim. toistojen määrästä muodosta 1,5

Tulee ilmoittaa

Toistojen määrän tulee olla kokonaisluku!

Samalla virheellinen syöttökenttä värittyy punaiseksi.