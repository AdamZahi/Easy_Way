package tn.esprit.controller.covoiturage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Choix {

    @FXML
    private Button ReclamationBtn;
    @FXML
    private void handleCovoiturage(ActionEvent event) throws IOException {
        Parent optionView = FXMLLoader.load(getClass().getResource("/Covoiturage/option.fxml"));
        Scene optionScene = new Scene(optionView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(optionScene);
        window.show();
    }

    @FXML
    private void handleTransportPublic(ActionEvent event) {
        // Code similaire ou autre redirection pour le transport public
        System.out.println("Transport Public sélectionné");
    }

    @FXML
    void RedirectToReclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/ajoutReclamation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ReclamationBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
