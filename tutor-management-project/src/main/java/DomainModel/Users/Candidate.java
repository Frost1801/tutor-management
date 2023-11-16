package main.java.DomainModel.Users;

public class Candidate extends User {

    private double GPA;
    

    // Constructor
    public Candidate(String firstName, String lastName, String email, String password, double GPA) {
        super(firstName, lastName,  email, password);
        this.GPA = GPA;
    }
}
