package budget.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User(1, "username");
    }

    @Test
    public void constructorCreatesUser() {
        assertEquals(user.toString(), "username");
    }

}
