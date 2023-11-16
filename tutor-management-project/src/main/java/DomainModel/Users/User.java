package main.java.DomainModel.Users;

import java.util.ArrayList;
import java.util.List;

import main.java.DomainModel.Lesson;

public abstract class User {
    private String firstName;
    private String lastName;
    private String password;
    private String email;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
