package tn.esprit.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Events.Evenements;
import tn.esprit.services.ServiceEvenement;

public class ShowList {
    @FXML
    private TableView<Evenements> tableView;
    @FXML
    TableColumn<Evenements, Void> actionColumn;

    @FXML
    private TableColumn<?, ?> debutColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> finColumn;

    @FXML
    private TableColumn<?, ?> ligneColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;
    @FXML
    private TableView<Evenements> eventTable;

    private ObservableList<Evenements> eventData = FXCollections.observableArrayList();

    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    public void initialize() {
        // Fetch data from the database using ServiceEvenement
        fetchDataFromDatabase();

        // Bind the data to the TableView
        eventTable.setItems(eventData);
    }

    private void fetchDataFromDatabase() {
        // Use ServiceEvenement to get all events
        eventData.addAll(serviceEvenement.getAll());
    }

    @FXML
    void addEvent(ActionEvent event) {

    }

    @FXML
    void searchEvent(ActionEvent event) {

    }

}