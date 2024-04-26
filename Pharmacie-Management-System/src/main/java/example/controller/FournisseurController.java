package example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
=======
>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
=======
>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7
import java.util.ResourceBundle;

public class FournisseurController extends Controller implements Initializable {

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
    private Text main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Online(ConnectionStat(),main,Connected);

<<<<<<< HEAD

            /*Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
            DatabaseManager Data = new DatabaseManager();
            if (Data.ConnectionStat() == true) {
                System.out.print(Data.ConnectionStat());
                Connected.setStyle("-fx-background-color: green;-fx-background-radius: 100px");
            }else {
                System.out.print(Data.ConnectionStat());
                Connected.setStyle("-fx-background-color: red;-fx-background-radius: 100px");
            }
*/
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
=======
        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
        }

>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
        }


    }

    @FXML
<<<<<<< HEAD
    public void Addfor() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
=======
    public void Addfor(ActionEvent event) throws IOException {
        super.NouveauFenetre("Add");
>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7
    }
    /* @FXML
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
     }*/
    @FXML
    public void AddforA(ActionEvent event) throws IOException {
        NouveauFenetre("ArchiverF");
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
<<<<<<< HEAD
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
            alert.setHeaderText(null); // Pas de sous-titre
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


=======
>>>>>>> 022289110064033b2a58265e1dec8a5c303ee2e7
}