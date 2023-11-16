package main.java.DomainModel;

import main.java.DomainModel.Users.Candidate;

public class Application {
    private Candidate applicant;
    private String subject;
    private ApplicationResult result;

    // Constructor
    public Application(Candidate applicant, String subject) {
        this.applicant = applicant;
        this.subject = subject;
        this.result = ApplicationResult.PENDING; // Default status is pending
    }

    public void setResult(ApplicationResult result) {
        this.result = result;
    }

}