package main.java.DomainModel.Users;

public class Tutor extends User {

    private String subject; 
    private int hours; 

    // Constructor
    public Tutor(int id, String firstName, String lastName, String email, String password, String subject, int hours ) {
        super(id, firstName, lastName,  email, password);
        this.subject = subject;
        this.hours = hours;
    }

    //Default hours is zero
    public Tutor(int id, String firstName, String lastName, String email, String password, String subject) {
        super(id, firstName, lastName,  email, password);
        this.subject = subject;
        this.hours = 0;
    }

    public String getSubject() {
        return subject;
    }

    public int getHours() {
        return hours;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
