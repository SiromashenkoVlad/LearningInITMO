import java.io.IOException;
import java.sql.*;


public class TestDB {
    public static void main(String[] args) throws IOException {
        try {
            runTest();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void runTest() throws SQLException, IOException {
        String query = "select * from Location";
//        String query = "ALTER TABLE Person " +
//                "ADD COLUMN creationDateZone varchar(50) NOT NULL DEFAULT 'UTC'";
//        String query = "insert into Users values ('admin', '')";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(query)
        ){

//            System.out.println("im here");
//            pr.executeUpdate();

            ResultSet rs =  pr.executeQuery();
            System.out.println("All good 1");
            while (rs.next()){
                int id = rs.getInt("id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                System.out.printf("id: %-12d | x: %-12d | y: %-12d, | z: %12d%n", id, x, y, z);
            }
        }
    }
}
