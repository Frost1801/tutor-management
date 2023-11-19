package main.java.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.LessonStatus;
import main.java.DomainModel.Users.Tutor;

public class LessonDAO implements DAO<Lesson> {

    @Override
    public Lesson get(int id) throws SQLException {
        Connection connection = Database.getConnection();
        Lesson lesson = null;

        String sqlQueryText = "SELECT * FROM lessons WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            TutorDAO tutorDAO = new TutorDAO();
            int tutorId = rs.getInt("tutor_id");
            Tutor tutor = tutorDAO.get(tutorId);

            String subject = rs.getString("subject");
            LocalDateTime dateTime = rs.getObject("date_time", LocalDateTime.class);
            int maxStudents = rs.getInt("max_students");
            LessonStatus status = LessonStatus.valueOf(rs.getString("status"));

            lesson = new Lesson(id, subject, tutor, dateTime, maxStudents);
            lesson.setStatus(status);
        }

        ps.close();
        connection.close();

        return lesson;
    }

    @Override
    public List<Lesson> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Lesson> lessons = new ArrayList<>();

        String sqlQueryText = "SELECT * FROM lessons";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            TutorDAO tutorDAO = new TutorDAO();
            int lessonId = rs.getInt("id");
            int tutorId = rs.getInt("tutor_id");
            Tutor tutor = tutorDAO.get(tutorId);

            String subject = rs.getString("subject");
            LocalDateTime dateTime = rs.getObject("date_time", LocalDateTime.class);
            int maxStudents = rs.getInt("max_students");
            LessonStatus status = LessonStatus.valueOf(rs.getString("status"));

            Lesson lesson = new Lesson(lessonId, subject, tutor, dateTime, maxStudents);
            lesson.setStatus(status);

            lessons.add(lesson);
        }

        connection.close();

        return lessons;
    }

    @Override
    public int insert(Lesson lesson) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "INSERT INTO lessons (id, tutor_id, subject, date_time, max_students, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, lesson.getId());
        ps.setInt(2, lesson.getTutor().getID());
        ps.setString(3, lesson.getSubject());
        ps.setObject(4, lesson.getDateTime());
        ps.setInt(5, lesson.getMaxStudents());
        ps.setString(6, lesson.getStatus().name());

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Insertion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int update(Lesson lesson) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "UPDATE lessons SET tutor_id = ?, subject = ?, date_time = ?, max_students = ?, status = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, lesson.getTutor().getID());
        ps.setString(2, lesson.getSubject());
        ps.setObject(3, lesson.getDateTime());
        ps.setInt(4, lesson.getMaxStudents());
        ps.setString(5, lesson.getStatus().name());
        ps.setInt(6, lesson.getId());

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Update failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Update failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "DELETE FROM lessons WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Deletion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Deletion failed, too many rows affected.");
        }

        return rowsAffected;
    }
}
