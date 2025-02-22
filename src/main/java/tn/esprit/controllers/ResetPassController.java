package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.services.EmailService;

import java.security.SecureRandom;

public class ResetPassController {

    @FXML
    private TextField emailfield;

    @FXML
    private TextField codefield;

    @FXML
    private Button envoyercode;

    @FXML
    private Button verifiercode;

    private final EmailService emailService = new EmailService(); // Instance du service d'email
    private String generatedCode; // Stocke le code généré

    /**
     * Méthode pour générer un code aléatoire à 6 chiffres
     */
    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(code);
    }

    /**
     * Méthode pour envoyer un email avec un code de réinitialisation
     */
    @FXML
    private void EnvoyerCode() {
        String recipient = emailfield.getText().trim();

        if (recipient.isEmpty()) {
            showAlert(AlertType.WARNING, "Champ vide", "Veuillez entrer votre adresse email.");
            return;
        }

        generatedCode = generateRandomCode(); // Générer et stocker un code aléatoire
        String subject = "Code de réinitialisation";
        String body = "Votre code de réinitialisation est : " + generatedCode;

        try {
            emailService.sendEmail(recipient, subject, body);
            showAlert(AlertType.INFORMATION, "Code Envoyé", "Un code a été envoyé à : " + recipient);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur d'envoi", "Impossible d'envoyer l'email. Vérifiez l'adresse et réessayez.");
        }
    }

    /**
     * Méthode pour vérifier si l'utilisateur a entré le bon code
     */
    @FXML
    private void VerifierCode() {
        String enteredCode = codefield.getText().trim();

        if (enteredCode.isEmpty()) {
            showAlert(AlertType.WARNING, "Champ vide", "Veuillez entrer le code reçu.");
            return;
        }

        if (enteredCode.equals(generatedCode)) {
            showAlert(AlertType.INFORMATION, "Code valide", "Le code est correct. Vous pouvez maintenant réinitialiser votre mot de passe.");
        } else {
            showAlert(AlertType.ERROR, "Code incorrect", "Le code saisi est invalide. Veuillez réessayer.");
        }
    }

    /**
     * Méthode pour afficher des alertes
     */
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
