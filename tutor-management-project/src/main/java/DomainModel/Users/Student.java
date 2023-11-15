package main.java.DomainModel.Users;

public class Student extends User {
    private String studentId;
    private String degreeProgram;
    private double GPA; // Grade Point Average

    public Student(String username, String password, String name, String email, String studentId, String degreeProgram, double GPA) {
        super(username, password, name, email);
        this.studentId = studentId;
        this.degreeProgram = degreeProgram;
        this.GPA = GPA;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public double getGPA() {
        return GPA;
    }

    // You can add more methods and attributes as needed
}
