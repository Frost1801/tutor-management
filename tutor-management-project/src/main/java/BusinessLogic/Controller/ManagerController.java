package main.java.BusinessLogic.Controller;

import main.java.BusinessLogic.UserController;
import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.UsersDataAccess.ManagerDAO;
import main.java.DomainModel.Users.Candidate;
import main.java.DomainModel.Users.Manager;
import main.java.DomainModel.Users.Tutor;
import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.BusinessLogic.Observer;
import main.java.BusinessLogic.Subject;

public class ManagerController extends UserController implements Subject{

    private ManagerDAO managerDAO;
    private TutorController tutorController;
    private ApplicationDAO applicationDAO;
    private List<Observer> observers = new ArrayList<>();

    public ManagerController(ManagerDAO managerDAO, ApplicationDAO applicationDAO, TutorController tutorController) {
        this.managerDAO = managerDAO;
        this.tutorController = tutorController;
    }


    // Additional methods specific to ManagerController
    public void addManager(String firstName, String lastName, String email, String password, String OTP) {
        try {
            int id = userDAO.getNextId();
            Manager newManager = Manager.getInstance(id, firstName, lastName, email, password, OTP);
    
            // Insert manager into the managers table
            managerDAO.insert(newManager);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

public Map<Integer, Double> getAllDuePayments(int rate) {

    // Your implementation for viewing all due payments
    // Example: Access payment information from the database

    List<Tutor> tutors = tutorController.getAllTutors();
    Map<Integer, Double> duePayments = new HashMap<>();

    for (Tutor tutor : tutors) {
        int tutorId = tutor.getID();
        int hours = tutor.getHours();
        double duePayment = hours * rate;

        duePayments.put(tutorId, duePayment);
    }

    return duePayments;
}


public Map <Integer, Double> getDuePaymentByID(int id, int rate) {
    // Your implementation for viewing due payment by ID
    // Example: Access payment information from the database based on ID

    Tutor tutor = tutorController.getTutor(id);
    Map <Integer, Double> duePayment = new HashMap<>();

    if (tutor != null) {
        int tutorId = tutor.getID();
        int hours = tutor.getHours();
        double due= hours * rate;
        duePayment.put(tutorId, due);
        return duePayment;

    } else {
        throw new IllegalArgumentException("Tutor with ID " + id + " does not exist.");
    }

}


public void sendApplicationResult(int id, ApplicationResult result) {
    try {
        // Your implementation for sending application result
        Application application = applicationDAO.get(id);

        if (application != null) {
            // Update the application result to the input result
            application.setResult(result);
            applicationDAO.update(application);

            // Check if the result is ACCEPTED
            if (result == ApplicationResult.ACCEPTED) {
                // Use TutorController to add a tutor using Candidate constructor
                Candidate candidate = application.getCandidate();

                // Remove candidate from the candidates table

                // Insert tutor into the tutors table

                tutorController.addTutor(candidate);

                // Notify the candidate's observer
                candidate.update();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
