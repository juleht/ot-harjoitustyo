package budget.domain;

import budget.dao.Database;
import budget.dao.EventDao;
import budget.dao.UserDao;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jtleh
 */
public class BudgetLogicTest {

    private BudgetLogic logicTest;
    private UserDao userDao;
    private EventDao eventDao;
    private Scanner reader;
    private Database database;

    public BudgetLogicTest() throws SQLException {
        this.database = createDB();
        this.userDao = new UserDao(database);
        this.eventDao = new EventDao(database);
        this.logicTest = new BudgetLogic(userDao, eventDao, reader);
    }

    @Before
    public void setUp() throws SQLException {
        this.userDao.create(new User("first"));

    }

    @After
    public void clearTablesInTestDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./budgetDBtest", "sa", "");
        String sql = "DROP TABLE User";
        String sql2 = "DROP TABLE Event";
        PreparedStatement st = conn.prepareStatement(sql);
        PreparedStatement st2 = conn.prepareStatement(sql2);

        st.executeUpdate();
        st2.executeUpdate();
    }

    @Test
    public void addUserIfNotExist() throws SQLException {
        this.reader = new Scanner("second");
        this.logicTest.addUser(reader);
        assertEquals(userDao.read(2).getUsername(), "second");
    }

    @Test
    public void addUserNotAddIfExists() throws SQLException {
        this.reader = new Scanner("first");
        this.logicTest.addUser(reader);
        assertEquals(userDao.list().size(), 1);
    }

    private Database createDB() throws SQLException {
        Database db = new Database("budgetDBtest");
        db.init();
        return db;
    }

}
