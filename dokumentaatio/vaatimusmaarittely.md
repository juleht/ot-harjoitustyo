# Vaatimusmäärittely

Budjetointi sovellus – henkilökohtaisten menojen ja tulojen seurantaan. 
Käyttäjän on mahdollista tallentaa sovellukseen meno- ja tulotapahtumiaan, kuten palkkansa tai kulunsa vaatteisiin tai ruokaan. Sovellus laskee automaattisesti 
menojen ja tulojen summan sekä näyttää saldon. Lisäksi sovellus laskee erilaisia tunnus lukuja menoista- ja tuloista, kuten tapahtumien määrän, suurimman 
yksttäisen tulon ja menon sekä keskiarvot tuloista ja menoista.

### Käyttäjät

Sovelluksella on vain yksi käyttäjärooli eli normaali käyttäjä. 

### Käyttöliittymäluonnos

Sovellus koostuu viidestä eri näkymästä. Ensimmäisessä näkymässä 
käyttäjälle avautuu kirjautumissivu, johon on mahdollista syöttää 
käyttäjätunnus tai luoda uusi käyttäjä. Onnistuneen kirjautumisen 
jälkeen käyttäjä ohjataan sovellusnäkymään, jossa käyttäjä voi lisätä meno- tai tulotapahtuman sekä näkee tulojen ja menojen summan sekä saldon. 
Tilastonäkymässä käyttäjä näkee tunnusluvut ja tapahtumanäkymässä käyttäjä voi tarkastella yksittäisiä tapahtuma ja poistaa niitä tarvittaessa.

### Perusversion toiminnallisuus

##### Ennen kirjautumista

* käyttäjä voi luoda järjestelmään käyttäjätunnuksen
* käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3
merkkiä
* käyttäjä voi kirjautua järjestelmään
* kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus 
kirjautumislomakkeelle
* jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

##### Kirjautumisen jälkeen

* käyttäjä voi luoda meno- tai tulotapahtuman.
* sovellus näyttää automaattisesti tulojen ja menojen summan sekä saldon.
* käyttäjä voi tarkastella meno ja tulotapahtumia sekä poistaa niitä.
* käyttäjä voi tarkastella tunnuslukuja kuten keskiarvoja.

### Jatkokehitysideat

Perusversion jälkeen budjetointi sovellusta täydennetään seuraavilla 
toiminnallisuuksilla
* menojen muokkaaminen
* käyttäjä voi poistaa käyttäjätilinsä
* tapahtumia voi tarkastella tietyltä ajanjaksolta
* tunnuslukuja voi tarkastella tietyltä ajanjaksolta
* sovellukseen lisätään grafiikkaa näyttämään tulojen ja menojen kehitystä viivakaaviolla (viivadiagrammi)

