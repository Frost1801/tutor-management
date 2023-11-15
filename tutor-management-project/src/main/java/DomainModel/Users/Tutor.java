public class Tutor extends User {

    private String degree;
    private double averageRating;

    // Constructor
    public Tutor(String username, String password, String email, String degree) {
        super(username, password, email);
        this.degree = degree;
        this.averageRating = 0.0; // Default rating
    }

    // Getter and Setter methods for additional attributes

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    // Additional methods specific to Tutor class can be added

    // Override toString() method to provide a meaningful string representation
    @Override
    public String toString() {
        return "Tutor{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", degree='" + degree + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
