package budget.domain;

import budget.dao.EventDao;
import budget.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BudgetLogic {

    private UserDao userDao;
    private EventDao eventDao;
    private Scanner reader;

    public BudgetLogic(UserDao userDao, EventDao eventDao, Scanner reader) {
        this.userDao = userDao;
        this.eventDao = eventDao;
        this.reader = reader;
    }

    public void addUser(Scanner reader) throws SQLException {
        List<User> users = userDao.list();

        System.out.println("Enter username: ");
        String username = reader.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return;
            }
        }
        userDao.create(new User(username));
    }

    public User logOn(Scanner reader) throws SQLException {
        List<User> users = userDao.list();
        System.out.println("Enter username:");
        String username = reader.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        System.out.println("User does not exist");
        return null;
    }

    public Event setEvent(Scanner reader, User u) throws SQLException {
        System.out.println("How much?");
        double amount = Double.valueOf(reader.nextLine());
        System.out.println("Event type");
        String eventtype = reader.nextLine();
        System.out.println("Event Date");
        LocalDate eventdate = LocalDate.parse(reader.nextLine());

        Event e = new Event(amount, eventtype, eventdate, u);
        eventDao.create(e);

        return e;
    }

}
