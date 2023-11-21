package test.java.DataAccessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.UsersDataAccess.StudentDAO;
import main.java.DomainModel.Users.Student;
import test.java.Utils.DatabaseUtils;

public class StudentDAOTest {

    private StudentDAO studentDAO;

    @BeforeEach
    public void setUp() {
        studentDAO = new StudentDAO();
        DatabaseUtils.createSchema();
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testInsertAndGet() throws SQLException {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "password", 2023, 'A');

        // When
        studentDAO.insert(student);
        Student retrievedStudent = studentDAO.get(1);

        // Then
        assertNotNull(retrievedStudent);
        assertEquals(student.getID(), retrievedStudent.getID());
        assertEquals(student.getFirstName(), retrievedStudent.getFirstName());
        assertEquals(student.getLastName(), retrievedStudent.getLastName());
        assertEquals(student.getEmail(), retrievedStudent.getEmail());
        assertEquals(student.getPassword(), retrievedStudent.getPassword());
        assertEquals(student.getYear(), retrievedStudent.getYear());
        assertEquals(student.getSection(), retrievedStudent.getSection());
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "password", 2023, 'A');
        studentDAO.insert(student);

        // When
        student.setYear(2);
        studentDAO.update(student);
        Student updatedStudent = studentDAO.get(1);

        // Then
        assertNotNull(updatedStudent);
        assertEquals(student.getID(), updatedStudent.getID());
        assertEquals(student.getYear(), updatedStudent.getYear());
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "password", 2023, 'A');
        studentDAO.insert(student);

        // When
        studentDAO.delete(1);
        Student deletedStudent = studentDAO.get(1);

        // Then
        assertNull(deletedStudent);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        Student student1 = new Student(1, "John", "Doe", "john.doe@example.com", "password", 2023, 'A');
        Student student2 = new Student(2, "Jane", "Smith", "jane.smith@example.com", "password", 2024, 'B');

        // When
        studentDAO.insert(student1);
        studentDAO.insert(student2);
        List<Student> allStudents = studentDAO.getAll();

        // Then
        assertNotNull(allStudents);
        assertEquals(2, allStudents.size());
    }
}
