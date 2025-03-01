package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.user.Conducteur;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class ServiceConducteur implements IService<Conducteur> {
    private Connection cnx;

    public ServiceConducteur() {
        cnx = MyDataBase.getInstance().getConnection();
    }

    public void add(Conducteur conducteur) {
        try {

            String queryUser = "INSERT INTO user (nom, prenom, email, mot_de_passe, telephonne, photo_profil) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmUser = cnx.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
            pstmUser.setString(1, conducteur.getNom());
            pstmUser.setString(2, conducteur.getPrenom());
            pstmUser.setString(3, conducteur.getEmail());
            pstmUser.setString(4, conducteur.getMot_de_passe());
            pstmUser.setInt(5, conducteur.getTelephonne());
            pstmUser.setString(6, conducteur.getPhoto_profil());


            int affectedRows = pstmUser.executeUpdate();
            if (affectedRows > 0) {

                ResultSet generatedKeys = pstmUser.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id_user = generatedKeys.getInt(1);

                    String queryConducteur = "INSERT INTO conducteur (id_user , nom , prenom , email, mot_de_passe , telephonne, photo_profil , numero_permis , experience) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmConducteur = cnx.prepareStatement(queryConducteur);
                    pstmConducteur.setInt(1, id_user);
                    pstmConducteur.setString(2, conducteur.getNom());
                    pstmConducteur.setString(3, conducteur.getPrenom());
                    pstmConducteur.setString(4, conducteur.getEmail());
                    pstmConducteur.setString(5, conducteur.getMot_de_passe());
                    pstmConducteur.setInt(6, conducteur.getTelephonne());
                    pstmConducteur.setString(7, conducteur.getPhoto_profil());
                    pstmConducteur.setString(8, conducteur.getNumero_permis());
                    pstmConducteur.setString(9, conducteur.getExperience());


                    int rowsInserted = pstmConducteur.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Conducteur ajouté avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du conducteur dans la table conducteur.");
                    }
                }
            } else {
                System.out.println("Aucune ligne insérée dans la table user.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du conducteur : " + e.getMessage());
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
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        rs.getString("numero_permis"),
                        rs.getString("experience")
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
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        rs.getString("numero_permis"),
                        rs.getString("experience")
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


            String queryConducteur = "UPDATE conducteur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ? , telephonne= ? , photo_profil = ? , numero_permis = ? , experience = ? WHERE id_user = ?";
            PreparedStatement pstmConducteur = cnx.prepareStatement(queryConducteur);

            pstmConducteur.setString(1, conducteur.getNom());
            pstmConducteur.setString(2, conducteur.getPrenom());
            pstmConducteur.setString(3, conducteur.getEmail());
            pstmConducteur.setString(4, conducteur.getMot_de_passe());
            pstmConducteur.setInt(5, conducteur.getTelephonne());
            pstmConducteur.setString(6, conducteur.getPhoto_profil());
            pstmConducteur.setString(7, conducteur.getNumero_permis());
            pstmConducteur.setString(8, conducteur.getExperience());

            pstmConducteur.setInt(9, conducteur.getId_user());

            int rowsUpdatedConducteur = pstmConducteur.executeUpdate();
            if (rowsUpdatedConducteur > 0) {
                System.out.println("le conducteur avec ID utilisateur " + conducteur.getId_user() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun Conducteur  trouvé avec l'ID utilisateur " + conducteur.getId_user());
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
            // Supprimer le conducteur
            PreparedStatement pstmConducteur = cnx.prepareStatement(queryConducteur);
            pstmConducteur.setInt(1, conducteur.getId_user());
            int rowsDeletedConducteur = pstmConducteur.executeUpdate();

            if (rowsDeletedConducteur > 0) {
                System.out.println("Conducteur supprimé avec succès !");
            } else {
                System.out.println("Aucun conducteur trouvé avec l'ID utilisateur " + conducteur.getId_user());
            }

            // Supprimer l'utilisateur uniquement si le conducteur a été supprimé
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
