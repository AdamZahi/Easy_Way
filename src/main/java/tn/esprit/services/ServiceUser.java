package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user.User;
import tn.esprit.util.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;
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
//                Timestamp timestamp = rs.getTimestamp("date_creation_compte");
//                LocalDateTime dateCreation = timestamp.toLocalDateTime();

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
                      //  rs.getInt(1,id_user),
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
        String query = "UPDATE user SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ? ,photo_profil = ? WHERE id_user = ?";

        try {

            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());
            pstm.setInt(7, user.getId_user()); // Condition WHERE

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

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        User user = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                user = new User(
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

    public boolean updatePassword(int id_user, String mot_de_passe) {
        String hashedPassword = BCrypt.hashpw(mot_de_passe, BCrypt.gensalt()); // Hachage du mot de passe
        String query = "UPDATE user SET mot_de_passe = ? WHERE id_user = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, id_user);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mot de passe mis à jour pour l'utilisateur avec ID: " + id_user);
            } else {
                System.out.println("Aucune mise à jour effectuée. ID utilisateur incorrect ?");
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la mise à jour du mot de passe:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        String query = "UPDATE user SET mot_de_passe = ? WHERE email = ?";

        try (Connection conn = MyDataBase.getInstance().getCnx();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, newPassword);
            pstm.setString(2, email);

            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Mot de passe mis à jour pour l'email : " + email);
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email : " + email);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            return false;
        }
    }
    public int getUserIdByEmail(String email) {
        System.out.println("Recherche de l'ID pour l'email : " + email);

        String query = "SELECT id_user FROM user WHERE email = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_user");
                System.out.println("ID trouvé : " + id);
                return id;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return -1;
    }





}
