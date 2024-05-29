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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        if(LoginController.Role == "Pharmacien" | LoginController.Role == "Admin") {
            MainPane.getChildren().clear();
            System.out.print("\nGestion des Produit");
            Pane view = object.setPage("Produits");
            MainPane.add(view, 0, 0);
            prod.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
            dash.setStyle("-fx-background-color: transparent");
            vent.setStyle("-fx-background-color: transparent");
            util.setStyle("-fx-background-color: transparent");
            comm.setStyle("-fx-background-color: transparent");
            four.setStyle("-fx-background-color: transparent");
            currentPage = "Produits";
        }else {
            showAlert("Alert " , "Vous ne pouvez pas acceder different role ");
        }
    }

    public void OnUtilisateur () throws IOException {
        if(LoginController.Role == "Gérant" | LoginController.Role == "Admin") {
            MainPane.getChildren().clear();
            System.out.print("\nGestion des Utilisateurs");
            Pane view = object.setPage("Utilisateur");
            MainPane.add(view, 0, 0);
            util.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
            dash.setStyle("-fx-background-color: transparent");
            vent.setStyle("-fx-background-color: transparent");
            comm.setStyle("-fx-background-color: transparent");
            prod.setStyle("-fx-background-color: transparent");
            four.setStyle("-fx-background-color: transparent");
            currentPage = "Utilisateur";
        }else {
            showAlert("Alert " , "Vous ne pouvez pas acceder different role ");
        }
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
        if(LoginController.Role == "Gérant" | LoginController.Role == "Admin") {
            MainPane.getChildren().clear();
            System.out.print("\nCommande");
            Pane view = object.setPage("Commande");
            MainPane.add(view, 0, 0);
            comm.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
            dash.setStyle("-fx-background-color: transparent");
            vent.setStyle("-fx-background-color: transparent");
            util.setStyle("-fx-background-color: transparent");
            prod.setStyle("-fx-background-color: transparent");
            four.setStyle("-fx-background-color: transparent");
            currentPage = "Commande";
        }else {
            showAlert("Alert " , "Vous ne pouvez pas acceder differents role ");
        }
    }
    public void OnVentes () throws IOException {
        if(LoginController.Role == "Pharmacien" | LoginController.Role == "Admin") {
            MainPane.getChildren().clear();
            System.out.print("\nVentes");
            Pane view = object.setPage("Ventes");
            MainPane.add(view, 0, 0);
            vent.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
            dash.setStyle("-fx-background-color: transparent");
            comm.setStyle("-fx-background-color: transparent");
            util.setStyle("-fx-background-color: transparent");
            prod.setStyle("-fx-background-color: transparent");
            four.setStyle("-fx-background-color: transparent");
            currentPage = "Ventes";
        }else {
            showAlert("Alert " , "Vous ne pouvez pas acceder differents role ");
        }
    }


    public void OnFournisseur () throws IOException {
        if(LoginController.Role == "Gérant" | LoginController.Role == "Admin") {
            MainPane.getChildren().clear();
            System.out.print("\nFournisseur");
            view = object.setPage("Fournisseur");
            MainPane.add(view, 0, 0);
            four.setStyle("-fx-background-color: transparent;-fx-effect: dropshadow(gaussian, white, 10, 0.05, 0, 0);-fx-border-color: WHITE; -fx-border-width: 0px 0px 0px 3px;");
            dash.setStyle("-fx-background-color: transparent");
            vent.setStyle("-fx-background-color: transparent");
            util.setStyle("-fx-background-color: transparent");
            comm.setStyle("-fx-background-color: transparent");
            prod.setStyle("-fx-background-color: transparent");
            currentPage = "Fournisseur";
        }else {
            showAlert("Alert " , "Vous ne pouvez pas acceder differents role ");
        }
    }


    public void Onlogout()  throws IOException{

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("deconexion");
        dialog.setHeaderText("Voulez vous vraiment de ce deconceter?");
        ButtonType okButton = new ButtonType("deconnecter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            Stage stage = (Stage) MainPane.getScene().getWindow();
            stage.close();

        }

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