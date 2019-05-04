package budget.dao;

import budget.domain.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO luokka, jolla käsitellään event-taulua tietokannassa
 *
 */
public class EventDao implements Dao<Event, Integer> {

    private Database database;

    public EventDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi tallentaa meno- tai tulotapahtuman tietokantaan
     *
     * @param event metodin parametriksi annetaan meno- tai tulotapahtuman
     * @return palauttaa luodun tapahtuman
     * @throws SQLException e
     */
    @Override
    public Event create(Event event) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "INSERT INTO Event (amount, eventtype, eventdate, user_id) VALUES (?, ?, ?, ?)";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setDouble(1, event.getAmount());
        st.setString(2, event.getEventtype());
        st.setDate(3, Date.valueOf(event.getEventdate()));
        st.setInt(4, event.getUser().getId());

        st.executeUpdate();
        st.close();
        conn.close();
        return event;

    }

    /**
     * Metodi lukee tapahtuman tietokannasta
     *
     * @param key metodille annetaan tapahtuman id parametriksi
     * @return metodi palauttaa null, jos tietokannassa ei ole id:llä meno- tai
     * tulotapahtumaa. Metodi palauttaa meno tai tulo tapahtuman, jos
     * tietokannassa on id:tä vastaava meno- tai tulotapahtuma.
     * @throws SQLException e
     */
    @Override
    public Event read(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM Event WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, key);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Event e = new Event(rs.getInt("id"), rs.getDouble("amount"), rs.getString("eventtype"), rs.getDate("eventdate").toLocalDate());

        st.close();
        rs.close();
        conn.close();

        return e;
    }

    /**
     * Metodi poistaa tietokannasta meno- tai tulotapahtuman.
     *
     * @param key metodille annetaan parametriksi meno- tai tulotapahtuman id.
     * @throws SQLException e
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "DELETE FROM Event WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setInt(1, key);
        st.executeUpdate();

        st.close();
        conn.close();
    }

    /**
     * Metodia ei tueta tässä luokassa
     *
     * @return error
     * @throws SQLException
     */
    @Override
    public List<Event> listAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported in this class.");

    }

    /**
     * Metodi listaa käyttäjä kohtaiset meno- tai tulotapahtumat tietokannasta
     *
     * @param key metodille annetaan parametriksi sen käyttäjän id, jonka
     * tapahtumat halutaan listata
     * @return metodi palauttaa listan tapahtumista, muodossa List
     * @throws SQLException e
     */
    @Override
    public List<Event> listById(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM Event WHERE user_id = ?";
        List<Event> events = new ArrayList<>();
        PreparedStatement st = conn.prepareCall(sql);
        st.setInt(1, key);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            double amount = rs.getDouble("amount");
            String eventtype = rs.getString("eventtype");
            LocalDate edate = rs.getDate("eventdate").toLocalDate();
            Event e = new Event(id, amount, eventtype, edate);
            events.add(e);

        }
        return events;
    }
}
