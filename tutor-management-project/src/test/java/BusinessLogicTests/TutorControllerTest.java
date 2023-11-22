package test.java.BusinessLogicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.CandidateController;
import main.java.BusinessLogic.Controller.TutorController;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Users.Candidate;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;

public class TutorControllerTest {

    private TutorController tutorController;
    private TutorDAO tutorDAO;

    @BeforeEach
    public void setUp() {
        DatabaseUtils.createSchema();
        tutorDAO = new TutorDAO();
        CandidateDAO candidateDAO = new CandidateDAO();
        tutorController = new TutorController(tutorDAO, candidateDAO);
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testAddTutor() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String subject = "Math";
        int hours = 10;

        // When
        tutorController.addTutor(firstName, lastName, email, password, subject, hours);
        Tutor addedTutor = tutorDAO.get(1);

        // Then
        assertNotNull(addedTutor);
        assertEquals(firstName, addedTutor.getFirstName());
        assertEquals(lastName, addedTutor.getLastName());
        assertEquals(email, addedTutor.getEmail());
        assertEquals(password, addedTutor.getPassword());
        assertEquals(subject, addedTutor.getSubject());
        assertEquals(hours, addedTutor.getHours());
    }

    @Test
    public void testIncreaseLessonHours() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String subject = "Math";
        int initialHours = 5;
        int additionalHours = 3;

        // When
        tutorController.addTutor(firstName, lastName, email, password, subject, initialHours);
        Tutor tutor = tutorDAO.get(1);
        assertNotNull(tutor);

        tutorController.increaseLessonHours(1, additionalHours);
        Tutor updatedTutor = tutorDAO.get(1);

        // Then
        assertNotNull(updatedTutor);
        assertEquals(initialHours + additionalHours, updatedTutor.getHours());
    }

    @Test
    public void testGetTutor() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String subject = "Math";
        int hours = 10;

        // When
        tutorController.addTutor(firstName, lastName, email, password, subject, hours);
        Tutor tutor = tutorController.getTutor(1);

        // Then
        assertNotNull(tutor);
        assertEquals(firstName, tutor.getFirstName());
        assertEquals(lastName, tutor.getLastName());
        assertEquals(email, tutor.getEmail());
        assertEquals(password, tutor.getPassword());
        assertEquals(subject, tutor.getSubject());
        assertEquals(hours, tutor.getHours());
    }

    @Test
    public void testGetAllTutors() throws SQLException {
        // Given
        String firstName1 = "John";
        String lastName1 = "Doe";
        String email1 = "john.doe@example.com";
        String password1 = "password";
        String subject1 = "Math";
        int hours1 = 10;

        String firstName2 = "Jane";
        String lastName2 = "Smith";
        String email2 = "jane.smith@example.com";
        String password2 = "password";
        String subject2 = "Physics";
        int hours2 = 8;

        // When
        tutorController.addTutor(firstName1, lastName1, email1, password1, subject1, hours1);
        tutorController.addTutor(firstName2, lastName2, email2, password2, subject2, hours2);
        List<Tutor> tutors = tutorController.getAllTutors();

        // Then
        assertNotNull(tutors);
        assertEquals(2, tutors.size());

        Tutor tutor1 = tutors.get(0);
        assertNotNull(tutor1);
        assertEquals(firstName1, tutor1.getFirstName());
        assertEquals(lastName1, tutor1.getLastName());
        assertEquals(email1, tutor1.getEmail());
        assertEquals(password1, tutor1.getPassword());
        assertEquals(subject1, tutor1.getSubject());
        assertEquals(hours1, tutor1.getHours());

        Tutor tutor2 = tutors.get(1);
        assertNotNull(tutor2);
        assertEquals(firstName2, tutor2.getFirstName());
        assertEquals(lastName2, tutor2.getLastName());
        assertEquals(email2, tutor2.getEmail());
        assertEquals(password2, tutor2.getPassword());
        assertEquals(subject2, tutor2.getSubject());
        assertEquals(hours2, tutor2.getHours());
    }

    @Test
    public void testAddTutorFromCandidate() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        double gpa = 3.5;

        // When
        CandidateController candidateController = new CandidateController(new CandidateDAO());
        candidateController.addCandidate(firstName, lastName, email, password, gpa);
        Candidate candidate = candidateController.getCandidate(1);

        assertNotNull(candidate);

        tutorController.addTutor(candidate);
        Tutor tutor = tutorDAO.get(1);

        // Then
        assertNull(candidateController.getCandidate(1)); // Candidate should be removed
        assertNotNull(tutor);
        assertEquals(firstName, tutor.getFirstName());
        assertEquals(lastName, tutor.getLastName());
        assertEquals(email, tutor.getEmail());
        assertEquals(password, tutor.getPassword());
        assertEquals("", tutor.getSubject()); // Subject is empty for tutor
        assertEquals(0, tutor.getHours()); // Hours start at 0 for tutor
    }
}

