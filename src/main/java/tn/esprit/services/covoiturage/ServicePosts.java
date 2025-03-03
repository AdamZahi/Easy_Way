package tn.esprit.services.covoiturage;

import tn.esprit.interfaces.IService;
import tn.esprit.models.covoiturage.Posts;
import tn.esprit.util.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ServicePosts implements IService<Posts> {
    private Connection cnx;

    public ServicePosts() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Posts posts) {
        String qry = "INSERT INTO posts (id_user, ville_depart, ville_arrivee, date, message) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, posts.getId_user());
            pstm.setString(2, posts.getVilleDepart());
            pstm.setString(3, posts.getVilleArrivee());
            pstm.setDate(4, posts.getDate());
            pstm.setString(5, posts.getMessage());
            pstm.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                posts.setId_post(rs.getInt(1));  // Met à jour l'objet Posts avec l'ID généré
            }

            System.out.println("Post ajouté avec ID : " + posts.getId_post());

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du post : " + e.getMessage());
        }
    }

    @Override
    public List<Posts> getAll() {
        List<Posts> postsList = new ArrayList<>();
        String qry = "SELECT * FROM posts";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Posts p = new Posts();
                p.setId_post(rs.getInt("id_post"));
                p.setId_user(rs.getInt("id_user"));
                p.setVilleDepart(rs.getString("ville_depart"));
                p.setVilleArrivee(rs.getString("ville_arrivee"));
                p.setDate(rs.getDate("date"));
                p.setMessage(rs.getString("message"));
                postsList.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postsList;
    }

    @Override
    public Posts getById(int id) {
        return null;
    }

    @Override
    public void update(Posts posts) {
        String qry = "UPDATE posts SET id_user = ?, ville_depart = ?, ville_arrivee = ?, date = ?, message = ? WHERE id_post = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, posts.getId_user());
            pstm.setString(2, posts.getVilleDepart());
            pstm.setString(3, posts.getVilleArrivee());
            pstm.setDate(4, posts.getDate());
            pstm.setString(5, posts.getMessage());
            pstm.setInt(6, posts.getId_post());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mise à jour réussie !");
            } else {
                System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du post : " + e.getMessage());
        }
    }

    @Override
    public void delete(Posts posts) {
        String qry = "DELETE FROM posts WHERE id_post = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, posts.getId_post());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
