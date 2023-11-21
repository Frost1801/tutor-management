package test.java.DataAccessTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;
import main.java.DomainModel.Users.Candidate;
import test.java.Utils.DatabaseUtils;

public class ApplicationDAOTest {

    private ApplicationDAO applicationDAO;

    @BeforeEach
    public void setUp() {
        applicationDAO = new ApplicationDAO();
        DatabaseUtils.createSchema();
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testInsertAndGet() throws SQLException {
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.insert(candidate);

        Application application = new Application(1, candidate, "Software Engineering");
        application.setResult(ApplicationResult.ACCEPTED);

        // When
        applicationDAO.insert(application);
        Application retrievedApplication = applicationDAO.get(1);

        // Then
        assertNotNull(retrievedApplication);
        assertEquals(application.getId(), retrievedApplication.getId());
        assertEquals(application.getCandidate().getID(), retrievedApplication.getCandidate().getID());
        assertEquals(application.getSubject(), retrievedApplication.getSubject());
        assertEquals(application.getResult(), retrievedApplication.getResult());
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.insert(candidate);

        Application application = new Application(1, candidate, "Software Engineering");
        application.setResult(ApplicationResult.PENDING);
        applicationDAO.insert(application);

        // When
        application.setResult(ApplicationResult.REJECTED);
        applicationDAO.update(application);
        Application updatedApplication = applicationDAO.get(1);

        // Then
        assertNotNull(updatedApplication);
        assertEquals(application.getId(), updatedApplication.getId());
        assertEquals(application.getResult(), updatedApplication.getResult());
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.insert(candidate);

        Application application = new Application(1, candidate, "Software Engineering");
        applicationDAO.insert(application);

        // When
        applicationDAO.delete(1);
        Application deletedApplication = applicationDAO.get(1);

        // Then
        assertNull(deletedApplication);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        Candidate candidate1 = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        Candidate candidate2 = new Candidate(2, "Jane", "Smith", "jane.smith@example.com", "password", 4.0);
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.insert(candidate1);
        candidateDAO.insert(candidate2);

        Application application1 = new Application(1, candidate1, "Software Engineering");
        application1.setResult(ApplicationResult.ACCEPTED);

        Application application2 = new Application(2, candidate2, "Data Science");
        application2.setResult(ApplicationResult.PENDING);

        // When
        applicationDAO.insert(application1);
        applicationDAO.insert(application2);
        List<Application> allApplications = applicationDAO.getAll();

        // Then
        assertNotNull(allApplications);
        assertEquals(2, allApplications.size());
    }

    @Test
    public void testGetNextId() throws SQLException {
        // Given
        int initialId = applicationDAO.getNextId();
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 3.5);
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.insert(candidate);


        // When
        Application application = new Application(initialId, candidate, "Testing");
        applicationDAO.insert(application);

        // Then
        assertEquals(initialId + 1, applicationDAO.getNextId());
    }
}
