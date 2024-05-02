package example.controller;
import example.Services.vente;

import example.model.DatabaseManager;
import javafx.beans.Observable;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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

    @FXML
    public AnchorPane Connected;
    @FXML
    private Text main;

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
    private TableView<vente> tabresult= new TableView<>();


    @FXML
    private TableColumn<vente, String> tabcat=new TableColumn<>("Category");

    @FXML
    private TableColumn<vente, Integer> tabcl=new TableColumn<>("Client");

    @FXML
    private TableColumn<vente, String> tabcode=new TableColumn<>("code");

    @FXML
    private TableColumn<vente, String> tabdate=new TableColumn<>("date");

    @FXML
    private TableColumn<vente, String> tabmed=new TableColumn<>("med");

    @FXML
    private TableColumn<vente, Float> tabprix=new TableColumn<>("prix");

    @FXML
    private TableColumn<vente, SimpleIntegerProperty> tabquan=new TableColumn<>("quantite");

    @FXML
    private TableColumn<vente, String> tabu=new TableColumn<>("user");





    @FXML
    private TableView<vente> listPurchases= new TableView<>();

    @FXML
    private TableColumn<vente, String> shcate=new TableColumn<>("Category");

    @FXML
    private TableColumn<vente, Integer> shcl=new TableColumn<>("Client");

    @FXML
    private TableColumn<vente, String> shcode=new TableColumn<>("code");

    @FXML
    private TableColumn<vente, String> shdate=new TableColumn<>("date");

    @FXML
    private TableColumn<vente, String> shmed=new TableColumn<>("med");

    @FXML
    private TableColumn<vente, Float> shprix=new TableColumn<>("prix");

    @FXML
    private TableColumn<?, ?> shqua=new TableColumn<>("quantite");

    @FXML
    private TableColumn<?, ?> shuser=new TableColumn<>("user");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Online(ConnectionStat(), main, Connected);

        String sql="SELECT `IDv`, `Prixv`, `Datev`, `IDc`, `IDca`, `IDu`, `MethPayementV` FROM `vente`";

        try {
            System.out.println("dkhol");
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();

                //for showing the table
                float prix = (rs.getFloat("Prixv"));//FloatProperty
                w.setprixv(prix);
                w.setdate("02/05/2024");
                w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("MethPayementV"));

                //System.out.println(w.getdate());


                w.setcateg("testcateg");
                w.setcode("code");
                w.setmed("medi");

                //w.setcateg(rs.getString("categ"));
                //w.setcode(rs.getString("code"));
                //w.setmed(rs.getString("med"));
                //int qua=rs.getInt("qua");
                //SimpleIntegerProperty quant = new SimpleIntegerProperty();
                //quant.set(qua);
                //w.setqua(quant);

                System.out.println(w.getcateg());


                shcate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
                shdate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getdate()));
                shcl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getidcl()).asObject());
                shcode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcode()));
                shmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getprixv()).asObject());

                ObservableList<vente> ventes=listPurchases.getItems();
                ventes.add(w);
                listPurchases.setItems(ventes);





            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("33");
        }





    }






        @FXML
        public void onadd() throws IOException {
            super.NouveauFenetre("Ajouter-vente");
        }

        @FXML
        public void addpurchases(ActionEvent event) throws IOException {

            String prixstr=addprix.getText();
            float prixf = Float.parseFloat(prixstr);
            //SimpleFloatProperty prixflo = new SimpleFloatProperty(prixf);


            String qua=addqte.getText();
            SimpleIntegerProperty quan = new SimpleIntegerProperty();


            int quint=Integer.parseInt(qua);


            System.out.println(addmed.getText());
            System.out.println(prixf);


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

            SimpleIntegerProperty idcl = new SimpleIntegerProperty();

            vente x=new vente(prixf,datefrm,5,1,0,method,categ,code,med,quint);

            String sql="INSERT INTO `vente`(`Prixv`, `Datev`,`IDu`,`IDca`,`IDc`,`MethPayementV`) VALUES (?,?,?,?,?,?)";
            try{

                PreparedStatement statement =getConnection().prepareStatement(sql);
                statement.setFloat(1,prixf);
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

            } catch (SQLException e) {

                System.out.println("connection failed");
                System.out.println(e.getMessage());


            }



        }


    }




