package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Events.Evenements;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventTable implements Initializable {

    ServiceEvenement se = new ServiceEvenement();
    @FXML
    private GridPane eventGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEvents();
    }

    private void loadEvents() {
        List<Evenements> events = se.getAll();
        eventGrid.getChildren().clear();

        int row = 0;
        int col = 0;
        for (Evenements e : events) {
            VBox card = createEventCard(e);
            eventGrid.add(card, col, row);

            col++;
            if (col == 2) { // 2 carte par ligne
                col = 0;
                row++;
            }
        }
    }

    private VBox createEventCard(Evenements evenement) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: #D32F2F; -fx-border-radius: 10px;");

        Text type = new Text("📌 Type: " + evenement.getType_evenement());
        Text description = new Text("📝 Description: " + evenement.getDescription());
        Text dateDebut = new Text("📅 Début: " + evenement.getDate_debut());
        Text dateFin = new Text("📅 Fin: " + evenement.getDate_fin());
        Text ligne = new Text("🚋 Ligne: " + evenement.getId_ligne_affectee());
        Text status = new Text("\uD83D\uDEA6 Status: " + evenement.getStatus_evenement());

        Button editButton = new Button("✏ Modifier");
        editButton.setStyle("-fx-background-color: #EF9A9A; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            try {
                handleEdit(evenement);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button deleteButton = new Button("🗑 Supprimer");
        deleteButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            try {
                handleDelete(evenement.getId_event());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox actions = new HBox(10, editButton, deleteButton);

        card.getChildren().addAll(type, description, dateDebut, dateFin, ligne, status, actions);
        return card;
    }

    private void handleEdit(Evenements evenements) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/editEventPage.fxml"));
            Parent root = loader.load();
            editPageController editController = loader.getController();
            editController.initData(evenements);

            Stage stage = (Stage) eventGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleDelete(int id_event) throws IOException {
        se.delete(id_event);
        editPageController epc = new editPageController();
        if(se.getById(id_event)==null) {
            epc.showAlert("Success", "Événement est supprimée avec succès!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/eventTable.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) eventGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
        else
            epc.showAlert("Error","Error lors de supprission de ce Événement");

    }


    @FXML
    void goToAddForm(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/AddEventForm.fxml"));
        root = loader.load();
        // Get the stage from the event source
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // Set the new scene and show
        stage.setScene(scene);
        stage.show();
    }
}