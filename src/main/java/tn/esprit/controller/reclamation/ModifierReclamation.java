package tn.esprit.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.models.reclamation.categories;
import tn.esprit.models.reclamation.categories;
import tn.esprit.services.reclamation.categorieService;
import tn.esprit.util.MyDataBase;
import tn.esprit.controller.reclamation.StaticStatu;
import java.io.IOException;
import java.sql.ResultSet;
import tn.esprit.controller.reclamation.CardView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModifierReclamation {

    @FXML
    private Label messageRecModifier;

    @FXML
    private Button modifierRec;

    @FXML
    private ComboBox<categories> nouvCategorie;
    @FXML
    private DatePicker nouvDate;
    @FXML
    private TextArea nouvDescriptionn;
    @FXML
    private ComboBox<String> nouvStatu;
    @FXML
    private Button gogo;
    @FXML
    private TextField nouvSujet;

    private Connection connection = MyDataBase.getInstance().getCnx();
    private final tn.esprit.services.reclamation.categorieService categorieService = new categorieService();

    private int currentId; // Pour garder l'ID de la réclamation en cours


    @FXML
    public void initialize() {
        if (nouvCategorie != null) {
            // Récupérer les catégories et les ajouter
            List<categories> categoriesList = categorieService.getAll();
            nouvCategorie.getItems().addAll(categoriesList);

            // Définir le StringConverter après avoir ajouté les éléments
            nouvCategorie.setConverter(new StringConverter<categories>() {
                @Override
                public String toString(categories cat) {
                    return (cat != null) ? cat.getType() : ""; // Vérifie bien que `getType()` existe
                }

                @Override
                public categories fromString(String string) {
                    return nouvCategorie.getItems().stream()
                            .filter(cat -> cat.getType().equals(string)) // Vérifie bien `getType()`
                            .findFirst()
                            .orElse(null);
                }
            });
        } else {
            System.err.println("ComboBox categorie not initialized!");
        }

        // Ajouter les statuts
        nouvStatu.getItems().addAll("En attente", "Traité", "Refusé");
    }


    public void setReclamationDetails(int id, String sujet, String description, int category_id_id, String date, String statut) {
        this.nouvSujet.setText(sujet);
        this.nouvSujet.setEditable(false); // lecture seule

        this.nouvDescriptionn.setText(description);
        this.nouvDescriptionn.setEditable(false); // lecture seule

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        this.nouvDate.setValue(dateTime.toLocalDate());
        this.nouvDate.setDisable(true); // lecture seule


        // Sélectionner la catégorie
        for (categories cat : nouvCategorie.getItems()) {
            if (cat.getId() == category_id_id) {
                nouvCategorie.setValue(cat);
                break;
            }
        }
        nouvCategorie.setDisable(true);
      


        nouvStatu.setValue(statut); // seul champ modifiable

        this.currentId = id;
    }


    @FXML
    void gotoCardView1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/reclamation/CardView.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modifierReclamation(ActionEvent event) {
        try {
            if (nouvStatu.getValue() == null) {
                messageRecModifier.setText("Veuillez sélectionner un statut !");
                return;
            }

            String query = "UPDATE reclamation SET statut = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, nouvStatu.getValue());
            pstmt.setInt(2, currentId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                messageRecModifier.setText("Statut mis à jour avec succès !");
            } else {
                messageRecModifier.setText("Erreur lors de la mise à jour !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageRecModifier.setText("Erreur SQL lors de la mise à jour !");
        }
    }


    public void gogotoStatestique(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/reclamation/staticStatu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void clearFields() {
        nouvSujet.clear();
        nouvDescriptionn.clear();
        nouvCategorie.setValue(null);
        nouvDate.setValue(null);
        nouvStatu.setValue(null);
    }
}


