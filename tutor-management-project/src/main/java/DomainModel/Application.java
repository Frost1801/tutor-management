package main.java.DomainModel;

public class Application {
    private User applicant;
    private String subject;
    private ApplicationResult result;

    // Constructor
    public Application(User applicant, String subject) {
        this.applicant = applicant;
        this.subject = subject;
        this.result = ApplicationResult.PENDING; // Default status is pending
    }

    // Getter and Setter methods

    public User getApplicant() {
        return applicant;
    }

    public String getSubject() {
        return subject;
    }

    public ApplicationResult getResult() {
        return result;
    }

    public void setResult(ApplicationResult result) {
        this.result = result;
    }

    // Additional methods if needed

    @Override
    public String toString() {
        return "Application{" +
                "applicant=" + applicant +
                ", subject='" + subject + '\'' +
                ", result=" + result +
                '}';
    }
}