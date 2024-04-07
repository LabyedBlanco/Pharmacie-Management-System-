package example.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        System.out.print("Width = "+screenWidth+" Height = "+screenHeight);
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Font labiad = Font.loadFont("resources/fonts/oblong.ttf", 45);

    }

    public static void main(String[] args) {
        launch();
    }
}