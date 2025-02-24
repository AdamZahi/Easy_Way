package tn.esprit.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import tn.esprit.models.user.User;
import tn.esprit.util.MyDataBase;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class UsersListController implements Initializable {

    @FXML
    private GridPane userGridPane;
    private Connection cnx = MyDataBase.getInstance().getConnection(); // Connexion à la base de données

    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String qry = "SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                User u = new User();
                u.setId_user(rs.getInt("id_user"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
                u.setTelephonne(rs.getInt("telephonne"));
                u.setPhoto_profil(rs.getString("photo_profil"));
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }
        return users;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<User> users = getAll(); // Récupère les utilisateurs depuis la base
        int row = 1; // Ligne où commencent les données (après l’en-tête)

        for (User user : users) {
            userGridPane.add(new Label(String.valueOf(user.getId_user())), 0, row);
            userGridPane.add(new Label(user.getNom()), 1, row);
            userGridPane.add(new Label(user.getPrenom()), 2, row);
            userGridPane.add(new Label(user.getEmail()), 3, row);
            userGridPane.add(new Label(user.getMot_de_passe()), 4, row);
            userGridPane.add(new Label(user.getTelephonne()), 5, row);
            userGridPane.add(new Label(user.getDateCreation().toString()), 6, row);
            userGridPane.add(new Label(user.getPhoto_profil()), 7, row);

            // 🔹 Ajouter boutons Modifier et Supprimer dans une VBox
            VBox buttonBox = new VBox(5); // Espacement de 5px
            buttonBox.setPadding(new Insets(5));

            Button btnModifier = new Button("Modifier");
            btnModifier.setStyle("-fx-background-color: #ef9a9a;");
            btnModifier.setOnAction(event -> modifierUtilisateur(user));

            Button btnSupprimer = new Button("Supprimer");
            btnSupprimer.setOnAction(event -> supprimerUtilisateur(user));

            buttonBox.getChildren().addAll(btnModifier, btnSupprimer);
            userGridPane.add(buttonBox, 8, row); // Ajout du VBox dans la colonne 8

            row++; // Passer à la ligne suivante
        }
    }



    private void modifierUtilisateur(User user) {
        System.out.println("Modifier : " + user.getNom());
    }

    private void supprimerUtilisateur(User user) {
        System.out.println("Supprimer : " + user.getNom());
    }
}
