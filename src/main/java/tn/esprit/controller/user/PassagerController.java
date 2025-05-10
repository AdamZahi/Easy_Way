package tn.esprit.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.user.Passager;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServicePassager;
import tn.esprit.services.user.ServiceUser;

import java.io.IOException;

public class PassagerController {

    @FXML
    private TextField nbTrajetsEffectuesField; // Champ pour le nombre de trajets

    @FXML
    private Button createAccountButton;

    private final ServicePassager passagerService;
    private final ServiceUser userService;
    private User user; // L'utilisateur qui doit compléter son profil

    public PassagerController() {
        this.passagerService = new ServicePassager();
        this.userService = new ServiceUser();
    }

    // ✅ Méthode pour recevoir l'utilisateur depuis SignUpController
    public void setUser(User user) {
        this.user = user;
        System.out.println("✅ Utilisateur reçu dans PassagerController : ID = " + user.getId_user());
    }

    @FXML
    private void initialize() {
        createAccountButton.setOnAction(event -> {
            if (user == null) {
                afficherAlerte("Erreur", "Aucun utilisateur reçu ! Veuillez vous inscrire d'abord.");
            } else {
                ajouterPassager();
            }
        });
    }

    private void ajouterPassager() {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            afficherAlerte("Erreur", "L'email de l'utilisateur est invalide.");
            return;
        }

        // 🔹 1️⃣ Vérifier si l'utilisateur existe déjà
        int idUser = userService.getUserIdByEmail(user.getEmail());

        if (idUser == 0) { // Si l'utilisateur n'existe pas, on l'ajoute
            userService.add(user);
            idUser = userService.getUserIdByEmail(user.getEmail()); // Récupérer son ID
            System.out.println("✅ Utilisateur ajouté avec ID : " + idUser);
        } else {
            System.out.println("ℹ️ Utilisateur déjà existant avec ID : " + idUser);
        }

        if (idUser <= 0) {
            afficherAlerte("Erreur", "Impossible de récupérer l'ID de l'utilisateur.");
            return;
        }

        user.setId_user(idUser); // Associer l'ID à l'utilisateur

        // 🔹 2️⃣ Vérification des champs passager
        String nbTrajetsEffectues = nbTrajetsEffectuesField.getText().trim();

        if (!validerChamps(nbTrajetsEffectues)) {
            return;
        }

        // 🔹 3️⃣ Création du passager
        Passager passager = new Passager(
                idUser,
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getMot_de_passe(),
                user.getTelephonne(),
                user.getPhoto_profil(),
                Integer.parseInt(nbTrajetsEffectues)
        );

        // 🔹 4️⃣ Ajout du passager avec vérification
        passagerService.add(passager);

        afficherAlerte("Succès", "Passager ajouté avec succès !");
        System.out.println("🚀 Passager ajouté avec ID utilisateur : " + idUser);

        redirigerVersPageSignIn();
    }
    private void redirigerVersPageSignIn() {
        try {
            // Créer une nouvelle scène pour la page de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/SignIn.fxml"));
            if (loader.getLocation() == null) {
                System.out.println("Erreur : Le fichier FXML 'SignIn.fxml' est introuvable.");
                return;
            }
            Parent root = loader.load();
            Scene signInScene = new Scene(root);

            // Obtenir le stage actuel (fenêtre)
            Stage stage = (Stage) nbTrajetsEffectuesField.getScene().getWindow();

            // Appliquer la scène de connexion
            stage.setScene(signInScene);
            stage.setTitle("Connexion");

            // Afficher la nouvelle scène (page de connexion)
            stage.show();
            System.out.println("Redirection vers la page de connexion réussie !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la redirection : " + e.getMessage());
        }
    }






    private boolean validerChamps(String nbTrajetsEffectues) {
        if (nbTrajetsEffectues.isEmpty()) {
            afficherAlerte("Validation", "Le nombre de trajets est obligatoire !");
            return false;
        }

        try {
            Integer.parseInt(nbTrajetsEffectues);
        } catch (NumberFormatException e) {
            afficherAlerte("Validation", "Le nombre de trajets doit être un nombre entier !");
            return false;
        }

        return true;
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
