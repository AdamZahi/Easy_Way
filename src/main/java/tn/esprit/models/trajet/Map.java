package tn.esprit.models.trajet;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Map {

    // Replace with your actual OpenCage API Key
    private static final String API_KEY = "ff72fda5ad874ffca77411b17e2e0b30";

    // Method to get coordinates for a given address
    public static double[] getCoordinates(String address) {
        double[] coordinates = new double[2];
        try {
            // Format the address for the URL (replace spaces with +)
            String formattedAddress = address.replace(" ", "+");

            // Build the URL to query the OpenCage API
            String urlString = "https://api.opencagedata.com/geocode/v1/json?q=" + formattedAddress
                    + "&key=" + API_KEY + "&countrycode=TN";

            // Create a URL object
            URL url = new URL(urlString);

            // Open the connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the response to extract latitude and longitude
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Get the status object and check the code
            JSONObject status = jsonResponse.getJSONObject("status");
            int statusCode = status.getInt("code");

            if (statusCode == 200) {
                // Extract the first result from the response
                JSONObject firstResult = jsonResponse.getJSONArray("results").getJSONObject(0);
                JSONObject geometry = firstResult.getJSONObject("geometry");
                double latitude = geometry.getDouble("lat");
                double longitude = geometry.getDouble("lng");

                // Return the coordinates as an array
                coordinates[0] = latitude;
                coordinates[1] = longitude;
            } else {
                System.out.println("Unable to geocode input address: " + address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
