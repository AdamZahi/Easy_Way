package tn.esprit.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceUser;

import java.io.IOException;

public class UsersListController {

    @FXML
    private ComboBox<String> actionComboBox;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private HBox headerBox;

    @FXML
    private Label usernameLabel;

    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("logout", "login");
        actionComboBox.setItems(options);

        // Gestion du choix dans le ComboBox
        actionComboBox.setOnAction(event -> {
            String selected = actionComboBox.getValue();
            if ("logout".equalsIgnoreCase(selected)) {
                handleLogout();
            } else if ("login".equalsIgnoreCase(selected)) {
                redirectToLogin();
            }
        });

        // Gestion directe par bouton logout (si utilisé)
        logoutButton.setOnAction(event -> handleLogout());

        // Chargement des infos utilisateur
        loadUserData();
    }

    private void handleLogout() {
        System.out.println("Utilisateur déconnecté.");
        redirectToLogin();
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/SignIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page de connexion.", Alert.AlertType.ERROR);
        }
    }

    private void loadUserData() {
        // TODO : Récupérer dynamiquement l'email utilisateur
        User user = serviceUser.getUserByEmail("example@example.com");

        if (user != null) {
            usernameLabel.setText(user.getNom() + " " + user.getPrenom());
        } else {
            showAlert("Erreur", "Utilisateur non trouvé.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void redirectTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger " + fxmlPath, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void RedirectToVehicule() {
        redirectTo("/vehicule/VehiculeList.fxml"); // ou le bon chemin FXML selon ton projet
    }

    @FXML
    private void RedirectToEvents() {
        redirectTo("/evenement/EventList.fxml"); // remplace le chemin par le bon fichier FXML de ta page événements
    }

    @FXML
    private void RedirectToReclamation() {
        redirectTo("/reclamation/ReclamationList.fxml");
    }

    @FXML
    private void RedirectToTrajet() {
        redirectTo("/trajet/TrajetList.fxml");
    }

    @FXML
    private void RedirectToLigne() {
        redirectTo("/ligne/LigneList.fxml");
    }


}
