## BudgetApp


Sovelluksen avulla käyttäjän on mahdollista helpottaa rahanhallintaa ja 
pitää kirjaa henkilökohtaista kuluistaan.

### Dokumentaatio

[Käyttöohje.md](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/K%C3%A4ytt%C3%B6ohje.md)
[Arkkitehtuurikuvaus.md](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/Arkkitehtuurikuvaus.md)
[Testausdokumentti.md](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/Testausdokumentti.md)
[tuntikirjanpito.md](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
[vaatimusmaarittely.md](https://github.com/juleht/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

### Releaset

### Komentorivitoiminnot

#### Testaus

Testit suoritetaan komennolla

mvn test

Testikattavuusraportti luodaan komennolla

mvn jacoco:report

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto 
target/site/jacoco/index.html

#### Suoritettavan jarin generointi

Komento

mvn package

generoi hakemistoon target suoritettavan jar-tiedoston 
Budget-1.0-SNAPSHOT.jar
