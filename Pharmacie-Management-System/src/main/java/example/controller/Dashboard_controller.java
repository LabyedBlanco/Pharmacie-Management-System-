package example.controller;
import example.model.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class Dashboard_controller implements Initializable {

    @FXML
    private AnchorPane Connected;
    @FXML
    private Text main;

    public void initialize(URL url , ResourceBundle resourceBundle){
        try {
            DatabaseManager Data = new DatabaseManager();
            boolean isConnected = Data.ConnectionStat();
            if (isConnected) {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: green; -fx-background-radius: 100px");
            } else {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
                main.setText("Offline");
            }
        } catch (Exception e) {
            System.err.println("Error initializing connection: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
    }


    public void start(Stage primaryStage) {
        
    }
}
