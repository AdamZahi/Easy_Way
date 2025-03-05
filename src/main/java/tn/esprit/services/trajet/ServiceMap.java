package tn.esprit.services.trajet;

import tn.esprit.models.trajet.Map;
import javafx.scene.web.WebView;

public class ServiceMap {

    // Function to initialize the map for Ariana
    public void initializeMap(WebView map) {
        // Default address is Ariana, Tunisia
        String address = "Ariana, Tunisia";
        double[] coordinates = Map.getCoordinates(address);

        if (coordinates[0] != 0 && coordinates[1] != 0) {
            // Create the OpenStreetMap URL with the coordinates
            String url = "https://www.openstreetmap.org/?mlat=" + coordinates[0] + "&mlon=" + coordinates[1] + "#map=12/" + coordinates[0] + "/" + coordinates[1];
            map.getEngine().load(url); // Load the map in the WebView
        } else {
            System.out.println("Error: Invalid coordinates for address: " + address);
        }
    }

    // Function to update the map with new coordinates (not used directly in AjoutReservation)
    public void updateMap(WebView map, String address) {
        double[] coordinates = Map.getCoordinates(address);

        if (coordinates[0] != 0 && coordinates[1] != 0) {
            // Create the OpenStreetMap URL with the coordinates
            String url = "https://www.openstreetmap.org/?mlat=" + coordinates[0] + "&mlon=" + coordinates[1] + "#map=12/" + coordinates[0] + "/" + coordinates[1];
            map.getEngine().load(url); // Load the map in the WebView
        } else {
            System.out.println("Error: Invalid coordinates for address: " + address);
        }
    }
}
