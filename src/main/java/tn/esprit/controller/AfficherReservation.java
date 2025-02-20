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
import javafx.util.Duration;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

import javax.swing.*;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.List;


public class AfficherReservation {
    @FXML
    private TextField arret;

    @FXML
    private TextField depart;

    @FXML
    private TextField vehicule;

    @FXML
    private TextField nb;

    @FXML
    private AnchorPane mama_anchor;

    @FXML
    private ComboBox<Integer> id;

    @FXML
    private TextField Supprimer;

    @FXML
    private TextField Retour;

    private ServiceReservation serviceReservation = new ServiceReservation();
    @FXML
    public void initialize() {
        List<Reservation> reservations = serviceReservation.getAll();
        for (Reservation reservation : reservations) {
            id.getItems().add(reservation.getId());
        }
        id.setOnAction(event -> {
            Integer selectedId = id.getValue();
            if (selectedId != null) {
                Reservation reservation = serviceReservation.getReservationById(selectedId);
                if (reservation != null) {
                    depart.setText(reservation.getDepart());
                    arret.setText(reservation.getArret());
                    vehicule.setText(reservation.getVehicule());
                    nb.setText(String.valueOf(reservation.getNb()));
                }
            }
        });
    }

    public void setReservations(Reservation p) {
        depart.setText(p.getDepart());
        arret.setText(p.getArret());
        vehicule.setText(p.getVehicule());
        nb.setText(""+p.getNb());
        mama_anchor.setStyle("-fx-background-radius: 13; -fx-background-color: white;");

    }
    @FXML
    void mama_anchor_clicked(MouseEvent event) {
        if (depart.isFocused()) {
            depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        }
        if (arret.isFocused()) {
            arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        }
        if (vehicule.isFocused()) {
            vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        }
        if (nb.isFocused()) {
            nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        }
        if (id.isFocused()) {
            id.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        }
    }
    //Buttons

    @FXML
    void entered(MouseEvent event) {
        Supprimer.setStyle(Supprimer.getStyle() + "-fx-background-color: #228B22;");
        Retour.setStyle(Retour.getStyle() + "-fx-background-color: #228B22;");
    }

    @FXML
    void exited(MouseEvent event) {
        Supprimer.setStyle(Supprimer.getStyle() + "-fx-background-color: #008000;");
        Retour.setStyle(Retour.getStyle() + "-fx-background-color: #008000;");
    }

    @FXML
    void pressed(MouseEvent event) {
        ScaleTransition clickTrans = new ScaleTransition(Duration.millis(50), Supprimer);
        clickTrans.setByX(0.05);
        clickTrans.setByY(0.04);
        clickTrans.setCycleCount(1);
        clickTrans.play();
        Supprimer.setStyle(Supprimer.getStyle() + "-fx-background-color: #004d00;");
        clickTrans.setOnFinished(e -> {
            ScaleTransition reverseTrans = new ScaleTransition(Duration.millis(50), Supprimer);
            reverseTrans.setByX(-0.05);
            reverseTrans.setByY(-0.04);
            reverseTrans.setCycleCount(1);
            reverseTrans.play();
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(ev -> {
                Supprimer.setStyle(Supprimer.getStyle().replace("#004d00", "#008000"));
            });
            pause.play();
        });
    }
    @FXML
    void pressed2(MouseEvent event) {
        ScaleTransition clickTrans = new ScaleTransition(Duration.millis(50), Retour);
        clickTrans.setByX(0.05);
        clickTrans.setByY(0.04);
        clickTrans.setCycleCount(1);
        clickTrans.play();
        Retour.setStyle(Retour.getStyle() + "-fx-background-color: #004d00;");
        clickTrans.setOnFinished(e -> {
            ScaleTransition reverseTrans = new ScaleTransition(Duration.millis(50), Retour);
            reverseTrans.setByX(-0.05);
            reverseTrans.setByY(-0.04);
            reverseTrans.setCycleCount(1);
            reverseTrans.play();
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(ev -> {
                Retour.setStyle(Retour.getStyle().replace("#004d00", "#008000"));
            });
            pause.play();
        });
    }


    @FXML
    void arret_pressed(MouseEvent event) {
        arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #0097b2; -fx-background-color: white; -fx-border-color: #0097b2;");
        depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        id.setStyle("-fx-background-radius: 16; -fx-background-color: #ADD8E6; -fx-border-radius: 16; -fx-border-color: #ADD8E6;");
        vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
    }

    @FXML
    void depart_pressed(MouseEvent event) {
        depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #0097b2; -fx-background-color: white; -fx-border-color: #0097b2;");
        arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        id.setStyle("-fx-background-radius: 16; -fx-background-color: #ADD8E6; -fx-border-radius: 16; -fx-border-color: #ADD8E6;");
    }

    @FXML
    void nb_pressed(MouseEvent event) {

        nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #0097b2; -fx-background-color: white; -fx-border-color: #0097b2;");
        depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        id.setStyle("-fx-background-radius: 16; -fx-background-color: #ADD8E6; -fx-border-radius: 16; -fx-border-color: #ADD8E6;");
    }

    @FXML
    void vehicule_pressed(MouseEvent event) {

        vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #0097b2; -fx-background-color: white; -fx-border-color: #0097b2;");
        depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        id.setStyle("-fx-background-radius: 16; -fx-background-color: #ADD8E6; -fx-border-radius: 16; -fx-border-color: #ADD8E6;");
        arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
    }
    @FXML
    void id_pressed(MouseEvent event) {

        id.setStyle("-fx-background-radius: 16; -fx-background-color: #0097b2; -fx-border-radius: 16; -fx-border-color: #0097b2;");
        depart.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        arret.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        vehicule.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
        nb.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #aeaeae; -fx-background-color: white; -fx-border-color: #ADD8E6;");
    }
}
