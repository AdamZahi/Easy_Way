package tn.esprit.controller.vehicule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.event.ServiceEvenement;
import java.io.IOException;


public class AddEventController {
    @FXML
    private TextField id_tf;
    @FXML
    private DatePicker date_debut_picker, date_fin_picker;
    @FXML
    private Button navigate_to_list;


    @FXML
    void addEvent(ActionEvent event) throws IOException {
        ServiceEvenement se = new ServiceEvenement();
        // Use LocalDate to create the dates
        java.time.LocalDate localDateDebut = date_debut_picker.getValue();
        java.sql.Date dateDebut = java.sql.Date.valueOf(localDateDebut);

        java.time.LocalDate localDateFin = date_fin_picker.getValue();
        java.sql.Date dateFin = java.sql.Date.valueOf(localDateFin);

        se.add(new Evenements(
                0,
                TypeEvenement.INCIDENT,
                5,
                id_tf.getText(),
                dateDebut,
                dateFin,
                StatusEvenement.ANNULE,
                4
        ));

    }

    @FXML
    void goToEventDetails(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/event/ListPage.fxml"));
        root = loader.load();

        // Get the stage from the event source
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // Set the new scene and show
        stage.setScene(scene);
        stage.show();
    }
}
