package tn.esprit.controller.user;

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
import tn.esprit.services.user.ServiceUser;
import tn.esprit.util.SessionManager;

import java.io.IOException;

public class SignInController {

    @FXML
    private PasswordField MdpField;

    @FXML
    private TextField emailField;

    @FXML
    private Button signInButton;

    private ServiceUser serviceUser = new ServiceUser();

    private String email;

    public void setEmail(String email) {
        this.email = email;
        System.out.println("Email reçu dans SignInController : " + email);
    }

    @FXML
    void SignIn(ActionEvent event) {
        String email = emailField.getText().trim();
        String mdp = MdpField.getText().trim();

        if (email.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        User user = serviceUser.getUserByEmail(email);

        if (user == null) {
            showAlert("Erreur", "Utilisateur non trouvé. Vérifiez votre email.");
        } else if (!user.getMot_de_passe().equals(mdp)) {
            showAlert("Erreur", "Mot de passe incorrect.");
        } else {
            System.out.println("Utilisateur trouvé : " + user.getNom());
            System.out.println("ID de l'utilisateur récupéré : " + user.getId_user());

            SessionManager.getInstance().setUser(user);

            System.out.println("ID utilisateur dans SessionManager : " + SessionManager.getInstance().getId_user());

            showAlert("Succès", "Connexion réussie !");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/covoiturage/Choix.fxml"));
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
        loadPage(event, "/user/SignUp.fxml");
    }

    @FXML
    void RedirectToResetPass(MouseEvent event) {
        loadPage(event, "/user/ResetPass.fxml");
    }

    private void loadPage(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
