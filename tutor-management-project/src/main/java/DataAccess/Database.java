package main.java.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String url = "jdbc:sqlite:tutorManager.db";
    //useful for a slightly easier way to connect to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
        return connection;
    }
}
