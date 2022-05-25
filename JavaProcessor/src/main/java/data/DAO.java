package data;

import java.util.List;

public interface DAO<T> {

    void insert(T element);

    boolean update(T element);

    boolean delete(T element);

    T getById(int id);

    List<T> getAll();
}
