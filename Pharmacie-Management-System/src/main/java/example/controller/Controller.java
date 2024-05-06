package example.controller;

import example.model.DatabaseManager;
import example.model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.interfaces.ECPublicKey;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller extends DatabaseManager {
    @Override
    //this is to get conexion in order to use it in sending SQL requests
    public Connection getConnection() {
        return super.getConnection();
    }
    //this to update the Stat in navbar
    public void Online(boolean Stat, Text text, AnchorPane pane) {
        if (pane != null) {
            if (Stat) {
                pane.setStyle("-fx-background-color: green; -fx-background-radius: 100px");
                text.setText("Active");
            } else {
                pane.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
                text.setText("Offline");
            }
        }
    }

    //this is to loadUp an image
    public File ParcourirFichier(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File file = fileChooser.showOpenDialog(new Stage());

        return file;
    }

    //this is to start an new Stage (fenetre)
    public void NouveauFenetre(String NomFichier) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + NomFichier + ".fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //this is to close the current Stage
    public void FermerFentere(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    //this is to Connfirm Something
    int c;

    public void MessageConfirmation(String NoMessge) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + NoMessge + ".fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Alert!");
    }

    public void Annuler(ActionEvent event) {
        try {
            FermerFentere(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
