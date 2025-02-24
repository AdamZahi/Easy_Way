package tn.esprit.controller.covoiturage;

import tn.esprit.models.covoiturage.Posts;
import tn.esprit.models.covoiturage.Commentaire;
import tn.esprit.services.covoiturage.ServicePosts;
import tn.esprit.services.covoiturage.ServiceCommentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import javafx.scene.Node;

public class Viewpostcontrolleur {

    @FXML
    private VBox postsContainer;

    private ServicePosts servicePosts = new ServicePosts();
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    @FXML
    public void initialize() {
        afficherPosts();
    }

    private void afficherPosts() {
        postsContainer.getChildren().clear();
        List<Posts> postsList = servicePosts.getAll();

        for (Posts post : postsList) {
            // Création d'un conteneur pour le post qui respecte le design du FXML
            VBox postBox = new VBox(10);
            // Utilisation de la couleur et du padding définis dans le FXML
            postBox.setStyle("-fx-background-color: #F4EFE2; -fx-padding: 15px; -fx-border-color: #ccc; -fx-border-radius: 10px;");

            // Titre "Offre Covoiturage" centré
            Label title = new Label("Offre Covoiturage");
            title.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 18));
            title.setStyle("-fx-text-fill: #000000;");
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);

            // Labels avec les informations du post (description, lieux et date)
            Label descriptionLabel = new Label("Description du trajet: " + post.getMessage());
            descriptionLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
            descriptionLabel.setStyle("-fx-text-fill: #6b0808;");
            descriptionLabel.setWrapText(true);

            Label villeDepartLabel = new Label("Lieu de départ : " + post.getVilleDepart());
            villeDepartLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
            villeDepartLabel.setStyle("-fx-text-fill: #6b0808;");

            Label villeArriveeLabel = new Label("Lieu d’arrivée : " + post.getVilleArrivee());
            villeArriveeLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
            villeArriveeLabel.setStyle("-fx-text-fill: #6b0808;");

            Label dateLabel = new Label("Date: " + post.getDate().toString());
            dateLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
            dateLabel.setStyle("-fx-text-fill: #6b0808;");

            // Zone de saisie pour ajouter un commentaire et bouton Envoyer
            TextArea commentInput = new TextArea();
            commentInput.setPromptText("Ajouter un commentaire...");
            commentInput.setWrapText(true);
            commentInput.setPrefHeight(60);
            commentInput.setPrefWidth(639);

            Button submitCommentButton = new Button("Envoyer");
            submitCommentButton.setStyle("-fx-background-color: #E5FEB3; -fx-text-fill: #333;");
            submitCommentButton.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
            submitCommentButton.setOnAction(event -> handleAddComment(event, post.getId_post(), commentInput));

            VBox commentInputBox = new VBox(10, commentInput, submitCommentButton);

            // Section pour afficher les commentaires
            VBox commentsContainer = new VBox();
            commentsContainer.setSpacing(5);
            commentsContainer.setStyle("-fx-padding: 10px; -fx-background-color: #f4f4f4; -fx-border-color: #bbb; -fx-border-radius: 5px;");

            Label commentsTitle = new Label("Commentaires :");
            commentsTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));
            commentsTitle.setStyle("-fx-text-fill: #333;");
            commentsContainer.getChildren().add(commentsTitle);

            List<String> commentaires = serviceCommentaire.getCommentsByPostId(post.getId_post());
            if (commentaires.isEmpty()) {
                Label noCommentsLabel = new Label("Aucun commentaire pour ce post.");
                noCommentsLabel.setStyle("-fx-text-fill: #888;");
                commentsContainer.getChildren().add(noCommentsLabel);
            } else {
                for (String contenu : commentaires) {
                    // Chaque commentaire dans une VBox avec son HBox de contenu et séparateur
                    HBox commentRow = new HBox(10);
                    commentRow.setStyle("-fx-padding: 5px; -fx-alignment: center-left;");

                    Label commentLabel = new Label(contenu);
                    commentLabel.setWrapText(true);
                    commentLabel.setStyle("-fx-text-fill: #555;");

                    Button deleteCommentButton = new Button("❌");
                    deleteCommentButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
                    deleteCommentButton.setOnAction(event -> handleDeleteComment(contenu, postBox));

                    Button updateCommentButton = new Button("✏️");
                    updateCommentButton.setStyle("-fx-background-color: transparent; -fx-text-fill: blue;");
                    // Vous pouvez ajouter ici l'action de mise à jour du commentaire si besoin

                    commentRow.getChildren().addAll(commentLabel, updateCommentButton, deleteCommentButton);
                    VBox commentBox = new VBox(5, commentRow, new Separator());
                    commentsContainer.getChildren().add(commentBox);
                }
            }

            // Boutons d'actions pour le post (supprimer et mettre à jour)
            Button deletePostButton = new Button("Supprimer");
            deletePostButton.setStyle("-fx-background-color: #C10707; -fx-text-fill: #fff;");
            deletePostButton.setOnAction(event -> handleDeletePost(post));

            Button updatePostButton = new Button("Mettre à jour");

            updatePostButton.setStyle("-fx-background-color: #C2D4A9; -fx-text-fill: #fff; -fx-padding: 10 20;");
            updatePostButton.setOnAction(event -> handleUpdatePost(event, post));

            HBox postActionButtons = new HBox(10, deletePostButton, updatePostButton);
            postActionButtons.setAlignment(Pos.CENTER_LEFT);
            postActionButtons.setStyle("-fx-padding: 10;");

            // Assemblage final du post en respectant l'ordre défini dans le FXML
            postBox.getChildren().addAll(
                    title,
                    descriptionLabel,
                    villeDepartLabel,
                    villeArriveeLabel,
                    dateLabel,
                    commentInputBox,
                    commentsContainer,
                    postActionButtons
            );

            postsContainer.getChildren().add(postBox);
        }
    }

    private void handleDeletePost(Posts post) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce post ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType buttonYes = new ButtonType("Oui");
        ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            servicePosts.delete(post);
            afficherPosts();
            System.out.println("Post supprimé avec succès !");
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    @FXML
    private void handleUpdatePost(ActionEvent event, Posts post) {
        Dialog<Posts> updateDialog = new Dialog<>();
        updateDialog.setTitle("Modifier le post");

        VBox dialogVbox = new VBox(10);
        dialogVbox.setStyle("-fx-padding: 20px;");

        // Champ pour le message
        TextField messageField = new TextField(post.getMessage());

        // Création des ComboBox pour les villes (gouvernorats de Tunisie)
        ComboBox<String> villeDepartCombo = new ComboBox<>();
        ComboBox<String> villeArriveeCombo = new ComboBox<>();

        // Liste des 24 gouvernorats
        List<String> gouvernorats = List.of(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur",
                "Tunis", "Zaghouan", "Kebili"
        );

        villeDepartCombo.getItems().addAll(gouvernorats);
        villeArriveeCombo.getItems().addAll(gouvernorats);

        // Sélectionner la valeur actuelle du post si elle figure dans la liste
        if (gouvernorats.contains(post.getVilleDepart())) {
            villeDepartCombo.setValue(post.getVilleDepart());
        }
        if (gouvernorats.contains(post.getVilleArrivee())) {
            villeArriveeCombo.setValue(post.getVilleArrivee());
        }

        // DatePicker pour la date du post
        DatePicker dateField = new DatePicker(post.getDate().toLocalDate());

        // Ajout des éléments au layout du dialogue
        dialogVbox.getChildren().addAll(
                new Label("Message"), messageField,
                new Label("Lieu de départ"), villeDepartCombo,
                new Label("Lieu d’arrivée"), villeArriveeCombo,
                new Label("Date"), dateField
        );

        updateDialog.getDialogPane().setContent(dialogVbox);

        ButtonType updateButtonType = new ButtonType("Mettre à jour", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType);

        // Récupération et désactivation initiale du bouton de mise à jour
        Node updateButton = updateDialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDisable(true);

        // Ajout des écouteurs pour la validation en temps réel
        messageField.textProperty().addListener((obs, oldVal, newVal) ->
                validateUpdateFields(messageField, villeDepartCombo, villeArriveeCombo, dateField, updateButton));
        villeDepartCombo.valueProperty().addListener((obs, oldVal, newVal) ->
                validateUpdateFields(messageField, villeDepartCombo, villeArriveeCombo, dateField, updateButton));
        villeArriveeCombo.valueProperty().addListener((obs, oldVal, newVal) ->
                validateUpdateFields(messageField, villeDepartCombo, villeArriveeCombo, dateField, updateButton));
        dateField.valueProperty().addListener((obs, oldVal, newVal) ->
                validateUpdateFields(messageField, villeDepartCombo, villeArriveeCombo, dateField, updateButton));

        updateDialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                post.setMessage(messageField.getText());
                post.setVilleDepart(villeDepartCombo.getValue());
                post.setVilleArrivee(villeArriveeCombo.getValue());
                post.setDate(java.sql.Date.valueOf(dateField.getValue()));

                servicePosts.update(post);
                afficherPosts();
                System.out.println("Post mis à jour avec succès !");
                return post;
            }
            return null;
        });

        updateDialog.showAndWait();
    }

    /**
     * Méthode de validation pour le formulaire de mise à jour.
     * Désactive le bouton de mise à jour si :
     * - Le message est vide,
     * - Une des villes n'est pas sélectionnée,
     * - Les villes de départ et d'arrivée sont identiques,
     * - La date est nulle ou dans le passé.
     */
    private void validateUpdateFields(TextField messageField, ComboBox<String> villeDepartCombo, ComboBox<String> villeArriveeCombo, DatePicker dateField, Node updateButton) {
        boolean disable = messageField.getText().trim().isEmpty() ||
                villeDepartCombo.getValue() == null ||
                villeArriveeCombo.getValue() == null ||
                villeDepartCombo.getValue().equals(villeArriveeCombo.getValue()) ||
                dateField.getValue() == null ||
                dateField.getValue().isBefore(LocalDate.now());
        updateButton.setDisable(disable);
    }


    private void handleDeleteComment(String contenu, VBox postContainer) {
        Commentaire commentaireToDelete = null;
        for (Commentaire c : serviceCommentaire.getAll()) {
            if (c.getContenu().equals(contenu)) {
                commentaireToDelete = c;
                break;
            }
        }
        if (commentaireToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer le commentaire");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce commentaire ?");
            alert.setContentText("Cette action est irréversible.");

            ButtonType yesButton = new ButtonType("Oui");
            ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                serviceCommentaire.delete(commentaireToDelete);
                afficherPosts(); // Rafraîchir l'interface
                System.out.println("Commentaire supprimé !");
            }
        }
    }

    private void handleAddComment(ActionEvent event, int postId, TextArea commentaireField) {
        String commentaireText = commentaireField.getText();
        if (!commentaireText.isEmpty()) {
            int id_user = 3;
            java.sql.Date date_creat = new java.sql.Date(System.currentTimeMillis());

            Commentaire newComment = new Commentaire(postId, id_user, commentaireText, date_creat);
            serviceCommentaire.add(newComment);

            commentaireField.clear();
            afficherPosts();
            System.out.println("Commentaire ajouté avec succès !");
        }
    }
}
