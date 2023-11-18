package main.java.DomainModel.Users;

import java.util.ArrayList;
import java.util.List;

import main.java.DomainModel.Lesson;

public abstract class User {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    


    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
