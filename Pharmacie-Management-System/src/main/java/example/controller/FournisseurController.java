package example.controller;


import example.model.DatabaseManager;
import javafx.event.ActionEvent;


import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FournisseurController extends Controller implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Text main;
    @FXML
    private AnchorPane Connected;

    @FXML
    private TableView<?> tableViewActif;

    @FXML
    private ImageView OnBack;

    @FXML
    private TableView<?> tableViewArchived;
    @FXML
    private Text main2;
    @FXML
    private TextField cityfor;

    @FXML
    private TextField countryfor;

    @FXML
    private TextField emailfor;

    @FXML
    private ComboBox<String> genderfor;


    @FXML
    private TextField namefor;

    @FXML
    private TextField phonefor;
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        try {
            DatabaseManager Data = new DatabaseManager();
            boolean isConnected = Data.ConnectionStat();
            if (isConnected) {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: green; -fx-background-radius: 100px");
            } else {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
            }
        } catch (Exception e) {
            System.err.println("Error initializing connection: " + e.getMessage());
        }

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
        }
        Online(ConnectionStat(),main,Connected);

    }
    @FXML
    public void Addfor() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void AddforA(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ArchiverF.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void afficherFournisseursActifs() {

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
            main2.setText("Fournisseur Actif");
            OnBack.setVisible(false);
        } else {
            System.err.println("TableView actif is null");
        }
    }

    @FXML
    void afficherFournisseursArchives() {

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(false);
            tableViewArchived.setVisible(true);
            main2.setText("Fournisseur Archivé");
            OnBack.setVisible(true);

        } else {
            System.err.println("TableView  archive is null");
        }
    }
    @FXML
    public void addf() {
        String name = namefor.getText();
        String email = emailfor.getText();
        String phone = phonefor.getText();
        String gender = genderfor.getValue();
        String city=cityfor.getText();
        String country=countryfor.getText();

        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            conn = dbManager.getConnection();

            String sql = "INSERT INTO fournisseur (Nomf, Telf, Emailf,country,city, gender) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, country);
            statement.setString(5, city);
            statement.setString(6, gender);

            statement.executeUpdate();


            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été insérées avec succès !");
            alert.showAndWait();
            Addfor();

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de données : " + e.getMessage());
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("probleme d'insertion !");
            alert.showAndWait();
        } finally {
            if (conn != null) {
                try {
                    dbManager.closeConnection();
                } catch (SQLException sqle) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + sqle.getMessage());
                }
            }
        }
    }
    //public void performSomeAction(ActionEvent event) {
       // Rafraichir(event);
    //}

}