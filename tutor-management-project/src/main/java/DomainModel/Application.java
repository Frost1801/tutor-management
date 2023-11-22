package main.java.DomainModel;

import main.java.DomainModel.Users.Candidate;

public class Application {
    private int id; 
    private String subject;
    private ApplicationResult result;
    private Candidate candidate;
    // Constructor
    public Application(int id, Candidate applicant, String subject) {
        this.id = id;
        this.candidate = applicant;
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
        return candidate;
    }
    public String getSubject() {
        return subject;
    }

}