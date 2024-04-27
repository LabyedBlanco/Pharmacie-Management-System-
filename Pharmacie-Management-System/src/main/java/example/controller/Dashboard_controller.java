package example.controller;

import example.model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Dashboard_controller extends Controller implements Initializable {
    public DatabaseManager DataConnexion = new DatabaseManager();
    @FXML
    private AnchorPane Connected;
    @FXML
    private Text main;

    public void initialize(URL url, ResourceBundle resourceBundle) {

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


            //This is for connecting with database
            //we are doing this on DashbordController cause its the first to be executed
            try {
                DataConnexion.setConnectionStat(true);
                Online(ConnectionStat(), main, Connected);
            } catch (Exception e1) {
                DataConnexion.setConnectionStat(false);
                e1.printStackTrace();
            }


            //this code is repetetive for each controller to


        }
    }
}
