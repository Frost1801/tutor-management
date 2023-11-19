package main.java.DataAccess.UsersDataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.java.DataAccess.DAO;
import main.java.DomainModel.Users.Tutor;

public class TutorDAO implements DAO<Tutor> {

    private Connection connection;

    public TutorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Tutor get(int id) {
        // Implement the logic to retrieve a Tutor by ID from the database
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Tutor> getAll() {
        // Implement the logic to retrieve all Tutors from the database
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int insert(Tutor tutor) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Tutor tutor) {
        // Implement the logic to update a Tutor in the database
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Tutor tutor) {
        // Implement the logic to delete a Tutor from the database
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
