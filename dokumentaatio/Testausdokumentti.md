## Testausdokumentti

Ohjelmaa on testattu yksikkötestein ja integraatiotestein. Testaus on tehty 
hyödyntäen Javan-JUnit työkaluja.

### Yksikkö- ja integraatiotestaus

#### Sovelluslogiikka

Testauksen ytimen muodostavat sovelluslogiikkaa eli pakkauksen 
[budget.domain](https://github.com/juleht/ot-harjoitustyo/tree/master/Budget/src/main/java/budget/domain) 
luokkaa 
[Logic](https://github.com/juleht/ot-harjoitustyo/blob/master/Budget/src/main/java/budget/domain/Logic.java) 
testaava 
[LogicTest](https://github.com/juleht/ot-harjoitustyo/blob/master/Budget/src/test/java/budget/domain/LogicTest.java), 
joka testaa sovelluslogiikan 
toiminnallisuutta.

Dao-rajapintojen metodeita testataan luokilla 
[EventDaoTest](https://github.com/juleht/ot-harjoitustyo/blob/master/Budget/src/test/java/budget/dao/EventDaoTest.java) 
ja 
[UserDaoTest](https://github.com/juleht/ot-harjoitustyo/blob/master/Budget/src/test/java/budget/dao/UserDaoTest.java).

#### DAO-luokat 
Molemmat luokat luovat väliaikaisen tietokannan, joilla tiedontallentamista 
tietokantaan testataan.

#### testikattavuus

Testauksessa ei huomioida käyttäliittymää ollenkaan. Testien kattavuus on 
tällöin 92%. Käytännössä kattavuutta laskee se, että User ja Event 
luokkien get ja set metodeita ei testata.



#### Asennus

Sovellusta on testattu windows ympäristössä käyttöohjeen kuvaamalla tavalla. 

Sovellusta on testattu tilanteissa, joissa käyttäjiä ja tulo- ja 
menotapahtumia on tietokannassa valmiina sekä tilanteissa, joissa käyttäjiä 
tai tapahtumia ei ole tietokannassa.

#### Toiminnallisuudet

Määrittelydokumentin ja käyttöohjessa olevat tilanteet on käyty läpi. 
Toiminnallisuuksiin on myös yritetty syöttää virheellisiä arvoja, kuten 
väärä päivämäärä.

#### Sovelluksen laatu ongelmat

* Sovellus ei tällä hetkellä tarjoa käyttäjälle valmista listaa tulo- ja 
menotapahtumista.

* Käyttöliittymä sijaitsee tällä hetkellä yhdessä luokassa ja sen 
pilkkominen useampaan metodiin ja luokkaan parantaisi 
jatkokehitysmahdollisuuksia. Käyttöliittymäluokan ongelmia on yritetty 
parantaa kommenteilla.
