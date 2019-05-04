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
        
        Event a = new Event(10.5, "salery", LocalDate.parse("2019-01-01"), userThird);
        Event b = new Event(-10.5, "food", LocalDate.parse("2019-01-01"), userThird);
        Event c = new Event(20, "salery", LocalDate.parse("2019-01-01"), userThird);
        Event d = new Event(-100, "clothes", LocalDate.parse("2019-01-01"), userThird);
        
        this.eventDao.create(a);
        this.eventDao.create(b);
        this.eventDao.create(c);
        this.eventDao.create(d);
        
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
    public void createUserDoesNotCreateUserIfUsernameTooShort() throws SQLException {
        assertFalse(this.logicTest.createUser("kk"));
    }
    
    @Test
    public void createUserCreatesUserSavesInDatabase() throws SQLException {
        this.logicTest.createUser("second");
        assertEquals(this.userDao.listAll().size(), 3);
    }
    
    @Test
    public void logOnReturnsNullIfUserDoesNotExist() throws SQLException {
        assertEquals(this.logicTest.logOn("second"), null);
    }
    
    @Test
    public void LogOnReturnsUserIfExist() throws SQLException {
        assertEquals(logicTest.logOn("first").getId(), this.userDao.read("first").getId());
    }
    
    @Test
    public void createEventCreatesEventToDatabase() throws SQLException {
        this.logicTest.createEvent(10, "food", LocalDate.parse("2019-01-01"));
        assertEquals(eventDao.read(5).getEventtype(), "food");
    }
    
    @Test
    public void incomesReturnsCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.incomes(), 30.5, 0.0001);
    }
    
    @Test
    public void expensesReturnCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.expenses(), -110.5, 0.0001);
    }
    
    @Test
    public void balanceReturnsCorrectAmount() throws SQLException {
        assertEquals(this.logicTest.balance(), -80.0, 0.0001);
    }
    
    @Test
    public void ListEventsByuserReturnsCorrectList() throws SQLException {
        assertEquals(this.logicTest.listUserEvents().size(), 4);
    }
    
    @Test
    public void deleteEvent() throws SQLException {
        this.logicTest.deleteEvent(eventDao.read(2));
        assertEquals(logicTest.listUserEvents().size(), 3);
        
    }
    
    @Test
    public void highestIncome() throws SQLException {
        assertEquals(this.logicTest.highestIncome().getAmount(), 20, 0.0001);
    }
    
    @Test
    public void highestExpence() throws SQLException {
        assertEquals(logicTest.highestExpence().getAmount(), -100, 0.0001);
    }
    
    @Test
    public void averageIncome() throws SQLException {
        double a = 20 + 10.5;
        assertEquals(logicTest.averageIncomes(), (a / 2), 0.0001);
    }
    
    @Test
    public void averageExpence() throws SQLException {
        double a = -10.5 - 100;
        assertEquals(logicTest.averageExpences(), (a / 2), 0.0001);
    }
    
    @Test
    public void numberOfEvents() throws SQLException {
        assertEquals(logicTest.numberOfEvents(), 4);
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
