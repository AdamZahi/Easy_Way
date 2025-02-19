package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.util.regex.Pattern;

public class SignUpController {

    @FXML
    private TextField EmailField;

    @FXML
    private TextField MotDePasseField;

    @FXML
    private TextField NomField;

    @FXML
    private ImageView PhotoProfil;

    @FXML
    private TextField PrenomField;

    @FXML
    private TextField confirmMdpField;

    @FXML
    private Button createAccountButton;

    @FXML
    private ImageView logo;

    @FXML
    private TextField telephonneField;

    @FXML
    void SignUp(ActionEvent event) {
        ServiceUser su = new ServiceUser();
        String nom = NomField.getText().trim();
        String prenom = PrenomField.getText().trim();
        String email = EmailField.getText().trim();
        String mdp = MotDePasseField.getText();
        String confirmMdp = confirmMdpField.getText();
        String telephoneText = telephonneField.getText().trim();

        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmMdp.isEmpty() || telephoneText.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Vérifier que l'email est valide
        if (!isValidEmail(email)) {
            showAlert("Erreur", "L'email n'est pas valide. Ex: exemple@mail.com");
            return;
        }

        // Vérifier la sécurité du mot de passe (au moins 8 caractères, 1 lettre majuscule, 1 chiffre)
        if (!isValidPassword(mdp)) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre.");
            return;
        }

        // Vérifier que les mots de passe correspondent
        if (!mdp.equals(confirmMdp)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Vérifier que le numéro de téléphone est valide (uniquement des chiffres et longueur correcte)
        if (!isValidPhoneNumber(telephoneText)) {
            showAlert("Erreur", "Le numéro de téléphone est invalide. Il doit contenir uniquement des chiffres (8 chiffres pour la Tunisie).");
            return;
        }

        int telephone = Integer.parseInt(telephoneText);

        // Si toutes les vérifications passent, on ajoute l'utilisateur
        su.add(new User(nom, prenom, email, mdp, telephone , "photo"));
        showAlert("Succès", "Compte créé avec succès !");
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Vérifie si l'email est valide
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Vérifie si le mot de passe est sécurisé
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$"; // Min 8 caractères, 1 majuscule, 1 chiffre
        return Pattern.matches(passwordRegex, password);
    }

    // Vérifie si le numéro de téléphone est valide
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{8}"); // Uniquement 8 chiffres (Tunisie)
    }
}


