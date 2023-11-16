package main.java.DomainModel.Users;

public class Tutor extends User {

    private String subject; 
    private int hours; 

    // Constructor
    public Tutor(String firstName, String lastName, String email, String password, String subject, int hours ) {
        super(firstName, lastName,  email, password);
        this.subject = subject;
        this.hours = hours;
    }

}
