package test.java.BusinessLogicTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.CandidateController;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Users.Candidate;
import test.java.Utils.DatabaseUtils;

public class CandidateControllerTest {

    private CandidateController candidateController;
    private CandidateDAO candidateDAO;

    @BeforeEach
    public void setUp() {
        DatabaseUtils.createSchema();
        candidateDAO = new CandidateDAO();
        candidateController = new CandidateController(candidateDAO);
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testAddCandidate() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        double GPA = 3.8;

        // When
        candidateController.addCandidate(firstName, lastName, email, password, GPA);
        Candidate addedCandidate = candidateDAO.get(1);

        // Then
        assertNotNull(addedCandidate);
        assertEquals(firstName, addedCandidate.getFirstName());
        assertEquals(lastName, addedCandidate.getLastName());
        assertEquals(email, addedCandidate.getEmail());
        assertEquals(password, addedCandidate.getPassword());
        assertEquals(GPA, addedCandidate.getGPA());
    }
}