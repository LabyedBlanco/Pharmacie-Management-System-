package example.controller;

import example.Services.Produit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class controlfich extends Controller implements Initializable {


    @FXML
    private Text categ;

    @FXML
    private Text code;

    @FXML
    private ImageView img;

    @FXML
    private Text med;

    @FXML
    private Text prixx;

    @FXML
    private Text qua;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        int idp=Produit_controller.idp;


            System.out.println("Selected ID: " + idp);

            try {
                String sql = "SELECT * FROM produit p join catégorie cat on cat.IDcat=p.IDcat WHERE IDp = ?;";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, idp);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String libellep = rs.getString("Libellép");
                    String codebr = rs.getString("Codebr");
                    String prixv = rs.getString("Prixv");
                    String qte = rs.getString("Qte");
                    byte[] imagedata=rs.getBytes("Imagep");
                    String categoryText = rs.getString("Libelléca");


                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imagedata);
                    Image image = new Image(inputStream);



                    System.out.println("Fetched details - libellep: " + libellep + ", codebr: " + codebr + ", prixv: " + prixv + ", qte: " + qte);

                    Platform.runLater(() -> {
                        try {
                            if (categ != null) {
                                categ.setText(categoryText);
                                System.out.println("categ updated to: " + categoryText);
                            } else {
                                System.out.println("categ is null");
                            }
                            if (img != null) {
                                img.setImage(image);
                                System.out.println("img updated");
                            } else {
                                System.out.println("img is null");
                            }
                            if (med != null) {
                                med.setText(libellep);
                                System.out.println("med updated to: " + libellep);
                            } else {
                                System.out.println("med is null");
                            }

                            if (code != null) {
                                code.setText(codebr);
                                System.out.println("code updated to: " + codebr);
                            } else {
                                System.out.println("code is null");
                            }

                            if (prixx != null) {
                                prixx.setText(prixv);
                                System.out.println("prixx updated to: " + prixv);
                            } else {
                                System.out.println("prixx is null");
                            }

                            if (qua != null) {
                                qua.setText(qte);
                                System.out.println("qua updated to: " + qte);
                            } else {
                                System.out.println("qua is null");
                            }
                        } catch (Exception e) {
                            System.err.println("Error setting text: " + e.getMessage());
                        }
                    });
                }
                stmt.close();
                rs.close();

            } catch (SQLException ea) {
                System.out.println(ea.getMessage());
            }





}
}
