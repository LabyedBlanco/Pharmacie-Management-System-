package example.controller;

import example.model.DatabaseManager;
import example.model.FxmlLoader;
import example.model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController extends Controller implements Initializable {


    public GridPane MainPane;
    @FXML
    private AnchorPane Display;
    @FXML
    private Button comm;
    @FXML
    private Button dash;

    @FXML
    private Button four;

    @FXML
    private Button prod;
    @FXML
    private Button logout;

    @FXML
    private Button util;

    @FXML
    private Button vent;
    @FXML
    Button refraichir;
    private String currentPage;


    public void initialize(URL url , ResourceBundle resourceBundle){
        //Style
        dash.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        prod.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");

        refraichir.setOnAction(this::onRefraichir);

        System.out.print("\nDashboard");
        FxmlLoader object = new FxmlLoader();
        Pane view = null;
        try {
            view = object.setPage("Dashboard");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        MainPane.add(view,0,0);
    }

    public void OnProduit (ActionEvent event) throws IOException {

        MainPane.getChildren().clear();
        System.out.print("\nGestion des Produit");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Produits");
        MainPane.add(view,0,0);
        prod.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");

    }

    public void OnUtilisateur (ActionEvent event) throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nGestion des Utilisateurs");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Utilisateur");
        MainPane.add(view,0,0);
        util.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Utilisateur";
    }
    public void OnDashboard (ActionEvent event) throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nDashboard");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Dashboard");
        MainPane.add(view,0,0);

        dash.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        comm.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Dashboard";

    }

    public void OnCommande (ActionEvent event) throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nCommande");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Commande");
        MainPane.add(view,0,0);
        comm.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Commande";


    }
    public void OnVentes (ActionEvent event) throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nVentes");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Ventes");
        MainPane.add(view,0,0);
        vent.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Ventes";
    }


    public void OnFournisseur (ActionEvent event) throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nFournisseur");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Fournisseur");
        MainPane.add(view,0,0);
        four.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        currentPage ="Fournisseur";

    }


    public void Onlogout(ActionEvent actionEvent)  throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Close.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void OnClose(ActionEvent actionEvent) throws IOException{
        MessageConfirmation("Close");

    }
    public void onRefraichir(ActionEvent event) {
        try {

            FxmlLoader object = new FxmlLoader();
            Pane view = object.setPage(currentPage);
            MainPane.getChildren().clear();
            MainPane.add(view, 0, 0);

            System.out.println("Rafraîchissement effectué");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}