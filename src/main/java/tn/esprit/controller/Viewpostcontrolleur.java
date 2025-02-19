package tn.esprit.controller;

import tn.esprit.models.Posts;
import tn.esprit.services.ServicePosts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Optional;
import tn.esprit.services.ServiceCommentaire;
import tn.esprit.models.Commentaire;
import javafx.scene.control.Label;

public class Viewpostcontrolleur {

    @FXML
    private VBox postsContainer;

    private ServicePosts servicePosts = new ServicePosts();
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire(); // Instancier ServiceCommentaire

    @FXML
    public void initialize() {
        afficherPosts();
    }

    private void afficherPosts() {
        postsContainer.getChildren().clear();
        List<Posts> postsList = servicePosts.getAll();

        for (Posts post : postsList) {
            VBox postBox = new VBox();
            postBox.setSpacing(10);
            postBox.setStyle("-fx-background-color: #E5FEB3; -fx-padding: 15px; -fx-border-color: #ccc; -fx-border-radius: 10px;");

            Label title = new Label("Offre Covoiturage");
            title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1c7007;");

            Label descriptionLabel = new Label("Description : " + post.getMessage());
            Label villeDepartLabel = new Label("Lieu de départ : " + post.getVilleDepart());
            Label villeArriveeLabel = new Label("Lieu d’arrivée : " + post.getVilleArrivee());
            Label dateLabel = new Label("Date : " + post.getDate().toString());

            // Section de commentaires
            TextArea commentaireField = new TextArea();
            commentaireField.setPromptText("Ajouter un commentaire...");
            commentaireField.setWrapText(true);
            commentaireField.setPrefHeight(60);

            Button envoyerButton = new Button("Envoyer");
            envoyerButton.setStyle("-fx-background-color: #CF321A; -fx-text-fill: #f4eeee;");
            envoyerButton.setOnAction(event -> handleAddComment(event, post.getId_post(), commentaireField));

            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: #fff;");
            deleteButton.setOnAction(event -> handleDeletePost(post));

            Button updateButton = new Button("Mettre à jour");
            updateButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: #fff;");
            updateButton.setOnAction(event -> handleUpdatePost(event, post));

            postBox.getChildren().addAll(title, descriptionLabel, villeDepartLabel, villeArriveeLabel, dateLabel, commentaireField, envoyerButton, deleteButton, updateButton);

            // Afficher les commentaires du post
            afficherCommentaires(post.getId_post(), postBox);

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

        TextField messageField = new TextField(post.getMessage());
        TextField villeDepartField = new TextField(post.getVilleDepart());
        TextField villeArriveeField = new TextField(post.getVilleArrivee());
        DatePicker dateField = new DatePicker(post.getDate().toLocalDate());

        dialogVbox.getChildren().addAll(
                new Label("Message"), messageField,
                new Label("Lieu de départ"), villeDepartField,
                new Label("Lieu d’arrivée"), villeArriveeField,
                new Label("Date"), dateField
        );

        updateDialog.getDialogPane().setContent(dialogVbox);

        ButtonType updateButton = new ButtonType("Mettre à jour", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        updateDialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);

        updateDialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                post.setMessage(messageField.getText());
                post.setVilleDepart(villeDepartField.getText());
                post.setVilleArrivee(villeArriveeField.getText());
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

    @FXML
    private void afficherCommentaires(int postId, VBox postContainer) {
        List<Commentaire> commentaires = serviceCommentaire.getCommentsByPostId(postId);

        VBox commentsSection = new VBox();
        commentsSection.setSpacing(5);
        commentsSection.setStyle("-fx-padding: 5; -fx-background-color: #F0F0F0; -fx-border-color: #ccc;");

        Label title = new Label("Commentaires :");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        commentsSection.getChildren().add(title);

        if (commentaires.isEmpty()) {
            Label noCommentsLabel = new Label("Aucun commentaire pour ce post.");
            noCommentsLabel.setStyle("-fx-text-fill: #888;");
            commentsSection.getChildren().add(noCommentsLabel);
        } else {
            for (Commentaire commentaire : commentaires) {
                System.out.println("Comment: " + commentaire.getContenu());
                Label commentLabel = new Label(commentaire.getContenu());
                commentLabel.setStyle("-fx-text-fill: #555;");
                commentsSection.getChildren().add(commentLabel);
            }
        }

        postContainer.getChildren().add(commentsSection);
    }

    private void handleAddComment(ActionEvent event, int postId, TextArea commentaireField) {
        String commentaireText = commentaireField.getText();
        if (!commentaireText.isEmpty()) {
            int id_user = 3; // À remplacer par l'ID réel de l'utilisateur connecté
            java.sql.Date date_creat = new java.sql.Date(System.currentTimeMillis());

            Commentaire newComment = new Commentaire(postId, id_user, commentaireText, date_creat);
            serviceCommentaire.add(newComment); // Utiliser instance

            commentaireField.clear();
            // Refresh the posts and comments section after adding a new comment
            afficherPosts();
            System.out.println("Commentaire ajouté avec succès !");
        }
    }
}
