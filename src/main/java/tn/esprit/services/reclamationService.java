package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.categories;
import tn.esprit.models.reclamations;
import tn.esprit.util.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reclamationService implements IService<reclamations> {

    private Connection connection = MyDataBase.getInstance().getConnection();

    @Override
    public void add(reclamations reclamation) {
        // Récupérer la catégorie depuis la base de données
        categorieService cs = new categorieService();
        categories categorie = cs.getById(reclamation.getCategorie().getId());

        if (categorie == null) {
            System.out.println("Catégorie introuvable !");
            return;
        }

        String req = "INSERT INTO reclamation (categorieId, email, sujet, description, statu, date_creation) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, categorie.getId()); // Utilisation de l'ID de la catégorie récupérée
            pst.setString(2, reclamation.getEmail());
            pst.setString(3, reclamation.getSujet());
            pst.setString(4, reclamation.getDescription());
            pst.setString(5, reclamation.getStatu());
            pst.setString(6, reclamation.getDate_creation());
            pst.executeUpdate();
            System.out.println("Réclamation ajoutée avec la catégorie : " + categorie.getType());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }


    @Override
    public void update(reclamations reclamation) {
        String req = "UPDATE reclamation SET categorieId=?, email=?, sujet=?, description=?, statu=?, date_creation=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, reclamation.getCategorie().getId()); // Utilisation de l'objet categorie
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
        String req = "SELECT r.id, r.email, r.sujet, r.description, r.statu, r.date_creation, " +
                "c.id AS categorieId, c.type AS categorieType " +
                "FROM reclamation r JOIN categories c ON r.categorieId = c.id";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                categories cat = new categories(rs.getInt("categorieId"), rs.getString("categorieType"));
                reclamationsList.add(new reclamations(
                        rs.getInt("id"),
                        rs.getString("email"),
                        cat,
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
        String req = "SELECT r.id, r.email, r.sujet, r.description, r.statu, r.date_creation, " +
                "c.id AS categorieId, c.type AS categorieType " +
                "FROM reclamation r JOIN categories c ON r.categorieId = c.id WHERE r.id = ?";
        reclamations reclamation = null;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                categories cat = new categories(rs.getInt("categorieId"), rs.getString("categorieType"));
                reclamation = new reclamations(
                        rs.getInt("id"),
                        rs.getString("email"),
                        cat,
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
