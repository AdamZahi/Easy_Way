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
        String query = "INSERT INTO `user`(`nom`, `prenom`, `email`, `mot_de_passe`, `telephonne` , `photo_profil`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());

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
                u.setId_user(rs.getInt("id_user"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
                u.setTelephonne(rs.getInt("telephonne"));
                u.setPhoto_profil(rs.getString("photo_profil"));

                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return users;
    }

    @Override
    public User getById(int id_user) {
        String query = "SELECT * FROM user WHERE id_user = ?";
        User user = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_user);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {

                user = new User(
                        //rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return user;
    }


    @Override
    public void update(User user) {
        String query = "UPDATE user SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ? ,photo_profil = ? , role = ? WHERE id_user = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());

            pstm.setInt(8, user.getId_user()); // Condition WHERE

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'utilisateur avec ID " + user.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + user.getId_user());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM user WHERE id_user = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, user.getId_user()); // Suppression basée sur l'ID de l'utilisateur

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'utilisateur avec ID " + user.getId_user() + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + user.getId_user());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

}
