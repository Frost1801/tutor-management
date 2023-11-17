package main.java.BusinessLogic.Controller;

import main.java.DataAccess.StudentDAO; // Assuming the existence of StudentDAO class
import main.java.DomainModel.Users.Student;
import main.java.BusinessLogic.UserController;

public class StudentController implements UserController {

    private StudentDAO studentDAO;

    public StudentController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean removeUser(int id) {
        // Your implementation for removing a student
        return true;
    }

    @Override
    public Student getUser(int id) {
        // Your implementation for getting a student
        return studentDAO.get(id);
    }

    @Override
    public Student[] getAll() {
        // Your implementation for getting all students
        return studentDAO.getAll().toArray(new Student[0]);
    }

    @Override
    public boolean login(String email, String password) {
        // Your implementation for student login
        // Example: Check credentials against the database
        return true;
    }

    // Additional method specific to StudentController
    public void addStudent(String firstName, String lastName, String email, String password, char section, int year) {
        // Your implementation for adding a new student
        Student newStudent = new Student(firstName, lastName, email, password, year, section);
        studentDAO.insert(newStudent);
    }
}
