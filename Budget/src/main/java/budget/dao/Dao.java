package budget.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * DAO, rajapinta tietokannan käsittelyyn.
 *
 * @param <T> olio
 * @param <K> tunniste, kuten id
 */
public interface Dao<T, K> {

    T create(T object) throws SQLException;

    T read(K key) throws SQLException;

    T update(T object) throws SQLException;

    void delete(K key) throws SQLException;

    List<T> list() throws SQLException;

}
