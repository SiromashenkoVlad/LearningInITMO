package server.db;

import common.Mainpart.Person;
import common.enums.Color;
import common.enums.Country;
import common.model.Coordinates;
import common.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DumpManager {
    private static final Logger LOGGER = LogManager.getLogger(DumpManager.class);

    public static List<Person> read() throws SQLException {
        List<Person> persons = new ArrayList<>();

        try(Connection conn = DataSource.getConnection();
            PreparedStatement pr = conn.prepareStatement("Select * from Person");
            PreparedStatement prLoc = conn.prepareStatement("select * from Location Where id = ?");
            PreparedStatement prCoor = conn.prepareStatement("select * from Coordinates where id = ?")
        ){
            LOGGER.info("Начало считывания");
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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

                prLoc.setInt(1, locationId);
                ResultSet rsLoc = prLoc.executeQuery();
                if (!rsLoc.next()) {
                    LOGGER.error("Данные о координатах у Person c id: " + id + " не считаны");
                    continue;
                }
                int x = rsLoc.getInt("x");
                Integer y = rsLoc.getInt("y");
                int z = rsLoc.getInt("z");
                Location loc = new Location(x, y, z);

                prCoor.setInt(1, coorId);
                ResultSet rsCoor = prCoor.executeQuery();
                if (!rsCoor.next()) {
                    LOGGER.error("Данные о координатах у Person c id: " + id + " не считаны");
                    continue;
                }
                x = rsCoor.getInt("x");
                y = rsCoor.getInt("y");
                Coordinates coor = new Coordinates(x, y);
                persons.add(new Person(id, name, coor, creationDate,
                        height, birthday, eyecolor, nationality, loc, maker));
                LOGGER.info("Данные успешно считаны");
            }
        } catch (SQLException e){
            LOGGER.error(e);
        }
        return persons;
    }
}