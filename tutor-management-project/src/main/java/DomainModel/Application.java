package main.java.DomainModel;

import main.java.DomainModel.Users.Candidate;

public class Application {
    private int id; 
    private Candidate applicant;
    private String subject;
    private ApplicationResult result;

    // Constructor
    public Application(int id, Candidate applicant, String subject) {
        this.id = id;
        this.applicant = applicant;
        this.subject = subject;
        this.result = ApplicationResult.PENDING; // Default status is pending
    }

    public void setResult(ApplicationResult result) {
        this.result = result;
    }

    public ApplicationResult getResult() {
        return result;
    }
    public int getId() {
        return id;
    }
    public Candidate getCandidate() {
        return applicant;
    }
    public String getSubject() {
        return subject;
    }

}