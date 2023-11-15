package main.java.DomainModel.Users;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private String username;
    private String password;
    private String name;
    private String email;
    // Other common attributes

    private List<Lesson> subscribedLessons; // Assuming a user can subscribe to multiple lessons

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.subscribedLessons = new ArrayList<>();
        // Initialize other common attributes
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Lesson> getSubscribedLessons() {
        return subscribedLessons;
    }

    public void subscribeToLesson(Lesson lesson) {
        subscribedLessons.add(lesson);
    }

    public void unsubscribeFromLesson(Lesson lesson) {
        subscribedLessons.remove(lesson);
    }

    // You can add more methods and attributes as needed
}
