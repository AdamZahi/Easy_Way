package tn.esprit.interfaces;

import java.util.ArrayList;
import java.util.List;
import tn.esprit.models.User;

public interface IService <T>{

    void add(T t);
    List<T> getAll();
    T getById(int id);
    void update(T t);
    void delete(T t);
}
