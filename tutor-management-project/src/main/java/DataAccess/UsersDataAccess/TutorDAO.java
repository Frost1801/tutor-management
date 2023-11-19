package main.java.DataAccess.UsersDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DataAccess.Database;
import main.java.DomainModel.Users.Tutor;
import main.java.DomainModel.Users.User;

public class TutorDAO implements DAO<Tutor> {

    public UserDAO userDAO = new UserDAO();

    @Override
    public Tutor get(int id) throws SQLException {
        Connection connection = Database.getConnection();

        // Get common user parameters using UserDAO
        User user = userDAO.get(id);

        // Get tutor-specific parameters from the tutors table
        String sqlQueryText = "SELECT * FROM tutors WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Tutor tutor = null;

        if (rs.next()) {
            String subject = rs.getString("subject");
            int hours = rs.getInt("hours");

            // Create a Tutor object with both common user parameters and tutor-specific parameters
            tutor = new Tutor(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), subject, hours);
        }

        ps.close();
        connection.close();

        return tutor;
    }

    @Override
    public List<Tutor> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Tutor> tutors = new ArrayList<>();

        // Get all common users using UserDAO
        List<User> users = userDAO.getAll();

        // Get tutor-specific parameters from the tutors table
        String sqlQueryText = "SELECT * FROM tutors";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int tutorId = rs.getInt("id");
            String subject = rs.getString("subject");
            int hours = rs.getInt("hours");

            // Find the corresponding user with the same ID
            User user = users.stream().filter(u -> u.getID() == tutorId).findFirst().orElse(null);

            if (user != null) {
                // Create a Tutor object with both common user parameters and tutor-specific parameters
                Tutor tutor = new Tutor(tutorId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), subject, hours);
                tutors.add(tutor);
            }
        }

        connection.close();

        return tutors;
    }

    @Override
    public int insert(Tutor tutor) throws SQLException {
        // Insert common user parameters using UserDAO
        int rowsAffected = userDAO.insert(tutor);

        // Insert tutor-specific parameters into the tutors table
        Connection connection = Database.getConnection();
        String sqlQueryText = "INSERT INTO tutors (id, subject, hours) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, tutor.getID());
        ps.setString(2, tutor.getSubject());
        ps.setInt(3, tutor.getHours());

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
    public int update(Tutor tutor) throws SQLException {
        // Update common user parameters using UserDAO
        int userRowsAffected = userDAO.update(tutor);

        // Update tutor-specific parameters in the tutors table
        Connection connection = Database.getConnection();
        String sqlQueryText = "UPDATE tutors SET subject = ?, hours = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setString(1, tutor.getSubject());
        ps.setInt(2, tutor.getHours());
        ps.setInt(3, tutor.getID());

        int tutorRowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + tutorRowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        // Delete tutor-specific parameters from the tutors table
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM tutors WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int tutorRowsAffected = ps.executeUpdate();

        // Delete common user parameters using UserDAO
        int userRowsAffected = userDAO.delete(id);

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + tutorRowsAffected;
    }
}
