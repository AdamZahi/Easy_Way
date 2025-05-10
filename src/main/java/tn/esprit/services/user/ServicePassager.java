package tn.esprit.services.user;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user.Passager;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePassager implements IService<Passager> {
    private Connection cnx;

    public ServicePassager() {
        cnx = MyDataBase.getInstance().getCnx();
    }



    @Override
    public void add(Passager passager) {
        Connection cnx = null;
        PreparedStatement pstmUser = null;
        PreparedStatement pstmPassager = null;
        ResultSet resultSet = null;
        ResultSet generatedKeys = null;

        try {
            cnx = MyDataBase.getInstance().getCnx();
            cnx.setAutoCommit(false);

            // Vérifier si l'utilisateur existe déjà
            String checkUserQuery = "SELECT id_user FROM user WHERE email = ?";
            pstmUser = cnx.prepareStatement(checkUserQuery);
            pstmUser.setString(1, passager.getEmail());
            resultSet = pstmUser.executeQuery();

            int idUser = -1;
            boolean userExistant = false;

            if (resultSet.next()) {
                idUser = resultSet.getInt("id_user");
                System.out.println("ℹ️ Utilisateur déjà existant avec ID : " + idUser);
                userExistant = true;
            }

            if (!userExistant) {
                if (passager.getNom() == null || passager.getNom().isEmpty() ||
                        passager.getPrenom() == null || passager.getPrenom().isEmpty() ||
                        passager.getEmail() == null || passager.getEmail().isEmpty() ||
                        passager.getMot_de_passe() == null || passager.getMot_de_passe().isEmpty()) {
                    System.out.println("❌ Un champ obligatoire est vide.");
                    return;
                }

                String insertUserQuery = "INSERT INTO user (nom, prenom, email, password, telephonne, photo_profil, roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmUser = cnx.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                pstmUser.setString(1, passager.getNom());
                pstmUser.setString(2, passager.getPrenom());
                pstmUser.setString(3, passager.getEmail());
                pstmUser.setString(4, passager.getMot_de_passe());
                pstmUser.setString(5, String.valueOf(passager.getTelephonne()));
                pstmUser.setString(6, passager.getPhoto_profil() != null ? passager.getPhoto_profil() : "default_profile.png");
                pstmUser.setString(7, "[\"ROLE_PASSAGER\"]");

                int affectedRows = pstmUser.executeUpdate();
                if (affectedRows == 0) {
                    System.out.println("❌ L'insertion de l'utilisateur a échoué.");
                    cnx.rollback();
                    return;
                }

                generatedKeys = pstmUser.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idUser = generatedKeys.getInt(1);
                    System.out.println("✅ Utilisateur ajouté avec ID : " + idUser);
                } else {
                    System.out.println("❌ Aucun ID généré.");
                    cnx.rollback();
                    return;
                }
            }

            String insertPassagerQuery = "INSERT INTO passager (id_user, nom, prenom, email, mot_de_passe, telephonne, photo_profil, nb_trajet_effectues) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmPassager = cnx.prepareStatement(insertPassagerQuery);
            pstmPassager.setInt(1, idUser);
            pstmPassager.setString(2, passager.getNom());
            pstmPassager.setString(3, passager.getPrenom());
            pstmPassager.setString(4, passager.getEmail());
            pstmPassager.setString(5, passager.getMot_de_passe());
            pstmPassager.setInt(6, passager.getTelephonne());
            pstmPassager.setString(7, passager.getPhoto_profil() != null ? passager.getPhoto_profil() : "default_profile.png");
            pstmPassager.setInt(8, passager.getNbTrajetsEffectues());

            int passagerRows = pstmPassager.executeUpdate();
            if (passagerRows > 0) {
                cnx.commit();
                System.out.println("✅ Passager ajouté avec succès !");
            } else {
                System.out.println("⚠️ Échec de l'ajout du passager.");
                cnx.rollback();
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Erreur SQL : " + e.getMessage());
            try {
                if (cnx != null) cnx.rollback();
            } catch (SQLException ex) {
                System.out.println("⚠️ Rollback failed : " + ex.getMessage());
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (generatedKeys != null) generatedKeys.close();
                if (pstmUser != null) pstmUser.close();
                if (pstmPassager != null) pstmPassager.close();
                if (cnx != null) cnx.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("⚠️ Erreur lors de la fermeture : " + ex.getMessage());
            }
        }
    }



    @Override
    public List<Passager> getAll() {
        List<Passager> passagers = new ArrayList<>();
        String query = "SELECT u.*, p.nb_trajet_effectues FROM user u JOIN passager p ON u.id_user = p.id_user";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Passager p = new Passager(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        rs.getInt("nb_trajet_effectues") // Récupération du nombre de trajets effectués
                );
                passagers.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des passagers : " + e.getMessage());
        }
        return passagers;
    }


    @Override
    public Passager getById(int id_passager) {
        String query = "SELECT p.id_passager, u.id_user, u.nom, u.prenom, u.email, u.mot_de_passe, u.telephonne, u.photo_profil, p.nb_trajet_effectues " +
                "FROM passager p " +
                "JOIN user u ON p.id_user = u.id_user " +
                "WHERE p.id_passager = ?";

        Passager passager = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_passager);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                passager = new Passager(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        rs.getInt("nb_trajet_effectues") // Ajout de nbTrajetsEffectues
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du passager : " + e.getMessage());
        }

        return passager;
    }



    @Override
    public void update(Passager passager) {
        String queryUser = "UPDATE user SET nom = ?, prenom = ?, email = ?, password = ?, telephonne = ?, photo_profil = ? WHERE id_user = ?";
        String queryPassager = "UPDATE passager SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ?, photo_profil = ?, nb_trajet_effectues = ? WHERE id_user = ?";

        try {
            // 🔹 Mise à jour des informations de l'utilisateur dans la table `user`
            PreparedStatement pstmUser = cnx.prepareStatement(queryUser);
            pstmUser.setString(1, passager.getNom());
            pstmUser.setString(2, passager.getPrenom());
            pstmUser.setString(3, passager.getEmail());
            pstmUser.setString(4, passager.getMot_de_passe());
            pstmUser.setInt(5, passager.getTelephonne());
            pstmUser.setString(6, passager.getPhoto_profil());
            pstmUser.setInt(7, passager.getId_user());

            int rowsUpdatedUser = pstmUser.executeUpdate();
            pstmUser.close(); // ✅ Fermeture du statement

            if (rowsUpdatedUser > 0) {
                System.out.println("✅ L'utilisateur avec ID " + passager.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("⚠️ Aucun utilisateur trouvé avec l'ID " + passager.getId_user());
            }

            // 🔹 Mise à jour des informations spécifiques au passager dans la table `passager`
            PreparedStatement pstmPassager = cnx.prepareStatement(queryPassager);
            pstmPassager.setString(1, passager.getNom());
            pstmPassager.setString(2, passager.getPrenom());
            pstmPassager.setString(3, passager.getEmail());
            pstmPassager.setString(4, passager.getMot_de_passe());
            pstmPassager.setInt(5, passager.getTelephonne());
            pstmPassager.setString(6, passager.getPhoto_profil());
            pstmPassager.setInt(7, passager.getNbTrajetsEffectues());
            pstmPassager.setInt(8, passager.getId_user());

            int rowsUpdatedPassager = pstmPassager.executeUpdate();
            pstmPassager.close(); // ✅ Fermeture du statement

            if (rowsUpdatedPassager > 0) {
                System.out.println("✅ Le passager avec ID utilisateur " + passager.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("⚠️ Aucun passager trouvé avec l'ID utilisateur " + passager.getId_user());
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du passager : " + e.getMessage());
        }
    }



    @Override
    public void delete(Passager passager) {
        String queryPassager = "DELETE FROM passager WHERE id_user = ?";
        String queryUser = "DELETE FROM user WHERE id_user = ?";

        try {
            // 🔹 Suppression du passager
            PreparedStatement pstmPassager = cnx.prepareStatement(queryPassager);
            pstmPassager.setInt(1, passager.getId_user());
            pstmPassager.executeUpdate();

            // 🔹 Suppression de l'utilisateur correspondant
            PreparedStatement pstmUser = cnx.prepareStatement(queryUser);
            pstmUser.setInt(1, passager.getId_user());
            int rowsDeleted = pstmUser.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("L'utilisateur avec ID " + passager.getId_user() + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + passager.getId_user());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }


}