package example.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import example.Services.Produit;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import example.Services.Commande;
import org.controlsfx.control.textfield.TextFields;


public class CommandeController extends Controller implements Initializable {
    ObservableList<Produit> Data = FXCollections.observableArrayList();
    ObservableList<Commande> data = FXCollections.observableArrayList();


    @FXML
    private GridPane Avoirs;
    @FXML
    private Text Prixtotal;
    @FXML
    private DatePicker ComDate;
    @FXML
    private GridPane CommandeGridpane;
    @FXML
    private AnchorPane Connected;
    @FXML
    private TableView<Commande> Commandes;
    @FXML
    private TableColumn<Commande, String> Datec;
    @FXML
    private TableColumn<Commande, String> IDcommande;
    @FXML
    private TableColumn<Commande, String> Idfournisseur;
    @FXML
    private TableColumn<Commande, String> PrixCo;
    @FXML
    private TableColumn<Commande, String> Status;
    @FXML
    private TableColumn<Commande, String> utilisateur;
    @FXML
    private TableColumn<Commande, String> Caisse;
    @FXML
    private TableColumn<Commande, String> Methode;
    @FXML
    private TableColumn<Produit, String> Nom;
    @FXML
    private TableColumn<Produit, Integer> Order;
    @FXML
    private TableColumn<Produit, String> Prix;
    @FXML
    private TableView<Produit> Produits;
    @FXML
    private TableColumn<Produit, String> Quantite;
    @FXML
    private TableColumn<Produit, String> Reference;
    @FXML
    private ComboBox<String> Selectbox;
    @FXML
    private Text main;
    @FXML
    private TextField ComQantite;
    @FXML
    private ComboBox<String> Depot;
    @FXML
    private ComboBox<String> ComPayement;
    @FXML
    private TextField SearchProduit;
    @FXML
    private TextField SearchTextfeild;
    @FXML
    private TextField SearchFournisseur;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (SearchProduit != null) {
            List<String> possibleSuggestions = new ArrayList<>(List.of(
                    "C", "C#"));

            try {
                String sqlSelect = "SELECT p.Libellép FROM produit p;";
                PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
                ResultSet result = stat.executeQuery();

                while (result.next()) {
                    String libeller = result.getString("Libellép");
                    possibleSuggestions.add(libeller);
                    System.out.println(libeller);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            TextFields.bindAutoCompletion(SearchProduit, possibleSuggestions);
        }

        if (SearchFournisseur != null) {
            List<String> possibleSuggestions = new ArrayList<>(List.of(
                    "C", "C#"));

            try {
                String sqlSelect = "SELECT f.Nomf FROM fournisseur f;";
                PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
                ResultSet result = stat.executeQuery();

                while (result.next()) {
                    String libeller = result.getString("Nomf");
                    possibleSuggestions.add(libeller);
                    System.out.println(libeller);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            TextFields.bindAutoCompletion(SearchFournisseur, possibleSuggestions);
        }


        Online(ConnectionStat(), main, Connected);
        new Thread(this::Commande).start();
        new Thread(this::Produit).start();


        if (Depot != null) {
            Depot.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
            Depot.setOnAction(e -> {
                String selectedOption = Depot.getValue();
                System.out.println(selectedOption);
            });
        }

        if (ComPayement != null) {
            ComPayement.setItems(FXCollections.observableArrayList("Cash", "Check", "Carte Bancaire"));
            ComPayement.setOnAction(e -> {
                String selectedOption = ComPayement.getValue();
                System.out.println(selectedOption);
            });
        }

        if (Selectbox != null) {
            Selectbox.setItems(FXCollections.observableArrayList("Gestion de Commande", "Gestion des Avoirs"));

            Selectbox.setOnAction(e -> {
                System.out.println("labiad");
                String selectedOption = Selectbox.getValue();
                System.out.println(selectedOption);
                if (selectedOption == "Gestion des Avoirs") {
                    System.out.println(selectedOption);
                    CommandeGridpane.setVisible(false);
                    Avoirs.setVisible(true);
                } else {
                    Avoirs.setVisible(false);
                    CommandeGridpane.setVisible(true);
                }
            });
        }
    }



    public void OnClose(ActionEvent actionEvent) throws IOException {
        super.FermerFentere(actionEvent);
    }
    float prixtotal;
    public void Ajouterliste(ActionEvent actionEvent) {
        String PrdAjouter = SearchProduit.getText();

        try {
            String sqlSelect = "SELECT c.Prixv,c.IDp,c.Libellép FROM produit c WHERE Libellép LIKE '" + PrdAjouter + "';";
            PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();

            while (result.next()) {
                Produit Prod = new Produit(
                        new SimpleStringProperty(result.getString("Libellép")),
                        new SimpleStringProperty(result.getString("Prixv")),
                        new SimpleStringProperty(ComQantite.getText()),
                        new SimpleStringProperty(result.getString("IDp"))
                );
                Nom.setCellValueFactory(f -> f.getValue().libeller);
                Prix.setCellValueFactory(f -> f.getValue().PrixProduit);
                Quantite.setCellValueFactory(f -> f.getValue().Quantite);
                Reference.setCellValueFactory(f -> f.getValue().Idp);
                Data.add(Prod);

                String QuantiteString = Prod.Quantite.get();

                try {
                    float floatValue = Float.parseFloat(QuantiteString);
                    prixtotal += floatValue*result.getFloat("Prixv");
                    System.out.println(prixtotal);
                    Prixtotal.setText(Float.toString(prixtotal));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                selectedDate = ComDate.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = selectedDate.format(formatter);


                Commande Com = new Commande(
                        new SimpleStringProperty(),   // IDcommande
                        new SimpleStringProperty(Prixtotal.getText()),   // Prixa
                        new SimpleStringProperty(formattedDate), // DateCommande
                        new SimpleStringProperty(SearchFournisseur.getText()),
                        new SimpleStringProperty("1"),
                        new SimpleStringProperty(), // IdUtilisateur
                        new SimpleStringProperty(ComPayement.toString()), // MethodePayement
                        new SimpleStringProperty("En Cours")  // Status
                );

                Com.Afficher();

                IDcommande.setCellValueFactory(f -> f.getValue().IDcommande);
                Datec.setCellValueFactory(f -> f.getValue().DateCommande);
                PrixCo.setCellValueFactory(f -> f.getValue().Prixa);
                Idfournisseur.setCellValueFactory(f -> f.getValue().IdFournisseur);
                utilisateur.setCellValueFactory(f -> f.getValue().IdUtilisateur);
                Status.setCellValueFactory(f -> f.getValue().Status);
                Caisse.setCellValueFactory(f -> f.getValue().IdCaisse);
                Methode.setCellValueFactory(f -> f.getValue().MethodePayement);
                data.add(Com);


            }

            Produits.setItems(Data);

        } catch (SQLException e) {
            System.out.println("problem est : " +e);
        }

        //table d'association ;


    }
    LocalDate selectedDate ;

    public void Ajouter(ActionEvent actionEvent) throws IOException {
        prixtotal = 0;
        super.NouveauFenetre("AddCommande");
        //get all the data from ui ;

        selectedDate = ComDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = selectedDate.format(formatter);

        Commande Com = new Commande(
                new SimpleStringProperty("800"),   // IDcommande
                new SimpleStringProperty(),   // Prixa
                new SimpleStringProperty(formattedDate), // DateCommande
                new SimpleStringProperty(SearchFournisseur.getText()),
                new SimpleStringProperty("1"),
                new SimpleStringProperty("333"), // IdUtilisateur
                new SimpleStringProperty(ComPayement.toString()), // MethodePayement
                new SimpleStringProperty("En Cours")  // Status
        );

        Com.Afficher();

        IDcommande.setCellValueFactory(f -> f.getValue().IDcommande);
        Datec.setCellValueFactory(f -> f.getValue().DateCommande);
        PrixCo.setCellValueFactory(f -> f.getValue().Prixa);
        Idfournisseur.setCellValueFactory(f -> f.getValue().IdFournisseur);
        utilisateur.setCellValueFactory(f -> f.getValue().IdUtilisateur);
        Status.setCellValueFactory(f -> f.getValue().Status);
        Caisse.setCellValueFactory(f -> f.getValue().IdCaisse);
        Methode.setCellValueFactory(f -> f.getValue().MethodePayement);
        data.add(Com);
    }

    public void Produit() {
    }

    public void Commande() {

        try {
            String sqlSelect = "SELECT c.IDco,c.Prixa,c.Datec,c.IDf,c.IDca,c.IDu,c.MethPayementC,c.Stat \n" +
                    "FROM commande c;";
            PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();

            while (result.next()) {
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

                IDcommande.setCellValueFactory(f -> f.getValue().IDcommande);
                Datec.setCellValueFactory(f -> f.getValue().DateCommande);
                PrixCo.setCellValueFactory(f -> f.getValue().Prixa);
                Idfournisseur.setCellValueFactory(f -> f.getValue().IdFournisseur);
                utilisateur.setCellValueFactory(f -> f.getValue().IdUtilisateur);
                Status.setCellValueFactory(f -> f.getValue().Status);
                Caisse.setCellValueFactory(f -> f.getValue().IdCaisse);
                Methode.setCellValueFactory(f -> f.getValue().MethodePayement);
                data.add(Com);
            }
            Commandes.setItems(data);
        } catch (SQLException e) {
            System.out.println(e);
        }

        //initialisation de la filtred list ;
        FilteredList<Commande> filteredListcommande = new FilteredList<>(data, b -> true);

        System.out.println(SearchTextfeild.getText());
        SearchTextfeild.textProperty().addListener((observable, OldValue, NewValue) -> {
                    System.out.println(SearchTextfeild.getText());
                    filteredListcommande.setPredicate(Commande -> {
                        //no search value ;
                        if (NewValue.isEmpty() || NewValue.isBlank() || NewValue == null) {
                            return true;
                        }
                        String searchedCommande = NewValue.toLowerCase();

                        if (Commande.IDcommande.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.DateCommande.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.Prixa.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.MethodePayement.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.IdFournisseur.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.IdUtilisateur.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.IdCaisse.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        if (Commande.Status.toString().toLowerCase().indexOf(searchedCommande) > -1) {
                            //si le nom est trouver dans Id commande;
                            return true;
                        }
                        //no result fousdsdnd;
                        return false;
                    });

                    SortedList<Commande> sortedList = new SortedList<>(filteredListcommande);

                    sortedList.comparatorProperty().bind(Commandes.comparatorProperty());

                    Commandes.setItems(sortedList);
                }

        );

    }

}