package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.user.Conducteur;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceConducteur;
import tn.esprit.services.user.ServiceUser;

import java.io.IOException;

public class ConducteurController {

    @FXML
    private Label component;

    @FXML
    private Button createAccountButton;

    private final ServiceConducteur conducteurService;
    private final ServiceUser userService;
    private User user;

    public ConducteurController() {
        this.conducteurService = new ServiceConducteur();
        this.userService = new ServiceUser();
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("✅ Utilisateur reçu dans ConducteurController : ID = " + user.getId_user());
    }

    @FXML
    private void initialize() {
        createAccountButton.setOnAction(event -> {
            if (user == null) {
                afficherAlerte("Erreur", "Aucun utilisateur reçu ! Veuillez vous inscrire d'abord.");
            } else {
                ajouterConducteur();
            }
        });
    }

    private void ajouterConducteur() {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            afficherAlerte("Erreur", "L'email de l'utilisateur est invalide.");
            return;
        }

        int idUser = userService.getUserIdByEmail(user.getEmail());

        if (idUser == 0) {
            userService.add(user);
            idUser = userService.getUserIdByEmail(user.getEmail());
            System.out.println("✅ Utilisateur ajouté avec ID : " + idUser);
        } else {
            System.out.println("ℹ️ Utilisateur déjà existant avec ID : " + idUser);
        }

        user.setId_user(idUser);

        Conducteur conducteur = new Conducteur(
                idUser,
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getMot_de_passe(),
                user.getTelephonne(),
                user.getPhoto_profil(),
                0, // nb_trajet_effectues
                0  // nb_passager_transportes
        );

        conducteurService.add(conducteur);
        System.out.println("🚀 Conducteur ajouté avec ID utilisateur : " + idUser);
        afficherAlerte("Succès", "Conducteur ajouté avec succès !");
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void RedirectToSignIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/SignIn.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) component.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
