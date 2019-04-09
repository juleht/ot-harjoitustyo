package budget.domain;

import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {

    Event event;
    User user;

    @Before
    public void setUp() {
        this.user = new User("first");
        this.event = new Event(1, 10.0, "food", LocalDate.of(2000, 01, 01), this.user);

    }

    @Test
    public void constructorCreatesEvent() {
        assertEquals(this.event.toString(), "1 10.0 food 2000-01-01 null first");
    }
}
