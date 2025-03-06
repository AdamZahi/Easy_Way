package tn.esprit.models.trajet;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class geminiAPI {

    // Replace with your actual Gemini API key
    private static final String API_KEY = "AIzaSyCLysb9iKCZI9FdVA9JtcZEoaMaoakFDcU";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    // Context: Only answering questions related to transport prices for the EasyWay app
    private static final String CONTEXT =
            "Vous êtes un assistant virtuel de l'application EasyWay, une application de transport public en Tunisie. " +
                    "Cette application offre des prix pour les transports suivants : " +
                    "Bus = 3.5 TND, Metro = 3.0 TND, Train = 2.5 TND, Voiture = 5.0 TND. " +
                    "Le TND fait référence au Dinar Tunisien, la monnaie officielle de la Tunisie. " +
                    "L'application EasyWay ne répond qu'aux questions concernant ces prix de transport. " +
                    "Veuillez répondre de manière brève et polie, sans entrer dans des détails inutiles. Si la question ne concerne pas les prix des transports, ignorez-la. " +
                    "Soyez respectueux et concis dans vos réponses.";

    public static String getResponse(String question) {
        // Construct the JSON input with the context and user question
        String jsonInputString = "{"
                + "\"contents\": [{"
                + "\"parts\":[{\"text\": \"" + CONTEXT + " " + question + "\"}]"
                + "}]"
                + "}";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonInputString));

            // Execute the request
            HttpResponse response = client.execute(httpPost);

            // Read the response
            String responseString = EntityUtils.toString(response.getEntity());

            // Debugging: Print the raw JSON response
            System.out.println("Réponse brute : " + responseString);

            // Parse the response using Gson
            JsonObject jsonResponse = JsonParser.parseString(responseString).getAsJsonObject();

            // Safely access the "contents" array and check if it's null
            if (jsonResponse.has("candidates") && !jsonResponse.getAsJsonArray("candidates").isEmpty()) {
                String resultText = jsonResponse.getAsJsonArray("candidates").get(0)
                        .getAsJsonObject().getAsJsonObject("content").getAsJsonArray("parts").get(0)
                        .getAsJsonObject().get("text").getAsString();

                return resultText; // Return the response text
            } else {
                return "Aucun contenu valide dans la réponse."; // Return error if no valid response
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Une erreur est survenue.";
        }
    }
}
