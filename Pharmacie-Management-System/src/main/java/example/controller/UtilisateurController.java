package example.controller;

import example.model.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UtilisateurController implements Initializable {
    public AnchorPane Connected ;
    public void initialize(URL url , ResourceBundle resourceBundle){
        Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
        DatabaseManager Data = new DatabaseManager();
        if (Data.ConnectionStat() == true) {
            System.out.print(Data.ConnectionStat());
            Connected.setStyle("-fx-background-color: green;-fx-background-radius: 100px");
        }else {
            System.out.print(Data.ConnectionStat());
            Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
        }
    }


    public Button bottonmodify;
    public ImageView image;
    int c = 0;
    public Pane imagePane;


    public static void main(String[] args) {

    }


    public void start(Stage primaryStage) {

    }

    public void switchToHello(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        if(c==0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile_utilisateur.fxml"));
            Parent root = loader.load();
            UtilisateurController controller = loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Add a User");
            stage.setResizable(false);
            stage.show();
            controller.bottonmodify.setVisible(false);
        }else {
            stage.show();
        }
        c++;
    }

    public void onaddimageUser(ActionEvent event){


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String cheminPhoto = file.getAbsolutePath();
            System.out.println("Chemin de la photo sélectionnée : " + cheminPhoto);

            Image imageview = new Image(file.toURI().toString());

            image.setImage(imageview);


        } else {
            System.out.println("Aucun fichier sélectionné.");
        }

        }
    }




