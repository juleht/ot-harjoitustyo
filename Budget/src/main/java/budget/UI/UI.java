package budget.ui;

import budget.domain.BudgetLogic;
import budget.domain.User;
import java.sql.SQLException;
import java.util.Scanner;

public class Ui {

    private BudgetLogic logic;

    public void start(Scanner reader, BudgetLogic logic) throws SQLException {
        this.logic = logic;

        while (true) {
            System.out.println("Commands:");
            System.out.println("x - end");
            System.out.println("1 - add user");
            System.out.println("2 - log on");

            String command = reader.nextLine();

            if (command.endsWith("x")) {
                break;

            } else if (command.equals("1")) {
                this.logic.addUser(reader);

            } else if (command.equals("2")) {
                User u = this.logic.logOn(reader);

                if (u != null) {

                    System.out.println("Commands:");
                    System.out.println("x - log out");
                    System.out.println("add - add income or expenditure");

                    command = reader.nextLine();

                    if (command.equals("x")) {
                        break;
                    } else if (command.equals("add")) {
                        this.logic.setEvent(reader, u);

                    }
                }
            }
        }
    }

}
