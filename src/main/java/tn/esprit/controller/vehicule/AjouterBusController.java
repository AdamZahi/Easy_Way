package tn.esprit.controller.vehicule;

import tn.esprit.models.vehicules.Bus;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.vehicules.Etat;
import tn.esprit.models.vehicules.TypeService;
import tn.esprit.models.vehicules.TypeVehicule;
import tn.esprit.services.VehiculeService.ServiceVehicule;

public class AjouterBusController {

    @FXML
    private TextField immatriculeField;
    @FXML
    private TextField capaciteField;
    @FXML
    private ComboBox<Etat> etatComboBox;
    @FXML
    private TextField nomConducteurField;
    @FXML
    private TextField prenomConducteurField;
    @FXML
    private TextField DepartField;
    @FXML
    private TextField ArretField;
    @FXML
    private TextField nombrePortesField;
    @FXML
    private ComboBox<TypeService> typeServiceComboBox;
    @FXML
    private TextField placesField;
    @FXML
    private TextField compagnieField;
    @FXML
    private CheckBox climatisationCheckBox;

    private ServiceVehicule vehiculeService = new ServiceVehicule(); // Instance du service

    @FXML
    private void initialize() {
        // Remplir les ComboBox avec les valeurs des énumérations
        typeServiceComboBox.getItems().setAll(TypeService.values());
        etatComboBox.getItems().setAll(Etat.values());
    }

    @FXML
    private void handleAjouter() {
        // Récupérer les valeurs du formulaire
        String immatricule = immatriculeField.getText();
        int capacite = Integer.parseInt(capaciteField.getText());
        Etat etat = etatComboBox.getValue();
        String nomConducteur = nomConducteurField.getText();
        String prenomConducteur = prenomConducteurField.getText();
        String arretTrajet = ArretField.getText();
        String departTrajet = DepartField.getText();
        int nombrePortes = Integer.parseInt(nombrePortesField.getText());
        TypeService typeService = typeServiceComboBox.getValue();
        int places = Integer.parseInt(placesField.getText());
        String compagnie = compagnieField.getText();
        boolean climatisation = climatisationCheckBox.isSelected();


        Bus bus = new Bus();
        bus.setImmatriculation(immatricule);
        bus.setCapacite(capacite);
        bus.setEtat(etat);
        bus.setIdConducteur(vehiculeService.getConducteurId(nomConducteur, prenomConducteur)); // Call the service method
        bus.setIdTrajet(vehiculeService.getTrajetId(departTrajet,arretTrajet));
        bus.setTypeVehicule(TypeVehicule.BUS); // Supposons que le type de véhicule est URBAIN pour un bus
        bus.setNombrePortes(nombrePortes);
        bus.setTypeService(typeService);
        bus.setNombreDePlaces(places);
        bus.setCompagnie(compagnie);
        bus.setClimatisation(climatisation);

        vehiculeService.add(bus);


        Stage stage = (Stage) immatriculeField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnnuler() {

        Stage stage = (Stage) immatriculeField.getScene().getWindow();
        stage.close();
    }
}