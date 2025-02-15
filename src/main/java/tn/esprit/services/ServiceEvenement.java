package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IService<Evenements> {
    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Evenements evenements) {
        String query = "INSERT INTO `evenement`(`type_evenement`, `description_evenement`, `date_debut`, `date_fin`, `ligne_affectee`, `statut_evenement`) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1,evenements.getType_evenement().name());//type event
            pstm.setString(2,evenements.getDescription());// description
            pstm.setDate(3,evenements.getDate_debut());// date debut
            pstm.setDate(4,evenements.getDate_fin());// date fin
            pstm.setInt(5,evenements.getId_ligne_affectee());// id ligne affected
            pstm.setString(6,evenements.getStatus_evenement().name()); // status event

            int rowsAdd = pstm.executeUpdate();
            if (rowsAdd > 0) {
                System.out.println("L'evenement a été crée avec succès.");
            } else {
                System.out.println("Error de creation!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenements> getAll() {
        ArrayList<Evenements> events = new ArrayList<>();
        String query = "SELECT * FROM `evenement`";
        try{
            Statement stm = cnx.createStatement();
            ResultSet rs =stm.executeQuery(query);
            while (rs.next()) {
                Evenements event = new Evenements();
                event.setId_event(rs.getInt("id_evenement"));
                event.setId_ligne_affectee(rs.getInt("ligne_affectee"));
                event.setType_evenement(TypeEvenement.fromString(rs.getString("type_evenement")));
                event.setDescription(rs.getString("description_evenement"));
                event.setDate_debut(rs.getDate("date_debut"));
                event.setDate_fin(rs.getDate("date_fin"));
                event.setStatus_evenement(StatusEvenement.fromString(rs.getString("statut_evenement")));

                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    @Override
    public Evenements getById(int id_evenement) {
        String query = "SELECT * FROM evenement WHERE id_evenement = ?";
        Evenements event = null;
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_evenement);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {

                event = new Evenements(rs.getInt("id_evenement"),
                        TypeEvenement.fromString(rs.getString("type_evenement")),
                        rs.getInt("statut_evenement"),
                        rs.getString("description_evenement"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        StatusEvenement.fromString(rs.getString("statut_evenement"))
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return event;
    }

    @Override
    public void update(Evenements evenements) {
        String query = "UPDATE `evenement` SET `type_evenement`= ?,`description_evenement`= ?,`date_debut`= ?,`date_fin`= ?,`ligne_affectee`= ?,`statut_evenement`= ? WHERE id_evenement = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1,evenements.getType_evenement().name());//type event
            pstm.setString(2,evenements.getDescription());// description
            pstm.setDate(3,evenements.getDate_debut());// date debut
            pstm.setDate(4,evenements.getDate_fin());// date fin
            pstm.setInt(5,evenements.getId_ligne_affectee());// id ligne affected
            pstm.setString(6,evenements.getStatus_evenement().name()); // status event
            pstm.setInt(7,evenements.getId_event());

            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("L'evenement " + evenements.getId_event() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun evenement trouvé avec l'ID " + evenements.getId_event());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Evenements evenements) {
        String query = "DELETE FROM `evenement` WHERE id_evenement = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, evenements.getId_event()); // Suppression basée sur l'ID
            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'evenement" + evenements.getId_event() + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucune evenement trouvé avec l'ID " + evenements.getId_event());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }
}
