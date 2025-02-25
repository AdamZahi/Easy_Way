package tn.esprit.services.trajet;

import tn.esprit.interfaces.trajet.IService;
import tn.esprit.util.MyDataBase;
import tn.esprit.models.trajet.Station;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceStation implements IService<Station> {
    private Connection cnx;
    public ServiceStation() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Station station) {
        String query = "INSERT INTO `station` (`nom`, `localisation`, `id_trajet`, `id_ligne`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, station.getNom());
            pstm.setString(2, station.getLocalisation());
            pstm.setInt(3, station.getId_trajet());
            pstm.setInt(4, station.getId_ligne());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Station> getAll() {
        ArrayList<Station> stations = new ArrayList<>();
        String qry ="SELECT * FROM `station`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                Station s = new Station();
                s.setId(rs.getInt("id"));
                s.setNom(rs.getString("nom"));
                s.setLocalisation(rs.getString("localisation"));
                s.setId_trajet(rs.getInt("id_trajet"));
                s.setId_ligne(rs.getInt("id_ligne"));
                stations.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        System.out.println(stations);
        return stations;
    }



    @Override
    public void update(Station station) {
        String query = "UPDATE `station` SET `nom` = ?, `localisation` = ?, `id_trajet` = ?, `id_ligne` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, station.getNom());
            pstm.setString(2, station.getLocalisation());
            pstm.setInt(3, station.getId_trajet());
            pstm.setInt(4, station.getId_ligne());
            pstm.setInt(5, station.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Station station) {
        String query = "DELETE FROM `station` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, station.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public Station getById(int id) {
        System.out.println("non implémenté");
        return null;
    }
}
