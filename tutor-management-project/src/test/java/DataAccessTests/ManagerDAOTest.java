package test.java.DataAccessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.UsersDataAccess.ManagerDAO;
import main.java.DomainModel.Users.Manager;
import test.java.Utils.DatabaseUtils;

public class ManagerDAOTest {

    private ManagerDAO managerDAO;
    private static final String TEST_OTP = "123456";

    @BeforeEach
    public void setUp() {
        managerDAO = new ManagerDAO();
        DatabaseUtils.createSchema();
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testInsertAndGet() throws SQLException {
        // Given
        Manager manager = Manager.getInstance(1, "John", "Doe", "john.doe@example.com", "password", TEST_OTP);

        // When
        managerDAO.insert(manager);
        Manager retrievedManager = managerDAO.get(1);

        // Then
        assertNotNull(retrievedManager);
        assertEquals(manager.getID(), retrievedManager.getID());
        assertEquals(manager.getFirstName(), retrievedManager.getFirstName());
        assertEquals(manager.getLastName(), retrievedManager.getLastName());
        assertEquals(manager.getEmail(), retrievedManager.getEmail());
        assertEquals(manager.getPassword(), retrievedManager.getPassword());
        assertEquals(manager.getOTP(), retrievedManager.getOTP());
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        Manager manager = Manager.getInstance(1, "John", "Doe", "john.doe@example.com", "password", TEST_OTP);
        managerDAO.insert(manager);

        // When
        manager.setOTP("654321");
        managerDAO.update(manager);
        Manager updatedManager = managerDAO.get(1);

        // Then
        assertNotNull(updatedManager);
        assertEquals(manager.getID(), updatedManager.getID());
        assertEquals(manager.getOTP(), updatedManager.getOTP());
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        Manager manager = Manager.getInstance(1, "John", "Doe", "john.doe@example.com", "password", TEST_OTP);
        managerDAO.insert(manager);

        // When
        managerDAO.delete(1);
        Manager deletedManager = managerDAO.get(1);

        // Then
        assertNull(deletedManager);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        Manager manager = Manager.getInstance(1, "John", "Doe", "john.doe@example.com", "password", TEST_OTP);

        // When
        managerDAO.insert(manager);
        List<Manager> allManagers = managerDAO.getAll();

        // Then
        assertNotNull(allManagers);
        assertEquals(1, allManagers.size());
    }
}
