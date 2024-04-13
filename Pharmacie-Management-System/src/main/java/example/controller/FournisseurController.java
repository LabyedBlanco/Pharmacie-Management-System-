package example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FournisseurController {
    @FXML
    private ImageView imageView;
    @FXML
    private void Addfor(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void parcourirPhoto() {
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());


        if (selectedFile != null) {

            String cheminPhoto = selectedFile.getAbsolutePath();
            System.out.println("Chemin de la photo sélectionnée : " + cheminPhoto);

            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
    @FXML
    private void AddforA(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ArchiverF.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
