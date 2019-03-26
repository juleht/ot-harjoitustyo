# Vaatimusmäärittely

Budjetointi sovellus – henkilökohtaisten menojen seurantaan. Käyttäjän 
on mahdollista tallentaa sovellukseen kuukausittainen budjettinsa, sekä 
yksittäisiä menojaan. Sovellus laskee erilaisia tunnusluku menoista 
käyttökohteen mukaan.

### Käyttäjät

Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. 

### Käyttöliittymäluonnos

Sovellus koostuu kolmesta eri näkymästä. Ensimmäisessä näkymässä 
käyttäjälle avautuu kirjautumissivu, johon on mahdollista syöttää 
käyttäjätunnus tai luoda uusi käyttäjä. Onnistuneen kirjautumisen 
jälkeen käyttäjä ohjataan sovellusnäkymään, jossa näkyy budjetti ja 
tapahtumia, menojen muodossa.

### Perusversion toiminnallisuus

##### Ennen kirjautumista

* käyttäjä voi luoda järjestelmään käyttäjätunnuksen
* käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 4 
merkkiä
* käyttäjä voi kirjautua järjestelmään
* kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus 
kirjautumislomakkeelle
* jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

##### Kirjautumisen jälkeen

* käyttäjä asettaa kuukausittaisen budjettinsa
* jos budjettinsa jo olemassa, käyttäjä ohjataan suoraan 
sovellusnäkymään
* käyttäjä voi luoda sovellukseen uusia menoja
* sovellus vähentää tapahtumat budjetista automaattisesti ja näyttää 
jäljellä olevan budjettinsa

### Jatkokehitysideat

* Perusversion jälkeen budjetointi sovellusta täydennetään seuraavilla 
toiminnallisuuksilla
* käyttäjä voi poistaa tapahtuman sovelluksesta
* käyttäjä voi kirjautua ulos järjestelmästä
* meno kohtaiset tunnusluvut
* menojen muokkaaminen
* menojen poistaminen

