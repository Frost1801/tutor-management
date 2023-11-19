package main.java;
import java.sql.SQLException;
import java.util.List;

import main.java.DataAccess.UsersDataAccess.StudentDAO;
import main.java.DomainModel.Users.Student;

public class Main {
    public static void main(String[] args) {
        Student test = new Student(1, "John", "Doe", "tes1@gmail.com", "password", 1, 'A');
        StudentDAO studentDAO = new StudentDAO();
        
        try {
            studentDAO.insert(test);
            List<Student> students = studentDAO.getAll();
            for (Student student : students) {
                System.out.println(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}