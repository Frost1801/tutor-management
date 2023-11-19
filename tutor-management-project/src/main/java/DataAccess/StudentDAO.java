package main.java.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DomainModel.Users.Student;

public class StudentDAO implements DAO<Student> {

    @Override
    public Student get(int id) throws SQLException {
        Connection connection = Database.getConnection();
        Student student = null;

        String sqlQueryText = "SELECT * FROM students WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        // setting the ? placeholder parameter
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int year = rs.getInt("year");
            char section = rs.getString("section").charAt(0);

            student = new Student(id, firstName, lastName, email, password, year, section);
        }
        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Student> students = new ArrayList<>();

        String sqlQueryText = "SELECT * FROM Student";

        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            int studentId = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int year = rs.getInt("year");
            char section = rs.getString("section").charAt(0);

            Student student = new Student(studentId, firstName, lastName, email, password, year, section);
            students.add(student);
        }

        return students;
    }

    @Override
    public int insert(Student student) throws SQLException {
        Connection connection = Database.getConnection();
        

        String sqlQueryText = "INSERT INTO students (first_name, last_name, email, password, year, section) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sqlQueryText, PreparedStatement.RETURN_GENERATED_KEYS);
        // setting parameters
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getLastName());
        ps.setString(3, student.getEmail());
        ps.setString(4, student.getPassword());
        ps.setInt(5, student.getYear());
        ps.setString(6, String.valueOf(student.getSection()));

        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Insertion failed, too many rows affected.");
        }

        // Retrieve the generated ID
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                return generatedId;
            } else {
                throw new SQLException("Insertion failed, no ID obtained.");
            }
        }
    }

    @Override
    public int update(Student student) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "UPDATE students SET first_name = ?, last_name = ?, email = ?, password = ?, year = ?, section = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        // setting parameters
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getLastName());
        ps.setString(3, student.getEmail());
        ps.setString(4, student.getPassword());
        ps.setInt(5, student.getYear());
        ps.setString(6, String.valueOf(student.getSection()));
        ps.setInt(7, student.getID());

        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Update failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Update failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = Database.getConnection();
        String sqlQueryText = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        // setting parameters
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Deletion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Deletion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    
}
