package example.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Produit_controller extends Controller implements Initializable {

    //this is to make the color of the Connection State
    public void initialize(URL url , ResourceBundle resourceBundle){
        Online(ConnectionStat(),main,Connected);
    }

    @FXML
    private AnchorPane Connected;
    public void start(Stage primaryStage) {

    }
    @FXML
    private ImageView image;
    @FXML
    private Text main;
    int c=0;
    int d=0;
    Stage stage = new Stage();

    public void Onclose(ActionEvent event) throws IOException {
        FermerFentere(event);
    }

    public void Addimage(ActionEvent event){
         File file = ParcourirFichier(event);
            if (file != null) {
                Image img=new Image(file.toURI().toString());
                image.setImage(img);
            }
    }
    public void addproduct(ActionEvent event) throws IOException {
        NouveauFenetre("ajouter-produit");
    }
    public void fichproduct(ActionEvent event) throws IOException {
        NouveauFenetre("fich-produit");
    }
}
