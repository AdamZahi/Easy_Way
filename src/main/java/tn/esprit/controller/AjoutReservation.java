package tn.esprit.controller;


import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

import javax.swing.*;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.List;

public class AjoutReservation {

    @FXML
    private TextField arret;

    @FXML
    private ComboBox<String> vehicule;

    @FXML
    private TextField depart;

    @FXML
    private Spinner<Integer> nb;

    @FXML
    private Button reserver;

    @FXML
    private ImageView loc;

    @FXML
    private AnchorPane page_map;

    @FXML
    Label erreurLabel;



    @FXML
    public void initialize() {
        // spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1); //min=0 max=100
        nb.setValueFactory(valueFactory);
        loc.setVisible(true);

    }

//magic link

    @FXML
    void showReservation(ActionEvent event) {
        ServiceReservation sp = new ServiceReservation();

        List<Reservation> reservations = sp.getAll();
        for (Reservation p : reservations){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml")) ;
                AnchorPane boc =fxmlLoader.load();
                AfficherReservation c = fxmlLoader.getController();
                c.setReservations(p);

                page_map.getChildren().add(boc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @FXML
    void entered(MouseEvent event) {
        reserver.setStyle(reserver.getStyle() + "-fx-background-color: #228B22;");
    }

    @FXML
    void exited(MouseEvent event) {
        reserver.setStyle(reserver.getStyle() + "-fx-background-color: #008000;");
    }

    @FXML
    void pressed(MouseEvent event) {
        ScaleTransition clickTrans = new ScaleTransition(Duration.millis(50), reserver);
        clickTrans.setByX(0.05);
        clickTrans.setByY(0.04);
        clickTrans.setCycleCount(1);
        clickTrans.play();
        reserver.setStyle(reserver.getStyle() + "-fx-background-color: #004d00;");
        clickTrans.setOnFinished(e -> {
            ScaleTransition reverseTrans = new ScaleTransition(Duration.millis(50), reserver);
            reverseTrans.setByX(-0.05);
            reverseTrans.setByY(-0.04);
            reverseTrans.setCycleCount(1);
            reverseTrans.play();
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(ev -> {
                reserver.setStyle(reserver.getStyle().replace("#004d00", "#008000"));
            });
            pause.play();
        });
    }

    @FXML
    void addReservation(ActionEvent event) {
        ServiceReservation sp = new ServiceReservation();
        if (nb.getValue() > 4) {
            erreurLabel.setText("Le nombre ne doit pas dépasser 4");
            erreurLabel.setTextFill(Color.RED);

            nb.setStyle("-fx-border-radius: 15px; -fx-border-color: red; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
        } else {
            sp.add(new Reservation(depart.getText(), arret.getText(), vehicule.getValue(), nb.getValue()));
            loc.setVisible(true);
            arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
            depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
            vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
            nb.setStyle("-fx-border-radius: 15px; -fx-border-color: #aeaeae; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
        }
    }

    @FXML
    void background_click(MouseEvent event) {
        loc.setVisible(true);
        if (depart.isFocused()) {
            depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        }
        if (arret.isFocused()) {
            arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        }
        if (vehicule.isFocused()) {
            vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
        }
        if (nb.isFocused()) {
            nb.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
        }
    }
    @FXML
    void depart_pressed(MouseEvent event) {
        loc.setVisible(false);
        depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: green; -fx-background-color: white;");
        arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
        nb.setStyle("-fx-border-radius: 15px; -fx-border-color: #aeaeae; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
    }
    @FXML
    void arret_pressed(MouseEvent event) {
        loc.setVisible(true);
        arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: green; -fx-background-color: white;");
        depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
        nb.setStyle("-fx-border-radius: 15px; -fx-border-color: #aeaeae; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
    }

    @FXML
    void vehicule_pressed(MouseEvent event) {
        loc.setVisible(true);
        vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: green; -fx-border-radius: 15;");
        depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        nb.setStyle("-fx-border-radius: 15px; -fx-border-color: #aeaeae; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
    }

    @FXML
    void nb_pressed(MouseEvent event) {
        loc.setVisible(true);
        nb.setStyle("-fx-border-radius: 15px; -fx-border-color: green; -fx-background-radius: 15px; -fx-background-color: white; -fx-font-size: 10; -fx-padding: 3px 30px;");
        vehicule.setStyle("-fx-background-radius: 15; -fx-background-color: white; -fx-border-color: #aeaeae; -fx-border-radius: 15;");
        depart.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
        arret.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #aeaeae; -fx-background-color: white;");
    }
}

