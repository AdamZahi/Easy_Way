package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceUser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Button changePhotoButton;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button saveButton;

    @FXML
    private TextField telephoneField;

    private final ServiceUser userService = new ServiceUser();
    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger l'utilisateur actuel (supposons que l'ID est 1 pour l'exemple)
        currentUser = userService.getById(1);
        if (currentUser != null) {
            loadUserData();
        }
    }

    private void loadUserData() {
        nomField.setText(currentUser.getNom());
        prenomField.setText(currentUser.getPrenom());
        emailField.setText(currentUser.getEmail());
        telephoneField.setText(String.valueOf(currentUser.getTelephonne())); // Conversion sécurisée en String

        // Charger l'image de profil
        if (currentUser.getPhoto_profil() != null && !currentUser.getPhoto_profil().isEmpty()) {
            profileImageView.setImage(new Image(new File(currentUser.getPhoto_profil()).toURI().toString()));
        }
    }

    @FXML
    void changeProfilePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            profileImageView.setImage(new Image(selectedFile.toURI().toString()));
            currentUser.setPhoto_profil(selectedFile.getAbsolutePath()); // Sauvegarde du chemin de l'image
        }
    }

    @FXML
    void saveProfileChanges(ActionEvent event) {
        if (!currentPasswordField.getText().isEmpty() && !newPasswordField.getText().isEmpty()) {
            if (!currentUser.getMot_de_passe().equals(currentPasswordField.getText())) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect.");
                return;
            }
            currentUser.setMot_de_passe(newPasswordField.getText());
        }

        currentUser.setNom(nomField.getText());
        currentUser.setPrenom(prenomField.getText());
        currentUser.setEmail(emailField.getText());

        // Conversion sécurisée de String en int pour le téléphone
        try {
            currentUser.setTelephonne(Integer.parseInt(telephoneField.getText()));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone doit être un nombre valide.");
            return;
        }

        try {
            userService.update(currentUser); // Suppression de la condition sur update()
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Profil mis à jour avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour du profil.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
