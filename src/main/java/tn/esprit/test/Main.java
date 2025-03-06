package tn.esprit.test;

import tn.esprit.models.trajet.Map;

public class Main {

    public static void main(String[] args) {
        // Set the address to "Tunis, Tunisia"
        String address = "Tunis, Tunisia";

        // Get the coordinates for the specified address
        double[] coordinates = Map.getCoordinates(address);

        // Print the coordinates to verify
        System.out.println("Coordinates for " + address + ": Latitude = " + coordinates[0] + ", Longitude = " + coordinates[1]);

        // Check if the coordinates are valid (non-zero)
        if (coordinates[0] != 0 && coordinates[1] != 0) {
            // Call getPlaceNameFromCoordinates to get the metadata (place name)
            String placeName = Map.getPlaceNameFromCoordinates(coordinates[0], coordinates[1]);

            // Print the place name (formatted address or metadata) to the console
            System.out.println("Place Name: " + placeName);  // Output the metadata of the place
        } else {
            System.out.println("Error: Unable to retrieve coordinates for the address: " + address);
        }
    }
}
//Tunis, Tunisia
//mourouj
//sfax
