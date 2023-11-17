package main.java.BusinessLogic;

import main.java.DomainModel.Users.User;

public interface UserController {

    public abstract boolean removeUser(int id);

    public abstract User getUser(int id);

    public abstract User[] getAll();

    public abstract boolean login(String email, String password);
}
