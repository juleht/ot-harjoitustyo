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

public class EventDao implements Dao<Event, Integer> {

    private Database database;

    public EventDao(Database database) {
        this.database = database;
    }

    @Override
    public Event create(Event event) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "INSERT INTO Event (amount, eventtype, eventdate, user_id) VALUES (?, ?, ?, ?)";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setDouble(1, event.getAmount());
        st.setString(2, event.getEventtype());
        st.setDate(3, java.sql.Date.valueOf(event.getEventdate()));
        st.setInt(4, event.getUser().getId());

        st.executeUpdate();
        st.close();
        conn.close();
        return event;

    }

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

    @Override
    public Event update(Event event) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "UPDATE Event SET amount = ?, eventtype=?, eventdate = ? WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setDouble(1, event.getAmount());
        st.setString(2, event.getEventtype());
        st.setDate(3, Date.valueOf(event.getEventdate()));
        st.setInt(4, event.getId());

        st.executeUpdate();
        st.close();
        conn.close();
        return event;
    }

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

    @Override
    public List<Event> list() throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM Event";
        List<Event> events = new ArrayList<>();

        PreparedStatement st = conn.prepareCall(sql);

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

    public List<Event> listById(int userId) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM Event WHERE user_id = ?";
        List<Event> events = new ArrayList<>();
        PreparedStatement st = conn.prepareCall(sql);
        st.setInt(1, userId);
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
