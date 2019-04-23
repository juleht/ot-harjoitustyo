package budget.dao;

import budget.domain.Event;
import budget.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventDaoTest {

    private Database testDB;
    private EventDao eventDao;
    private UserDao userDao;
    private User firstUser;
    private User secondUser;
    private Event firstEvent;
    private Event secondEvent;

    @Before
    public void setUp() throws SQLException {
        testDB = createDB();
        eventDao = new EventDao(testDB);
        userDao = new UserDao(testDB);

        this.firstUser = new User(1, "first");
        this.secondUser = new User(2, "second");

        this.firstEvent = new Event(1, 10.0, "food", LocalDate.parse("2019-01-01"), firstUser);
        this.secondEvent = new Event(2, 10.0, "clothes", LocalDate.parse("2019-02-02"), secondUser);

        userDao.create(firstUser);
        userDao.create(secondUser);

        eventDao.create(firstEvent);
        eventDao.create(secondEvent);
    }

    @After
    public void clearTestDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./budgetDBtest", "sa", "");
        String sql = "DROP ALL OBJECTS DELETE FILES";
        PreparedStatement st = conn.prepareStatement(sql);

        st.executeUpdate();
    }

    @Test
    public void readReturnNullIfEventNotExists() throws SQLException {
        assertEquals(eventDao.read(4), null);
    }

    @Test
    public void readReturnsId() throws SQLException {
        assertEquals(eventDao.read(1).getId(), 1);
    }

    @Test
    public void readReturnsEventtype() throws SQLException {
        assertEquals(eventDao.read(1).getEventtype(), "food");

    }

    @Test
    public void readReturnsEventDate() throws SQLException {
        assertEquals(eventDao.read(1).getEventdate(), LocalDate.parse("2019-01-01"));

    }

    @Test
    public void readReturnsEventAmount() throws SQLException {
        assertEquals(eventDao.read(1).getAmount(), 10.0, 0.0001);

    }

    @Test
    public void readReturnsNulluser() throws SQLException {
        assertEquals(eventDao.read(1).getUser(), null);
    }

    @Test
    public void updateChangesEventAmountAllElseEqual() throws SQLException {
        Event second = eventDao.read(2);
        second.setAmount(20.0);
        eventDao.update(second);
        assertEquals(eventDao.read(2).toString(), "20.0 clothes 2019-02-02");
    }

    @Test
    public void updateChangesEventTypeAllElseEqual() throws SQLException {
        Event second = eventDao.read(2);
        second.setEventtype("food");
        eventDao.update(second);
        assertEquals(eventDao.read(2).toString(), "10.0 food 2019-02-02");
    }

    @Test
    public void updateChangesEventDateAllElseEqual() throws SQLException {
        Event second = eventDao.read(2);
        second.setEventdate(LocalDate.parse("2019-02-03"));
        eventDao.update(second);
        assertEquals(eventDao.read(2).toString(), "10.0 clothes 2019-02-03");
    }

    @Test
    public void deleteEvent() throws SQLException {
        eventDao.delete(1);
        assertEquals(eventDao.read(1), null);
    }

    @Test
    public void list() throws SQLException {
        List<Event> events = eventDao.list();
        assertEquals(events.size(), 2);
    }

    private Database createDB() throws SQLException {
        Database db = new Database("budgetDBtest");
        db.init();
        return db;
    }
}
