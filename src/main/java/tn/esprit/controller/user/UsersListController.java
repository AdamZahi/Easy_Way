package tn.esprit.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import tn.esprit.models.user.User;
import tn.esprit.services.user.ServiceUser;


import java.text.SimpleDateFormat;
import java.util.List;

public class UsersListController {

    @FXML
    private GridPane userGridPane;

    @FXML
    private Label EmailUser;

    @FXML
    private Label MotDePasseUser;

    @FXML
    private Label NomUser;

    @FXML
    private Label OptionField;

    @FXML
    private Label PhotoProfilUser;

    @FXML
    private Label PrenomUser;

    @FXML
    private Label dateCreationUser;

    @FXML
    private Label idUser;

    @FXML
    private Label telephoneUser;

    @FXML
    private Button DeleteButton;

    private ServiceUser userService = new ServiceUser();

    public void initialize() {
        if (userGridPane == null) {
            System.out.println("Erreur : GridPane non trouvé.");
            return;
        }

        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        int row = 1;

        for (User u : users) {
            userGridPane.add(new Label(String.valueOf(u.getId_user())), 0, row);
            userGridPane.add(new Label(u.getNom()), 1, row);
            userGridPane.add(new Label(u.getPrenom()), 2, row);
            userGridPane.add(new Label(u.getEmail()), 3, row);
            userGridPane.add(new Label(u.getMot_de_passe()), 4, row);
            userGridPane.add(new Label(String.valueOf(u.getTelephonne())), 5, row);

            userGridPane.add(new Label(u.getPhoto_profil()), 7, row);



            row++;
        }

        System.out.println("Affichage des utilisateurs terminé !");
    }
}
