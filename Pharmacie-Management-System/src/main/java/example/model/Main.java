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
        System.out.print("Width = " + screenWidth + " Height = " + screenHeight);
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Pharmacie.exe");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        int id = 1;
        String name = "John Doe";
        int age = 30;
        String adress = "oujda12";
        String telc = "0699955";
        String password = "redaf";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/pharmacie", "root", "");
            //System.out.println(conn);
            String sql = "INSERT INTO client (IDc, Nomc,adress,age,Telc,passwordc) VALUES (?, ?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, adress);
            statement.setInt(4, age);
            statement.setString(5, telc);
            statement.setString(6, password);
            statement.executeUpdate();
            System.out.println("Données insérées avec succès !");
        } catch (Exception e) {


            //Trying to get Connection with Database *
            try {
                DatabaseManager dbManager = new DatabaseManager();
                Connection conn = dbManager.getConnection();
                dbManager.setConnectionStat(true);

            } catch (Exception ei) {


                ei.printStackTrace();
            }


            launch();

        }
    }
}