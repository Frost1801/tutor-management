package main.java.DataAccess;

import java.util.List;

// DAO.java
public interface DAO<T> {
    T get(int id);

    List<T> getAll();

    int insert(T t);

    int update(T t);

    int delete(T t);
}
