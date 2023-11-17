package main.java.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.java.DomainModel.Users.Candidate;

public class CandidateDAO implements DAO<Candidate> {

    private Connection connection;

    public CandidateDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Candidate get(int id) {
        // Implement the logic to retrieve a Candidate by ID from the database
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Candidate> getAll() {
        // Implement the logic to retrieve all Candidates from the database
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int insert(Candidate candidate) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Candidate candidate) {
        // Implement the logic to update a Candidate in the database
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Candidate candidate) {
        // Implement the logic to delete a Candidate from the database
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
