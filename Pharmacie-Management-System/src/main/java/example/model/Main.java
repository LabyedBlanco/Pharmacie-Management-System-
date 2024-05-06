package example.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),909 , 604);
        stage.setTitle("Se Connecter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        //Trying to get Connection with Database;
        try{
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();
            dbManager.setConnectionStat(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        launch();
    }
}