package tn.esprit.controller.vehicule;

import tn.esprit.models.vehicules.Etat;
import tn.esprit.models.vehicules.TypeService;
import tn.esprit.models.vehicules.TypeVehicule;
import tn.esprit.models.vehicules.Metro;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.VehiculeService.ServiceVehicule;


public class AjouterMetroController {
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
    private TextField longuerReseauField;
    @FXML
    private TextField nombreLignesField;
    @FXML
    private TextField ramesField;
    @FXML
    private TextField ProprieteField;

    private ServiceVehicule serviceVehicule = new ServiceVehicule();

    @FXML
    private void initialize() {

        etatComboBox.getItems().setAll(Etat.values());
    }

    @FXML
    private void handleAjouter() {

        String immatricule = immatriculeField.getText();
        int capacite = Integer.parseInt(capaciteField.getText());
        Etat etat = etatComboBox.getValue();
        String nomConducteur = nomConducteurField.getText();
        String prenomConducteur = prenomConducteurField.getText();
        String lieuDepart = DepartField.getText();
        String lieuArrivee = ArretField.getText();
        double longueurReseau = Double.parseDouble(longuerReseauField.getText());
        int nombreLignes = Integer.parseInt(nombreLignesField.getText());
        int rames = Integer.parseInt(ramesField.getText());
        String propriete = ProprieteField.getText();


        Metro metro = new Metro();
        metro.setImmatriculation(immatricule);
        metro.setCapacite(capacite);
        metro.setEtat(etat);
        metro.setIdConducteur(serviceVehicule.getConducteurId(nomConducteur, prenomConducteur));
        metro.setIdTrajet(serviceVehicule.getTrajetId(lieuDepart,lieuArrivee));
        metro.setTypeVehicule(TypeVehicule.METRO);
        metro.setLongueurReseau(longueurReseau);
        metro.setNombreLignes(nombreLignes);
        metro.setNombreRames(rames);
        metro.setProprietaire(propriete);


        serviceVehicule.add(metro);


        Stage stage = (Stage) immatriculeField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnnuler() {
        Stage stage = (Stage) etatComboBox.getScene().getWindow();
        stage.close();
    }}