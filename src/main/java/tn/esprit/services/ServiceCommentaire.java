package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Commentaire;
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
        String qry = "INSERT INTO commentaire (id_post, id_user, contenu, date_creat) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, commentaire.getId_post());
            pstm.setInt(2, commentaire.getId_user());
            pstm.setString(3, commentaire.getContenu());  // Utilisation du bon getter
            pstm.setDate(4, commentaire.getDate_creat()); // Utilisation de date_creat
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public void update(Commentaire commentaire) {
        String qry = "UPDATE commentaire SET id_post = ?, id_user = ?, contenu = ?, date_creat = ? WHERE id_com = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, commentaire.getId_post());
            pstm.setInt(2, commentaire.getId_user());
            pstm.setString(3, commentaire.getContenu()); // Utilisation du bon getter
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
}
