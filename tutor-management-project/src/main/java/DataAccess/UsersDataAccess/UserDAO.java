package main.java.DataAccess.UsersDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DataAccess.Database;
import main.java.DomainModel.Users.User;

public class UserDAO implements DAO<User> {

    @Override
    public User get(int id) throws SQLException {
        Connection connection = Database.getConnection();
        User user = null;

        String sqlQueryText = "SELECT * FROM users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        // setting the ? placeholder parameter
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String password = rs.getString("password");

            user = new User(id, firstName, lastName, email, password);
        }

        ps.close();
        connection.close();
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<User> users = new ArrayList<>();

        String sqlQueryText = "SELECT * FROM users";

        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int userId = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String password = rs.getString("password");

            User user = new User(userId, firstName, lastName, email, password);
            users.add(user);
        }
        rs.close();
        connection.close();

        return users;
    }

    @Override
    public int insert(User user) throws SQLException {
        Connection connection = Database.getConnection();
    
        String sqlQueryText = "INSERT INTO users (id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";
    
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        // setting parameters
        ps.setInt(1, user.getID());  // Set the specific ID
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPassword());
    
        int rowsAffected = ps.executeUpdate();
        ps.close();
    
        if (rowsAffected == 0) {
            throw new SQLException("User insertion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("User insertion failed, too many rows affected.");
        }
        

        // Close the connection
        connection.close();
    
        return rowsAffected;
    }
    
    @Override
    public int update(User user) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        // setting parameters
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setInt(5, user.getId());

        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("User update failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("User update failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        // setting parameters
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("User deletion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("User deletion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    public User getByEmail (String email) throws SQLException{
        Connection connection = Database.getConnection();
        User user = null;
        String sqlQueryText = "SELECT * FROM users WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        // setting the ? placeholder parameter
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String password = rs.getString("password");

            user = new User(id, firstName, lastName, email, password);
        }



        ps.close();
        connection.close();
        return user;
    }

    public int getNextId() throws SQLException {
        Connection connection = Database.getConnection();
        String query = "SELECT MAX(ID) FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id; 
        if (rs.next()) {
            id = rs.getInt(1) + 1;
        } else {
            id = 1;
        }

        rs.close();
        statement.close();
        connection.close();
        return id;
    }

}
