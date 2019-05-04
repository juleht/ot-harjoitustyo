package budget.dao;

import budget.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO luokka, jolla käsitellään user-taulua tietokannassa
 *
 */
public class UserDao implements Dao<User, String> {

    private Database database;

    public UserDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi luo käyttäjälle käyttäjänimen tietokantaan
     *
     * @param user metodin parametri on käyttäjä-olio
     * @return metodi palauttaa juuri luodun käyttäjä-olion
     * @throws SQLException e
     */
    @Override
    public User create(User user) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "INSERT INTO User (username) VALUES (?)";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, user.getUsername());

        st.executeUpdate();
        st.close();
        conn.close();
        return user;
    }

    /**
     * Metodi listaa tietokannassa olevien käyttäjien id:t ja käyttäjänimet
     *
     * @return metodi palauttaa listan käyttäjä olioita List muodossa
     * @throws SQLException e
     */
    @Override
    public List<User> listAll() throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();

        PreparedStatement st = conn.prepareCall(sql);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            User u = new User(id, username);
            users.add(u);

        }
        st.close();
        rs.close();
        conn.close();
        return users;
    }

    /**
     * Metodi hakee tietokannasta parametria vastaavan käyttäjänimen
     *
     * @param username metodille annetaan parametriksi käyttäjänimi
     * @return metodi palauttaa null, jos tietokannassa ei ole parametria
     * annettua käyttäjänimeä metodi palauttaa käyttäjä-olion, jos tietokannassa
     * on parametria vastaava käyttäjänimi
     * @throws SQLException e
     */
    @Override
    public User read(String username) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM User WHERE username = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) {
            return null;
        }

        User u = new User(rs.getInt("id"), rs.getString("username"));
        st.close();
        rs.close();
        conn.close();
        return u;
    }

    /**
     * Metodia ei tueta tässä luokassa
     *
     * @param key avain
     * @throws SQLException
     */
    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported in this class.");

    }

    /**
     * Metodia ei tueta tässä luokassa
     *
     * @param key avain
     * @return null
     * @throws SQLException
     */
    @Override
    public List<User> listById(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported in this class.");

    }

}
