package tn.esprit.controller.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.event.ServiceEvenement;
import java.io.IOException;


public class AddEventController {
    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField ligneField;

    @FXML
    private TextArea descText;
    @FXML
    private ChoiceBox<StatusEvenement> statusChoiceBox;

    @FXML
    private ChoiceBox<TypeEvenement> typeChoiceBox;
    private final ServiceEvenement se = new ServiceEvenement();

    @FXML
    public void initialize() {
        // Populate ChoiceBoxes with Enum values
        typeChoiceBox.getItems().setAll(TypeEvenement.values());
        statusChoiceBox.getItems().setAll(StatusEvenement.values());
    }
    @FXML
    void addEventToDB(ActionEvent event) {
        String description = descText.getText();
        TypeEvenement type = typeChoiceBox.getValue();
        int ligne = Integer.parseInt(ligneField.getText());

        java.time.LocalDate localDateDebut = dateDebutPicker.getValue();
        java.sql.Date dateDebut = java.sql.Date.valueOf(localDateDebut);

        java.time.LocalDate localDateFin = dateFinPicker.getValue();
        if (localDateFin == null) {
            java.sql.Date dateFin = null;
        }
        java.sql.Date dateFin = java.sql.Date.valueOf(localDateFin);

        StatusEvenement status = statusChoiceBox.getValue();

        if (dateDebut.before(dateFin)|| description.isEmpty() || type == null || ligneField.getText()==null || dateDebut == null || status == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        Evenements newEvent = new Evenements(0,
                type,
                ligne,
                description,
                dateDebut,
                dateFin,
                status,
                4);
        se.add(newEvent);
        showAlert("Succès", "Événement ajouté avec succès!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void clearAll(ActionEvent event) {
        typeChoiceBox.getItems().clear();
        statusChoiceBox.getItems().clear();
        ligneField.setText("");
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
        descText.setText("");
    }
    @FXML
    void goToEventList(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/eventTable.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
