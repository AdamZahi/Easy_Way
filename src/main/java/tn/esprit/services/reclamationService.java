package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.reclamations;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reclamationService implements IService<reclamations> {

    private Connection connection = MyDataBase.getInstance().getConnection();

    @Override
    public void add(reclamations reclamation) {
        String req = "INSERT INTO reclamation (categorieId, email, sujet, description, statu, date_creation) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, reclamation.getCategorieId());
            pst.setString(2, reclamation.getEmail());
            pst.setString(3, reclamation.getSujet());
            pst.setString(4, reclamation.getDescription());
            pst.setString(5, reclamation.getStatu());
            pst.setString(6, reclamation.getDate_creation());
            pst.executeUpdate();
            System.out.println("Réclamation ajoutée");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void update(reclamations reclamation) {
        String req = "UPDATE reclamation SET categorieId=?, email=?, sujet=?, description=?, statu=?, date_creation=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, reclamation.getCategorieId());
            pst.setString(2, reclamation.getEmail());
            pst.setString(3, reclamation.getSujet());
            pst.setString(4, reclamation.getDescription());
            pst.setString(5, reclamation.getStatu());
            pst.setString(6, reclamation.getDate_creation());
            pst.setInt(7, reclamation.getId());
            int rowsUpdated = pst.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "Réclamation modifiée" : "Aucune réclamation trouvée");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void delete(reclamations reclamation) {
        String req = "DELETE FROM reclamation WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, reclamation.getId());
            int rowsDeleted = pst.executeUpdate();
            System.out.println(rowsDeleted > 0 ? "Réclamation supprimée" : "Aucune réclamation trouvée");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public List<reclamations> getAll() {
        List<reclamations> reclamationsList = new ArrayList<>();
        String req = "SELECT * FROM reclamation";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                reclamationsList.add(new reclamations(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("categorieId"),
                        rs.getString("sujet"),
                        rs.getString("statu"),
                        rs.getString("description"),
                        rs.getString("date_creation")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réclamations : " + e.getMessage());
        }
        return reclamationsList;
    }

    @Override
    public reclamations getById(int id) {
        String req = "SELECT * FROM reclamation WHERE id = ?";
        reclamations reclamation = null;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                reclamation = new reclamations(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("categorieId"),
                        rs.getString("sujet"),
                        rs.getString("statu"),
                        rs.getString("description"),
                        rs.getString("date_creation")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la réclamation : " + e.getMessage());
        }
        return reclamation;
    }
}
