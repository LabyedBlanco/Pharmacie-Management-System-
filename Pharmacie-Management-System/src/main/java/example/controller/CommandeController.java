package example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private ComboBox<String> Selectbox;

    @FXML
    private Text main;

    @FXML
    private GridPane Avoirs;

    @FXML
    private GridPane Commande;


    @Override
    public void initialize(URL url , ResourceBundle resourceBundle){
        if (Selectbox != null) {
            Selectbox.setItems(FXCollections.observableArrayList("Gestion des Commandes", "Gestion des Avoirs"));
            Selectbox.setOnAction(e -> {
                String selectedOption = Selectbox.getValue();
                System.out.println(selectedOption);
                if (selectedOption == "Gestion des Avoirs") {
                    System.out.println(selectedOption);
                    Commande.setVisible(false);
                    Avoirs.setVisible(true);

                } else {
                    Avoirs.setVisible(false);
                    Commande.setVisible(true);
                }
            });
        }
    }

    public void Ajouter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddCommande.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
