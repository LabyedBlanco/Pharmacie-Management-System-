package example.controller;

import example.model.FxmlLoader;
import javafx.event.ActionEvent;
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
        System.out.print("Gestion des Produit");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.setPage("Produits");
        MainPane.add(view,1,0);
    }

}