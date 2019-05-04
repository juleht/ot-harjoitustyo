package budget.dao;

import budget.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDaoTest {

    private Database testDB;
    private UserDao userdao;

    @Before
    public void setUp() throws SQLException {
        testDB = createDB();
        userdao = new UserDao(testDB);
        userdao.create(new User("first"));
        userdao.create(new User("second"));
    }

    @After
    public void clearTestDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./budgetDBtest", "sa", "");
        String sql = "DROP ALL OBJECTS DELETE FILES";
        PreparedStatement st = conn.prepareStatement(sql);

        st.executeUpdate();
    }

    @Test
    public void createUser() throws SQLException {
        User third = new User("third");
        userdao.create(third);
        assertEquals(userdao.read("third").toString(), "third");
    }

    @Test
    public void readUsername() throws SQLException {
        assertEquals(userdao.read("first").toString(), "first");
    }

    @Test
    public void readUserId() throws SQLException {
        assertEquals(userdao.read("first").getId(), 1);

    }

    @Test
    public void list() throws SQLException {
        List<User> users = userdao.listAll();
        assertEquals(users.size(), 2);
    }

    private Database createDB() throws SQLException {
        Database db = new Database("budgetDBtest");
        db.init();
        return db;
    }
}
