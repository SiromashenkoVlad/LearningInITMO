package server.db.dao;

import server.db.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

interface SaveDao<T, R> {
    R save(T element) throws SQLException;
    static Connection getConnection() throws SQLException {
        return DataSource.getConnection();
    }
}
