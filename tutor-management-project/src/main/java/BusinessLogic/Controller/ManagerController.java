package main.java.BusinessLogic.Controller;

import main.java.BusinessLogic.UserController;
import main.java.DataAccess.UsersDataAccess.ManagerDAO;
import main.java.DomainModel.Users.Manager;
import main.java.DomainModel.ApplicationResult;

public class ManagerController implements UserController {

    private ManagerDAO managerDAO;

    public ManagerController(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    @Override
    public boolean removeUser(int id) {
        // Your implementation for removing a manager
        return true;
    }

    @Override
    public Manager getUser(int id) {
        // Your implementation for getting a manager
        return managerDAO.get(id);
    }

    @Override
    public Manager[] getAll() {
        // Your implementation for getting all managers
        return managerDAO.getAll().toArray(new Manager[0]);
    }

    @Override
    public boolean login(String email, String password) {
        // Your implementation for login
        return true;
    }

    // Additional methods specific to ManagerController
    public void addManager(String firstName, String lastName, String email, String password, String OTP) {
        // Your implementation for adding a new manager
        
        // Set additional attributes like OTP
        
    }

    public void viewAllDuePayments() {
        // Your implementation for viewing all due payments
        // Example: Access payment information from the database
    }

    public void viewDuePaymentByID(int id) {
        // Your implementation for viewing due payment by ID
        // Example: Access payment information from the database based on ID
    }

    public void sendApplicationResult(int id, ApplicationResult result) {
        // Your implementation for sending application result
        // Example: Update application result in the database
    }
}
