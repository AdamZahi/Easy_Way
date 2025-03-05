package tn.esprit.test;

import tn.esprit.services.VehiculeService.ServiceVehicule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/test.fxml"));

        primaryStage.setTitle("Gestion des Véhicules");

        primaryStage.setScene(new Scene(root, 1000, 700));

        primaryStage.show();
    }
    public static void main(String[] args) {
        ServiceVehicule serviceVehicule = new ServiceVehicule();
//        Bus bus = new Bus(1, "B123", 50, Etat.DISPONIBLE, 1, TypeVehicule.BUS, 4, 4, TypeService.URBAIN, 30, "Compagnie ABC", true);
//        Metro metro = new Metro(3, "M456", 30, Etat.HORS_SERVICE, 2, TypeVehicule.METRO, 20, 15.0, 5, 10, "Propriétaire XYZ");
//        serviceVehicule.deleteByImmatriculation("B123");
        System.out.println(serviceVehicule.getAll());

        launch(args);


    }
}