package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.util.MyDataBase;
import tn.esprit.models.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceReservation implements IService<Reservation> {
    private Connection cnx;
    public ServiceReservation() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Reservation reservation) {
        String query = "INSERT INTO `reservation` (`depart`, `arret`, `vehicule`, `nb`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, reservation.getDepart());
            pstm.setString(2, reservation.getArret());
            pstm.setString(3, reservation.getVehicule());
            pstm.setInt(4, reservation.getNb());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String qry ="SELECT * FROM `reservation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet  rs =stm.executeQuery(qry);
            while (rs.next()){
                Reservation l = new Reservation();
                l.setId(rs.getInt("id"));
                l.setDepart(rs.getString("depart"));
                l.setArret(rs.getString("arret"));
                l.setVehicule(rs.getString("vehicule"));
                l.setNb(rs.getInt("nb"));
                reservations.add(l);
            }
            System.out.println(reservations);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return reservations;
    }

    @Override
    public void update(Reservation reservation) {
        String query = "UPDATE `reservation` SET `depart` = ?, `arret` = ?, `vehicule` = ?, `nb` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, reservation.getDepart());
            pstm.setString(2, reservation.getArret());
            pstm.setString(3, reservation.getVehicule());
            pstm.setInt(4, reservation.getNb());
            pstm.setInt(5, reservation.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Reservation reservation) {
        String query = "DELETE FROM `reservation` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, reservation.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Reservation getReservationById(int id) {
        String query = "SELECT * FROM `reservation` WHERE `id` = ?";
        Reservation reservation = null;
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setDepart(rs.getString("depart"));
                reservation.setArret(rs.getString("arret"));
                reservation.setVehicule(rs.getString("vehicule"));
                reservation.setNb(rs.getInt("nb"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservation;
    }
}
