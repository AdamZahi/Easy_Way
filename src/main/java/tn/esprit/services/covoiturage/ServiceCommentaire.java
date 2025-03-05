package tn.esprit.services.covoiturage;

import tn.esprit.interfaces.IService;
import tn.esprit.models.covoiturage.Commentaire;
import tn.esprit.util.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {
    private Connection cnx;

    public ServiceCommentaire() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override

    public void add(Commentaire commentaire) {
        String qry = "INSERT INTO commentaire (id_post, id_user, contenu, date_creat, nom) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, commentaire.getId_post());
            pstm.setInt(2, commentaire.getId_user());
            pstm.setString(3, commentaire.getContenu());
            pstm.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Utilisation de la date actuelle
            pstm.setString(5, commentaire.getNom());

            pstm.executeUpdate(); // Exécute l'insertion dans la base de données

            System.out.println("Commentaire ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }


    @Override
    public List<Commentaire> getAll() {


        List<Commentaire> commentairesList = new ArrayList<>();
        String qry = "SELECT * FROM commentaire";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Commentaire c = new Commentaire();

                c.setId_com(rs.getInt("id_com"));
                c.setId_post(rs.getInt("id_post"));
                c.setId_user(rs.getInt("id_user"));
                c.setNom(rs.getString("nom"));
                c.setContenu(rs.getString("contenu")); // Utilisation du bon setter
                c.setDate_creat(rs.getDate("date_creat")); // Utilisation de date_creat
                commentairesList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentairesList;
    }




    @Override
    public Commentaire getById(int id) {
        return null;
    }

    @Override
    public void update(Commentaire commentaire) {
        String qry = "UPDATE commentaire SET id_post = ?, id_user = ?, contenu = ?, date_creat = ? ,nom = ? WHERE id_com = ? ";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, commentaire.getId_post());
            pstm.setInt(2, commentaire.getId_user());
            pstm.setString(3, commentaire.getContenu());
            pstm.setString(5, commentaire.getNom());// Utilisation du bon getter
            pstm.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Nouvelle date actuelle
            pstm.setInt(5, commentaire.getId_com()); // Utilisation du bon getter
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Commentaire commentaire) {
        String qry = "DELETE FROM commentaire WHERE id_com = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, commentaire.getId_com()); // Utilisation du bon getter
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Commentaire> getCommentsByPostId(int id_post) {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT c.contenu, c.date_creat, u.nom " +
                "FROM commentaire c " +
                "JOIN user u ON c.id_user = u.id_user " +
                "WHERE c.id_post = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id_post);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contenu = rs.getString("contenu");
                Date date_creat = rs.getDate("date_creat");
                String nom = rs.getString("nom");
                // On récupère le nom d'utilisateur

                // On passe maintenant aussi le username dans l'objet Commentaire
                commentaires.add(new Commentaire(id_post, 0, contenu, date_creat, nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentaires;
    }




}
