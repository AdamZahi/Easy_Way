package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/event/addEvenement.fxml"));
        try {
            Parent root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("hello from the other side ");
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
