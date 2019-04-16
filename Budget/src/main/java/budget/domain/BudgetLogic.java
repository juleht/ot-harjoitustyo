package budget.domain;

import budget.dao.EventDao;
import budget.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BudgetLogic {

    private UserDao userDao;
    private EventDao eventDao;
    private User userLoggedIn;

    public BudgetLogic(UserDao userDao, EventDao eventDao) {
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public Boolean createUser(String username) throws SQLException {
        List<User> users = userDao.list();
        for (User u : users) {
            if (u.getUsername().equals(username) || username.length() <= 3) {
                return false;
            }
        }
        userDao.create(new User(username));
        return true;
    }

    public User logOn(String username) throws SQLException {
        User u = userDao.readUsername(username);
        if (u != null) {
            userLoggedIn = u;
            return userLoggedIn;
        }
        return null;
    }

    public Event createEvent(double amount, String eventtype, LocalDate eventdate) throws SQLException {
        Event e = new Event(amount, eventtype, eventdate, userLoggedIn);
        eventDao.create(e);
        return e;
    }

    public double incomes() throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        double incomes = 0;
        for (Event e : events) {
            if (e.getAmount() > 0) {
                incomes += e.getAmount();
            }
        }
        return incomes;
    }

    public double expenses() throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        double incomes = 0;
        for (Event e : events) {
            if (e.getAmount() < 0) {
                incomes += e.getAmount();
            }
        }
        return incomes;
    }

    public double balance() throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        double incomes = 0;
        for (Event e : events) {
            incomes += e.getAmount();
        }
        return incomes;
    }

    public List<Event> listEvents() throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        if (!events.isEmpty()) {
            return events;
        }
        return null;
    }

    public void deleteEvent(Event e) throws SQLException {
        User u = getUserLoggedIn();
        List<Event> events = eventDao.listById(u.getId());
        for (Event k : events) {
            if (k.getId() == e.getId()) {
                eventDao.delete(k.getId());
            }
        }

    }
}
