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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        System.out.print("Width = "+screenWidth+" Height = "+screenHeight);
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Pharmacie.exe");
        stage.setScene(scene);
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