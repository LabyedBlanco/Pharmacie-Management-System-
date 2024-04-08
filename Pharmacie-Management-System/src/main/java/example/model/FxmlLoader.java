package example.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private GridPane view;

    public Pane setPage(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+filename+".fxml"));
        view = fxmlLoader.load();
        return view;
    }
}
