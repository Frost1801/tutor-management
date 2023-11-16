package main.java.DomainModel.Users;

public class Manager extends User {
    private static Manager instance;

    private Manager(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    public static Manager getInstance(String firstName, String lastName, String email, String password) {
        if (instance == null) {
            instance = new Manager(firstName, lastName, email, password);
        }
        return instance;
    }
}
