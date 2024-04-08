package example.controller;

import example.model.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {
    @FXML
    private GridPane MainPane;
    @FXML
    private Label welcomeText;



    public void OnProduit (ActionEvent event) throws IOException {
        System.out.print("\nGestion des Produit");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Produits");
        MainPane.add(view,1,0);
    }

    public void OnUtilisateur (ActionEvent event) throws IOException {

        System.out.print("\nGestion des Utilisateurs");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Utilisateur");
        MainPane.add(view,1,0);

    }
    public void OnDashboard (ActionEvent event) throws IOException {

        System.out.print("\nDashboard");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Dashboard");
        MainPane.add(view,1,0);

    }

    public void OnCommande (ActionEvent event) throws IOException {

        System.out.print("\nCommande");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Commande");
        MainPane.add(view,1,0);

    }
    public void OnVentes (ActionEvent event) throws IOException {

        System.out.print("\nVentes");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Ventes");
        MainPane.add(view,1,0);

    }
}