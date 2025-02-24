package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.user.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;


public class SignInController {

    @FXML
    private PasswordField MdpField;

    @FXML
    private TextField emailField;

    @FXML
    private Button signInButton;

    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    void SignIn(ActionEvent event) {
        String email = emailField.getText().trim();
        String mdp = MdpField.getText().trim();

        if (email.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        ServiceUser serviceUser = new ServiceUser();
        User user = serviceUser.getUserByEmail(email);

        if (user == null) {
            showAlert("Erreur", "Utilisateur non trouvé. Vérifiez votre email.");
        } else if (!user.getMot_de_passe().equals(mdp)) {
            showAlert("Erreur", "Mot de passe incorrect.");
        } else {
            showAlert("Succès", "Connexion réussie !");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UsersList.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de charger l'interface utilisateur.");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void RedirectToSignUp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RedirectToResetPass(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPass.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
