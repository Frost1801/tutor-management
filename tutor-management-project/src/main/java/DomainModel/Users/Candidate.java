package main.java.DomainModel.Users;

public class Candidate extends User {

    private double GPA;
    

    // Constructor
    public Candidate(int id, String firstName, String lastName, String email, String password, double GPA) {
        super(id, firstName, lastName,  email, password);
        this.GPA = GPA;
    }

    public double getGPA() {
        return GPA;
    }
}
