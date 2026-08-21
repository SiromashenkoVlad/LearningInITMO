package server.db.dao;

import common.Mainpart.Person;
import common.enums.Color;
import common.enums.Country;
import common.model.Coordinates;
import common.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.db.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PersonDao implements SaveDao<Person, Integer>, ReadDao<Person, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(PersonDao.class);
    private static PersonDao instance = null;

    private PersonDao(){}

    public synchronized static PersonDao getInstance() {
        if (instance == null) instance = new PersonDao();
        return instance;
    }

    @Override
    public Integer save(Person p) {
        String sql = "insert into Person (name, coordinates_id, creationDate, creationDateZone, height, birthday," +
        " eyecolor, nationality, location_id, maker) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?::Color, ?::Country, ?, ?) returning id";
        try (Connection conn = SaveDao.getConnection()){
            conn.setAutoCommit(false);
             try(PreparedStatement pr = conn.prepareStatement(sql)){
                LOGGER.debug("Начало записи");
                LocationDao locationDao = LocationDao.getInstance();
                int locId = locationDao.save(p.getLocation(), conn);
                LOGGER.debug("Записал локацию");
                CoordinateDao coordinateDao = CoordinateDao.getInstance();
                int coorId = coordinateDao.save(p.getCoordinates(), conn);
                LOGGER.debug("Записал координату");
                pr.setString(1, p.getName());
                pr.setInt(2, coorId);
                pr.setObject(3, p.getCreationDate().toOffsetDateTime(),
                        Types.TIMESTAMP_WITH_TIMEZONE);
                pr.setString(4, p.getCreationDate().getZone().getId());
                pr.setLong(5, p.getHeight());
                pr.setObject(6, p.getBirthday(), Types.OTHER);
                pr.setObject(7, p.getEyeColor(), Types.OTHER);
                pr.setObject(8, p.getNationality(), Types.OTHER);
                pr.setInt(9, locId);
                pr.setString(10, p.getMaker());
                try (ResultSet rs = pr.executeQuery()) {
                    if (rs.next()) {
                        conn.commit();
                        return rs.getInt(1);
                    }
                    return -1;
                }
            } catch (SQLException e){
                 conn.rollback();
                 LOGGER.error(e);
                 return -1;
             }
        } catch (SQLException e){
            LOGGER.error(e.getMessage(), e.getSQLState(), e.fillInStackTrace());
            return -1;
        }
    }

    public Optional<Person> read(Integer id){
        try(Connection conn = DataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement("Select * from Person where id = ?")
        ){
            LOGGER.info("Начало считывания");
            pr.setInt(1, id);
            try(ResultSet rs = pr.executeQuery();) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int coorId = rs.getInt("coordinates_id");
                    OffsetDateTime odt = rs.getObject("creationDate", OffsetDateTime.class);
                    ZoneId zone = ZoneId.of(rs.getString("creationDateZone"));
                    ZonedDateTime creationDate = odt.atZoneSameInstant(zone);
                    int height = rs.getInt("height");
                    LocalDateTime birthday = rs.getObject("birthday", LocalDateTime.class);
                    Color eyecolor = Color.valueOf(rs.getString("eyecolor"));
                    Country nationality = Country.valueOf(rs.getString("nationality"));
                    int locationId = rs.getInt("location_id");
                    String maker = rs.getString("maker");

                    Optional<Location> loc = LocationDao.getInstance().read(locationId);
                    if (loc.isEmpty()) {
                        LOGGER.error("Данные о координатах у Person c id: " + id + " не считаны");
                        return Optional.empty();
                    }

                    Optional<Coordinates> coor = CoordinateDao.getInstance().read(coorId);
                    if (coor.isEmpty()) {
                        LOGGER.error("Данные о координатах у Person c id: " + id + " не считаны");
                        return Optional.empty();
                    }
                    return Optional.of(new Person(id, name, coor.get(), creationDate,
                            height, birthday, eyecolor, nationality, loc.get(), maker));

                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка чтения Person id=" + id, e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<String> readMaker(int id){
        String query = "Select maker from Person where id = ?";
        try (Connection conn = DataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String maker = rs.getString("maker");
                return Optional.of(maker);
            }
            return Optional.empty();
        }  catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
            return Optional.empty();
        }
    }

    public void update(int id, Person p) {
        String selectIds = "SELECT coordinates_id, location_id FROM Person WHERE id = ?";
        String query = """
        UPDATE Person SET name=?, height=?, birthday=?,
        eyecolor=?::Color, nationality=?::Country 
        WHERE id=?
        """;
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            LOGGER.debug("установка транзакции");
            try {
                int locationId;
                int coordinatesId;
                try (PreparedStatement prIds = conn.prepareStatement(selectIds)) {
                    prIds.setInt(1, id);
                    try (ResultSet rs = prIds.executeQuery()) {
                        if (!rs.next()) {
                            LOGGER.error("Person id=" + id + " не найден");
                            conn.rollback();
                            return;
                        }
                        coordinatesId = rs.getInt("coordinates_id");
                        locationId = rs.getInt("location_id");
                        LOGGER.debug("read location and coor id");
                    }
                }

                LocationDao.getInstance().update(locationId, p.getLocation(), conn);
                CoordinateDao.getInstance().update(coordinatesId, p.getCoordinates(), conn);

                try (PreparedStatement pr = conn.prepareStatement(query)) {
                    pr.setString(1, p.getName());
                    pr.setLong(2, p.getHeight());
                    pr.setObject(3, p.getBirthday(), Types.OTHER);
                    pr.setObject(4, p.getEyeColor(), Types.OTHER);
                    pr.setObject(5, p.getNationality(), Types.OTHER);
                    pr.setInt(6, id);
                    pr.executeUpdate();
                }

                conn.commit();
                LOGGER.info("Person id=" + id + " обновлён");
            } catch (SQLException e) {
                conn.rollback();
                LOGGER.error("Ошибка обновления Person id=" + id, e);
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
        }
    }

    public void remove(int personId) {
        String selectIds = "SELECT coordinates_id, location_id FROM Person WHERE id = ?";
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            LOGGER.debug("autocommit false");
            try {
                int locationId;
                int coordinatesId;
                try (PreparedStatement prIds = conn.prepareStatement(selectIds)) {
                    prIds.setInt(1, personId);
                    try (ResultSet rs = prIds.executeQuery()) {
                        if (!rs.next()) {
                            LOGGER.error("Person id=" + personId + " не найден");
                            conn.rollback();
                            return;
                        }
                        coordinatesId = rs.getInt("coordinates_id");
                        locationId = rs.getInt("location_id");
                        LOGGER.debug("Считал id у локации и координат");
                    }
                }

                try (PreparedStatement pr = conn.prepareStatement("DELETE FROM Person WHERE id = ?")) {
                    pr.setInt(1, personId);
                    pr.executeUpdate();
                    LOGGER.debug("delete person");
                }

                LocationDao.getInstance().remove(locationId, conn);
                CoordinateDao.getInstance().remove(coordinatesId, conn);

                conn.commit();
                LOGGER.info("Person id=" + personId + " удалён");
            } catch (SQLException e) {
                conn.rollback();
                LOGGER.error("Ошибка удаления Person id=" + personId, e);
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
        }
    }

    public boolean clear(String maker) {
        String selectQuery = "SELECT location_id, coordinates_id FROM Person WHERE maker = ?";
        String deleteQuery = "DELETE FROM Person WHERE maker = ?";
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                List<Integer> locationIds = new ArrayList<>();
                List<Integer> coordinatesIds = new ArrayList<>();

                try (PreparedStatement ps = conn.prepareStatement(selectQuery)) {
                    ps.setString(1, maker);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            locationIds.add(rs.getInt("location_id"));
                            coordinatesIds.add(rs.getInt("coordinates_id"));
                        }
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
                    ps.setString(1, maker);
                    ps.executeUpdate();
                }

                LocationDao.getInstance().removeBatch(locationIds, conn);
                CoordinateDao.getInstance().removeBatch(coordinatesIds, conn);

                conn.commit();
                LOGGER.info("Удалены Person и связанные сущности maker=" + maker);
                return true;
            } catch (SQLException e) {
                conn.rollback();
                LOGGER.error("Ошибка удаления maker=" + maker, e);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
            return false;
        }
    }

    public boolean clear(){
        String query = "truncate Person";
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            System.out.println(1);
            try {
                if(!clear(conn) | !LocationDao.getInstance().clear(conn) |
                        !CoordinateDao.getInstance().clear(conn)){
                    System.out.println(2);
                    return false;
                }
                conn.commit();
                System.out.println(3);

                LOGGER.info("Удалены Person");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                LOGGER.error("Ошибка очищения Person", e);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
            return false;
        }
    }

    public boolean clear(Connection conn){
        String query = "truncate Person";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(query);
            LOGGER.info("Person truncate выполнено успешно");
            return true;
        } catch (SQLException e) {
            LOGGER.error("Ошибка truncate Person");
            return false;
        }
    }
}
