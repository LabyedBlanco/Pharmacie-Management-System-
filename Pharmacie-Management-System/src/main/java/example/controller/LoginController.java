package example.controller;

import example.model.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox Role;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Role != null) {
            Role.setItems(FXCollections.observableArrayList("Gérant","Pharmacien", "Admin"));
        }
    }

        public void section (ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        }
    }

