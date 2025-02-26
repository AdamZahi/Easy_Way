package tn.esprit.controller.reclamation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.reclamation.categories;
import tn.esprit.models.reclamation.reclamations;
import tn.esprit.services.reclamation.categorieService;
import tn.esprit.services.reclamation.reclamationService;
import tn.esprit.util.MyDataBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import tn.esprit.services.reclamation.MailService;



public class AjoutReclamation {

    private final reclamationService reclamationService = new reclamationService();
    private final categorieService categorieService = new categorieService();
    @FXML
    public TextField descriptionn;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<categories> categorie;
    @FXML
    private TextField sujet;
    @FXML
    private DatePicker date; // Ajouter la DatePicker ici
    @FXML
    private HBox boxh; // Conteneur pour afficher les réclamations
    private int currentId;
    private Connection connection = MyDataBase.getInstance().getConnection();

    @FXML
    private Label categoriecontrol;
    @FXML
    private Label datecontrol;
    @FXML
    private Label descriptioncontrol;
    @FXML
    private Label emailcontrol;
    @FXML
    private Label sujetcontrol;
    @FXML
    private Label messagerec;



    @FXML
    public void initialize() {
        if (categorie != null) {
            List<categories> categoriesList = categorieService.getAll();
            categorie.getItems().addAll(categoriesList);
        } else {
            System.err.println("ComboBox categorie not initialized!");
        }
    }




    @FXML
    void addReclamation(ActionEvent event) {
        String emailText = this.email.getText();
        categories selectedCategorie = categorie.getValue();
        String sujetText = this.sujet.getText();
        String descriptionText = this.descriptionn.getText();
        LocalDate dateIncident = date.getValue();

        // Réinitialiser les messages d'erreur
        emailcontrol.setText("");
        sujetcontrol.setText("");
        descriptioncontrol.setText("");
        categoriecontrol.setText("");
        datecontrol.setText("");
        messagerec.setText("");

        boolean isValid = true;

        // Vérifications
        if (emailText.isEmpty()) {
            emailcontrol.setText("L'email est requis.");
            isValid = false;
        } else if (!emailText.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            emailcontrol.setText("Format de l'email invalide.");
            isValid = false;
        }

        if (sujetText.isEmpty()) {
            sujetcontrol.setText("Le sujet est requis.");
            isValid = false;
        }

        if (descriptionText.isEmpty()) {
            descriptioncontrol.setText("La description est requise.");
            isValid = false;
        }

        if (selectedCategorie == null) {
            categoriecontrol.setText("La catégorie est requise.");
            isValid = false;
        }

        if (dateIncident == null) {
            datecontrol.setText("La date d'incidence est requise.");
            isValid = false;
        }

        if (isValid) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'ajout");
            alert.setHeaderText("Voulez-vous vraiment ajouter cette réclamation ?");
            alert.setContentText("Cliquez sur OK pour confirmer, Annuler pour annuler.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    if (currentId > 0) {
                        // Mode modification
                        reclamationService.update(new reclamations(currentId, emailText, selectedCategorie, sujetText, "En attente", descriptionText, dateIncident.toString()));
                        messagerec.setText("Réclamation mise à jour avec succès !");
                    } else {
                        // Mode ajout
                        reclamationService.add(new reclamations(emailText, selectedCategorie, sujetText, "En attente", descriptionText, dateIncident.toString()));
                        messagerec.setText("Réclamation ajoutée avec succès !");

                        // ✅ Envoi d'un mail de confirmation
                        new Thread(() -> {
                            String subject = "Confirmation de votre réclamation";
                            String body = "Bonjour,\n\nVotre réclamation a bien été reçue.\n\nDétails :\n" +
                                    "Sujet : " + sujetText + "\n" +
                                    "Description : " + descriptionText + "\n" + "Description : " + descriptionText + "\n" +
                                    "Date : " + dateIncident.toString() + "\n\nMerci de votre patience.";
                            MailService.sendMail(emailText, subject, body);
                        }).start();
                    }

                    // ✅ Vider les champs après l'ajout
                    clearFields();

                    // ✅ Réinitialiser le message après 3 secondes
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            Platform.runLater(() -> messagerec.setText(""));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearFields() {
        email.clear();
        sujet.clear();
        descriptionn.clear();
        categorie.setValue(null);
        date.setValue(null);
    }







    public void gotoCardView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/reclamation/CardView.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void setReclamationDetails(int id, String email, String sujet, String description, String dateCreation, int categorieId) {
        this.email.setText(email);
        this.sujet.setText(sujet);
        this.descriptionn.setText(description);
        this.date.setValue(LocalDate.parse(dateCreation));

        // Sélectionner la catégorie dans le ComboBox
        for (categories cat : categorie.getItems()) {
            if (cat.getId() == categorieId) {
                categorie.setValue(cat);
                break;
            }
        }

        // Stocker l'ID pour l'utiliser lors de la mise à jour
        this.currentId = id;
    }


    public void update(reclamations rec) {
        String updateQuery = "UPDATE reclamation SET email = ?, categorieId = ?, sujet = ?, description = ?, date_creation = ? WHERE id = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);

            pstmt.setString(1, rec.getEmail());
            pstmt.setInt(2, rec.getCategorie().getId());
            pstmt.setString(3, rec.getSujet());
            pstmt.setString(4, rec.getDescription());
            pstmt.setString(5, rec.getDate_creation());
            pstmt.setInt(6, rec.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<reclamations> getAllReclamationsSansId() {
        List<reclamations> listeReclamations = new ArrayList<>();
        String query = "SELECT id, email, sujet, description, date_creation, categorieId FROM reclamation"; // Ajout de 'id'
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reclamations rec = new reclamations();
                rec.setId(rs.getInt("id")); // Assigner l'ID correctement
                rec.setEmail(rs.getString("email"));
                rec.setSujet(rs.getString("sujet"));
                rec.setDescription(rs.getString("description"));
                rec.setDate_creation(rs.getString("date_creation"));
                // Assurez-vous que la catégorie est bien récupérée
                rec.setCategorie(new categories(rs.getInt("categorieId"), "NomCategorie"));

                listeReclamations.add(rec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeReclamations;
    }







}
