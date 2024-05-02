package example.controller;
import example.Services.vente;

import example.model.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;



public class VentesController extends Controller implements Initializable {

    public AnchorPane Connected;
    @FXML
    private Text main;
    @FXML
    static TableView<String> listPurchases;
    //@FXML
    //private AnchorPane Connected;



    @FXML
    private TextField addmethod;


    @FXML
    private TextField addcat;

    @FXML
    private TextField addcl;

    @FXML
    private TextField addcode;

    @FXML
    private TextField addmed;

    @FXML
    private TextField addprix;

    @FXML
    private TextField addqte;


    @FXML
    private TableView<vente> tabresult;


    @FXML
    private TableColumn<vente, String> tabcat;

    @FXML
    private TableColumn<vente, Integer> tabcl;

    @FXML
    private TableColumn<vente, Integer> tabcode;

    @FXML
    private TableColumn<vente, String> tabdate;

    @FXML
    private TableColumn<vente, String> tabmed;

    @FXML
    private TableColumn<vente, Integer> tabprix;

    @FXML
    private TableColumn<vente, Integer> tabquan;



    @FXML
    private TableColumn<vente, String> tabu;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        Online(ConnectionStat(), main, Connected);
        try {
            DatabaseManager Data = new DatabaseManager();
            boolean isConnected = Data.ConnectionStat();
            if (isConnected) {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: green; -fx-background-radius: 100px");

            } else {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
                main.setText("Offline");
            }
        } catch (Exception e) {
            System.err.println("Error initializing connection: " + e.getMessage());
        }

        String sql="SELECT `IDv`, `Prixv`, `Datev`, `IDc`, `IDca`, `IDu`, `MethPayementV` FROM `vente`";
        try {
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();




            while (rs.next()){

                vente w=new vente();
                //w.setprixv(rs.getFloat("Prixv"));
                w.setdate(rs.getString("Datev"));

                w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("MethPayementV"));

                System.out.println(w.getdate());


                String cat=rs.getString("categ");
                String code=rs.getString("code");
                String med=rs.getString("med");
                int qua=rs.getInt("qua");

                tabcat.setCellValueFactory(new PropertyValueFactory<vente,String>("prixv"));
                tabdate.setCellValueFactory(new PropertyValueFactory<vente,String>("date"));




            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





    }







        public void onadd() throws IOException {
            super.NouveauFenetre("Ajouter-vente");
        }


        public void addpurchases(ActionEvent event) throws IOException {

            String prixstr=addprix.getText();
            float prixfl=Float.parseFloat(prixstr);
            String qua=addqte.getText();
            int quint=Integer.parseInt(qua);

            System.out.println(addmed.getText());
            System.out.println(prixfl);


            System.out.println(addcat.getText());
            System.out.println(addcode.getText());
            System.out.println(addmethod.getText());
            String method=addmethod.getText();
            String categ=addcat.getText();
            String code=addcode.getText();
            String med=addmed.getText();



            Date date=new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String datefrm = dateFormat.format(date);

            System.out.println(datefrm);

            System.out.println(addcl.getText());

            vente x=new vente(prixfl,datefrm,5,1,0,method,categ,code,med,quint);




            String sql="INSERT INTO `vente`(`Prixv`, `Datev`,`IDu`,`IDca`,`IDc`,`MethPayementV`) VALUES (?,?,?,?,?,?)";
            DatabaseManager dbmanager = new DatabaseManager();
            try{
                Connection conn=dbmanager.getConnection();
                PreparedStatement statement =conn.prepareStatement(sql);
                statement.setFloat(1,prixfl);
                statement.setString(2,datefrm);

                // pour l essaie

                statement.setInt(3,0);
                statement.setInt(4,1);
                statement.setInt(5,5);


                statement.setString(6,method);

                int Affected = statement.executeUpdate();

                if (Affected > 0) {
                    System.out.println("Insertion successful");
                } else {
                    System.out.println("Insertion failed");
                }


                statement.close();
                dbmanager.closeConnection();




            } catch (SQLException e) {

                System.out.println("connection failed");
                System.out.println(e.getMessage());





            }



        }


    }




