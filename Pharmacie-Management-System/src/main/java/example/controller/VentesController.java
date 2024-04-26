package example.controller;

import example.model.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class VentesController implements Initializable {

    public AnchorPane Connected;
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
    @FXML
    private Text main;

    @FXML
    static TableView<String> listPurchases;

    private int c = 0;
    public static void main(String[] args) {

    }

    public void start(Stage primaryStage) {

    }

    Stage stage = new Stage();

    public void onadd(ActionEvent event) throws IOException {

        if(c==0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajouter-vente.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Purchase");
            stage.show();

        }else {
            stage.show();
        }
        c++;
    }



    @FXML
    private TextField amount;

    @FXML
    private TextField clientname;

    @FXML
    private DatePicker date;

    @FXML
    private TextField productname;





    public void addpurchases(ActionEvent event) throws IOException {
        String amounttxt=amount.getText();
        int amountint = Integer.parseInt(amounttxt);


        System.out.println(amountint);
        System.out.println(productname.getText());
        System.out.println(date.getValue());
        System.out.println(clientname.getText());


    }



    }



