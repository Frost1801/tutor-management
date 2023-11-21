package test.java.DataAccessTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.LessonDAO;
import main.java.DataAccess.LessonStudentDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;

public class LessonStudentDAOTest {

    private LessonStudentDAO lessonStudentDAO;
    private int maxStudents = 10;

    @BeforeEach
    public void setUp() {
        DatabaseUtils.createSchema();
        Tutor tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math");
        Lesson lesson = new Lesson (1, "Math", tutor, LocalDateTime.parse("2019-03-27T10:15:30") ,  maxStudents);

        TutorDAO tutorDAO = new TutorDAO();
        LessonDAO lessonDAO = new LessonDAO();
        try{
            tutorDAO.insert(tutor);
            lessonDAO.insert(lesson);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        lessonStudentDAO = new LessonStudentDAO();
        
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testBookLesson() throws SQLException {
        // Given
        int studentId = 1;
        int lessonId = 1;

        // When
        boolean booked = lessonStudentDAO.bookLesson(lessonId, studentId);

        // Then
        assertTrue(booked);
        assertEquals(9, lessonStudentDAO.getRemainingSpots(lessonId));
    }

    @Test
    public void testCancelBooking() throws SQLException {

        // Given
        int studentId = 1;
        int lessonId = 1;
        lessonStudentDAO.bookLesson(lessonId, studentId); // Booking the lesson first

        // When
        boolean canceled = lessonStudentDAO.cancelBooking(lessonId, studentId);

        // Then
        assertTrue(canceled);
        assertEquals(maxStudents, lessonStudentDAO.getRemainingSpots(lessonId));
    }

    @Test
    public void testBookLessonNoAvailableSpots() throws SQLException {
        
        // Given
        int studentId = 1;
        int lessonId = 1;
        for (int i = 0; i < maxStudents; i++) {
            lessonStudentDAO.bookLesson(lessonId, i + 2); // Book all available spots
        }

        // When
        boolean booked = lessonStudentDAO.bookLesson(lessonId, studentId);

        // Then
        assertFalse(booked);
        assertEquals(0, lessonStudentDAO.getRemainingSpots(lessonId));
    }
}

