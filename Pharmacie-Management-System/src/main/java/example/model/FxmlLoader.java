package example.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private BorderPane view;
    private GridPane View;

    public Pane setPage(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+filename+".fxml"));
        view = fxmlLoader.load();
        return view;
    }
<<<<<<< HEAD

=======
    public Pane SetPage(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+filename+".fxml"));
        View = fxmlLoader.load();
        return View;
    }
>>>>>>> 025ef587ee9fbb51ccbc770305fe048ad4d3b1bc
}
