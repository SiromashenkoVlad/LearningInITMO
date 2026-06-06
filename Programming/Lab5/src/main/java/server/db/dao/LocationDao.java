package server.db.dao;

import common.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.db.DataSource;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public final class LocationDao implements SaveDao<Location, Integer>, ReadDao<Location, Integer> {
    private final static Logger LOGGER = LogManager.getLogger(LocationDao.class);
    private static LocationDao instance = null;

    private LocationDao(){}

    public synchronized static LocationDao getInstance() {
        if (instance == null) instance = new LocationDao();
        return instance;
    }

    @Override
    public Integer save(Location loc) {
        String query = "insert into Location (x, y, z) VALUES (?, ?, ?) Returning id";
        try(Connection conn = SaveDao.getConnection();
            PreparedStatement pr = conn.prepareStatement(query)
        ){
            pr.setInt(1, loc.getX());
            pr.setInt(2, loc.getY());
            pr.setInt(3, loc.getZ());
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                else return -1;
            }catch (SQLException e) {
                LOGGER.error("Ошибка сохранения Location", e);
                return -1;
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка сохранения Location", e);
            return -1;
        }
    }

    public Integer save(Location loc, Connection conn) throws SQLException{
        String query = "insert into Location (x, y, z) VALUES (?, ?, ?) Returning id";
        try(PreparedStatement pr = conn.prepareStatement(query)){
            pr.setInt(1, loc.getX());
            pr.setInt(2, loc.getY());
            pr.setInt(3, loc.getZ());
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                else return -1;
            }catch (SQLException e) {
                LOGGER.error("Ошибка сохранения Location", e);
                return -1;
            }
        }
    }

    @Override
    public Optional<Location> read(Integer id){
        try(Connection conn = DataSource.getConnection();
            PreparedStatement prLoc = conn.prepareStatement("select * from Location Where id = ?"))
        {
            prLoc.setInt(1, id);
            try(ResultSet rsLoc = prLoc.executeQuery()){
                if (!rsLoc.next()) {
                    LOGGER.warn("Данные о координатах c id: " + id + " неизвестны");
                    return Optional.empty();
                }
                int x = rsLoc.getInt("x");
                Integer y = rsLoc.getInt("y");
                int z = rsLoc.getInt("z");
                return Optional.of(new Location(x, y, z));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    public void update(int id, Location loc, Connection conn) throws SQLException {
        String query = "UPDATE Location SET x=?, y=?, z=? WHERE id=?";
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, loc.getX());
            pr.setInt(2, loc.getY());
            pr.setInt(3, loc.getZ());
            pr.setInt(4, id);
            pr.executeUpdate();
            LOGGER.info("Location id=" + id + " обновлён");
        }
    }

    public void remove(int id, Connection conn) throws SQLException {
        try (PreparedStatement pr = conn.prepareStatement("DELETE FROM Location WHERE id = ?")) {
            pr.setInt(1, id);
            pr.executeUpdate();
            LOGGER.info("Location id=" + id + " удалён");
        }
    }

    public void removeBatch(List<Integer> ids, Connection conn) throws SQLException {
        if (ids.isEmpty()) return;

        // строим "DELETE FROM Location WHERE id = ANY(?)"
        Array array = conn.createArrayOf("integer", ids.toArray());
        try (PreparedStatement pr = conn.prepareStatement(
                "DELETE FROM Location WHERE id = ANY(?)")) {
            pr.setArray(1, array);
            pr.executeUpdate();
            LOGGER.info("Удалено Location ids=" + ids);
        }
    }
}
