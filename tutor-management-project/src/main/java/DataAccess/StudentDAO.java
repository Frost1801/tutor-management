package main.java.DataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.java.DomainModel.Users.Student;

public class StudentDAO implements DAO<Student> {

    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student get(int id) {
        // Implement the logic to retrieve a Student by ID from the database
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Student> getAll() {
        // Implement the logic to retrieve all Students from the database
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int insert(Student student) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Student student) {
        // Implement the logic to update a Student in the database
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Student student) {
        // Implement the logic to delete a Student from the database
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
