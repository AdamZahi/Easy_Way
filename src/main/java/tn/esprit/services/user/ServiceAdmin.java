package tn.esprit.services.user;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user.Admin;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService<Admin> {
    private Connection cnx;

    public ServiceAdmin() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void add(Admin admin) {
        Connection cnx = null;
        PreparedStatement pstmUser = null;
        PreparedStatement pstmAdmin = null;
        ResultSet generatedKeys = null;

        try {
            cnx = MyDataBase.getInstance().getCnx();
            cnx.setAutoCommit(false);

            // Vérification des champs obligatoires
            if (admin.getNom() == null || admin.getNom().isEmpty() ||
                    admin.getPrenom() == null || admin.getPrenom().isEmpty() ||
                    admin.getEmail() == null || admin.getEmail().isEmpty() ||
                    admin.getMot_de_passe() == null || admin.getMot_de_passe().isEmpty()) {
                System.out.println("❌ Un champ obligatoire est vide.");
                return;
            }

            // Vérifier si l'utilisateur existe déjà
            String checkQuery = "SELECT id_user FROM user WHERE email = ?";
            pstmUser = cnx.prepareStatement(checkQuery);
            pstmUser.setString(1, admin.getEmail());
            ResultSet rs = pstmUser.executeQuery();

            int id_user = -1;

            if (rs.next()) {
                id_user = rs.getInt("id_user");
                System.out.println("ℹ️ Utilisateur déjà existant avec ID : " + id_user);
            } else {
                // Insertion dans user avec rôle ADMIN
                String queryUser = "INSERT INTO user (nom, prenom, email, password, telephonne, photo_profil, roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmUser = cnx.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
                pstmUser.setString(1, admin.getNom());
                pstmUser.setString(2, admin.getPrenom());
                pstmUser.setString(3, admin.getEmail());
                pstmUser.setString(4, admin.getMot_de_passe());
                pstmUser.setInt(5, admin.getTelephonne());
                pstmUser.setString(6, admin.getPhoto_profil() != null ? admin.getPhoto_profil() : "default_profile.png");
                pstmUser.setString(7, "[\"ROLE_ADMIN\"]");

                int affectedRows = pstmUser.executeUpdate();
                if (affectedRows == 0) {
                    System.out.println("❌ Insertion user échouée.");
                    cnx.rollback();
                    return;
                }

                generatedKeys = pstmUser.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id_user = generatedKeys.getInt(1);
                    System.out.println("✅ Utilisateur admin créé avec ID : " + id_user);
                } else {
                    System.out.println("❌ ID user non généré.");
                    cnx.rollback();
                    return;
                }
            }

            // Insertion dans la table admin
            String queryAdmin = "INSERT INTO admin (id_user, nom, prenom, email, mot_de_passe, telephonne, photo_profil) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmAdmin = cnx.prepareStatement(queryAdmin);

            pstmAdmin.setInt(1, id_user); // Utiliser l'ID utilisateur pour lier les deux tables
            pstmAdmin.setString(2, admin.getNom());
            pstmAdmin.setString(3, admin.getPrenom());
            pstmAdmin.setString(4, admin.getEmail());
            pstmAdmin.setString(5, admin.getMot_de_passe());
            pstmAdmin.setInt(6, admin.getTelephonne());
            pstmAdmin.setString(7, admin.getPhoto_profil() != null ? admin.getPhoto_profil() : "default_profile.png");

            int rowsInserted = pstmAdmin.executeUpdate();
            if (rowsInserted > 0) {
                cnx.commit();
                System.out.println("✅ Admin ajouté avec succès !");
            } else {
                System.out.println("❌ Échec insertion admin.");
                cnx.rollback();
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Erreur SQL : " + e.getMessage());
            try {
                if (cnx != null) cnx.rollback();
            } catch (SQLException ex) {
                System.out.println("⚠️ Erreur rollback : " + ex.getMessage());
            }
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pstmUser != null) pstmUser.close();
                if (pstmAdmin != null) pstmAdmin.close();
                if (cnx != null) cnx.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("⚠️ Erreur fermeture : " + ex.getMessage());
            }
        }
    }


    @Override
    public List<Admin> getAll() {
        List<Admin> admins = new ArrayList<>();
        String qry = "SELECT * FROM `admin`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Admin a = new Admin();
                       // a.setId_admin(rs.getInt("id_admin"));
                        a.setNom(rs.getString("nom"));
                        a.setPrenom(rs.getString("prenom"));
                        a.setMot_de_passe(rs.getString("mot_de_passe"));
                        a.setTelephonne(rs.getInt("telephonne"));
                        a.setPhoto_profil(rs.getString("photo_profil"));

                admins.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des admins : " + e.getMessage());
        }
        return admins;
    }

    @Override
    public Admin getById(int id_admin) {
        String query = "SELECT a.id_admin, u.id_user, u.nom, u.prenom, u.email, u.mot_de_passe, u.telephonne, u.photo_profil " +
                "FROM admin a " +
                "JOIN user u ON a.id_user = u.id_user " +
                "WHERE a.id_admin = ?";

        Admin admin = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_admin);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("id_admin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil")
                );
                System.out.println("✅ Admin trouvé : " + admin.getNom() + " " + admin.getPrenom());
            } else {
                System.out.println("❌ Aucun admin trouvé avec l'ID " + id_admin);
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Erreur SQL : " + e.getMessage());
        }
        return admin;
    }


    @Override
    public void update(Admin admin) {
        String queryUser = "UPDATE user SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ?, photo_profil = ? WHERE id_user = ?";

        try {
            // Mise à jour des informations de l'utilisateur
            PreparedStatement pstmUser = cnx.prepareStatement(queryUser);
            pstmUser.setString(1, admin.getNom());
            pstmUser.setString(2, admin.getPrenom());
            pstmUser.setString(3, admin.getEmail());
            pstmUser.setString(4, admin.getMot_de_passe());
            pstmUser.setInt(5, admin.getTelephonne());
            pstmUser.setString(6, admin.getPhoto_profil());
            pstmUser.setInt(7, admin.getId_user());

            int rowsUpdatedUser = pstmUser.executeUpdate();
            if (rowsUpdatedUser > 0) {
                System.out.println("L'utilisateur avec ID " + admin.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + admin.getId_user());
            }

            String queryAdmin = "UPDATE admin SET nom = ?, prenom = ?, email = ?, mot_de_passe = ? , telephonne= ? , photo_profil = ? WHERE id_user = ?";
            PreparedStatement pstmAdmin = cnx.prepareStatement(queryAdmin);

            pstmAdmin.setString(1, admin.getNom());
            pstmAdmin.setString(2, admin.getPrenom());
            pstmAdmin.setString(3, admin.getEmail());
            pstmAdmin.setString(4, admin.getMot_de_passe());
            pstmAdmin.setInt(5, admin.getTelephonne());
            pstmAdmin.setString(6, admin.getPhoto_profil());

            pstmAdmin.setInt(7, admin.getId_user());

            int rowsUpdatedAdmin = pstmAdmin.executeUpdate();
            if (rowsUpdatedAdmin > 0) {
                System.out.println("L'admin avec ID utilisateur " + admin.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun admin trouvé avec l'ID utilisateur " + admin.getId_user());
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'admin : " + e.getMessage());
        }
    }



    @Override
    public void delete(Admin admin) {
        String query = "DELETE FROM admin WHERE id_admin = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, admin.getId_admin());
            int rowsDeleted = pstm.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Admin supprimé avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'admin : " + e.getMessage());
        }
    }
}

