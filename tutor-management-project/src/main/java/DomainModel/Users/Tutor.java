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

    public String getSubject() {
        return null;
    }

    public int getHours() {
        return 0;
    }

}
