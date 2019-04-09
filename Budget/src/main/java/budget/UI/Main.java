package budget.ui;

import budget.dao.Database;
import budget.dao.EventDao;
import budget.dao.UserDao;
import budget.domain.BudgetLogic;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Database db = createDB();
        Scanner reader = new Scanner(System.in);
        Ui interf = new Ui();
        UserDao userDao = new UserDao(db);
        EventDao eventDao = new EventDao(db);
        BudgetLogic logic = new BudgetLogic(userDao, eventDao, reader);

        interf.start(reader, logic);
    }

    private static Database createDB() throws SQLException {
        String databaseName = "budgetDB";
        Database db = new Database(databaseName);
        db.init();
        return db;
    }
}
