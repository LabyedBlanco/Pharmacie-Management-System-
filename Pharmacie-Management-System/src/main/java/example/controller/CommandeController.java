package example.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class CommandeController extends Controller implements Initializable {

    @FXML
    private AnchorPane Connected;
    @FXML
    private ComboBox<String> SwithcBox;
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
    Connection conexion = getConnection();

    @Override
    public void initialize(URL url , ResourceBundle resourceBundle){

        Online(ConnectionStat(),main,Connected);

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
        if(SwithcBox != null ) {
            SwithcBox.setItems(FXCollections.observableArrayList("Gestion des Commandes", "Gestion des Avoirs"));
        }
        /*if (Selectbox != null) {
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
        }*/
    }

    public void Ajouter(ActionEvent actionEvent) throws IOException {
        super.NouveauFenetre("AddCommande");
    }
}
