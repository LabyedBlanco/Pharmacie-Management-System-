package example.controller;

import example.Services.Utilisateur;
import example.model.DatabaseManager;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import example.Services.Commande;


public class CommandeController extends Controller implements Initializable {
    @FXML
    private GridPane Avoirs;

    @FXML
    private GridPane Commande;

    @FXML
    private AnchorPane Connected;

    @FXML
    private TableView<Commande> Commandes;
    @FXML
    private TableColumn<Commande,String> Datec;
    @FXML
    private TableColumn<Commande,String> IDcommande;
    @FXML
    private TableColumn<Commande,String> Idfournisseur;
    @FXML
    private TableColumn<Commande,String> PrixCo;
    @FXML
    private TableColumn<Commande,String> Status;
    @FXML
    private TableColumn<Commande,String> utilisateur;
    @FXML
    private TableColumn<Commande,String> Caisse;
    @FXML
    private TableColumn<Commande,String> Methode;
    @FXML
    private ComboBox<String> Selectbox;
    @FXML
    private Text main;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
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
    private ComboBox<String> ComPayement;
    Connection conexion = getConnection();

    @Override
    public void initialize(URL url , ResourceBundle resourceBundle){

        Online(ConnectionStat(),main,Connected);
        new Thread(this::Commande).start();

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
            Selectbox.setItems(FXCollections.observableArrayList("Gestion de Commande","Gestion des Avoirs"));

            Selectbox.setOnAction(e -> {
                System.out.println("labiad");
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

    public void Ajouter(ActionEvent actionEvent) throws IOException {
        super.NouveauFenetre("AddCommande");
    }

    public void Commande(){
        ObservableList<Commande> data = FXCollections.observableArrayList();
        try{
            String sqlSelect="SELECT c.IDco,c.Prixa,c.Datec,c.IDf,c.IDca,c.IDu,c.MethPayementC,c.Stat \n" +
                    "FROM commande c;";
            PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();

            while (result.next()){
                Commande Com = new Commande(
                        new SimpleStringProperty(result.getString("IDco")),   // IDcommande
                        new SimpleStringProperty(result.getString("Prixa")),   // Prixa
                        new SimpleStringProperty(result.getString("Datec")), // DateCommande
                        new SimpleStringProperty(result.getString("IDf")),
                        new SimpleStringProperty(result.getString("IDca")),
                        new SimpleStringProperty(result.getString("IDu")),   // IdUtilisateur
                        new SimpleStringProperty(result.getString("MethPayementC")), // MethodePayement
                        new SimpleStringProperty(result.getString("Stat"))  // Status
                );

                Com.Afficher();

                IDcommande.setCellValueFactory(f->f.getValue().IDcommande);
                Datec.setCellValueFactory(f->f.getValue().DateCommande);
                PrixCo.setCellValueFactory(f->f.getValue().Prixa);
                Idfournisseur.setCellValueFactory(f->f.getValue().IdFournisseur);
                utilisateur.setCellValueFactory(f->f.getValue().IdUtilisateur);
                Status.setCellValueFactory(f->f.getValue().Status);
                Caisse.setCellValueFactory(f->f.getValue().IdCaisse);
                Methode.setCellValueFactory(f->f.getValue().MethodePayement);
                data.add(Com);
            }
            Commandes.setItems(data);
        }

        catch (SQLException e) {
            System.out.println(e);
        }

    }


}

