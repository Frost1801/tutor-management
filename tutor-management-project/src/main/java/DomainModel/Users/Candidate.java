package main.java.DomainModel.Users;
import main.java.BusinessLogic.Observer;

public class Candidate extends User implements Observer{

    private double GPA;
    

    // Constructor
    public Candidate(int id, String firstName, String lastName, String email, String password, double GPA) {
        super(id, firstName, lastName,  email, password);
        this.GPA = GPA;
    }

    public double getGPA() {
        return GPA;
    }

    @Override
    public void update() {
        System.out.println("Candidate " + this.getFirstName() + " " + this.getLastName() + " has been upgraded to tutor");
    }
}
