package tn.esprit.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Events.Evenements;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventTable implements Initializable {
    @FXML
    private TableColumn<?, ?> actionColumn;
    @FXML
    private TableColumn<Evenements, String> debutColumn, descColumn, finColumn, lineColumn, statusColumn, typeColumn;
    @FXML
    private TableView<Evenements> eventTableView;
    ServiceEvenement se = new ServiceEvenement();
    ObservableList<Evenements> eventObservableList = FXCollections.observableList(se.getAll());

    private void addActionColumn(TableView<Evenements> table) {
        TableColumn<Evenements, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(createActionCell());
        table.getColumns().add(actionColumn);
    }

    private Callback<TableColumn<Evenements, Void>, TableCell<Evenements, Void>> createActionCell() {
        return param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox buttonsBox = new HBox(10, editButton, deleteButton);

            {
                editButton.getStyleClass().add("edit-button");
                deleteButton.getStyleClass().add("delete-button");

                editButton.setOnAction(event -> handleEdit(getIndex()));
                deleteButton.setOnAction(event -> handleDelete(getIndex()));
            }


        };

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type_evenement"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        debutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        finColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        lineColumn.setCellValueFactory(
                cellData -> {
                    Evenements event = cellData.getValue();
                    String lineInfo = se.getLigneInfo(event.getId_ligne_affectee()); // Fetch lineInfo
                    return new SimpleStringProperty(lineInfo);
                }
        );

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status_evenement"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("id_event"));
        eventTableView.setItems(eventObservableList);
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
    private void handleDelete(int index) {
    }

    private void handleEdit(int index) {
    }
}