package tn.esprit.test.reclamation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class mainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
           URL fxmlUrl = getClass().getResource("/ajoutReclamation.fxml");
          //  URL fxmlUrl = getClass().getResource("/CardView.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find ajoutReclamation.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            // Créer la scène et l'ajouter au stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestion des Réclamations");
            primaryStage.setScene(scene);

            // Personnalisation de la taille de la fenêtre si besoin
          //  primaryStage.setWidth(580);
         //   primaryStage.setHeight(620);

            // Afficher la fenêtre
            primaryStage.show();

        } catch (IOException e) {
            // Amélioration de la gestion des exceptions
            System.err.println("Erreur lors du chargement du fichier FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
