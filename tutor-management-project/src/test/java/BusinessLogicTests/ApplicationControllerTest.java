package test.java.BusinessLogicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.ApplicationController;
import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;
import main.java.DomainModel.Users.Candidate;
import test.java.Utils.DatabaseUtils;

public class ApplicationControllerTest {

    private ApplicationController applicationController;
    private ApplicationDAO applicationDAO;
    private CandidateDAO candidateDAO;

    @BeforeEach
    public void setUp() {
        DatabaseUtils.createSchema();
        applicationDAO = new ApplicationDAO();
        candidateDAO = new CandidateDAO();
        applicationController = new ApplicationController(applicationDAO, candidateDAO);

        // Insert a candidate for testing
        Candidate candidate = new Candidate(1, "John", "Doe", "john.doe@example.com", "password", 4.0);
        CandidateDAO candidateDAO = new CandidateDAO();
        try {
            candidateDAO.insert(candidate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testAddApplication() throws SQLException {
        // Given
        int candidateID = 1;
        String subject = "Math";
        String school = "High School";
        float avg = 90.0f;

        // When
        applicationController.addApplication(candidateID, subject, school, avg);
        Application addedApplication = applicationDAO.get(1);

        // Then
        assertNotNull(addedApplication);
        assertEquals(candidateID, addedApplication.getCandidate().getID());
        assertEquals(subject, addedApplication.getSubject());
        assertEquals(ApplicationResult.PENDING, addedApplication.getResult());
    }

    @Test
    public void testViewStatus() throws SQLException {
        // Given
        int candidateID = 1;
        String subject = "Physics";
        String school = "High School";
        float avg = 85.0f;
        applicationController.addApplication(candidateID, subject, school, avg);
        // When
        ApplicationResult status = applicationController.viewStatus(1);

        // Then
        assertNotNull(status);
        assertEquals(ApplicationResult.PENDING, status);
    }

    @Test
    public void testViewStatusNonexistentApplication() {
        // When
        ApplicationResult status = applicationController.viewStatus(1);

        // Then
        assertNull(status);
    }
}
