package main.java.BusinessLogic.Controller;

import main.java.DataAccess.TutorDAO; // Assuming the existence of TutorDAO class
import main.java.DomainModel.Users.Tutor;
import main.java.DomainModel.Users.Candidate;
import main.java.BusinessLogic.UserController;

public class TutorController implements UserController {

    private TutorDAO tutorDAO;

    public TutorController(TutorDAO tutorDAO) {
        this.tutorDAO = tutorDAO;
    }

    @Override
    public boolean removeUser(int id) {
        // Your implementation for removing a tutor
        return true; 
    }

    @Override
    public Tutor getUser(int id) {
        // Your implementation for getting a tutor
        return tutorDAO.get(id);
    }

    @Override
    public Tutor[] getAll() {
        // Your implementation for getting all tutors
        return tutorDAO.getAll().toArray(new Tutor[0]);
    }

    @Override
    public boolean login(String email, String password) {
        // Your implementation for tutor login
        // Example: Check credentials against the database
        return true;
    }

    // Additional methods specific to TutorController
    public void addTutor(String firstName, String lastName, String email, String password, String subject, int hours) {
        // Your implementation for adding a new tutor
        Tutor newTutor = new Tutor(firstName, lastName, email, password, subject, hours);
        tutorDAO.insert(newTutor);
    }

    public void addTutor(Candidate candidate) {
        // Your implementation for adding a tutor from a candidate
        // Example: Convert candidate to tutor and add to the database
    }

    public void increaseLessonHours(int id, int hours) {
        // Your implementation for increasing lesson hours for a tutor
        Tutor tutor = tutorDAO.get(id);
        if (tutor != null) {
            tutorDAO.update(tutor);
        }
    }

    public Tutor getTutor(int id) {
        // Your implementation for getting a tutor
        return tutorDAO.get(id);
    }
}
