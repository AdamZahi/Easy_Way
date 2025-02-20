package tn.esprit.services;


import tn.esprit.interfaces.IService;
import tn.esprit.util.MyDataBase;
import tn.esprit.models.Payement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePayement implements IService<Payement> {

    private Connection cnx;
    public ServicePayement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Payement payement) {
        String query = "INSERT INTO `payement` (`mode`, `montant`) VALUES (?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, payement.getMode());
            pstm.setFloat(2, payement.getMontant());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Payement> getAll() {
        ArrayList<Payement> payements = new ArrayList<>();
        String qry ="SELECT * FROM `payement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                Payement l = new Payement();
                l.setId(rs.getInt("id"));
                l.setMode(rs.getString("mode"));
                l.setMontant(rs.getFloat("montant"));
                payements.add(l);
            }
            System.out.println(payements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return payements;
    }

    @Override
    public void update(Payement payement) {
        String query = "UPDATE `payement` SET `mode` = ?, `montant` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, payement.getMode());
            pstm.setFloat(2, payement.getMontant());
            pstm.setInt(3, payement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Payement payement) {
        String query = "DELETE FROM `payement` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, payement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
