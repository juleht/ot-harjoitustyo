package budget.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String address;
    private String password;
    private String username;

    public Database(String address) {
        this.address = "jdbc:h2:./" + address;
        this.password = "";
        this.username = "sa";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.address, this.username, this.password);
    }

    public void init() throws SQLException {
        List<String> tables = setDatabase();
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            for (String sqlTables : tables) {
                st.executeUpdate(sqlTables);
            }
        } catch (SQLException ex) {

        }
    }

    private List<String> setDatabase() {
        List<String> commands = new ArrayList<>();
        commands.add("CREATE TABLE User (id serial, username varchar(20), primary key (id));");
        commands.add("CREATE TABLE Event (id serial, amount real, eventtype varchar(30) ,eventdate DATE, user_id integer, foreign key (user_id) references User(id), primary key (id));");
        return commands;
    }
}
