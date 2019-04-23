# Arkkiterhtuurikuvaus

## Rakenne

Ohjelmassa on kolmitasoinen kerrosarkkitehtuuri. Koodin pakkausrakenne:


Pakkaus budget.ui sisältää käyttöliittymän. Käyttöliittymä on tehty 
JavaFx:llä. Pakkaus budget.domain sisältää sovelluslogiikan ja budget.dao 
koodin joka tallentaa tietoa tietokantaan.

## Käyttöliittymä

Käyttöliittymä sisältää neljä erillistä näkymää.

* sovellukseen kirjautuminen
* käyttäjän luominen sovellukseen
* päänäkymän
* näkymän menoista ja tuloista

Kaikki näkymät on toteuttettu Scene-oliolla ja kaikki näytettään samassa 
Stage ikkunassa, joka sisältää kaikki JavaFX-applicaation oliot. 
Käyttöliittämä on luokassa budget.ui.Gui.

Sovelluslogiikka ja käyttöliittymä on sovelluksessa eriytetty. 
Käyttöliittymä sovelluslogiikan toteuttavan olion metodeja.

Käyttäjän kirjautuessa sovellukseen. Käyttäjän on mahdollista 
kirjata sovellukseen uusia tuloja ja menoja. Käyttäjän on myös mahdollista 
tarkastella meno ja tulo tapahtumiaan ja poistaa niitä tarvittaessa. 

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat user ja event. 
User-luokka kuvaavat käyttäjiä ja eventluokka käyttäjän meno- ja 
tulotapahtumia.

Sovelluksen toiminnoista vastaa luokka BudgetLogic, jossa käyttöliittymän 
toiminnot ovat. Toimintoja ovat:

* Boolean createUser(String username)
* User logOn(String username)
* Event createEvent(double amount, String eventtype, LocalDate eventdate)
* double incomes()
* double expenses()
* double balance()
* List<Event> listEvents()  
* void deleteEvent(Event e)

Tiedon tallentamisesta tietokantaan vastaa luokat UserDao ja EventDao. 
Molemmat luokat toteuttavat rajapinnan DAO ja sijaitsevat pakkauksessa 
budget.dao. BudgetLogic luokka pääsee käsiksi tietoihin näiden luokkien 
avulla. Alla on kuva pakkaus/luokkakaaviosta, joka kuvaa ohjelman osien 
suhdetta toisiinsa.


![pakkauskaavio kuva](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/BudgetPackageFigure.png)

## Tiedon pysyväistallennus

budget.dao pakkauksen luokat UserDao ja EventDao vastaavat tiedon 
hakemisesta tietokannasta ja tallentamisesta tietokantaan. 

Luokat on tehty DAO-suunnittelumallilla ja ne voidaan korvata uusilla 
toteutuksilla tarvittaessa. Luokat on eristytetty DAO rajapinnan taakse.

Sovellustallentaa budgetDB.db tietokantaan käyttäjät ja käyttäjille kuuluvat 
tulot ja menot.

Käyttäjät tallennetaan muodossa: Käyttäjästä tallennetaan ainoastaan 
käyttäjätunnus. Jokaiselle käyttäjälle asetetaan uniikki id käyttäjän 
tunnistamiseksi.

id | käyttäjätunnus
-- | ------------
1  | Jari Korhonen
2  | Matti Virtanen
3  | Riitta Mäkinen


Tulot ja menot tallennetaan muodossa: Jokaiselle tulolle tai menolle 
asetetaan uniikki id, jotta tulon tai menon voi tunnistaa. Lisäksi 
jokainen meno- tai tulotapahtuma asetetaan tietylle käyttäjälle, joka sen on 
tehnyt tallentamalla käyttäjän id tapahtuman yhteyteen. Muut tallenettavat 
tiedot ovat tapahtuman summa, päivämäärä ja tyyppi.

id | summa | tyyppi | päivämäärä | käyttäjä_id
-- | ----- | ------ | ---------- | -----------
1  | -100  | ruoka  | 20-3-2011  | 1
2  | -20   | taksi  | 21-2-2019  | 2
3  | 2000  | palkka | 1-3-2019   | 2


## Päätoiminnallisuudet

Sovelluksen toiminnallisuudet kuvattuna sekvenssikaavioina.

#### käyttäjänimen luominen

Kun uusi käyttäjänimi luodaan, käyttäjä syöttää käyttäjänimen, ja sovellus 
etenee seuraavasti: 

![sekvenssikaaviokuva](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/createUser.png)

Tapahtuman käsittelijä kutsuu sovelluslogiikan metodia createUser, joka saa 
parametrikseen käyttäjän syöttämän käyttäjänimen. UserDaon avulla 
tietokannasta haetaan lista käyttäjänimista ja tarkistetaan onko 
käyttäjänimi olemassa. Mikäli käyttäjänimi on olemassa tästä ilmoitetaan 
käyttäjälle ja pyydetään vaihtamaan käyttäjänimi.

Mikäli käyttäjänimi ei ole 
olemassa Luodaan User-olio ja tallennetaan se UserDao create-metodin avulla 
tietokantaan. Lopuksi käyttöliittymässä vaihdetaan näkymäksi logInScene.
