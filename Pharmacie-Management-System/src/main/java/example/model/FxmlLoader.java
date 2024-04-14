package example.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FxmlLoader {
    private BorderPane view;
    private GridPane View;



    public Pane setPage(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+filename+".fxml"));
        view = fxmlLoader.load();
        return view;
    }


    public Pane SetPage(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+filename+".fxml"));
        View = fxmlLoader.load();
        return View;
    }
}
