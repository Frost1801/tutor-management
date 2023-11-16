package main.java.DomainModel.Users;

public class Student extends User {
    private String firstName;
    private String lastName;
    private String email;
    private int year;
    private char section;

    public Student(String firstName, String lastName, String email, String password, int year, char section) {
        super(firstName, lastName,  email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.year = year;
        this.section = section;
    }

}
