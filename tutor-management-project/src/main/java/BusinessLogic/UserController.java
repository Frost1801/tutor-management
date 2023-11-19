package main.java.BusinessLogic;

import java.sql.SQLException;
import java.util.List;

import main.java.DataAccess.UsersDataAccess.UserDAO;
import main.java.DomainModel.Users.User;

public class UserController {

    protected UserDAO userDAO;

    public boolean removeUser(int id){
        try {
            return userDAO.delete(id) > 0;
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public User getUser(int id){
        try {
            return userDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public List<User> getAll(){
        try {
            return userDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        }
    }
        
    public boolean login(String email, String password) {
        try{
            User user = userDAO.getByEmail(email);
            return user != null && user.getPassword().equals(password);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
       
}
