package example.controller;

import example.model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane Connected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void section(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
