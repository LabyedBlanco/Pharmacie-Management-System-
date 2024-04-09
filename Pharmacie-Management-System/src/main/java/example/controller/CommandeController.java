package example.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private ListView<String> Listeview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Kadkjsl" , "djsklds" , "sdpoid"};
        Listeview.getItems().addAll(items);
    }
}
