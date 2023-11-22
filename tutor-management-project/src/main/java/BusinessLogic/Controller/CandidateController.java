package main.java.BusinessLogic.Controller;


import main.java.DomainModel.Users.Candidate;

import java.sql.SQLException;

import main.java.BusinessLogic.UserController;
import main.java.DataAccess.UsersDataAccess.CandidateDAO;


public class CandidateController extends UserController {

    private CandidateDAO candidateDAO;

    public CandidateController(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    public void addCandidate(String firstName, String lastName, String email, String password,double GPA ) {
        try {
            int id = userDAO.getNextId();
            Candidate newCandidate = new Candidate(id, firstName, lastName, email, password, GPA);
            
            // Insert candidate into the candidates table
            candidateDAO.insert(newCandidate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Candidate getCandidate(int id) {
        try {
            return candidateDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}