package main.java.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonStudentDAO {

    public boolean bookLesson(int studentId, int lessonId) throws SQLException{
        Connection connection = Database.getConnection();

        // Check if the lesson has available spots
        if (getRemainingSpots(lessonId) > 0) {
            String sqlQueryText = "INSERT INTO student_lessons (student_id, lesson_id) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlQueryText);

            ps.setInt(1, studentId);
            ps.setInt(2, lessonId);

            int rowsAffected = ps.executeUpdate();

            ps.close();
            connection.close();

            return rowsAffected > 0;
        } else {
            return false; // No available spots
        }
    }

    public boolean cancelBooking(int studentId, int lessonId) throws SQLException{

        Connection connection = Database.getConnection();

        String sqlQueryText = "DELETE FROM student_lessons WHERE student_id = ? AND lesson_id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, studentId);
        ps.setInt(2, lessonId);

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        return rowsAffected > 0;
    }

    public int getRemainingSpots(int lessonId) throws SQLException{
        Connection connection = Database.getConnection();

        String sqlQueryText = "SELECT max_students - COUNT(*) AS remaining_spots FROM lessons " +
                                "LEFT JOIN student_lessons ON lessons.id = student_lessons.lesson_id " +
                                "WHERE lessons.id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, lessonId);

        ResultSet rs = ps.executeQuery();

        int remainingSpots = 0;
        if (rs.next()) {
            remainingSpots = rs.getInt("remaining_spots");
        }

        rs.close();
        ps.close();
        connection.close();

        return remainingSpots;
    }
}
