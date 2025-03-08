package tn.esprit.models.trajet;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeminiAPI {

    private static final String API_KEY = "secret";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    private static final String CONTEXT =
            "Vous êtes un assistant virtuel de l'application EasyWay, une application de transport public en Tunisie. " +
                    "Cette application offre des prix pour les transports suivants : " +
                    "Bus = 3.5 TND, Metro = 3.0 TND, Train = 2.5 TND, Voiture = 5.0 TND. " +
                    "Le TND fait référence au Dinar Tunisien, la monnaie officielle de la Tunisie. " +
                    "L'application EasyWay ne répond qu'aux questions concernant ces prix de transport. " +
                    "Veuillez répondre de manière brève et polie, sans entrer dans des détails inutiles. Si la question ne concerne pas les prix des transports, ignorez-la. " +
                    "Soyez respectueux et concis dans vos réponses.";

    public static String getResponse(String question) {
        String jsonInputString = "{"
                + "\"contents\": [{"
                + "\"parts\":[{\"text\": \"" + CONTEXT + " " + question + "\"}]"
                + "}]"
                + "}";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonInputString));

            HttpResponse response = client.execute(httpPost);

            String responseString = EntityUtils.toString(response.getEntity());

            System.out.println("Réponse brute : " + responseString);

            JsonObject jsonResponse = JsonParser.parseString(responseString).getAsJsonObject();

            if (jsonResponse.has("candidates") && !jsonResponse.getAsJsonArray("candidates").isEmpty()) {
                String resultText = jsonResponse.getAsJsonArray("candidates").get(0)
                        .getAsJsonObject().getAsJsonObject("content").getAsJsonArray("parts").get(0)
                        .getAsJsonObject().get("text").getAsString();

                return resultText; // Return the response text
            } else {
                return "Aucun contenu valide dans la réponse.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Une erreur est survenue.";
        }
    }
}