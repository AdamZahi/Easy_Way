package tn.esprit.services.trajet;


import tn.esprit.interfaces.trajet.IService;
import tn.esprit.models.trajet.Paiement;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePaiement implements IService<Paiement> {

    private Connection cnx;
    public ServicePaiement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Paiement paiement) {
        String query = "INSERT INTO `paiement` (`pay_id`,`montant`) VALUES (?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, paiement.getPay_id());
            pstm.setDouble(2, paiement.getMontant());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Paiement> getAll() {
        ArrayList<Paiement> paiements = new ArrayList<>();
        String qry ="SELECT * FROM `paiement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                Paiement l = new Paiement();
                l.setId(rs.getInt("id"));
                l.setPay_id(rs.getString("pay_id"));
                l.setMontant(rs.getDouble("montant"));
                paiements.add(l);
            }
            System.out.println(paiements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return paiements;
    }

    @Override
    public void update(Paiement paiement) {
        String query = "UPDATE `paiement` SET `pay_id` = ?, `montant` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, paiement.getPay_id());
            pstm.setDouble(2, paiement.getMontant());
            pstm.setInt(3, paiement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Paiement paiement) {
        String query = "DELETE FROM `paiement` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, paiement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public Paiement getById(int id) {
        System.out.println("non implémenté");
        return null;
    }
    public String PaymentDetails(Paiement paiement) {
        return "Paiement Details: \n" +
                "ID: " + paiement.getId() + "\n" +
                "Pay ID: " + paiement.getPay_id() + "\n" +
                "Amount: " + paiement.getMontant();
    }

}
