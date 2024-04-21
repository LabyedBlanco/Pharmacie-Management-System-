package example.controller;


import example.model.DatabaseManager;
import javafx.event.ActionEvent;


import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
import java.util.ResourceBundle;

public class FournisseurController implements Initializable {
    @FXML
    private ImageView imageView;

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


            Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
            DatabaseManager Data = new DatabaseManager();
            if (Data.ConnectionStat() == true) {
                System.out.print(Data.ConnectionStat());
                Connected.setStyle("-fx-background-color: green;-fx-background-radius: 100px");
            }else {
                System.out.print(Data.ConnectionStat());
                Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
            }


        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
        }
    }
    @FXML
    public void Addfor(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void parcourirPhoto() {
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());


        if (selectedFile != null) {

            String cheminPhoto = selectedFile.getAbsolutePath();
            System.out.println("Chemin de la photo sélectionnée : " + cheminPhoto);

            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
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
            System.err.println("TableView is null");
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
            System.err.println("TableView is null");
        }
    }
    @FXML
    public void addf(){
        String name = namefor.getText();
        String email = emailfor.getText();
        String phone = phonefor.getText();
        String city = cityfor.getText();
        String country = countryfor.getText();
        String gender = genderfor.getValue();

        try{
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3307/pharmacie","root","");

            String sql = "INSERT INTO fournisseur (,Nomf ,Telf , Emailf,gender) VALUES(?,?,?,?,?,?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3,phone );
            statement.setString(4, email);
            statement.setString(5,gender);
            //statement.setString(6,password);
            statement.executeUpdate();
            System.out.println("Données insérées avec succès !");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}