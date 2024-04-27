package example.controller;

import example.model.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane Connected;
    @FXML
    private ComboBox Role;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Role != null) {
            Role.setItems(FXCollections.observableArrayList("Gérant","Pharmacien", "Admin"));
            stage.setResizable(false);
        }
    }

    @Override
    public void FermerFentere(javafx.event.ActionEvent event) throws IOException {
        super.FermerFentere(event);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        System.out.print("Width = "+screenWidth+" Height = "+screenHeight);
        Scene scene = new Scene(fxmlLoader.load(),screenWidth , screenHeight);
        Stage Main = new Stage();

            Main.setTitle("Se Connecter");
            Main.setScene(scene);
            Main.show();
            System.out.println("Vous avez connecter !:");

    }
}



