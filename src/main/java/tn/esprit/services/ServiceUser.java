package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection cnx;
    public ServiceUser() {
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(User user) {
        String query = "INSERT INTO `user`(`nom`, `prenom`, `age`) VALUES ("+user.getName()+","+user.getLastname()+","+user.getAge()+")";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getLastname());
            pstm.setInt(3, user.getAge());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String qry ="SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("nom"));
                u.setLastname(rs.getString("prenom"));
                u.setAge(rs.getInt("age"));
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return users;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
