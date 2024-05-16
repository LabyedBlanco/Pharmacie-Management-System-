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
import javafx.scene.layout.VBox;
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
    private TableColumn<Produit, String> Order;
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
    @FXML
    private ComboBox<String> StatusCom;

    int PanierOrder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String sqldelete = "DELETE FROM commande where Prixa IS NULL;";
            PreparedStatement delete = getConnection().prepareStatement(sqldelete);
            int rowsAffected = delete.executeUpdate();

        }catch (SQLException ex){
            System.out.println(ex+"labiad");
        }


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
            try {


            String sqlSelect = "SELECT d.IDdep FROM depot d;";
            PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                System.out.println(result.getString("IDdep"));
                Depot.setItems(FXCollections.observableArrayList(result.getString("IDdep")));
            }

            }catch (SQLException ex){
                System.out.println(ex);
            }


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
        if(StatusCom != null){
            StatusCom.setItems(FXCollections.observableArrayList("Annuler", "Succes", "En Cours"));

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
    public void OnModify(ActionEvent event){
        Commande c = Commandes.getSelectionModel().getSelectedItem();
        if(Commandes.getSelectionModel().getSelectedItem() == null){
            showAlert("Alert","Aucune element dans le tableux est selecter");
        }
        c.Afficher();
        Modify(c);
    }

    public void Modify(Commande commande){


        TextField fournisseurField = new TextField(commande.IdFournisseur.getValue().toString());
        TextField utilisateurField= new TextField(commande.IdUtilisateur.getValue().toString());
        TextField statusfeild= new TextField(commande.Status.getValue().toString());
        TextField Caissefeild= new TextField(commande.IdCaisse.getValue().toString());
        TextField Prixfeild = new TextField(commande.Prixa.getValue().toString());
        TextField Methodefeild = new TextField(commande.MethodePayement.getValue().toString());

        // Remplir les ComboBox pour le jour, le mois et l'année
        DatePicker datePicker = new DatePicker();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate ;




        datePicker.setValue(LocalDate.parse(commande.DateCommande.getValue()));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier le produit");
        dialog.setHeaderText("Modifier les détails de la commande : " + commande.IDcommande);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("Fournisseur :"), fournisseurField,
                new Label("Prix :"), utilisateurField,
                new Label("Quantité :"), statusfeild,
                new Label("Date d'commande :"),datePicker,
                new Label("Inventaire :"), Caissefeild,
                new Label("Prix Total :"), Prixfeild,
                new Label("Methode de Payement :"), Methodefeild
        );

        dialog.getDialogPane().setContent(vbox);

        ButtonType okButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {


            try {
                String sql = "UPDATE commande SET Prixa = ?, Datec = ?, IDf = ?, IDca = ?, IDu = ? , MethPayementC = ? , Stat = ? WHERE IDco = ?";
                PreparedStatement statement = getConnection().prepareStatement(sql);

                selectedDate = datePicker.getValue();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formattedDate = selectedDate.format(formatter);

                statement.setString(2, formattedDate);
                statement.setString(1, Prixfeild.getText());
                statement.setString(3, fournisseurField.getText());
                statement.setString(4, Caissefeild.getText());
                statement.setString(5, utilisateurField.getText());
                statement.setString(6, Methodefeild.getText());
                statement.setString(7, statusfeild.getText());
                statement.setString(8, commande.IDcommande.getValue());


                // Concaténer les valeurs sélectionnées pour former la date d'expiration au format "année-mois-jour"

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Commande mis à jour avec succès.");
                } else {
                    System.out.println("Aucune modification apportée.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Erreur de format de nombre.");
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors de la modification de la commande." + ex);
            }
        }
    }


    float prixtotal;
    int IDcomm = 999;

    public void Confirmer(ActionEvent event) throws IOException {
        //get date ;

        selectedDate = ComDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = selectedDate.format(formatter);

        if( Depot.getValue() != null && ComDate.getValue() != null && StatusCom.getValue() != null && Prixtotal.getText() != "0"){
            try {
                String sqlupdate = "UPDATE `commande` SET `Prixa` = ?, `Datec` = ?, `MethPayementC` = ?, `Stat` = ? WHERE `IDco` = ?";
                PreparedStatement update = getConnection().prepareStatement(sqlupdate);
                update.setString(1, String.valueOf(prixtotal));
                update.setString(2, formattedDate);
                update.setString(3, ComPayement.getValue().toString());
                update.setString(4, StatusCom.getValue().toString());
                update.setInt(5, IDcomm);
                int effectedRows = update.executeUpdate();


            } catch (SQLException ex) {
                System.out.println("Update exception " + ex);
            }

            super.FermerFentere(event);
        }else {
            showAlert("Alert","Veuiller saisir tout les données ");
        }
    }
    int Action = 0;
    public void Ajouterliste(ActionEvent actionEvent) {
        //first get id produit ;
        if(Action == 0){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ? Cette action supprimera également toutes les ventes et contenus associés.");


            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Action++;
            }
        }

        if(Action>0){
            String PrdAjouter = SearchProduit.getText();

            try {
                String sqlSelect = "SELECT c.Prixv,c.IDp,c.Libellép,c.Qte FROM produit c WHERE Libellép LIKE ?;";
                PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
                stat.setString(1, PrdAjouter);
                ResultSet resultproduit = stat.executeQuery();

                while (resultproduit.next()) {
        /*}catch (SQLException ex){
            System.out.println("produit " + ex);
        }
*/
                    //get quantite

                    String quantite = ComQantite.getText();

                    //get ID commande virtual

                    if (Ajouter == 0) {

                        try {
                            String Idco = "SELECT IDco FROM commande ORDER BY IDco DESC LIMIT 1;";
                            PreparedStatement statIdv = getConnection().prepareStatement(Idco);
                            ResultSet resultIdv = statIdv.executeQuery();

                            while (resultIdv.next()) {
                                IDcomm = resultIdv.getInt(1);
                                System.out.println("previous cmd ID " +IDcomm);
                                IDcomm++;
                                System.out.println("current cmd ID " +IDcomm);
                            }
                        } catch (SQLException ex) {
                            System.out.println("Virtual ID : " + ex);
                        }
                    }

                    Ajouter++;

                    //get ID fournisseur
                    int IDfourni = 999;
                    if (!SearchFournisseur.getText().isEmpty()) {
                        String FourniAjt = SearchFournisseur.getText();
                        try {
                            sqlSelect = "SELECT f.IDf FROM fournisseur f WHERE Nomf LIKE ?;";
                            stat = getConnection().prepareStatement(sqlSelect);
                            stat.setString(1, FourniAjt);
                            ResultSet resultfournisseur = stat.executeQuery();

                            while (resultfournisseur.next()) {
                                IDfourni = resultfournisseur.getInt("IDf");
                            }
                        } catch (SQLException ex) {
                            System.out.println("fournisseur : " + ex);
                        }


                        //default Id user and Id inventaire ;

                        int Idca = 1;
                        int Iduser = 1;

                        //initialize the virtual commande;
                        try {
                            String sqlInsert = "INSERT INTO `commande` (`IDco`, `Prixa`, `Datec`, `IDf`, `IDca`, `IDu`, `MethPayementC`, `Stat`) VALUES (?, NULL, NULL, ?, ?, ?, NULL, NULL);";
                            PreparedStatement stmt = getConnection().prepareStatement(sqlInsert);


                            stmt.setInt(1, IDcomm);
                            stmt.setInt(2, IDfourni);
                            stmt.setInt(3, Idca);
                            stmt.setInt(4, Iduser);

                            int rowsAffected = stmt.executeUpdate();  // Executing the PreparedStatement
                            if (rowsAffected > 0) {
                                System.out.println("INSERT successful!");
                            } else {
                                System.out.println("INSERT failed!");
                            }
                        } catch (SQLException ex) {
                            System.out.println("intialisation de la commande virtuel " + ex);
                        }
                    } else {
                        System.out.println("No fournisseur");
                        showAlert("Alert", "Veuiller choisiser un fournisseur dabbord");
                    }
                    //INSERER maintenent dans la table commander;

                    try {

                        String sqlInsert = "INSERT INTO `commander` (`IDcom`, `IDco`, `Idpro`, `Quantcm`) VALUES (NULL,?,?,?) ";
                        PreparedStatement insert = getConnection().prepareStatement(sqlInsert);
                        System.out.println(sqlInsert);


                        insert.setInt(1, IDcomm);
                        insert.setString(2, resultproduit.getString("IDp"));
                        System.out.println(IDcomm);
                        insert.setString(3, ComQantite.getText());
                        int rowsAffected = insert.executeUpdate();

                        int Qte = Integer.parseInt(ComQantite.getText());

                        int currentQty = resultproduit.getInt("Qte");
                        String productId = resultproduit.getString("IDp");
                        String sqlUpdate = "UPDATE produit SET Qte = ? WHERE IDp = ?;";

                        PreparedStatement update = getConnection().prepareStatement(sqlUpdate);
                        update.setInt(1, Qte + currentQty);
                        update.setString(2, productId);
                        int updateAffected = update.executeUpdate();

                        if(updateAffected > 0){
                            System.out.println("update successful!");
                        }else {
                            System.out.println("update failed!");
                        }

                        if (rowsAffected > 0) {
                            System.out.println("INSERT successful!");
                        } else {
                            System.out.println("INSERT failed!");
                        }


                    } catch (SQLException e) {
                        System.out.println(" Insert problem is : " + e);
                    }

                    if(IDfourni != 999) {

                        Produit Prod = new Produit(
                                new SimpleStringProperty(resultproduit.getString("Libellép")),
                                new SimpleStringProperty(resultproduit.getString("Prixv")),
                                new SimpleStringProperty(ComQantite.getText()),
                                new SimpleStringProperty(resultproduit.getString("IDp")),
                                new SimpleStringProperty(Integer.toString(PanierOrder++))
                        );

                        Nom.setCellValueFactory(f -> f.getValue().libeller);
                        Quantite.setCellValueFactory(f -> f.getValue().Quantite);
                        Prix.setCellValueFactory(f -> f.getValue().PrixProduit);
                        Reference.setCellValueFactory(f -> f.getValue().Idp);
                        Order.setCellValueFactory(f -> f.getValue().order);

                        Prod.Afficher();
                        Data.add(Prod);
                        Produits.setItems(Data);

                        String QuantiteString = Prod.Quantite.get();


                        try {
                            float floatValue = Float.parseFloat(QuantiteString);
                            prixtotal += floatValue * resultproduit.getFloat("Prixv");
                            System.out.println(prixtotal);
                            Prixtotal.setText(Float.toString(prixtotal));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }catch (SQLException ex){
                System.out.println("produit " + ex);
            }
        }




    }

        //table d'association ;



    LocalDate selectedDate ;
    int Ajouter ;

    public void Ajouter(ActionEvent actionEvent) throws IOException {

        Ajouter = 0;
        Action = 0;
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


                Datec.setCellValueFactory(f -> f.getValue().DateCommande);
                PrixCo.setCellValueFactory(f -> f.getValue().Prixa);
                Idfournisseur.setCellValueFactory(f -> f.getValue().IdFournisseur);
                utilisateur.setCellValueFactory(f -> f.getValue().IdUtilisateur);
                Status.setCellValueFactory(f -> f.getValue().Status);
                Caisse.setCellValueFactory(f -> f.getValue().IdCaisse);
                Methode.setCellValueFactory(f -> f.getValue().MethodePayement);
                IDcommande.setCellValueFactory(f -> f.getValue().IDcommande);
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