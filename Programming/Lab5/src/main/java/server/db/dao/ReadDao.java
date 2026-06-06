package server.db.dao;

import server.db.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface ReadDao<T, R>{
    Optional<T> read(R element);
    static Connection getConnection() throws SQLException {
        return DataSource.getConnection();
    }
}
