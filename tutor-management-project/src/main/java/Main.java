package main.java;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import main.java.BusinessLogic.Controller.StudentController;
import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.LessonDAO;
import main.java.DataAccess.LessonStudentDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DataAccess.UsersDataAccess.ManagerDAO;
import main.java.DataAccess.UsersDataAccess.StudentDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DomainModel.Application;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Users.Candidate;
import main.java.DomainModel.Users.Manager;
import main.java.DomainModel.Users.Student;
import main.java.DomainModel.Users.Tutor;


public class Main {
    public static void main(String[] args) {
        
        Student test = new Student(111, "Mario", "Doe", "te1s1@gmail.com", "password", 1, 'A');
        StudentDAO studentDAO = new StudentDAO();
        //Tutor test2 = new Tutor(215, "Ciles", "Doe", "tefst@hgaha.com", "password", "Meth");
        TutorDAO tutorDAO = new TutorDAO();
        //Candidate test = new Candidate(51, "Mkik,e", "Bungiognri", "ahhhh@g.com", "password", 3.5);
        CandidateDAO candidateDAO = new CandidateDAO();
        //Manager test = Manager.getInstance(87558, "Ciruzzo", "Esposito", "laziocapitale@gmail.cum", "lamaggica", "1323");
        ManagerDAO managerDAO = new ManagerDAO();
        //test = Manager.getInstance(766, "Gianmacco", "Akkappaqarantasetti", "fortnite@gmail.cum", "password", "246");
        ApplicationDAO applicationDAO = new ApplicationDAO();
        LessonDAO lessonDAO = new LessonDAO();
        

        

        try {
            StudentController studentController = new StudentController(studentDAO);
            studentController.addStudent("Mario", "Doe", "te1@gmail.com", "password",  'A', 1);

            List <Student> students = studentDAO.getAll();
            for (Student student : students) {
                System.out.println(student);
            }

            //tutorDAO.insert(test2);
            //candidateDAO.insert(test);
            //managerDAO.insert(test);
            //Application apptest = new Application(10, candidateDAO.get(131), "Slaying");
            Lesson l = new Lesson(1, "Math", tutorDAO.get(212), LocalDateTime.now(), 5);

            //applicationDAO.insert(apptest);
            //lessonDAO.insert(l);

            
            List<Tutor> tutors = tutorDAO.getAll();
            for (Tutor tutor : tutors) {
                System.out.println(tutor);
            }
            List<Candidate> candidates = candidateDAO.getAll();
            for (Candidate candidate : candidates) {
                System.out.println(candidate);
            }

            List<Manager> managers = managerDAO.getAll();
            for (Manager manager : managers) {
                System.out.println(manager);
            }

            List<Application> applications = applicationDAO.getAll();
            for (Application application : applications) {
                System.out.println(application);
            }

            List<Lesson> lessons = lessonDAO.getAll();
            for (Lesson lesson : lessons) {
                System.out.println(lesson);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    
    
    }
}