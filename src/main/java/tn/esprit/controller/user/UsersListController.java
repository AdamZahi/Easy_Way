package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceUser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class UsersListController {

    @FXML
    private GridPane userGridPane;

    private final ServiceUser userService = new ServiceUser();
    private List<User> userList;

    public void initialize() {
        if (userGridPane == null) {
            System.out.println("Erreur : GridPane non trouvé.");
            return;
        }

        userList = userService.getAll();
        if (userList.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        int row = 1;
        for (User u : userList) {
            final int currentRow = row;

            userGridPane.add(new Label(String.valueOf(u.getId_user())), 0, row);
            userGridPane.add(new Label(u.getNom()), 1, row);
            userGridPane.add(new Label(u.getPrenom()), 2, row);
            userGridPane.add(new Label(u.getEmail()), 3, row);
            userGridPane.add(new Label(u.getMot_de_passe()), 4, row);
            userGridPane.add(new Label(String.valueOf(u.getTelephonne())), 5, row);
            userGridPane.add(new Label(u.getPhoto_profil()), 6, row);
            userGridPane.add(new Label(u.getRole().toString()), 7, row);

            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(event -> handleDeleteUser(event));
            userGridPane.add(deleteButton, 8, row);

            Button editButton = new Button("Modifier");
            editButton.setOnAction(event -> handleEditUser(u, currentRow));
            userGridPane.add(editButton, 9, row);

            row++;
        }

        System.out.println("Affichage des utilisateurs terminé !");
    }

    private void deleteUser(User user) {
        userService.delete(user);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur supprimé avec succès !");
        refreshUserList();
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        Integer rowIndex = GridPane.getRowIndex(clickedButton);

        if (rowIndex == null || rowIndex < 1) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Impossible de trouver l'utilisateur à supprimer.");
            return;
        }

        List<User> userList = userService.getAll();

        if (rowIndex - 1 >= userList.size()) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Utilisateur introuvable.");
            return;
        }

        User userToDelete = userList.get(rowIndex - 1);
        deleteUser(userToDelete);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshUserList() {
        userGridPane.getChildren().clear();
        initialize();
    }

    private void handleEditUser(User user, int rowIndex) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Modifier Utilisateur");
        dialog.setHeaderText("Modifier les informations de l'utilisateur");

        TextField nomField = new TextField(user.getNom());
        TextField prenomField = new TextField(user.getPrenom());
        TextField emailField = new TextField(user.getEmail());
        TextField mdpField = new TextField(user.getMot_de_passe());
        TextField telephoneField = new TextField(String.valueOf(user.getTelephonne()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Mot de passe :"), 0, 3);
        grid.add(mdpField, 1, 3);
        grid.add(new Label("Téléphone:"), 0, 4);
        grid.add(telephoneField, 1, 4);


        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                user.setNom(nomField.getText());
                user.setPrenom(prenomField.getText());
                user.setEmail(emailField.getText());
                user.setMot_de_passe(mdpField.getText());
                user.setTelephonne(Integer.parseInt(telephoneField.getText()));


                return user;
            }
            return null;
        });

        Optional<User> result = dialog.showAndWait();

        result.ifPresent(updatedUser -> {
            userService.update(updatedUser);
            updateGridRow(updatedUser, rowIndex);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur modifié avec succès !");
        });
    }

    @FXML
    private void handleEditUser(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        Integer rowIndex = GridPane.getRowIndex(clickedButton);

        if (rowIndex == null || rowIndex < 1) {
            System.out.println("Erreur : Impossible de récupérer la ligne.");
            return;
        }

        List<User> users = userService.getAll();
        if (rowIndex - 1 >= users.size()) {
            System.out.println("Erreur : Index utilisateur invalide.");
            return;
        }

        User user = users.get(rowIndex - 1);
        handleEditUser(user, rowIndex);
    }

    private void updateGridRow(User user, int rowIndex) {
        userGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex);

        userGridPane.add(new Label(String.valueOf(user.getId_user())), 0, rowIndex);
        userGridPane.add(new Label(user.getNom()), 1, rowIndex);
        userGridPane.add(new Label(user.getPrenom()), 2, rowIndex);
        userGridPane.add(new Label(user.getEmail()), 3, rowIndex);
        userGridPane.add(new Label(user.getMot_de_passe()), 4, rowIndex);
        userGridPane.add(new Label(String.valueOf(user.getTelephonne())), 5, rowIndex);
        userGridPane.add(new Label(user.getPhoto_profil()), 6, rowIndex);
        userGridPane.add(new Label(user.getRole().toString()), 7, rowIndex);

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> handleDeleteUser(event));
        userGridPane.add(deleteButton, 8, rowIndex);

        Button editButton = new Button("Modifier");
        editButton.setOnAction(event -> handleEditUser(user, rowIndex));
        userGridPane.add(editButton, 9, rowIndex);
    }
}
