package main.java.BusinessLogic.Controller;


import main.java.DomainModel.Users.Candidate;
import main.java.BusinessLogic.UserController;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;


public class CandidateController implements UserController {

    private CandidateDAO candidateDAO;

    public CandidateController(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    @Override
    public boolean removeUser(int id) {
        // Your implementation for removing a candidate
        return true;
    }

    @Override
    public Candidate getUser(int id) {
        // Your implementation for getting a candidate
        return candidateDAO.get(id);
    }

    @Override
    public Candidate[] getAll() {
        // Your implementation for getting all candidates
        return candidateDAO.getAll().toArray(new Candidate[0]);
    }

    @Override
    public boolean login(String email, String password) {
        // Your implementation for candidate login
        // Example: Check credentials against the database

        return true;
    }

    // Additional methods specific to CandidateController
    public void addCandidate(String firstName, String lastName, String email, String password) {
        // Your implementation for adding a new candidate
        
    }

    public void updateSchool(int id, String school) {
        // Your implementation for updating the school of a candidate
        Candidate candidate = candidateDAO.get(id);
        if (candidate != null) {
    
            candidateDAO.update(candidate);
        }
    }

    public void updateGPA(int id, float avg) {
        // Your implementation for updating the GPA of a candidate
        Candidate candidate = candidateDAO.get(id);
        if (candidate != null) {
            
            candidateDAO.update(candidate);
        }
    }

    public String getSchool(int id) {
        // Your implementation for getting the school of a candidate
        
        return "";
    }

    public float getGPA(int id) {
        // Your implementation for getting the GPA of a candidate
        
        return 0;
    }
}