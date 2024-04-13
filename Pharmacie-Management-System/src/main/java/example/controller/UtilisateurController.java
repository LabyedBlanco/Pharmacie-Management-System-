package example.controller;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;


public class UtilisateurController extends Application {

    public Button bottonmodify;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void switchToHello(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile_utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add a User");
        stage.setResizable(false);
        stage.show();
        controller.bottonmodify.setVisible(false);

    }

    public void onaddimageUser(ActionEvent event){


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {

        }
    }



}
