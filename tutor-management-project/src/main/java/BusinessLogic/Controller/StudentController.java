package main.java.BusinessLogic.Controller;

import main.java.DomainModel.Users.Student;

import java.sql.SQLException;

import main.java.BusinessLogic.UserController;
import main.java.DataAccess.UsersDataAccess.StudentDAO;

public class StudentController extends UserController {

    private StudentDAO studentDAO;

    public StudentController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void addStudent(String firstName, String lastName, String email, String password, char section, int year) {
        try {
            // Check if the email is a valid institutional email
            if (isValidInstitutionalEmail(email)) {
                int id = userDAO.getNextId();
                Student newStudent = new Student(id, firstName, lastName, email, password, year, section);
                studentDAO.insert(newStudent);
            } else {
                throw new IllegalArgumentException("Error: Invalid institutional email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInstitutionalEmail(String email) {
        return email.endsWith("@russell-newton.edu.it");
    }
}
