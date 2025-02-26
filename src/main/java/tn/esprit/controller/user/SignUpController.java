package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceUser;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        // Vérification des champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmMdp.isEmpty() || telephoneText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Validation de l'email
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'email n'est pas valide. Ex: exemple@mail.com");
            return;
        }

        // Validation du mot de passe
        if (!isValidPassword(mdp)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre.");
            return;
        }

        // Vérification de la confirmation du mot de passe
        if (!mdp.equals(confirmMdp)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Validation du numéro de téléphone
        if (!isValidPhoneNumber(telephoneText)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone est invalide. Il doit contenir uniquement des chiffres (8 chiffres pour la Tunisie).");
            return;
        }

        int telephone = Integer.parseInt(telephoneText);
        String hashedMdp = PasswordHash(mdp);

        // Ajout de l'utilisateur avec un rôle par défaut (par exemple, USER)
        User newUser = new User(nom, prenom, email, hashedMdp, telephone, "photo", User.Role.Passager);
        su.add(newUser);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte créé avec succès !");
    }

    /**
     * Affiche une boîte de dialogue d'alerte.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Vérifie si un email est valide.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Vérifie si un mot de passe est valide (au moins 8 caractères, une majuscule et un chiffre).
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    /**
     * Vérifie si un numéro de téléphone est valide (uniquement 8 chiffres pour la Tunisie).
     */
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{8}");
    }

    /**
     * Hashage du mot de passe avec SHA-256.
     */
    public static String PasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : rbt) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Redirection vers la page de connexion.
     */
    @FXML
    void RedirectToSignIn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/SignIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
