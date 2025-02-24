package tn.esprit.controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.ServiceEvenement;

import java.util.List;

public class EvenementList {
    @FXML
    private TableView<Evenements> tableView;
    @FXML
    private TableColumn<Evenements, String> eventColumn;
    @FXML
    private TableColumn<Evenements, String> statusColumn;
    @FXML
    private TableColumn<Evenements, Void> actionColumn;
    @FXML
    private Button viewButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ServiceEvenement se = new ServiceEvenement();
    public void initialize() {
        tableView.setStyle("-fx-background-color: #FFFFFF;");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        viewButton.setStyle("-fx-background-color: #008000; -fx-text-fill: white;");
        editButton.setStyle("-fx-background-color: #EF9A9A; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");

        // Initialize Table Columns
        eventColumn.setCellValueFactory(cellData -> {
            Evenements event = cellData.getValue();
            String ligneInfo = se.getLigneInfo(event.getId_ligne_affectee());
            String formattedText = String.format(String.format(event.getType_evenement() + " en ligne " + ligneInfo + " dés le " + event.getDate_debut()));
            return new SimpleStringProperty(formattedText);
        });

        // Load data
        loadEvents();
    }
    private void loadEvents() {
        List<Evenements> events = se.getAll();
        ObservableList<Evenements> eventList = FXCollections.observableArrayList(events);
        tableView.setItems(eventList);
    }

}
