package test.java.BusinessLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.BusinessLogic.Controller.LessonController;
import main.java.BusinessLogic.Controller.TutorController;
import main.java.DataAccess.LessonDAO;
import main.java.DataAccess.LessonStudentDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.LessonStatus;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;


class LessonControllerTest {

    private LessonController lessonController;
    private LessonDAO lessonDAO;
    private LessonStudentDAO lessonStudentDAO;
    private TutorController tutorController;

    @BeforeEach
    void setUp() {
        DatabaseUtils.createSchema();
        lessonDAO = new LessonDAO();
        lessonStudentDAO = new LessonStudentDAO();
        lessonController = new LessonController(lessonDAO, lessonStudentDAO);
        tutorController = new TutorController(new TutorDAO(), new CandidateDAO());
    }

    @AfterEach
    void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    void testAddLesson() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now();
        String subject = "Math";
        Tutor tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math", 0);
        tutorController.addTutor(tutor);
        // When
        lessonController.addLesson(time, subject, tutor);
        List<Lesson> lessons = lessonDAO.getAll();

        // Then
        assertNotNull(lessons);
        assertEquals(1, lessons.size());

        Lesson addedLesson = lessons.get(0);
        assertEquals(time, addedLesson.getDateTime());
        assertEquals(subject, addedLesson.getSubject());
        assertEquals(tutor.getId(), addedLesson.getTutor().getId());
        assertEquals(LessonStatus.SCHEDULED, addedLesson.getStatus());
    }

    @Test
    void testDeleteLesson() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now();
        String subject = "Physics";
        Tutor tutor = new Tutor(1, "Jane", "Doe", "jane.doe@example.com", "password", "Physics", 0);

        lessonController.addLesson(time, subject, tutor);
        List<Lesson> lessonsBeforeDelete = lessonDAO.getAll();

        // When
        lessonController.deleteLesson(1);
        List<Lesson> lessonsAfterDelete = lessonDAO.getAll();

        // Then
        assertEquals(1, lessonsBeforeDelete.size());
        assertEquals(0, lessonsAfterDelete.size());
    }

    @Test
    void testSetDate() throws SQLException {
        // Given
        LocalDateTime initialTime = LocalDateTime.now();
        String subject = "Chemistry";
        Tutor tutor = new Tutor(1, "Alice", "Smith", "alice.smith@example.com", "password", "Chemistry", 0);
        tutorController.addTutor(tutor);

        lessonController.addLesson(initialTime, subject, tutor);

        // When
        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        lessonController.setDate(1, newTime);
        Lesson updatedLesson = lessonDAO.get(1);

        // Then
        assertNotNull(updatedLesson);
        assertEquals(newTime, updatedLesson.getDateTime());
    }

    @Test
    void testSetTime() throws SQLException {
        // Given
        LocalDateTime initialTime = LocalDateTime.now();
        String subject = "Biology";
        Tutor tutor = new Tutor(1, "Bob", "Johnson", "bob.johnson@example.com", "password", "Biology", 0);
        tutorController.addTutor(tutor);

        lessonController.addLesson(initialTime, subject, tutor);

        // When
        LocalDateTime newTime = LocalDateTime.now().plusHours(2);
        lessonController.setTime(1, newTime);
        Lesson updatedLesson = lessonDAO.get(1);

        // Then
        assertNotNull(updatedLesson);
        assertEquals(newTime, updatedLesson.getDateTime());
    }

    @Test
    void testChangeStatus() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now();
        String subject = "History";
        Tutor tutor = new Tutor(1, "Charlie", "Brown", "charlie.brown@example.com", "password", "History", 0);
        tutorController.addTutor(tutor);

        lessonController.addLesson(time, subject, tutor);

        // When
        lessonController.changeStatus(1, LessonStatus.CANCELLED);
        Lesson updatedLesson = lessonDAO.get(1);

        // Then
        assertNotNull(updatedLesson);
        assertEquals(LessonStatus.CANCELLED, updatedLesson.getStatus());
    }

    @Test
    void testGetLessonByDate() throws SQLException {
        // Given
        LocalDateTime time1 = LocalDateTime.now().plusDays(1);
        LocalDateTime time2 = LocalDateTime.now().plusDays(2);
        String subject = "Geography";
        Tutor tutor = new Tutor(1, "David", "Miller", "david.miller@example.com", "password", "Geography", 0);
        tutorController.addTutor(tutor);

        lessonController.addLesson(time1, subject, tutor);
        lessonController.addLesson(time2, subject, tutor);

        // When
        List<Lesson> lessons = lessonController.getLessonByDate(time1);

        // Then
        assertNotNull(lessons);
        assertEquals(1, lessons.size());
        assertEquals(time1, lessons.get(0).getDateTime());
    }

    @Test
    void testGetLessonBySubject() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now();
        String subject1 = "English";
        String subject2 = "Literature";
        Tutor tutor = new Tutor(1, "Eva", "Clark", "eva.clark@example.com", "password", "English", 0);
        tutorController.addTutor(tutor);

        lessonController.addLesson(time, subject1, tutor);
        lessonController.addLesson(time, subject2, tutor);

        // When
        List<Lesson> lessons = lessonController.getLessonBySubject(subject1);

        // Then
        assertNotNull(lessons);
        assertEquals(1, lessons.size());
        assertEquals(subject1, lessons.get(0).getSubject());
    }

    @Test
    void testBookLesson() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        String subject = "Programming";
        Tutor tutor = new Tutor(1, "Frank", "Johnson", "frank.johnson@example.com", "password", "Programming", 0);
        int studentId = 1;

        lessonController.addLesson(time, subject, tutor);

        // When
        boolean booked = lessonController.bookLesson(studentId, 1);
        int remainingSpots = lessonController.getRemainingSpots(1);

        // Then
        assertTrue(booked);
        assertEquals(9, remainingSpots); // Assuming a max of 10 students
    }

    @Test
    void testCancelBooking() throws SQLException {
        // Given
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        String subject = "Physics";
        Tutor tutor = new Tutor(1, "Grace", "Smith", "grace.smith@example.com", "password", "Physics", 0);
        int studentId = 1;

        lessonController.addLesson(time, subject, tutor);
        lessonController.bookLesson(studentId, 1);

        // When
        boolean cancelled = lessonController.cancelBooking(studentId, 1);
        int remainingSpots = lessonController.getRemainingSpots(1);

        // Then
        assertTrue(cancelled);
        assertEquals(10, remainingSpots); // Assuming a max of 10 students
    }

}
