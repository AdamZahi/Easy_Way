package tn.esprit.controller.covoiturage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class option {

    @FXML
    private void option(ActionEvent event) {
        try {
            // Charger le fichier gestioncov.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Covoiturage/Gestioncov.fxml"));
            Parent gestionCovRoot = loader.load();

            // Récupérer la scène actuelle via le bouton cliqué
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé
            Scene scene = new Scene(gestionCovRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void offer(ActionEvent event) {

            try {
                // Charger le fichier gestioncov.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Covoiturage/offres.fxml"));
                Parent gestionCovRoot = loader.load();

                // Récupérer la scène actuelle via le bouton cliqué
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

                // Créer une nouvelle scène avec le contenu chargé
                Scene scene = new Scene(gestionCovRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
