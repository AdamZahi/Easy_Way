package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.util.MyDataBase;
import tn.esprit.models.Ligne;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceLigne implements IService<Ligne> {
    private Connection cnx;
    public ServiceLigne() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Ligne ligne) {
        String query = "INSERT INTO `ligne` (`depart`, `arret`, `type`) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, ligne.getDep());
            pstm.setString(2, ligne.getArr());
            pstm.setString(3, ligne.getType());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Ligne> getAll() {
        ArrayList<Ligne> lignes = new ArrayList<>();
        String qry ="SELECT * FROM `ligne`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                Ligne l = new Ligne();
                l.setId(rs.getInt("id"));
                l.setDep(rs.getString("depart"));
                l.setArr(rs.getString("arret"));
                l.setType(rs.getString("type"));
                lignes.add(l);
            }
            System.out.println(lignes);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return lignes;
    }

    @Override
    public void update(Ligne ligne) {
        String query = "UPDATE `ligne` SET `depart` = ?, `arret` = ?, `type` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, ligne.getDep());
            pstm.setString(2, ligne.getArr());
            pstm.setString(3, ligne.getType());
            pstm.setInt(4, ligne.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Ligne ligne) {
        String query = "DELETE FROM `ligne` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, ligne.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}