package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.util.MyDataBase;
import tn.esprit.models.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection cnx;

    public ServiceUser() {
        cnx = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO user (nom, prenom, email, mot_de_passe, telephonne, photo_profil, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());
            pstm.setString(7, user.getRole().name());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String qry = "SELECT * FROM user";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        Role.fromString(rs.getString("role"))
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM user WHERE id_user = ?";
        User user = null;
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        Role.fromString(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        String query = "UPDATE user SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ?, photo_profil = ?, role = ? WHERE id_user = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());
            pstm.setString(7, user.getRole().name());
            pstm.setInt(8, user.getId_user()); // Correction index
            int rowsUpdated = pstm.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "Utilisateur mis à jour" : "Aucun utilisateur trouvé");
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM user WHERE id_user = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, user.getId_user());
            int rowsDeleted = pstm.executeUpdate();
            System.out.println(rowsDeleted > 0 ? "Utilisateur supprimé" : "Aucun utilisateur trouvé");
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
