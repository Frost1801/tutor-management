package test.java.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.DataAccess.Database;

public class DatabaseUtils {

    public static void createSchema() {
        executeSQLScript("src/main/resources/createSchema.sql");
    }

    public static void dropSchema() {
        executeSQLScript("src/main/resources/dropSchema.sql");
    }

    private static void executeSQLScript(String scriptFileName) {
            ArrayList <String> querys = new ArrayList<>(); 
            try {
                String queryString = TestUtils.readFile(scriptFileName, StandardCharsets.UTF_8);
                String[] queryArray = queryString.split(";");
                for (String query : queryArray) {
                    querys.add(query);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String query : querys) {
                try {
                    executeQuery(query);
                } catch (SQLException e) {
                    //
                }
            }
            
    }
    private static void executeQuery (String query) throws SQLException{
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
         ps.execute();
        ps.close();
        conn.close();
    }
}
