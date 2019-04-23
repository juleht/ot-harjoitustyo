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
        assertEquals(userdao.read(3).toString(), "third");
    }

    @Test
    public void readUsername() throws SQLException {
        assertEquals(userdao.read(1).getUsername(), "first");
    }

    @Test
    public void readUserId() throws SQLException {
        assertEquals(userdao.read(1).getId(), 1);

    }

    @Test
    public void updateUserName() throws SQLException {
        User second = userdao.read(2);
        second.setUsername("updated second");
        userdao.update(second);
        assertEquals(userdao.read(2).getUsername(), "updated second");

    }

    @Test
    public void deleteUser() throws SQLException {
        userdao.delete(1);
        assertEquals(userdao.read(1), null);
    }

    @Test
    public void list() throws SQLException {
        List<User> users = userdao.list();
        assertEquals(users.size(), 2);
    }

    private Database createDB() throws SQLException {
        Database db = new Database("budgetDBtest");
        db.init();
        return db;
    }
}
