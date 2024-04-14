package example.controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView image;

    int c=0;
    int d=0;
    Stage stage = new Stage();

    @FXML
    private void onaddimageproduct(ActionEvent event){


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");

            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null) {
                Image img=new Image(file.toURI().toString());
                image.setImage(img);

            }
    }

    public void addproduct(ActionEvent event) throws IOException {

        if(c==0){
            c++;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ajouter-produit.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Product");
            stage.show();
        }else{
            stage.show();

        }


    }
    public void fichproduct(ActionEvent event) throws IOException {

        if(d==0){
            d++;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fich-produit.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("sheet product");
        stage.show();
    }else{
        stage.show();
    }

    }
}
