package main.java.DataAccess.UsersDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DataAccess.Database;
import main.java.DomainModel.Users.Student;
import main.java.DomainModel.Users.User;

public class StudentDAO implements DAO<Student> {

    UserDAO userDAO = new UserDAO();

    @Override
    public Student get(int id) throws SQLException {
        Connection connection = Database.getConnection();

        // Get common user parameters using UserDAO
        User user = userDAO.get(id);

        // Get student-specific parameters from the students table
        String sqlQueryText = "SELECT * FROM students WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Student student = null;

        if (rs.next()) {
            int year = rs.getInt("year");
            char section = rs.getString("section").charAt(0);

            // Create a Student object with both common user parameters and student-specific parameters
            student = new Student(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), year, section);
        }

        ps.close();
        connection.close();

        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Student> students = new ArrayList<>();

        // Get all common users using UserDAO
        List<User> users = userDAO.getAll();

        // Get student-specific parameters from the students table
        String sqlQueryText = "SELECT * FROM students";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int studentId = rs.getInt("id");
            int year = rs.getInt("year");
            char section = rs.getString("section").charAt(0);

            // Find the corresponding user with the same ID
            User user = users.stream().filter(u -> u.getID() == studentId).findFirst().orElse(null);

            if (user != null) {
                // Create a Student object with both common user parameters and student-specific parameters
                Student student = new Student(studentId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), year, section);
                students.add(student);
            }
        }

        connection.close();

        return students;
    }

    @Override
    public int insert(Student student) throws SQLException {
        // Insert common user parameters using UserDAO
        int rowsAffected = userDAO.insert(student);

        // Insert student-specific parameters into the students table
        Connection connection = Database.getConnection();
        String sqlQueryText = "INSERT INTO students (id, year, section) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, student.getID());
        ps.setInt(2, student.getYear());
        ps.setString(3, String.valueOf(student.getSection()));

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
    public int update(Student student) throws SQLException {
        // Update common user parameters using UserDAO
        int userRowsAffected = userDAO.update(student);

        // Update student-specific parameters in the students table
        Connection connection = Database.getConnection();
        String sqlQueryText = "UPDATE students SET year = ?, section = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, student.getYear());
        ps.setString(2, String.valueOf(student.getSection()));
        ps.setInt(3, student.getID());

        int studentRowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + studentRowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        // Delete student-specific parameters from the students table
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int studentRowsAffected = ps.executeUpdate();

        // Delete common user parameters using UserDAO
        int userRowsAffected = userDAO.delete(id);

        ps.close();
        connection.close();

        // Return the total number of affected rows
        return userRowsAffected + studentRowsAffected;
    }
}
