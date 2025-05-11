package tn.esprit.services.user;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user.Conducteur;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class ServiceConducteur implements IService<Conducteur> {
    private Connection cnx;

    public ServiceConducteur() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Conducteur conducteur) {
        Connection cnx = null;
        PreparedStatement pstmUser = null;
        PreparedStatement pstmConducteur = null;
        ResultSet resultSet = null;
        ResultSet generatedKeys = null;

        try {
            cnx = MyDataBase.getInstance().getCnx();
            cnx.setAutoCommit(false);

            // Vérifier si l'utilisateur existe déjà
            String checkUserQuery = "SELECT id_user FROM user WHERE email = ?";
            pstmUser = cnx.prepareStatement(checkUserQuery);
            pstmUser.setString(1, conducteur.getEmail());
            resultSet = pstmUser.executeQuery();

            int idUser = -1;
            boolean userExists = false;

            if (resultSet.next()) {
                idUser = resultSet.getInt("id_user");
                userExists = true;
                System.out.println("ℹ️ Utilisateur déjà existant avec ID : " + idUser);
            } else {
                // Champs obligatoires
                if (conducteur.getNom() == null || conducteur.getNom().isEmpty() ||
                        conducteur.getPrenom() == null || conducteur.getPrenom().isEmpty() ||
                        conducteur.getEmail() == null || conducteur.getEmail().isEmpty() ||
                        conducteur.getMot_de_passe() == null || conducteur.getMot_de_passe().isEmpty()) {
                    System.out.println("❌ Un champ obligatoire est vide.");
                    return;
                }

                // Insertion dans `user`
                String insertUserQuery = "INSERT INTO user (nom, prenom, email, password, telephonne, photo_profil, roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmUser = cnx.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                pstmUser.setString(1, conducteur.getNom());
                pstmUser.setString(2, conducteur.getPrenom());
                pstmUser.setString(3, conducteur.getEmail());
                pstmUser.setString(4, conducteur.getMot_de_passe());
                pstmUser.setInt(5, conducteur.getTelephonne());
                pstmUser.setString(6, conducteur.getPhoto_profil() != null ? conducteur.getPhoto_profil() : "default_profile.png");
                pstmUser.setString(7, "[\"ROLE_CONDUCTEUR\"]");

                int affectedRows = pstmUser.executeUpdate();
                if (affectedRows == 0) {
                    System.out.println("❌ Insertion user échouée.");
                    cnx.rollback();
                    return;
                }

                generatedKeys = pstmUser.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idUser = generatedKeys.getInt(1);
                    System.out.println("✅ Utilisateur créé avec ID : " + idUser);
                } else {
                    System.out.println("❌ ID user non généré.");
                    cnx.rollback();
                    return;
                }
            }

            // Vérifie si ce user est déjà conducteur
            String checkConducteurQuery = "SELECT * FROM conducteur WHERE id_user = ?";
            PreparedStatement checkConducteurStmt = cnx.prepareStatement(checkConducteurQuery);
            checkConducteurStmt.setInt(1, idUser);
            ResultSet conducteurCheckResult = checkConducteurStmt.executeQuery();
            if (conducteurCheckResult.next()) {
                System.out.println("⚠️ Ce conducteur est déjà enregistré.");
                cnx.rollback();
                return;
            }

            // Insertion dans `conducteur` (avec TOUS les champs obligatoires)
            String insertConducteurQuery = "INSERT INTO conducteur (id_user, nom, prenom, email, mot_de_passe, telephonne, photo_profil, nb_trajet_effectues, nb_passagers_transportes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmConducteur = cnx.prepareStatement(insertConducteurQuery);
            pstmConducteur.setInt(1, idUser);
            pstmConducteur.setString(2, conducteur.getNom());
            pstmConducteur.setString(3, conducteur.getPrenom());
            pstmConducteur.setString(4, conducteur.getEmail());
            pstmConducteur.setString(5, conducteur.getMot_de_passe());
            pstmConducteur.setInt(6, conducteur.getTelephonne());
            pstmConducteur.setString(7, conducteur.getPhoto_profil() != null ? conducteur.getPhoto_profil() : "default_profile.png");
            pstmConducteur.setInt(8, conducteur.getNb_trajet_effectues());
            pstmConducteur.setInt(9, conducteur.getNb_passagers_transportes());

            int rowsInserted = pstmConducteur.executeUpdate();
            if (rowsInserted > 0) {
                cnx.commit();
                System.out.println("✅ Conducteur ajouté avec succès !");
            } else {
                System.out.println("⚠️ Échec insertion conducteur.");
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
                if (resultSet != null) resultSet.close();
                if (generatedKeys != null) generatedKeys.close();
                if (pstmUser != null) pstmUser.close();
                if (pstmConducteur != null) pstmConducteur.close();
                if (cnx != null) cnx.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("⚠️ Erreur fermeture : " + ex.getMessage());
            }
        }
    }



    @Override
    public List<Conducteur> getAll() {
        List<Conducteur> conducteurs = new ArrayList<>();
        String query = "SELECT * FROM conducteur";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                Conducteur c = new Conducteur(
                        //rs.getInt("id_conducteur"),
                        rs.getInt("nb_trajet_effectues"),
                        rs.getInt("nb_passagers_transportes")
                );
                conducteurs.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des conducteurs : " + e.getMessage());
        }
        return conducteurs;
    }

    @Override
    public Conducteur getById(int id_conducteur) {
        String query = "SELECT * FROM conducteur WHERE id_conducteur = ?";
        Conducteur conducteur = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_conducteur);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                conducteur = new Conducteur(
                        rs.getInt("id_conducteur"),
                        rs.getInt("nb_trajet_effectues"),
                        rs.getInt("nb_passagers_transportes")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du conducteur : " + e.getMessage());
        }
        return conducteur;
    }

    @Override
    public void update(Conducteur conducteur) {
        String queryUser = "UPDATE user SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephonne = ?, photo_profil = ? WHERE id_user = ?";

        try {
            PreparedStatement pstmUser = cnx.prepareStatement(queryUser);
            pstmUser.setString(1, conducteur.getNom());
            pstmUser.setString(2, conducteur.getPrenom());
            pstmUser.setString(3, conducteur.getEmail());
            pstmUser.setString(4, conducteur.getMot_de_passe());
            pstmUser.setInt(5, conducteur.getTelephonne());
            pstmUser.setString(6, conducteur.getPhoto_profil());

            pstmUser.setInt(7, conducteur.getId_user());

            int rowsUpdatedUser = pstmUser.executeUpdate();
            if (rowsUpdatedUser > 0) {
                System.out.println("L'utilisateur avec ID " + conducteur.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + conducteur.getId_user());
            }

            String queryConducteur = "UPDATE conducteur SET nb_trajet_effectues = ?, nb_passagers_transportes = ? WHERE id_user = ?";
            PreparedStatement pstmConducteur = cnx.prepareStatement(queryConducteur);

            pstmConducteur.setInt(1, conducteur.getNb_trajet_effectues());
            pstmConducteur.setInt(2, conducteur.getNb_passagers_transportes());

            pstmConducteur.setInt(3, conducteur.getId_user());

            int rowsUpdatedConducteur = pstmConducteur.executeUpdate();
            if (rowsUpdatedConducteur > 0) {
                System.out.println("Le conducteur avec ID utilisateur " + conducteur.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun conducteur trouvé avec l'ID utilisateur " + conducteur.getId_user());
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du conducteur : " + e.getMessage());
        }
    }

    @Override
    public void delete(Conducteur conducteur) {
        System.out.println("Tentative de suppression du conducteur avec ID utilisateur : " + conducteur.getId_user());

        String queryConducteur = "DELETE FROM conducteur WHERE id_user = ?";
        String queryUser = "DELETE FROM user WHERE id_user = ?";

        try {
            PreparedStatement pstmConducteur = cnx.prepareStatement(queryConducteur);
            pstmConducteur.setInt(1, conducteur.getId_user());
            int rowsDeletedConducteur = pstmConducteur.executeUpdate();

            if (rowsDeletedConducteur > 0) {
                System.out.println("Conducteur supprimé avec succès !");
            } else {
                System.out.println("Aucun conducteur trouvé avec l'ID utilisateur " + conducteur.getId_user());
            }

            PreparedStatement pstmUser = cnx.prepareStatement(queryUser);
            pstmUser.setInt(1, conducteur.getId_user());
            int rowsDeletedUser = pstmUser.executeUpdate();

            if (rowsDeletedUser > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID utilisateur " + conducteur.getId_user());
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
