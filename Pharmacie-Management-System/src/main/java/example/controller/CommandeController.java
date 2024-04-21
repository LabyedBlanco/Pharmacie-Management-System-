package example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private ComboBox<String> Selectbox;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Text main;

    @FXML
    private GridPane Avoirs;

    @FXML
    private GridPane Commande;

    @FXML
    private TextField AjProduit;

    @FXML
    private Button ComConfirm;

    @FXML
    private DatePicker ComDate;

    @FXML
    private TextField ComDetails;

    @FXML
    private TextField ComFournisseur;

    @FXML
    private TextField ComQantite;


    @FXML
    private ComboBox<String> Depot;

    @FXML
    private GridPane MainPane;
    @FXML
    private ComboBox<String> ComPayement;





    @Override
    public void initialize(URL url , ResourceBundle resourceBundle){
        if(Depot != null){
            Depot.setItems(FXCollections.observableArrayList("1","2","3","4"));
            Depot.setOnAction(e -> {
                String selectedOption = Depot.getValue();
                System.out.println(selectedOption);
            });
        }

        if(ComPayement != null){
            ComPayement.setItems(FXCollections.observableArrayList("Cash","Check","Carte Bancaire"));
            ComPayement.setOnAction(e -> {
                String selectedOption = ComPayement.getValue();
                System.out.println(selectedOption);
            });
        }

        if (Selectbox != null) {
            Selectbox.setItems(FXCollections.observableArrayList("Gestion des Commandes", "Gestion des Avoirs"));
            Selectbox.setOnAction(e -> {
                String selectedOption = Selectbox.getValue();
                System.out.println(selectedOption);
                if (selectedOption == "Gestion des Avoirs") {
                    System.out.println(selectedOption);
                    Commande.setVisible(false);
                    Avoirs.setVisible(true);

                } else {
                    Avoirs.setVisible(false);
                    Commande.setVisible(true);
                }
            });
        }
    }
    private int c = 0;
    Stage stage = new Stage();
    public void Ajouter() throws IOException {

        if (c==0) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddCommande.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            stage.show();
        }
        c++;
    }

}
