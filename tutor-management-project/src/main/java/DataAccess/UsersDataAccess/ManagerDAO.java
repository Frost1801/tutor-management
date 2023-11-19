package main.java.DataAccess.UsersDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DataAccess.Database;
import main.java.DomainModel.Users.Manager;
import main.java.DomainModel.Users.User;

public class ManagerDAO implements DAO<Manager> {

    UserDAO userDAO = new UserDAO();

    @Override
    public Manager get(int id) throws SQLException {
        Connection connection = Database.getConnection();

        // Get common user parameters using UserDAO
        User user = userDAO.get(id);

        // Get manager-specific parameters from the managers table
        String sqlQueryText = "SELECT * FROM managers WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Manager manager = null;

        if (rs.next()) {
            String otp = rs.getString("otp");

            // Create a Manager object with both common user parameters and manager-specific parameters
            manager = Manager.getInstance(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), otp);
        }

        ps.close();
        connection.close();

        return manager;
    }

    @Override
    public List<Manager> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Manager> managers = new ArrayList<>();

        // Get all common users using UserDAO
        List<User> users = userDAO.getAll();

        // Get manager-specific parameters from the managers table
        String sqlQueryText = "SELECT * FROM managers";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int id = rs.getInt("id");
            String otp = rs.getString("otp");

            // Find the corresponding user with the same ID
            User user = users.stream().filter(u -> u.getID() == id).findFirst().orElse(null);

            if (user != null) {
                // Create a Manager object with both common user parameters and manager-specific parameters
                Manager manager = Manager.getInstance(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), otp);
                managers.add(manager);
            }
        }

        connection.close();

        return managers;
    }

    @Override
    public int insert(Manager manager) throws SQLException {
        // Insert common user parameters using UserDAO
        int rowsAffected = userDAO.insert(manager);

        // Insert manager-specific parameters into the managers table
        Connection connection = Database.getConnection();
        String sqlQueryText = "INSERT INTO managers (id, otp) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, manager.getID());
        ps.setInt(2, Integer.parseInt(manager.getOTP()));

        rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Insertion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int update(Manager manager) throws SQLException {
        // Update common user parameters using UserDAO
        int userRowsAffected = userDAO.update(manager);

        // Update manager-specific parameters in the managers table
        Connection connection = Database.getConnection();
        String sqlQueryText = "UPDATE managers SET otp = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, Integer.parseInt(manager.getOTP()));
        ps.setInt(2, manager.getID());

        int managerRowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + managerRowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        // Delete manager-specific parameters from the managers table
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM managers WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int managerRowsAffected = ps.executeUpdate();

        // Delete common user parameters using UserDAO
        int userRowsAffected = userDAO.delete(id);

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + managerRowsAffected;
    }
}
