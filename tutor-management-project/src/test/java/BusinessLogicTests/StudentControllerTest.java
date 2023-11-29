package test.java.BusinessLogicTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.StudentController;
import main.java.DataAccess.UsersDataAccess.StudentDAO;
import main.java.DomainModel.Users.Student;
import test.java.Utils.DatabaseUtils;

public class StudentControllerTest {

    private StudentController studentController;
    private StudentDAO studentDAO;

    @BeforeEach
    public void setUp() {
        DatabaseUtils.createSchema();
        studentDAO = new StudentDAO();
        studentController = new StudentController(studentDAO);
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testAddStudent() throws SQLException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@russell-newton.edu.it";
        String password = "password";
        char section = 'A';
        int year = 2;

        // When
        studentController.addStudent(firstName, lastName, email, password, section, year);
        Student addedStudent = studentDAO.get(1);

        // Then
        assertNotNull(addedStudent);
        assertEquals(firstName, addedStudent.getFirstName());
        assertEquals(lastName, addedStudent.getLastName());
        assertEquals(email, addedStudent.getEmail());
        assertEquals(password, addedStudent.getPassword());
        assertEquals(section, addedStudent.getSection());
        assertEquals(year, addedStudent.getYear());
    }
}
