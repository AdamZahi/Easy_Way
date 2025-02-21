package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPassController {

    @FXML
    private TextField emailfield;

    @FXML
    private Button envoyercode;

    @FXML
    void EnvoyerCode(ActionEvent event) {

    }

    @FXML
    void RedirectToSignIn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
