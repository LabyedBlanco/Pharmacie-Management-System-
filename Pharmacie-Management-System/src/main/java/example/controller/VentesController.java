package example.controller;

import example.model.FxmlLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;




public class VentesController extends Application {



    @FXML
    private Text main;

    @FXML
    static TableView<String> listPurchases;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void onadd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajouter-vente.fxml"));
        Parent root = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add a Purchase");
        stage.show();
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



