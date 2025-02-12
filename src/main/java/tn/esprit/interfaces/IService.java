package tn.esprit.interfaces;

import java.util.List;
import tn.esprit.models.User;

public interface IService <T>{

    void add(T t);

    List<T> getAll();

    User getById(int id_user);

    void update(T t);

    void delete(T t);

}
