package main.java.DomainModel.Users;

public class Manager extends User {

    private String department;

    // Constructor
    public Manager(String username, String password, String email, String department) {
        super(username, password, email);
        this.department = department;
    }

    // Getter and Setter methods for additional attributes

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Additional methods specific to Manager class can be added

    // Override toString() method to provide a meaningful string representation
    @Override
    public String toString() {
        return "Manager{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
