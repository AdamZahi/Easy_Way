package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;



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

        ServiceUser serviceUser = new ServiceUser();  // Crée une instance
        User user = serviceUser.getUserByEmail(email);  // Appelle la méthode

        if (user == null) {
            showAlert("Erreur", "Utilisateur non trouvé. Vérifiez votre email.");
        } else if (!user.getMot_de_passe().equals(mdp)) {
            showAlert("Erreur", "Mot de passe incorrect.");
        } else {
            showAlert("Succès", "Connexion réussie !");
            // TODO : Rediriger vers la page principale après connexion
            // Exemple : changer de scène en JavaFX
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/main.fxml"));
            // Parent root = loader.load();
            // Stage stage = (Stage) signInButton.getScene().getWindow();
            // stage.setScene(new Scene(root));
            // stage.show();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
