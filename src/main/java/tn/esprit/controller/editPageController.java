package tn.esprit.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editPageController implements Initializable {
    @FXML
    private DatePicker dateDebutPicker, dateFinPicker;

    @FXML
    private TextArea descText;


    @FXML
    private ChoiceBox<StatusEvenement> statusChoiceBox;

    @FXML
    private ChoiceBox<TypeEvenement> typeChoiceBox;

    @FXML
    private TextField ligneField;

    ServiceEvenement se = new ServiceEvenement();
    Evenements myEvent = se.getById(27);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeChoiceBox.getItems().setAll(TypeEvenement.values());
        statusChoiceBox.getItems().setAll(StatusEvenement.values());
        typeChoiceBox.setValue(myEvent.getType_evenement());
        statusChoiceBox.setValue(myEvent.getStatus_evenement());
        ligneField.setText(String.valueOf(myEvent.getId_ligne_affectee()));
        dateDebutPicker.setValue(myEvent.getDate_debut().toLocalDate());
        dateFinPicker.setValue(myEvent.getDate_fin().toLocalDate());
        descText.setText(myEvent.getDescription());
    }

    @FXML
    void clearAll() {
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

    @FXML
    void updateEvent() {
        if(dateDebutPicker.getValue().isBefore(dateFinPicker.getValue())){
            showAlert("Erreur", "date invalid.");
            return;
        }
        if ( descText.getText().isEmpty() || typeChoiceBox.getValue() == null || ligneField.getText()==null || dateDebutPicker == null || statusChoiceBox.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        Evenements updatedEvent = new Evenements(
                myEvent.getId_event(),
                typeChoiceBox.getValue(),
                Integer.parseInt(ligneField.getText()),
                descText.getText(),
                java.sql.Date.valueOf(dateDebutPicker.getValue()),
                java.sql.Date.valueOf(dateFinPicker.getValue()),
                statusChoiceBox.getValue(),4);

        se.update(updatedEvent);
        showAlert("Succès", "Événement mettre a jour avec succès!");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
