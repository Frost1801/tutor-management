package main.java.DomainModel.Users;

public class Student extends User {
    private int year;
    private char section;

    public Student(int id, String firstName, String lastName, String email, String password, int year, char section) {
        super(id, firstName, lastName, email, password);
        this.year = year;
        this.section = section;
    }

    // Getters specific to Student
    public int getYear() {
        return year;
    }

    public char getSection() {
        return section;
    }
}
