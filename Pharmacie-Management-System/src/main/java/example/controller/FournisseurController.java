package example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
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
        super.NouveauFenetre("Add");
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
}