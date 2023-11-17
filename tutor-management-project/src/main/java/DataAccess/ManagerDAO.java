package main.java.DataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.java.DomainModel.Users.Manager;

public class ManagerDAO implements DAO<Manager> {

    private Connection connection;

    public ManagerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Manager get(int id) {
        // Implement the logic to retrieve a Manager by ID from the database
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Manager> getAll() {
        // Implement the logic to retrieve all Managers from the database
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int insert(Manager manager) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Manager manager) {
        // Implement the logic to update a Manager in the database
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Manager manager) {
        // Implement the logic to delete a Manager from the database
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
