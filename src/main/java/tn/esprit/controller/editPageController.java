package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class editPageController {
    Stage stage;
    Scene scene;
    Parent root;
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
    Evenements currentEvent = se.getById(27);

    @FXML
    public void initialize() {
        typeChoiceBox.getItems().setAll(TypeEvenement.values());
        statusChoiceBox.getItems().setAll(StatusEvenement.values());
    }

    public void initData(Evenements event) {
        this.currentEvent = event;

        descText.setText(event.getDescription());
        typeChoiceBox.setValue(event.getType_evenement());
        statusChoiceBox.setValue(event.getStatus_evenement());
        ligneField.setText(String.valueOf(event.getId_ligne_affectee()));
        dateDebutPicker.setValue(event.getDate_debut().toLocalDate());
        dateFinPicker.setValue(event.getDate_fin().toLocalDate());

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/eventTable.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateEvent() {
        if(dateDebutPicker.getValue().isAfter(dateFinPicker.getValue())){
            showAlert("Error", "Date invalid!");
            return;
        }
        if ( descText.getText().isEmpty() || typeChoiceBox.getValue() == null || ligneField.getText()==null || dateDebutPicker == null || statusChoiceBox.getValue() == null) {
            showAlert("Error", "Veuillez remplir tous les champs.");
            return;
        }
        Evenements updatedEvent = new Evenements(
                currentEvent.getId_event(),
                typeChoiceBox.getValue(),
                Integer.parseInt(ligneField.getText()),
                descText.getText(),
                java.sql.Date.valueOf(dateDebutPicker.getValue()),
                java.sql.Date.valueOf(dateFinPicker.getValue()),
                statusChoiceBox.getValue(),4);

        se.update(updatedEvent);
        showAlert("Success", "Événement mettre a jour avec succès!");
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/eventTable.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) descText.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
