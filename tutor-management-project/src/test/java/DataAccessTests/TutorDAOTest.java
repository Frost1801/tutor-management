package test.java.DataAccessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;

public class TutorDAOTest {

    private TutorDAO tutorDAO;

    @BeforeEach
    public void setUp() {
        tutorDAO = new TutorDAO();
        DatabaseUtils.createSchema();
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testInsertAndGet() throws SQLException {
        // Given
        Tutor tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math", 10);

        // When
        tutorDAO.insert(tutor);
        Tutor retrievedTutor = tutorDAO.get(1);

        // Then
        assertNotNull(retrievedTutor);
        assertEquals(tutor.getID(), retrievedTutor.getID());
        assertEquals(tutor.getFirstName(), retrievedTutor.getFirstName());
        assertEquals(tutor.getLastName(), retrievedTutor.getLastName());
        assertEquals(tutor.getEmail(), retrievedTutor.getEmail());
        assertEquals(tutor.getPassword(), retrievedTutor.getPassword());
        assertEquals(tutor.getSubject(), retrievedTutor.getSubject());
        assertEquals(tutor.getHours(), retrievedTutor.getHours());
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        Tutor tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math", 10);
        tutorDAO.insert(tutor);

        // When
        tutor.setHours(15);
        tutorDAO.update(tutor);
        Tutor updatedTutor = tutorDAO.get(1);

        // Then
        assertNotNull(updatedTutor);
        assertEquals(tutor.getID(), updatedTutor.getID());
        assertEquals(tutor.getHours(), updatedTutor.getHours());
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        Tutor tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math", 10);
        tutorDAO.insert(tutor);

        // When
        tutorDAO.delete(1);
        Tutor deletedTutor = tutorDAO.get(1);

        // Then
        assertNull(deletedTutor);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        Tutor tutor1 = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math", 10);
        Tutor tutor2 = new Tutor(2, "Jane", "Smith", "jane.smith@example.com", "password", "Physics", 8);

        // When
        tutorDAO.insert(tutor1);
        tutorDAO.insert(tutor2);
        List<Tutor> allTutors = tutorDAO.getAll();

        // Then
        assertNotNull(allTutors);
        assertEquals(2, allTutors.size());
    }
}
