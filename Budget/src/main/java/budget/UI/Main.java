package budget.UI;

import budget.dao.Database;
import budget.dao.UserDao;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Database db = createDB();
        Scanner reader = new Scanner(System.in);
        UI interf = new UI();
        UserDao userdao = new UserDao(db);
        interf.start(reader, userdao);
    }

    private static Database createDB() throws SQLException {
        String databaseName = "budgetDB";
        Database db = new Database(databaseName);
        db.init();
        return db;
    }
}
