package example.controller;

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


<<<<<<< HEAD
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
=======
public class VentesController extends Controller implements Initializable {
>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7
    @FXML
    private Text main;
    @FXML
    static TableView<String> listPurchases;
    @FXML
    private AnchorPane Connected;
    @FXML
    private TextField amount;

    @FXML
    private TextField clientname;

    @FXML
    private DatePicker date;

    @FXML
    private TextField productname;



    public void initialize(URL url , ResourceBundle resourceBundle){
        Online(ConnectionStat(),main,Connected);
    }

    public void onadd() throws IOException {
        super.NouveauFenetre("Ajouter-vente");
    }







    public void addpurchases(ActionEvent event) throws IOException {
        String amounttxt=amount.getText();
        int amountint = Integer.parseInt(amounttxt);


        System.out.println(amountint);
        System.out.println(productname.getText());
        System.out.println(date.getValue());
        System.out.println(clientname.getText());


    }



    }



