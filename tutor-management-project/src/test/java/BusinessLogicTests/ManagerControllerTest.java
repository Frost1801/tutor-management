package test.java.BusinessLogicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.sql.SQLException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.ApplicationController;
import main.java.BusinessLogic.Controller.CandidateController;
import main.java.BusinessLogic.Controller.ManagerController;
import main.java.BusinessLogic.Controller.TutorController;

import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DataAccess.UsersDataAccess.ManagerDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.ApplicationResult;
import main.java.DomainModel.Users.Candidate;
import main.java.DomainModel.Users.Manager;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;

class ManagerControllerTest {

    private ManagerController managerController;
    private ManagerDAO managerDAO;
    private TutorController tutorController;
    private ApplicationDAO applicationDAO;
    private CandidateController candidateController;
    private CandidateDAO candidateDAO ; 

    @BeforeEach
    void setUp() {
        DatabaseUtils.createSchema();
        managerDAO = new ManagerDAO();
        TutorDAO tutorDAO = new TutorDAO();
        candidateDAO = new CandidateDAO();
        tutorController = new TutorController(tutorDAO, candidateDAO);
        candidateController = new CandidateController(candidateDAO);
        applicationDAO = new ApplicationDAO();
        managerController = new ManagerController(managerDAO, applicationDAO, tutorController);
    }

    @AfterEach
    void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    void testAddManager() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String OTP = "1234";

        // When
        managerController.addManager(firstName, lastName, email, password, OTP);
        Manager addedManager = managerDAO.get(1);

        // Then
        assertNotNull(addedManager);
        assertEquals(firstName, addedManager.getFirstName());
        assertEquals(lastName, addedManager.getLastName());
        assertEquals(email, addedManager.getEmail());
        assertEquals(password, addedManager.getPassword());
        assertEquals(OTP, addedManager.getOTP());
    }

    @Test
    void testGetAllDuePayments() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String subject = "Math";
        int hours = 10;

        // When
        managerController.addManager(firstName, lastName, email, password, "1234");
        tutorController.addTutor(firstName, lastName, "test@gmail.com", password, subject, hours);

        Map<Integer, Double> duePayments = managerController.getAllDuePayments(5);

        // Then
        assertNotNull(duePayments);
        assertEquals(1, duePayments.size());

        double expectedDuePayment = hours * 5;
        assertEquals(expectedDuePayment, duePayments.get(2));
    }

    @Test
    void testGetDuePaymentByID() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String subject = "Math";
        int hours = 8;

        // When
        managerController.addManager(firstName, lastName, email, password, "1234");
        tutorController.addTutor(firstName, lastName, email + "m", password, subject, hours);

        Map<Integer, Double> duePayment = managerController.getDuePaymentByID(2, 7);

        // Then
        assertNotNull(duePayment);
        assertEquals(1, duePayment.size());

        double expectedDuePayment = hours * 7;
        assertEquals(expectedDuePayment, duePayment.get(2));
    }

    @Test
    void testSendApplicationResultAccepted() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        double gpa = 3.5;

        // When
        managerController.addManager(firstName, lastName, email, password, "1234");

        candidateController.addCandidate(firstName, lastName, email + "m", password, gpa);
        Candidate candidate = candidateController.getCandidate(2);

        ApplicationController applicationController = new ApplicationController(applicationDAO);
        applicationController.addApplication(2, "Math", "High School", 90.0f);

        assertNotNull(candidate);

        managerController.sendApplicationResult(1, ApplicationResult.ACCEPTED);
        Tutor tutor = tutorController.getTutor(2);

        // Then
        assertNull(candidateController.getCandidate(2)); // Candidate should be removed
        assertNotNull(tutor);
        assertEquals(firstName, tutor.getFirstName());
        assertEquals(lastName, tutor.getLastName());
        assertEquals(email + "m", tutor.getEmail());
        assertEquals(password, tutor.getPassword());
        assertEquals("", tutor.getSubject()); // Subject is empty for tutor
        assertEquals(0, tutor.getHours()); // Hours start at 0 for tutor
    }

    @Test
    void testSendApplicationResultRejected() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        double gpa = 3.2;

        // When
        managerController.addManager(firstName, lastName, email, password, "1234");

        candidateController.addCandidate(firstName, lastName, email +"m", password, gpa);
        Candidate candidate = candidateController.getCandidate(2);

        assertNotNull(candidate);

        managerController.sendApplicationResult(1, ApplicationResult.REJECTED);
        Tutor tutor = tutorController.getTutor(1);

        // Then
        assertNull(candidateController.getCandidate(1)); // Candidate should be removed
        assertNull(tutor); // Tutor should not be created
    }
}
