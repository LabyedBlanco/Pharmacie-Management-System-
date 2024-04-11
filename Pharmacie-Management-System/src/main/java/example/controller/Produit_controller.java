package example.controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Produit_controller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
    }

    @FXML
    private void onaddimageproduct(ActionEvent event){


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");

            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {

            }
    }
}
