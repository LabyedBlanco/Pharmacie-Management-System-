package example.controller;

import example.model.FxmlLoader;
import example.model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private Button comm;
    @FXML
    private Button dash;
    @FXML
    private Button four;
    public Button prod;
    @FXML
    private Button logout;
    @FXML
    private Button util;
    @FXML
    private Button vent;
    @FXML
    Button refraichir;
    private String currentPage ="Dashboard" ;
    FxmlLoader object = new FxmlLoader();
    Pane view;
    public void initialize(URL url , ResourceBundle resourceBundle){
        //Style
        dash.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        prod.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
        System.out.print("\nDashboard");
        try {
            view = object.setPage("Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            MainPane.add(view, 0, 0);
    }
    public void OnProduit () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nGestion des Produit");
        Pane view = object.setPage("Produits");
        MainPane.add(view,0,0);
        prod.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Produits";
    }

    public void OnUtilisateur () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nGestion des Utilisateurs");
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
    public void OnDashboard () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nDashboard");
        Pane view = object.setPage("Dashboard");
        MainPane.add(view,0,0);
        dash.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        comm.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        four.setStyle("-fx-background-color: transparent");
        currentPage ="Dashboard" ;
    }
    public void OnCommande () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nCommande");
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
    public void OnVentes () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nVentes");
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


    public void OnFournisseur () throws IOException {
        MainPane.getChildren().clear();
        System.out.print("\nFournisseur");
        view = object.setPage("Fournisseur");
        MainPane.add(view,0,0);
        four.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
        dash.setStyle("-fx-background-color: transparent");
        vent.setStyle("-fx-background-color: transparent");
        util.setStyle("-fx-background-color: transparent");
        comm.setStyle("-fx-background-color: transparent");
        prod.setStyle("-fx-background-color: transparent");
        currentPage ="Fournisseur";
    }


    public void Onlogout()  throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Close.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void OnClose() throws IOException{
        MessageConfirmation("Close");
    }
    public void OnSettings() throws IOException{
        MainPane.getChildren().clear();
        System.out.print("\nSettings");
        view = object.setPage("Settings");
    }

    public void onRefraichir() {
        try {
            MainPane.getChildren().clear();
            view = object.setPage(currentPage);
            MainPane.add(view, 0, 0);

            System.out.println("Rafraîchissement effectué "+currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}