package tn.esprit.test;

import tn.esprit.services.trajet.ServicePaiement;
import tn.esprit.services.trajet.ServiceReservation;
import tn.esprit.models.trajet.Reservation;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create an instance of ServicePaiement to retrieve reservation IDs
        ServicePaiement servicePaiement = new ServicePaiement();

        // Get the list of reservation IDs from ServicePaiement
        List<Integer> reservationIds = servicePaiement.getReservationIds();

        // Check if we have reservation IDs
        if (reservationIds != null && !reservationIds.isEmpty()) {
            System.out.println("Reservation IDs: ");
            for (Integer id : reservationIds) {
                System.out.println(id);
            }

            // Now, use the reservation IDs to fetch reservation details from ServiceReservation
            ServiceReservation serviceReservation = new ServiceReservation();
            List<Reservation> reservations = serviceReservation.getReservationsByIds(reservationIds);

            // Print the reservation details
            System.out.println("\nReservation Details:");
            for (Reservation reservation : reservations) {
                System.out.println("Depart: " + reservation.getDepart());
                System.out.println("Arret: " + reservation.getArret());
                System.out.println("Vehicule: " + reservation.getVehicule());
                System.out.println("Nb: " + reservation.getNb());
                System.out.println("-----------------------------");
            }

        } else {
            System.out.println("No reservation IDs found.");
        }
    }
}
