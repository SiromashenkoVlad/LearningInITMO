import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;


public class TestDB {
    public static void main(String[] args) throws IOException {
        try {
            runTest();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void runTest() throws SQLException, IOException {
        try (Connection conn = getConnection();
             Statement stat = conn.createStatement()){
            stat.executeUpdate("CREATE TABLE Greetings (MESSAGE CHAR(20))");
            stat.executeUpdate("INSERT INTO Greetings VALUES ('HELLO WORLD')");

            try(ResultSet res = stat.executeQuery("SELECT * FROM Greetings")){
                if (res.next()){
                    System.out.println(res.getString(1));
                }
            }
            stat.executeUpdate("DROP TABLE Greetings");
        }
    }

    public static Connection getConnection()  throws SQLException, IOException{
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(
                Paths.get("database.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null){
            System.setProperty("jdbc.drivers.drivers", drivers);
        }
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }
}
