package tn.esprit.controller.vehicule;

import tn.esprit.models.vehicules.Train;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import tn.esprit.models.vehicules.Etat;
import tn.esprit.models.vehicules.TypeService;
import tn.esprit.models.vehicules.TypeVehicule;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.VehiculeService.ServiceVehicule;

public class AjouterTrainController {

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
    private TextField NombreWagonsField;
    @FXML
    private TextField proprietaireField;
    @FXML
    private TextField longuerReseauField;
    @FXML
    private TextField nombreLignesField;
    @FXML
    private TextField vitesseField;

    @FXML
    private void initialize() {
        // Remplir le ComboBox avec les valeurs de l'énumération Etat
        etatComboBox.getItems().setAll(Etat.values());
    }
    private ServiceVehicule vehiculeService = new ServiceVehicule();
    @FXML
    private void handleAjouter() {
        // Récupérer les valeurs des champs
        String immatricule = immatriculeField.getText();
        int capacite = Integer.parseInt(capaciteField.getText());
        Etat etat = etatComboBox.getValue();
        String nomConducteur = nomConducteurField.getText();
        String prenomConducteur = prenomConducteurField.getText();
        String depart = DepartField.getText();
        String arret = ArretField.getText();
        double longueurReseau = Double.parseDouble(longuerReseauField.getText());
        int nombreLignes = Integer.parseInt(nombreLignesField.getText());
        int nombreWagons = Integer.parseInt(NombreWagonsField.getText());
        String proprietaire = proprietaireField.getText();
        double vitesse = Double.parseDouble(vitesseField.getText());


        Train train = new Train();

        train.setImmatriculation(immatricule);
        train.setCapacite(capacite);
        train.setEtat(etat);
        train.setTypeVehicule(TypeVehicule.TRAIN);
        train.setIdConducteur(vehiculeService.getConducteurId(nomConducteur,prenomConducteur));
        train.setIdTrajet(vehiculeService.getTrajetId(depart,arret));
        train.setLongueurReseau(longueurReseau);
        train.setNombreLignes(nombreLignes);
        train.setNombreWagons(nombreWagons);
        train.setProprietaire(proprietaire);
        train.setVitesseMaximale(vitesse);


        vehiculeService.add(train);


        Stage stage = (Stage) etatComboBox.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleAnnuler() {

        Stage stage = (Stage) immatriculeField.getScene().getWindow();
        stage.close();
    }
}