package budget.domain;

import budget.dao.EventDao;
import budget.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Luokka sovelluslogiikalle
 *
 */
public class Logic {

    private UserDao userDao;
    private EventDao eventDao;
    private User userLoggedIn;

    /**
     * Luo uuden sovelluslogiikan, jolle annetaan parametreiksi DAO-oliot
     *
     * @param userDao
     * @param eventDao
     */
    public Logic(UserDao userDao, EventDao eventDao) {
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    /**
     * Metodi luo uuden käyttäjänimen
     *
     * @param username
     * @return false, jos käyttäjänimi olemassa. true, jos käyttäjä nimi
     * tallennetaan tietokantaan
     * @throws SQLException e
     */
    public Boolean createUser(String username) throws SQLException {
        List<User> users = userDao.list();
        for (User u : users) {
            if (u.getUsername().equals(username) || username.length() <= 3) {
                return false;
            }
        }
        userDao.create(new User(username));
        return true;
    }

    /**
     * Metodi kirjautuu sovellukseen
     *
     * @param username metodin parametri on käyttäjänimi
     * @return jos, käyttäjänimi on tietokannassa metodi palauttaa
     * sisäänkirjautuneen käyttäjän jos, käyttäjänimeä ei löydy tietokannasta
     * metodi palauttaa null arvon
     * @throws SQLException e
     */
    public User logOn(String username) throws SQLException {
        User u = userDao.readUsername(username);
        if (u != null) {
            userLoggedIn = u;
            return userLoggedIn;
        }
        return null;
    }

    /**
     * Metodi käyttäjän menojen ja tulojen kirjaamiseen, tulot ja menot
     * tallennetaan tietokantaan. Metodin parametrit ovat: määrä, tapahtuman
     * tyyppi ja päivämäärä:
     *
     * @param amount
     * @param eventtype
     * @param eventdate
     * @return metodi palauttaa luodun tapahtuman.
     * @throws SQLException e
     */
    public Event createEvent(double amount, String eventtype, LocalDate eventdate) throws SQLException {
        Event e = new Event(amount, eventtype, eventdate, userLoggedIn);
        eventDao.create(e);
        return e;
    }

    /**
     * Metodi summaa käyttäjän tulot
     *
     * @return palauttaa tulot double muodossa
     * @throws SQLException e
     */
    public double incomes() throws SQLException {
        List<Event> events = listUserEvents();
        double incomes = 0;
        for (Event e : events) {
            if (e.getAmount() > 0) {
                incomes += e.getAmount();
            }
        }
        return incomes;
    }

    /**
     * Metodi summaa käyttäjän menot
     *
     * @return palauttaa menot double muodossa
     * @throws SQLException e
     */
    public double expenses() throws SQLException {
        List<Event> events = listUserEvents();
        double incomes = 0;
        for (Event e : events) {
            if (e.getAmount() < 0) {
                incomes += e.getAmount();
            }
        }
        return incomes;
    }

    /**
     * Metodi summaa käyttäjän menot ja tulot
     *
     * @return palauttaa saldon
     * @throws SQLException e
     */
    public double balance() throws SQLException {
        List<Event> events = listUserEvents();
        double incomes = 0;
        for (Event e : events) {
            incomes += e.getAmount();
        }
        return incomes;
    }

    /**
     * Metodi palauttaa käyttäjä kohtaisen listan tapahtumista
     *
     * @return mikäli käyttäjällä ei ole tapahtumia metodi palauttaa null,
     * muuten palautetaan listan tapahtumista
     * @throws SQLException e
     */
    public List<Event> listUserEvents() throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        return events;

    }

    /**
     * Metodi käyttäjän meno- tai tulotapahtuman poistamiseen
     *
     * @param e metodi ottaa parametrikseen meno tai tulo tapahtuman
     * @throws SQLException e
     */
    public void deleteEvent(Event e) throws SQLException {
        List<Event> events = listUserEvents();
        for (Event k : events) {
            if (k.getId() == e.getId()) {
                eventDao.delete(k.getId());
            }
        }

    }
}
