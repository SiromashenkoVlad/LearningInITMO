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
        String query = "select * from Person";
//        String query = "ALTER TABLE Person " +
//                "ADD COLUMN creationDateZone varchar(50) NOT NULL DEFAULT 'UTC'";
//        String query = "alter table Users add column salt varchar (32) not null";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(query)
        ){

//            System.out.println("im here");
//            pr.executeUpdate();

            ResultSet rs =  pr.executeQuery();
            System.out.println("All good 1");
            while (rs.next()){
                String name = rs.getString("name");
                String maker = rs.getString("maker");
                System.out.printf("name: %-12s | maker: %-12s%n", name, maker);
            }
        }
    }
}
