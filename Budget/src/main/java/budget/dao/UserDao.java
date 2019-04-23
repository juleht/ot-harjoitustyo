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
public class UserDao implements Dao<User, Integer> {

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
     * Metodi lukee käyttäjänimen tietokannasta
     *
     * @param key metodille annetaan parametriksi käyttäjän id
     * @return metodi palauttaa null, jos tietokannassa ei ole id:tä vastaavaa
     * käyttäjää metodi palauttaa käyttäjän jos tietokannassa on id:tä vastaava
     * käyttäjänimi
     * @throws SQLException e
     */
    @Override
    public User read(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "SELECT * FROM User WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, key);
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
     * Metodin päivittää käyttäjänimen tietokantaan
     *
     * @param user metodille annetaan parametriksi käyttäjä-olio
     * @return metodi palauttaa päivitetyn käyttäjä-olion
     * @throws SQLException e
     */
    @Override
    public User update(User user) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "UPDATE User SET username = ? WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, user.getUsername());
        st.setInt(2, user.getId());
        st.executeUpdate();
        st.close();
        conn.close();
        return user;
    }

    /**
     * Metodi poistaa käyttäjänimen tietokannasta
     *
     * @param key metodille annetaan parametriksi poistettavan käyttäjän id
     * @throws SQLException e
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "DELETE FROM User WHERE id = ?";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setInt(1, key);
        st.executeUpdate();
        st.close();
        conn.close();
    }

    /**
     * Metodi listaa tietokannassa olevien käyttäjien id:t ja käyttäjänimet
     *
     * @return metodi palauttaa listan käyttäjä olioita List muodossa
     * @throws SQLException e
     */
    @Override
    public List<User> list() throws SQLException {
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
    public User readUsername(String username) throws SQLException {
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

}
