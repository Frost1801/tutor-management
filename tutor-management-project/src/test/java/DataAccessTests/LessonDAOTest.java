package test.java.DataAccessTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.DataAccess.LessonDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.LessonStatus;
import main.java.DomainModel.Users.Tutor;
import test.java.Utils.DatabaseUtils;

public class LessonDAOTest {

    private LessonDAO lessonDAO;
    private Tutor tutor;
    private int maxStudents = 10;

    @BeforeEach
    public void setUp() {
        lessonDAO = new LessonDAO();
        DatabaseUtils.createSchema();

        tutor = new Tutor(1, "John", "Doe", "john.doe@example.com", "password", "Math");
        TutorDAO tutorDAO = new TutorDAO();
        try{
            tutorDAO.insert(tutor);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        DatabaseUtils.dropSchema();
    }

    @Test
    public void testInsertAndGet() throws SQLException {
        // Given
        LocalDateTime dateTime = LocalDateTime.parse("2023-01-15T12:30:00");
        Lesson lesson = new Lesson(1, "Math", tutor, dateTime, maxStudents);
        lesson.setStatus(LessonStatus.SCHEDULED);

        // When
        lessonDAO.insert(lesson);
        Lesson retrievedLesson = lessonDAO.get(1);

        // Then
        assertNotNull(retrievedLesson);
        assertEquals(lesson.getId(), retrievedLesson.getId());
        assertEquals(lesson.getSubject(), retrievedLesson.getSubject());
        assertEquals(lesson.getTutor().getID(), retrievedLesson.getTutor().getID());
        assertEquals(lesson.getDateTime(), retrievedLesson.getDateTime());
        assertEquals(lesson.getMaxStudents(), retrievedLesson.getMaxStudents());
        assertEquals(lesson.getStatus(), retrievedLesson.getStatus());
    }

    @Test
    public void testUpdate() throws SQLException {
        // Given
        LocalDateTime dateTime = LocalDateTime.parse("2023-01-15T12:30:00");
        Lesson lesson = new Lesson(1, "Math", tutor, dateTime, maxStudents);
        lesson.setStatus(LessonStatus.SCHEDULED);
        lessonDAO.insert(lesson);

        // When
        lesson.setStatus(LessonStatus.CANCELLED);
        lessonDAO.update(lesson);
        Lesson updatedLesson = lessonDAO.get(1);

        // Then
        assertNotNull(updatedLesson);
        assertEquals(lesson.getId(), updatedLesson.getId());
        assertEquals(lesson.getStatus(), updatedLesson.getStatus());
    }

    @Test
    public void testDelete() throws SQLException {
        // Given
        LocalDateTime dateTime = LocalDateTime.parse("2023-01-15T12:30:00");
        Lesson lesson = new Lesson(1, "Math", tutor, dateTime, maxStudents);
        lessonDAO.insert(lesson);

        // When
        lessonDAO.delete(1);
        Lesson deletedLesson = lessonDAO.get(1);

        // Then
        assertNull(deletedLesson);
    }

    @Test
    public void testGetAll() throws SQLException {
        // Given
        LocalDateTime dateTime1 = LocalDateTime.parse("2023-01-15T12:30:00");
        LocalDateTime dateTime2 = LocalDateTime.parse("2023-01-20T15:00:00");

        Lesson lesson1 = new Lesson(1, "Math", tutor, dateTime1, maxStudents);
        lesson1.setStatus(LessonStatus.SCHEDULED);

        Lesson lesson2 = new Lesson(2, "Physics", tutor, dateTime2, maxStudents);
        lesson2.setStatus(LessonStatus.COMPLETED);

        // When
        lessonDAO.insert(lesson1);
        lessonDAO.insert(lesson2);
        List<Lesson> allLessons = lessonDAO.getAll();

        // Then
        assertNotNull(allLessons);
        assertEquals(2, allLessons.size());
    }

    @Test
    public void testGetNextId() throws SQLException {
        // Given
        int initialId = lessonDAO.getNextId();

        // When
        Lesson lesson = new Lesson(initialId, "History", tutor, LocalDateTime.now(), maxStudents);
        lessonDAO.insert(lesson);

        // Then
        assertEquals(initialId + 1, lessonDAO.getNextId());
    }

    @Test
    public void testGetLessonsByDate() throws SQLException {
        // Given
        LocalDateTime dateTime1 = LocalDateTime.parse("2023-01-15T12:30:00");
        LocalDateTime dateTime2 = LocalDateTime.parse("2023-01-20T15:00:00");

        Lesson lesson1 = new Lesson(1, "Math", tutor, dateTime1, maxStudents);
        lesson1.setStatus(LessonStatus.SCHEDULED);

        Lesson lesson2 = new Lesson(2, "Physics", tutor, dateTime2, maxStudents);
        lesson2.setStatus(LessonStatus.COMPLETED);

        // When
        lessonDAO.insert(lesson1);
        lessonDAO.insert(lesson2);
        List<Lesson> lessonsByDate = lessonDAO.getLessonsByDate(dateTime1);

        // Then
        assertNotNull(lessonsByDate);
        assertEquals(1, lessonsByDate.size());
        assertEquals(dateTime1, lessonsByDate.get(0).getDateTime());
    }

    @Test
    public void testGetLessonsBySubject() throws SQLException {
        // Given
        LocalDateTime dateTime1 = LocalDateTime.parse("2023-01-15T12:30:00");
        LocalDateTime dateTime2 = LocalDateTime.parse("2023-01-20T15:00:00");

        Lesson lesson1 = new Lesson(1, "Math", tutor, dateTime1, maxStudents);
        lesson1.setStatus(LessonStatus.SCHEDULED);

        Lesson lesson2 = new Lesson(2, "Physics", tutor, dateTime2, maxStudents);
        lesson2.setStatus(LessonStatus.COMPLETED);

        // When
        lessonDAO.insert(lesson1);
        lessonDAO.insert(lesson2);
        List<Lesson> lessonsBySubject = lessonDAO.getLessonsBySubject("Physics");

        // Then
        assertNotNull(lessonsBySubject);
        assertEquals(1, lessonsBySubject.size());
        assertEquals("Physics", lessonsBySubject.get(0).getSubject());
    }
}
