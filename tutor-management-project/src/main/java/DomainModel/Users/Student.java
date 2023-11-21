package main.java.DomainModel.Users;

import main.java.BusinessLogic.Observer;

public class Student extends User implements Observer {
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

    @Override
    public void update() {
        System.out.println("Student " + this.getFirstName() + " " + this.getLastName() + " has been notified.");
    }

    public void setYear(int year) {
        this.year = year;
    }

}
