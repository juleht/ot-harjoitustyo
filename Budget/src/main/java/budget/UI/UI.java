package budget.UI;

import budget.dao.UserDao;
import budget.domain.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UI {

    private UserDao userDao;

    public void start(Scanner reader, UserDao userdao) throws SQLException {
        this.userDao = userdao;
        while (true) {
            System.out.println("Commands:");
            System.out.println("x - end");
            System.out.println("1 - log on");
            System.out.println("2 - add user");
            String command = reader.nextLine();

            if (command.endsWith("x")) {
                break;
            } else if (command.equals("1")) {
                logOn(reader);
            } else if (command.equals("2")) {
                addUser(reader);
            }
        }
    }

    private void addUser(Scanner reader) throws SQLException {

        System.out.println("Enter user name: ");
        String username = reader.nextLine();
        userDao.create(new User(username));
    }

    private int logOn(Scanner reader) throws SQLException {
        List<User> users = userDao.list();
        System.out.println("Enter username:");
        String username = reader.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return -1;
    }
}
