package budget.domain;

import budget.dao.Database;
import budget.dao.EventDao;
import budget.dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jtleh
 */
public class LogicTest {

    private Logic logicTest;
    private UserDao userDao;
    private EventDao eventDao;
    private Database database;
    private User userThird;

    public LogicTest() throws SQLException {
        this.database = createDB();
        this.userDao = new UserDao(database);
        this.eventDao = new EventDao(database);
        this.logicTest = new Logic(userDao, eventDao);
    }

    @Before
    public void setUp() throws SQLException {
        this.userDao.create(new User("first"));
        this.userDao.create(new User("third"));

        userThird = logicTest.logOn("third");

        Event e = new Event(10.5, "food", LocalDate.parse("2019-01-01"), userThird);
        Event r = new Event(-10.5, "food", LocalDate.parse("2019-01-01"), userThird);

        this.eventDao.create(e);
        this.eventDao.create(r);

    }

    @Test
    public void createUserDoesNotCreateUserIfExist() throws SQLException {
        assertFalse(this.logicTest.createUser("first"));
    }

    @Test
    public void createUserCreatesUserIfNotExist() throws SQLException {
        assertTrue(this.logicTest.createUser("second"));
    }

    @Test
    public void createUserCreatesUserInDatabase() throws SQLException {
        this.logicTest.createUser("second");
        assertEquals(this.userDao.list().size(), 3);
    }

    @Test
    public void logOnReturnsNullIfUserDoesNotExist() throws SQLException {
        assertEquals(this.logicTest.logOn("second"), null);
    }

    @Test
    public void LogOnReturnsUserIfExist() throws SQLException {
        assertEquals(logicTest.logOn("first").getId(), this.userDao.readUsername("first").getId());
    }

    @Test
    public void createEventCreatesEventToDatabase() throws SQLException {
        this.logicTest.createEvent(10, "food", LocalDate.parse("2019-01-01"));
        assertEquals(eventDao.read(1).getEventtype(), "food");
    }

    @Test
    public void incomesReturnsCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.incomes(), 10.5, 0.0001);
    }

    @Test
    public void expensesReturnCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.expenses(), -10.5, 0.0001);
    }

    @Test
    public void balanceReturnsCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.balance(), 0.0, 0.00001);
    }

    @Test
    public void ListEventsByuserReturnsCorrectList() throws SQLException {
        assertEquals(this.logicTest.listUserEvents().size(), 2);
    }

    @Test
    public void deleteEvent() throws SQLException {
        this.logicTest.deleteEvent(eventDao.read(2));
        assertEquals(logicTest.listUserEvents().size(), 1);

    }

    @After
    public void clearTestDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./budgetDBtest", "sa", "");
        String sql = "DROP ALL OBJECTS DELETE FILES";
        PreparedStatement st = conn.prepareStatement(sql);

        st.executeUpdate();
    }

    private Database createDB() throws SQLException {
        Database db = new Database("budgetDBtest");
        db.init();
        return db;
    }

}
