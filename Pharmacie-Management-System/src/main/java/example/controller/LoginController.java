package example.controller;

import example.model.DatabaseManager;
import example.model.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField Email;

    @FXML
    private TextField Password;

    @FXML
    private ComboBox<String> Rolebox;
    public DatabaseManager DataConnexion = new DatabaseManager();
    private String selectedOption;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Rolebox != null) {
            Rolebox.setItems(FXCollections.observableArrayList("Gérant", "Pharmacien", "Admin"));
        }
    }

    @FXML
    void Onpasswordtype(KeyEvent event) {

    }

    @Override
    public void FermerFentere(javafx.event.ActionEvent event) throws IOException {

        Rolebox.setOnAction(e -> {
            selectedOption = Rolebox.getValue();
        });

            if (Verification() == true) {
                super.FermerFentere(event);
                //To load up the new stage and show;
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
                double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
                double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
                System.out.print("Width = " + screenWidth + " Height = " + screenHeight);
                Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
                Stage Main = new Stage();
                Main.setTitle("Se Connecter");
                Main.setScene(scene);
                Main.show();
                System.out.println("Vous avez connecter !:");

            }else{
                System.out.println("Password ou Email eroner ");
            }

    }

    public boolean Verification() {

        //the SQL request ;
        System.out.println(selectedOption);
        String sql = "SELECT u.Email, u.Mpasse FROM utilisateur u WHERE u.Email = '"+Email.getText()+"' AND u.Mpasse = '"+Password.getText()+"' AND u.Role = '"+Rolebox.getValue()+"';";
        //prepare the Statement;
        PreparedStatement statement = null;
        //ceci pour verifier dabbord est ce que vous étes connecter avec la base de donnés ;
        if (getConnection() != null) {
            try {
                System.out.println("1");
                //this is to send the SQL requete;
                statement = getConnection().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    System.out.println("No user found with the provided email and password and role.");
                    Password.setStyle("-fx-border-color: red");
                    Email.setStyle("-fx-border-color: red");
                    return false;
                } else {
                    do {
                        String email = resultSet.getString("Email");
                        String password = resultSet.getString("Mpasse");
                        System.out.println("Email: " + email + ", Password: " + password + ", Role: "+Rolebox.getValue());
                        Password.setStyle("-fx-border-color: red");
                        return true;
                    } while (resultSet.next()); // Move to the next row
                }
            } catch (SQLException e) {
                System.out.println("2");
                System.out.println(e);
                Password.setStyle("-fx-border-color: red");
                Email.setStyle("-fx-border-color: red");
                return false;
            }
        } else {
            System.out.println("Vous n'éte pas connecter avec la Base de donnés");
            return false;
        }

    }
}




