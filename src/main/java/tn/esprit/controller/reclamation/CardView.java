package tn.esprit.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.reclamation.reclamations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import tn.esprit.services.reclamation.reclamationService;


import tn.esprit.util.MyDataBase;


public class CardView {

    public Button suppbtn;
    public Button modfbtn;
    @FXML
    private Label emailLabel;

    @FXML
    private Label categorieLabel;

    @FXML
    private Label sujetLabel;

    @FXML
    private Label statutLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox cardBox;
    private Connection connection = MyDataBase.getInstance().getConnection();
    
    @FXML
    private TextField txtId;
    @FXML
    private Label lblEmail, lblSujet, lblDescription, lblCategorie, lblDate , lblMessage;
    @FXML
    private GridPane gridPaneReclamations;

    @FXML
    private ComboBox<String> comboBoxTrier;

    private final reclamationService reclamationService = new reclamationService(); // ✅ Ajout de cette ligne
    

    public void gotoAjoutReclamation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/reclamation/ajoutReclamation.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        System.out.println("Initialisation de l'interface...");
        afficherReclamations(); // Appel automatique de l'affichage des réclamations
        comboBoxTrier.getItems().addAll("email", "sujet", "description", " catégorie", "date");
    }


    @FXML
    private void afficherReclamations() {
        List<reclamations> reclamations = reclamationService.getAllReclamationsSansId();
        remplirGridPane(reclamations);

    }


    @FXML
    private void supprimerReclamation2() {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) {
            lblMessage.setText("Enter ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid ID");
            return;
        }

        String deleteQuery = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {

                lblMessage.setText("Deleted successfully");
                lblEmail.setText("");
                lblSujet.setText("");
                lblDescription.setText("");
                lblCategorie.setText("");
                lblDate.setText("");
            } else {
                lblMessage.setText("ID not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblMessage.setText("Error deleting record");
        }


    }
    @FXML
    private void modifierReclamation1(ActionEvent event) throws IOException {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) {
            lblMessage.setText("Enter ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid ID");
            return;
        }

        try {
            String query = "SELECT categorieId, email, sujet, description, statu, date_creation FROM reclamation WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/ajoutReclamation.fxml"));
                Parent root = loader.load();

                AjoutReclamation controller = loader.getController();
                controller.setReclamationDetails(
                        id,
                        rs.getString("email"),
                        rs.getString("sujet"),
                        rs.getString("description"),
                        rs.getString("date_creation"),
                        rs.getInt("categorieId")
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                lblMessage.setText("ID not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblMessage.setText("Error retrieving data");
        }
    }

    // Assurez-vous que cette variable est bien reliée à votre FXML

    public void remplirGridPane(List<reclamations> reclamations) {
        gridPaneReclamations.getChildren().clear(); // Nettoyer les anciennes données

        // 🔹 Création de la ligne d'en-tête
        Label headerEmail = new Label("Email");
        Label headerCategorie = new Label("Catégorie");
        Label headerSujet = new Label("Sujet");
        Label headerDescription = new Label("Description");
        Label headerDate = new Label("Date de création");
        Label headerAction = new Label("Action");

        // 🔹 Appliquer un style en gras pour les titres
        String headerStyle = "-fx-font-weight: bold; -fx-font-size: 14px;";
        headerEmail.setStyle(headerStyle);
        headerCategorie.setStyle(headerStyle);
        headerSujet.setStyle(headerStyle);
        headerDescription.setStyle(headerStyle);
        headerDate.setStyle(headerStyle);
        headerAction.setStyle(headerStyle);
        // 🔹 Ajouter la ligne d'en-tête à la première ligne du `GridPane`
        gridPaneReclamations.add(headerEmail, 0, 0);
        gridPaneReclamations.add(headerCategorie, 1, 0);
        gridPaneReclamations.add(headerSujet, 2, 0);
        gridPaneReclamations.add(headerDescription, 3, 0);
        gridPaneReclamations.add(headerDate, 4, 0);
        gridPaneReclamations.add(headerAction, 5, 0);

        int row = 1; // Ligne de départ après l'en-tête

        for (reclamations rec : reclamations) {
            Label labelEmail = new Label(rec.getEmail());
            Label labelCategorie = new Label(rec.getCategorie().getType());
            Label labelSujet = new Label(rec.getSujet());
            Label labelDescription = new Label(rec.getDescription());
            Label labelDate = new Label(rec.getDate_creation());

            System.out.println("ID de la réclamation récupérée : " + rec.getId());

            // 🔴 Bouton de suppression
            Button suppbtn = new Button("Supprimer");
            suppbtn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 3px 7px;");
            suppbtn.setUserData(rec.getId());

            // 🔵 Bouton de modification
            Button modfbtn = new Button("Modifier");
            modfbtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 3px 7px;");
            modfbtn.setUserData(rec.getId());

            suppbtn.setOnAction(e -> {
                System.out.println("Bouton Supprimer cliqué pour ID : " + rec.getId());
                confirmerSuppression(rec.getId());
            });

// ✅ Correction ici
            modfbtn.setOnAction(e -> {
                System.out.println("Bouton Modifier cliqué pour ID : " + rec.getId());
                modifierReclamation(e);  // Passez l'événement ici
            });


            // 🔹 Ajouter les éléments au GridPane
            gridPaneReclamations.add(labelEmail, 0, row);
            gridPaneReclamations.add(labelCategorie, 1, row);
            gridPaneReclamations.add(labelSujet, 2, row);
            gridPaneReclamations.add(labelDescription, 3, row);
            gridPaneReclamations.add(labelDate, 4, row);

            HBox buttonBox = new HBox(5);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(suppbtn, modfbtn);
            gridPaneReclamations.add(buttonBox, 5, row);

            row++; // Passer à la ligne suivante
        }
    }




    public void supprimerReclamation(ActionEvent actionEvent) {
        // Récupérer le bouton qui a déclenché l'événement
        Button supprimerButton = (Button) actionEvent.getSource();

        // Récupérer l'ID de la réclamation à partir de l'attribut 'userData' du bouton
        Integer reclamationId = (Integer) supprimerButton.getUserData();
        if (reclamationId == null) {
            lblMessage.setText("Aucune réclamation sélectionnée.");
            return;
        }

        // Création de l'alerte de confirmation avant de supprimer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réclamation ?");
        alert.setContentText("Cette action est irréversible.");

        // Affichage de l'alerte et gestion de la réponse
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme, on tente de supprimer la réclamation
                supprimerReclamationDeBase(reclamationId);  // Utiliser l'ID pour supprimer
            } else {
                // Si l'utilisateur annule, on affiche un message de cancellation
                lblMessage.setText("Suppression annulée.");
            }
        });
    }



    // 🔴 Confirmation de suppression avant d'exécuter la suppression
    private void confirmerSuppression(int id) {
        System.out.println("🟡 Confirmation de suppression pour ID : " + id);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réclamation ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("✅ Suppression confirmée pour ID : " + id);
                supprimerReclamationDeBase(id);  // Appeler supprimerReclamationDeBase directement avec l'ID
            } else {
                System.out.println("❌ Suppression annulée.");
            }
        });
    }


    // Méthode qui effectue la suppression de la réclamation dans la base de données
    private void supprimerReclamationDeBase(int id) {
        String deleteQuery = "DELETE FROM reclamation WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, id);

            // Exécution de la requête et vérification du nombre de lignes affectées
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                lblMessage.setText("Réclamation supprimée avec succès.");
                // Vous pouvez aussi mettre à jour l'affichage de la liste des réclamations ici
                afficherReclamations();  // Recharger les réclamations après suppression
            } else {
                lblMessage.setText("Aucune réclamation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblMessage.setText("Erreur lors de la suppression de la réclamation.");
        }
    }

    public void modifierReclamation(ActionEvent actionEvent) {
        // Récupérer l'ID de la réclamation à partir du bouton cliqué
        Button clickedButton = (Button) actionEvent.getSource();
        int reclamationId = (int) clickedButton.getUserData(); // L'ID de la réclamation stocké dans le bouton

        try {
            // Logique pour charger la réclamation en fonction de l'ID
            String query = "SELECT id, categorieId, email, sujet, description, statu, date_creation FROM reclamation WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, reclamationId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/ajoutReclamation.fxml"));
                Parent root = loader.load();  // Cela pourrait lancer une IOException
                AjoutReclamation controller = loader.getController();
                controller.setReclamationDetails(
                        reclamationId,
                        rs.getString("email"),
                        rs.getString("sujet"),
                        rs.getString("description"),
                        rs.getString("date_creation"),
                        rs.getInt("categorieId")
                );
                Stage stage = (Stage) gridPaneReclamations.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                lblMessage.setText("Réclamation non trouvée");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            lblMessage.setText("Erreur avec la base de données");
        } catch (IOException e) {
            e.printStackTrace();
            lblMessage.setText("Erreur de chargement de la page");
        }
    }




}


