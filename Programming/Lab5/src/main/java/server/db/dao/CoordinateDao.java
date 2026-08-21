package server.db.dao;

import common.model.Coordinates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public final class CoordinateDao implements SaveDao<Coordinates, Integer>, ReadDao<Coordinates, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(CoordinateDao.class);
    private static CoordinateDao instance = null;

    private CoordinateDao(){}

    public synchronized static CoordinateDao getInstance(){
        if (instance == null){
            instance = new CoordinateDao();
        }
        return instance;
    }

    @Override
    public Integer save(Coordinates coor) {
        String query = "INSERT INTO Coordinates (x, y) VALUES (?, ?) RETURNING id";
        try (Connection conn = SaveDao.getConnection();
             PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setFloat(1, coor.getX());
            pr.setFloat(2, coor.getY());
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                return -1;
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка сохранения Coordinates", e);
            return -1;
        }
    }

    public Integer save(Coordinates coor, Connection conn) throws SQLException{
        String query = "INSERT INTO Coordinates (x, y) VALUES (?, ?) RETURNING id";
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setFloat(1, coor.getX());
            pr.setFloat(2, coor.getY());
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                return -1;
            }
        }
    }

    @Override
    public Optional<Coordinates> read(Integer id) {
        String query = "SELECT * FROM Coordinates WHERE id = ?";
        try (Connection conn = SaveDao.getConnection();
             PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, id);
            try (ResultSet rs = pr.executeQuery()) {
                if (!rs.next()) {
                    LOGGER.warn("Coordinates с id: " + id + " не найдены");
                    return Optional.empty();
                }
                return Optional.of(new Coordinates(
                        rs.getFloat("x"),
                        rs.getFloat("y")
                ));
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка чтения Coordinates id=" + id, e);
            return Optional.empty();
        }
    }

    public void update(int id, Coordinates coor, Connection conn) throws SQLException {
        String query = "UPDATE Coordinates SET x=?, y=? WHERE id=?";
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setFloat(1, coor.getX());
            pr.setFloat(2, coor.getY());
            pr.setInt(3, id);
            pr.executeUpdate();
            LOGGER.info("Coordinates id=" + id + " обновлены");
        }
    }

    public void remove(int id, Connection conn) throws SQLException {
        try (PreparedStatement pr = conn.prepareStatement("DELETE FROM Coordinates WHERE id = ?")) {
            pr.setInt(1, id);
            pr.executeUpdate();
            LOGGER.info("Coordinates id=" + id + " удалены");
        }
    }

    public void removeBatch(List<Integer> ids, Connection conn) throws SQLException {
        if (ids.isEmpty()) return;

        Array array = conn.createArrayOf("integer", ids.toArray());
        try (PreparedStatement pr = conn.prepareStatement(
                "DELETE FROM Coordinates WHERE id = ANY(?)")) {
            pr.setArray(1, array);
            pr.executeUpdate();
            LOGGER.info("Удалено Coordinates ids=" + ids);
        }
    }

    public boolean clear(Connection conn) {
        String query = "truncate table Coordinates";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(query);
            LOGGER.info("Удалены Coordinates");
            return true;
        } catch (SQLException e) {
            LOGGER.error("Ошибка выполнения запроса TRUNCATE в Coordinates", e);
            return false;
        }
    }
}
