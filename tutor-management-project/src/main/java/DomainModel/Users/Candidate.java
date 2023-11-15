package main.java.DomainModel.Users;

public class Candidate extends User {

    private String degree;
    private double GPA;

    // Constructor
    public Candidate(String username, String password, String email, String degree, double GPA) {
        super(username, password, email);
        this.degree = degree;
        this.GPA = GPA;
    }

    // Getter and Setter methods for additional attributes

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    // Additional methods specific to Candidate class can be added

    // Override toString() method to provide a meaningful string representation
    @Override
    public String toString() {
        return "Candidate{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", degree='" + degree + '\'' +
                ", GPA=" + GPA +
                '}';
    }
}
