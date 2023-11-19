package main.java.DataAccess.UsersDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DataAccess.Database;
import main.java.DomainModel.Users.Candidate;
import main.java.DomainModel.Users.User;

public class CandidateDAO implements DAO<Candidate> {

    UserDAO userDAO = new UserDAO();

    @Override
    public Candidate get(int id) throws SQLException {
        Connection connection = Database.getConnection();

        // Get common user parameters using UserDAO
        User user = userDAO.get(id);

        // Get candidate-specific parameters from the candidates table
        String sqlQueryText = "SELECT * FROM candidates WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Candidate candidate = null;

        if (rs.next()) {
            double gpa = rs.getDouble("GPA");

            // Create a Candidate object with both common user parameters and candidate-specific parameters
            candidate = new Candidate(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), gpa);
        }

        ps.close();
        connection.close();

        return candidate;
    }

    @Override
    public List<Candidate> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Candidate> candidates = new ArrayList<>();

        // Get all common users using UserDAO
        List<User> users = userDAO.getAll();

        // Get candidate-specific parameters from the candidates table
        String sqlQueryText = "SELECT * FROM candidates";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int candidateId = rs.getInt("id");
            double gpa = rs.getDouble("GPA");

            // Find the corresponding user with the same ID
            User user = users.stream().filter(u -> u.getID() == candidateId).findFirst().orElse(null);

            if (user != null) {
                // Create a Candidate object with both common user parameters and candidate-specific parameters
                Candidate candidate = new Candidate(candidateId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), gpa);
                candidates.add(candidate);
            }
        }

        connection.close();

        return candidates;
    }

    @Override
    public int insert(Candidate candidate) throws SQLException {
        // Insert common user parameters using UserDAO
        int rowsAffected = userDAO.insert(candidate);

        // Insert candidate-specific parameters into the candidates table
        Connection connection = Database.getConnection();
        String sqlQueryText = "INSERT INTO candidates (id, GPA) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, candidate.getID());
        ps.setDouble(2, candidate.getGPA());

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
    public int update(Candidate candidate) throws SQLException {
        // Update common user parameters using UserDAO
        int userRowsAffected = userDAO.update(candidate);

        // Update candidate-specific parameters in the candidates table
        Connection connection = Database.getConnection();
        String sqlQueryText = "UPDATE candidates SET GPA = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setDouble(1, candidate.getGPA());
        ps.setInt(2, candidate.getID());

        int candidateRowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + candidateRowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        // Delete candidate-specific parameters from the candidates table
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM candidates WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int candidateRowsAffected = ps.executeUpdate();

        // Delete common user parameters using UserDAO
        int userRowsAffected = userDAO.delete(id);

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + candidateRowsAffected;
    }
}
