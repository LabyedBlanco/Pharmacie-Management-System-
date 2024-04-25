package example.controller;

import example.model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Dashboard_controller extends Controller implements Initializable {
    public DatabaseManager DataConnexion = new DatabaseManager();
    @FXML
    private AnchorPane Connected;
    @FXML
    private Text main;

    public void initialize(URL url , ResourceBundle resourceBundle){

        //This is for connecting with database
        //we are doing this on DashbordController cause its the first to be executed
        try{
            DataConnexion.setConnectionStat(true);
            Online(ConnectionStat(),main,Connected);
        }catch (Exception e){
            DataConnexion.setConnectionStat(false);
            e.printStackTrace();
        }


        //this code is repetetive for each controller to



    }
}
