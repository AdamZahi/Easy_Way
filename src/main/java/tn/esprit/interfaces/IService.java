package tn.esprit.interfaces;

import java.util.List;

public interface IService <T>{

    void add(T t);

    List<T> getAll();

    //User getById(int id_user);

    void update(T t);

    void delete(T t);

}
