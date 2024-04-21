package example.controller;
import example.model.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class Dashboard_controller implements Initializable {

    @FXML
    private AnchorPane Connected;

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
    public static void main(String[] args) {
    }


    public void start(Stage primaryStage) {
        
    }
}
