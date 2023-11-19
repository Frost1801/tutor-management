package main.java.DataAccess;

import java.sql.SQLException;
import java.util.List;

// DAO.java
//CRUD - Create Read Update Delete
public interface DAO<T> {
    public T get(int id) throws SQLException;

    public List<T> getAll() throws SQLException;

    public int insert(T t) throws SQLException;

    public int update(T t) throws SQLException;

    public int delete(int id) throws SQLException;

    /* 
     * TODO 
     * Fare un UserDAO generale che si occupa di inserire i parametri comuni a tutti gli utenti
     * Fixare l'id degli user in modo che combaci in tutte le tabelle che li rigaurdano
     * 
     */
}
