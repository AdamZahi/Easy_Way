package tn.esprit.controller.vehicule;

import tn.esprit.controller.vehicule.AjouterBusController;
import tn.esprit.controller.vehicule.AjouterMetroController;
import tn.esprit.controller.vehicule.AjouterTrainController;
import tn.esprit.models.vehicules.vehicule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.services.VehiculeService.ServiceVehicule;

import java.sql.Connection;

public class VehiculeController {

    @FXML
    private TableView<vehicule> busTable;

    @FXML
    private TableView<vehicule> metroTable;

    @FXML
    private TableView<vehicule> trainTable;

    private ServiceVehicule vehiculeService = new ServiceVehicule();
    private Connection connection;

    @FXML
    private void initialize() {
        // Add action column to each table
        addActionColumn(busTable);
        addActionColumn(metroTable);
        addActionColumn(trainTable);
    }

    private void addActionColumn(TableView<vehicule> table) {
        TableColumn<vehicule, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(createActionCell());
        table.getColumns().add(actionColumn);
    }

    private Callback<TableColumn<vehicule, Void>, TableCell<vehicule, Void>> createActionCell() {
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

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonsBox);
                }
            }
        };
    }

    private void handleEdit(int index) {
        System.out.println("Modification de la ligne : " + index);
    }

    private void handleDelete(int index) {
        System.out.println("Suppression de la ligne : " + index);
    }


    @FXML
    private void handleAddBus() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulaireBus.fxml"));
            Scene scene = new Scene(loader.load());


            AjouterBusController controller = loader.getController();


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un bus");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddMetro(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulaireMetro.fxml"));
            Scene scene = new Scene(loader.load());
            AjouterMetroController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un Metro");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    @FXML
    public void handleAddTrain(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulaireTrain.fxml"));
            Scene scene = new Scene(loader.load());
            AjouterTrainController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un Metro");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
