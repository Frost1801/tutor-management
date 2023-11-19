package main.java.BusinessLogic.Controller;

import main.java.DomainModel.Users.Tutor;
import main.java.DomainModel.Users.User;
import main.java.DomainModel.Users.Candidate;
import main.java.BusinessLogic.UserController;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DataAccess.UsersDataAccess.TutorDAO;
import main.java.DataAccess.UsersDataAccess.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class TutorController extends UserController {

    private TutorDAO tutorDAO;
    private CandidateDAO candidateDAO;

    public TutorController(TutorDAO tutorDAO) {
        this.tutorDAO = tutorDAO;
    }

    // Additional methods specific to TutorController
    public void addTutor(String firstName, String lastName, String email, String password, String subject, int hours) {
        try {
            int id = userDAO.getNextId();
            Tutor newTutor = new Tutor(id, firstName, lastName, email, password, subject, hours);
            tutorDAO.insert(newTutor);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    //when a candidate is upgraded to tutor
    public void addTutor(Candidate candidate) {
        try {
            // Get candidate details
            int id = candidate.getID();
            String firstName = candidate.getFirstName();
            String lastName = candidate.getLastName();
            String email = candidate.getEmail();
            String password = candidate.getPassword();

            // Remove candidate from the candidates table
            candidateDAO.delete(id);

            // Insert tutor into the tutors table
            Tutor tutor = new Tutor(id, firstName, lastName, email, password, ""); 
            tutorDAO.insert(tutor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseLessonHours(int id, int hours) {
        try {
            Tutor tutor = tutorDAO.get(id);
            if (tutor != null) {
                //increase hours
                tutor.setHours(tutor.getHours() + hours);
                tutorDAO.update(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public Tutor getTutor(int id) {
        try {
            return tutorDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public List<Tutor> getAllTutors() {
        try {
            return tutorDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null; 
        }
    }
}
