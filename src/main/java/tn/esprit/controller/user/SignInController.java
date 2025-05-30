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
import org.mindrot.jbcrypt.BCrypt;


import java.io.IOException;
import tn.esprit.util.SessionManager;



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
    private void SignIn(ActionEvent event) {
        String email = emailField.getText();
        String mdp = MdpField.getText();

        // Vérifier que les champs ne sont pas vides
        if (email.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        // Rechercher l'utilisateur par email
        ServiceUser us = new ServiceUser();
        User user = us.getUserByEmail(email);

        if (user == null) {
            showAlert("Erreur", "Utilisateur non trouvé.", Alert.AlertType.ERROR);
            return;
        }

        if (!BCrypt.checkpw(mdp, user.getMot_de_passe())) {
            showAlert("Erreur", "Mot de passe incorrect.", Alert.AlertType.ERROR);
            return;
        }


        // Affichage pour débogage
        System.out.println("Utilisateur trouvé : " + user);
        System.out.println("Rôle de l'utilisateur : " + user.getRole());

        // Redirection selon le rôle
        try {
            FXMLLoader loader;
            Parent root;

            // Vérification du rôle et redirection
            if (user.getRole() == User.Role.ROLE_ADMIN) {
                loader = new FXMLLoader(getClass().getResource("/user//UsersList.fxml"));
                System.out.println("Redirection vers AdminDashboard");
            } else if (user.getRole() == User.Role.ROLE_CONDUCTEUR) {
                loader = new FXMLLoader(getClass().getResource("/Covoiturage/choix.fxml"));
                System.out.println("Redirection vers ConducteurDashboard");
            } else if (user.getRole() == User.Role.ROLE_PASSAGER) {
                loader = new FXMLLoader(getClass().getResource("/Covoiturage/choix.fxml"));
                System.out.println("Redirection vers PassagerDashboard");
            } else {
                showAlert("Erreur", "Rôle utilisateur inconnu.", Alert.AlertType.ERROR);
                return;
            }

            // Charger la vue de la nouvelle page
            root = loader.load();
            emailField.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger l'interface.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type); // Utilisation du type passé en paramètre
        alert.setTitle(title);
        alert.setHeaderText(null); // Supprime l'en-tête
        alert.setContentText(content); // Définit le message de contenu
        alert.showAndWait(); // Affiche l'alerte et attend la fermeture
    }


    @FXML
    void RedirectToSignUp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/SignUp.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/ResetPass.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectTo(String fxmlPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger " + fxmlPath, Alert.AlertType.ERROR);
        }
    }

}
