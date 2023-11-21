package test.java.DataAccessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Users.Candidate;
import test.java.Utils.DatabaseUtils;

public class CandidateDAOTest {

    private CandidateDAO candidateDAO;
    private double delta = 0.000001;
    

    @BeforeEach
    public void setUp() {
        candidateDAO = new CandidateDAO();
        DatabaseUtils.createSchema();
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }


    @Test
    public void testInsertAndGet() throws SQLException {
        candidateDAO = new CandidateDAO();
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);

        // When
        candidateDAO.insert(candidate);
        Candidate retrievedCandidate = candidateDAO.get(1);

        // Then
        assertNotNull(retrievedCandidate);
        assertEquals(candidate.getID(), retrievedCandidate.getID());
        assertEquals(candidate.getFirstName(), retrievedCandidate.getFirstName());
        assertEquals(candidate.getLastName(), retrievedCandidate.getLastName());
        assertEquals(candidate.getEmail(), retrievedCandidate.getEmail());
        assertEquals(candidate.getPassword(), retrievedCandidate.getPassword());
        assertEquals(candidate.getGPA(), retrievedCandidate.getGPA(), delta);
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        candidateDAO.insert(candidate);

        // When
        candidate.setGPA(4.0);
        candidateDAO.update(candidate);
        Candidate updatedCandidate = candidateDAO.get(1);

        // Then
        assertNotNull(updatedCandidate);
        assertEquals(candidate.getID(), updatedCandidate.getID());
        assertEquals(candidate.getGPA(), updatedCandidate.getGPA(), delta);
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        candidateDAO.insert(candidate);

        // When
        candidateDAO.delete(1);
        Candidate deletedCandidate = candidateDAO.get(1);

        // Then
        assertNull(deletedCandidate);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        Candidate candidate1 = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        Candidate candidate2 = new Candidate(2, "Jane", "Smith", "jane.smith@example.com", "password", 4.0);

        // When
        candidateDAO.insert(candidate1);
        candidateDAO.insert(candidate2);
        List<Candidate> allCandidates = candidateDAO.getAll();

        // Then
        assertNotNull(allCandidates);
        assertEquals(2, allCandidates.size());
    }

}
