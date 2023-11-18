package main.java;
import main.java.DomainModel.Users.Student;
import main.java.DataAccess.StudentDAO;

public class Main {
    public static void main(String[] args) {
        Student test = new Student(0, "John", "Doe", "test@gmail.com", "password", 1, 'A');
        StudentDAO studentDAO = new StudentDAO();

        int id = 0;
        try {
            id = studentDAO.insert(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(id);


    }
}