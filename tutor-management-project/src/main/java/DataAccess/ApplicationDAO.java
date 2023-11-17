package main.java.DataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;

public class ApplicationDAO implements DAO<Application> {

    private Connection connection;

    public ApplicationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Application get(int id) {
        // Implement the logic to retrieve an Application by ID from the database
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Application> getAll() {
        // Implement the logic to retrieve all Applications from the database
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int insert(Application application) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Application application) {
        // Implement the logic to update an Application in the database
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Application application) {
        // Implement the logic to delete an Application from the database
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
