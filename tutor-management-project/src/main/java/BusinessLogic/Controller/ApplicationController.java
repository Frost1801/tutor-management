package main.java.BusinessLogic.Controller;

import java.sql.SQLException;

import main.java.DataAccess.ApplicationDAO;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;
import main.java.DomainModel.Users.Candidate;

public class ApplicationController {

    private ApplicationDAO applicationDAO;
    private CandidateDAO candidateDAO;

    public ApplicationController(ApplicationDAO applicationDAO, CandidateDAO candidateDAO) {
        this.applicationDAO = applicationDAO;
        this.candidateDAO = candidateDAO;
    }

    public void addApplication(int candidateID, String subject, String school, float avg) {
        try {
            int id = applicationDAO.getNextId();
            Candidate candidate = candidateDAO.get(candidateID);
            if (candidate != null) {
                Application newApplication = new Application(id, candidate, subject);
                applicationDAO.insert(newApplication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ApplicationResult viewStatus(int applicationID) {
        try {
            // Your implementation for viewing the status of an application
            Application application = applicationDAO.get(applicationID);
            return (application != null) ? application.getResult() : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
