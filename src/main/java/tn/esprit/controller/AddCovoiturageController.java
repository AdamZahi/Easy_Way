package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import tn.esprit.models.Posts;
import tn.esprit.services.ServicePosts;
import java.time.LocalDate;



import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddCovoiturageController implements Initializable {

    @FXML
    private ComboBox<String> departureCity; // Correspond à fx:id="departureCity" dans le FXML

    @FXML
    private ComboBox<String> arrivalCity; // Correspond à fx:id="arrivalCity" dans le FXML

    @FXML
    private DatePicker travelDate; // Correspond à fx:id="travelDate"

    @FXML
    private TextArea travelDetails; // Correspond à fx:id="travelDetails"

    @FXML
    private Button ajouter; // Correspond à fx:id="ajouter"

    private final ServicePosts servicePosts = new ServicePosts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vérification pour éviter NullPointerException
        if (departureCity != null && arrivalCity != null) {
            departureCity.getItems().addAll("Tunis", "Sousse", "Sfax", "Monastir", "Nabeul", "Bizerte",
                    "Gabès", "Gafsa", "Kairouan", "Mahdia", "Djerba", "Tozeur", "Zarzis", "Ben Arous",
                    "Ariana", "Manouba", "Beja", "Jendouba", "Kef", "Siliana", "Kasserine", "Sidi Bouzid",
                    "Médenine", "Tataouine");

            arrivalCity.getItems().addAll(departureCity.getItems());
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (departureCity.getValue() == null || arrivalCity.getValue() == null ||
                travelDate.getValue() == null || travelDetails.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        if (departureCity.getValue().equals(arrivalCity.getValue())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La ville de départ et la ville d'arrivée ne peuvent pas être identiques !");
            return;
        }

        // Check if the date is in the past
        if (travelDate.getValue().isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La date de départ ne peut pas être dans le passé !");
            return;
        }
        // Create ServicePost instance
        ServicePosts sp = new ServicePosts();

        // Create a new Posts object with the form inputs
        Posts newPost = new Posts(
                0, // ID is auto-generated, so set it to 0 or ignore it
                1, // Replace with the actual user ID
                departureCity.getValue(),
                arrivalCity.getValue(),
                Date.valueOf(travelDate.getValue()),
                travelDetails.getText()
        );

        // Add the post using the service method
        sp.add(newPost);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Votre annonce a été ajoutée !");
        departureCity.setValue(null);
        arrivalCity.setValue(null);
        travelDate.setValue(null);
        travelDetails.clear();
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}