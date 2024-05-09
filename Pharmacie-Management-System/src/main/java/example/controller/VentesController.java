package example.controller;
import example.Services.vente;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class VentesController extends Controller implements Initializable {

    @FXML
    public AnchorPane Connected;
    @FXML
    private Text main;

    @FXML
    private ListView<String> searchli=new ListView<>();
    ObservableList<String> searchlili = FXCollections.observableArrayList();

    @FXML
    private TextField searchprinc = new TextField();


    //@FXML
    //private AnchorPane Connected;

    @FXML
    private Text lmonth= new Text();

    @FXML
    private Text month= new Text();
    @FXML
    private Text total= new Text();
    @FXML
    private Text year= new Text();

    @FXML
    private ChoiceBox<String> addmethod=new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> addcat=new ChoiceBox<>();

    @FXML
    private DatePicker adddate;

    @FXML
    private Text totalaj= new Text();


    @FXML
    private TextField addcl;


    @FXML
    private TextField addmed = new TextField();


    @FXML
    private TextField addqte;


    @FXML
    private TableView<vente> tabresult= new TableView<>();

    @FXML
    private TableColumn<vente, String> metho =new TableColumn<>("method");


    @FXML
    private TableColumn<vente, String> tabcat=new TableColumn<>("Category");




    @FXML
    private TableColumn<vente, Date> tabdate=new TableColumn<>("date");

    @FXML
    private TableColumn<vente, String> tabmed=new TableColumn<>("med");

    @FXML
    private TableColumn<vente, Float> tabprix=new TableColumn<>("prix");

    @FXML
    private TableColumn<vente, Integer> tabquan=new TableColumn<>("quantite");



    private ObservableList<vente> allItems = FXCollections.observableArrayList();




    @FXML
    private TableView<vente> listPurchases= new TableView<>();



    @FXML
    private TableColumn<vente, String> shcate=new TableColumn<>("Category");



    @FXML
    private TableColumn<vente, Date> shdate=new TableColumn<>("date");

    @FXML
    private TableColumn<vente, String> shmed=new TableColumn<>("med");

    @FXML
    private TableColumn<vente, Float> shprix=new TableColumn<>("prix");

    @FXML
    private TableColumn<vente, Integer> shqua=new TableColumn<>("quantite");


    float totprice=0;

    ArrayList<vente> ventee=new ArrayList<>();


    ObservableList<vente> ventes;

    void afficher(){
        ventes=listPurchases.getItems();
        ventes.clear();
        //refresh!!!

        try {
            String sql="SELECT vente.MethPayementV as x, produit.libellép as a, produit.Prixv as b, vente.Datev as c, catégorie.Libelléca as d, contenir.Quantpr as e " +
                    "FROM contenir" +
                    " JOIN produit ON produit.IDp = contenir.IDp" +
                    " JOIN vente ON vente.IDv = contenir.IDv" +
                    " JOIN catégorie ON produit.IDcat = catégorie.IDcat;";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprix(rs.getFloat("b"));

                w.setqua(rs.getInt("e"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total

                w.calculerprix();
                /*w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");*/
                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));



                //w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("d"));
                w.setmed(rs.getString("a"));


                //float total=w.gettotal();
                //totprice+=total;





                //w.setcateg(rs.getString("categ"));
                //w.setcode(rs.getString("code"));
                //w.setmed(rs.getString("med"));
                //int qua=rs.getInt("qua");
                //SimpleIntegerProperty quant = new SimpleIntegerProperty();
                //quant.set(qua);
                //w.setqua(quant);



                shcate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());

                ventes.add(w);

            }
            listPurchases.setItems(ventes);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("moshkil");
        }

        try {
            float all = 0;

            //jointure produit prix m3a contenir quantite
            String sqlQuery = "SELECT produit.Prixv AS x, contenir.Quantpr AS y " +
                    "FROM produit " +
                    "INNER JOIN contenir ON produit.IDp = contenir.IDp";
            PreparedStatement stmte = getConnection().prepareStatement(sqlQuery);


            ResultSet rse = stmte.executeQuery();

            while (rse.next()) {
                int quan = rse.getInt("y");
                float prixun = rse.getFloat("x");

                float un = quan * prixun;
                all += un;


            }
            System.out.println("all is " + all);
            total.setText(String.valueOf(all));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        try {
            float all = 0;
            String sql = "SELECT produit.Prixv AS x, contenir.Quantpr AS y" +
                    " FROM produit" +
                    " INNER JOIN contenir ON produit.IDp = contenir.IDp" +
                    " INNER JOIN vente ON contenir.IDv = vente.IDv" +
                    " WHERE YEAR(vente.Datev) = YEAR(CURDATE());";
            PreparedStatement stmt = getConnection().prepareStatement(sql);

            ResultSet rsa = stmt.executeQuery();


            while (rsa.next()) {
                int quan = rsa.getInt("y");
                float prixun = rsa.getFloat("x");

                float un = quan * prixun;
                all += un;

            }
            year.setText(String.valueOf(all));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            float all = 0;
            String sql = "SELECT produit.Prixv AS x, contenir.Quantpr AS y" +
                    " FROM produit" +
                    " INNER JOIN contenir ON produit.IDp = contenir.IDp" +
                    " INNER JOIN vente ON contenir.IDv = vente.IDv" +
                    " WHERE YEAR(vente.Datev) = YEAR(CURDATE()) AND MONTH(vente.Datev) = MONTH(CURDATE());";
            PreparedStatement stmt = getConnection().prepareStatement(sql);


            ResultSet rsb = stmt.executeQuery();


            while (rsb.next()) {
                int quan = rsb.getInt("y");
                float prixun = rsb.getFloat("x");

                float un = quan * prixun;
                all += un;

            }
            month.setText(String.valueOf(all));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            float all = 0;
            String sql = "SELECT produit.Prixv AS x, contenir.Quantpr AS y" +
                    " FROM produit" +
                    " INNER JOIN contenir ON produit.IDp = contenir.IDp" +
                    " INNER JOIN vente ON contenir.IDv = vente.IDv" +
                    " WHERE YEAR(vente.Datev) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH) " +
                    "AND MONTH(vente.Datev) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH);";
            PreparedStatement stmt = getConnection().prepareStatement(sql);


            ResultSet rsc = stmt.executeQuery();


            while (rsc.next()) {
                int quan = rsc.getInt("y");
                float prixun = rsc.getFloat("x");

                float un = quan * prixun;
                all += un;

            }
            lmonth.setText(String.valueOf(all));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void confirm(ActionEvent event) {

    }
    vente x =new vente();


    private void updateSearchResults(String searchText, TableView<vente> searchResultsTableView) {
        ObservableList<vente> searchResults = FXCollections.observableArrayList();
        for (vente item : ventes) {
            if (item.getmed().toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(item);
            }
        }
        searchResultsTableView.setItems(searchResults);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Online(ConnectionStat(), main, Connected);
        addmethod.getItems().addAll("Check", "Espece", "Virement");
        addmethod.setValue("Espece");
        addcat.getItems().addAll("X", "Y", "Z");
        addcat.setValue("X");


        searchprinc.textProperty().addListener((observe, old, neww) -> {
            updateSearchResults(neww, listPurchases);
        });




        // Retrieve the sum from the result set





        afficher();


        //String afficherpr=String.valueOf(totprice);

        String sql2 = "Select Libellép,IDp from produit";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql2);
            ResultSet rse = stmt.executeQuery();

            while (rse.next()) {
                searchlili.add(rse.getString("Libellép"));
                idp=rse.getInt("IDp");
                System.out.println("produit kayen dial search pour l ajout");
            }


        } catch (SQLException a) {
            System.out.println(a.getMessage());
            System.out.println("produit error");
        }

        searchli.setItems(searchlili);


        addmed.textProperty().addListener((observable, oldValue, newValue) -> {
            searchli.setItems(filterItems(newValue));
        });
/*
        vente selectedItem = listPurchases.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            x.setidv(selectedItem.getidv());
            System.out.println("Selected value: " + x.getidv());
        }

*/






    }
    private ObservableList<String> filterItems(String searchText) {
        ObservableList<String> filteredItems = FXCollections.observableArrayList();

        String med=addmed.getText();

        for (String item : searchlili) {
            if (item.toLowerCase().contains(searchText.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }







    @FXML
    void ondelete(ActionEvent event) {
        int todel=x.getidv();

        String sql = "DELETE FROM vente WHERE IDv = "+todel;

        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.executeUpdate(sql);
            System.out.println("deleted "+x.getidv());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        listPurchases.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int x=newValue.getidv();
                System.out.println("Selected value: " + x);
            }
        });


        afficher();






    }





    @FXML
    public void onadd() throws IOException {
        super.NouveauFenetre("Ajouter-vente");
    }

    @FXML
    void month(ActionEvent event) {

        String sql="SELECT * " +
                "FROM vente " +
                "WHERE MONTH(Datev) = MONTH(CURRENT_DATE()) AND YEAR(Datev) = YEAR(CURRENT_DATE());";


        ventes=listPurchases.getItems();
        ventes.clear();
        //refresh!!!

        try {
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprix(rs.getFloat("Prixv"));

                w.setqua(rs.getInt("quantite"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total

                w.calculerprix();
                w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");
                w.setdate(dt);
                w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("MethPayementV"));



                w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("categ"));
                w.setmed(rs.getString("namemed"));


                float total=w.gettotal();
                totprice+=total;





                //w.setcateg(rs.getString("categ"));
                //w.setcode(rs.getString("code"));
                //w.setmed(rs.getString("med"));
                //int qua=rs.getInt("qua");
                //SimpleIntegerProperty quant = new SimpleIntegerProperty();
                //quant.set(qua);
                //w.setqua(quant);



                shcate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());



                ventes.add(w);
                listPurchases.setItems(ventes);






            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void jour(ActionEvent event) {
        String sql="SELECT * FROM vente WHERE DATE(Datev) = CURDATE()";
        ventes=listPurchases.getItems();
        ventes.clear();
        //refresh!!!

        try {
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprix(rs.getFloat("Prixv"));

                w.setqua(rs.getInt("quantite"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total

                w.calculerprix();
                w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");
                w.setdate(dt);
                w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("MethPayementV"));



                w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("categ"));
                w.setmed(rs.getString("namemed"));


                float total=w.gettotal();
                totprice+=total;








                shcate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());



                ventes.add(w);
                listPurchases.setItems(ventes);






            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void week(ActionEvent event) {
        String sql="SELECT * FROM vente WHERE WEEK(Datev) = WEEK(CURDATE());";
        ventes=listPurchases.getItems();
        ventes.clear();
        //refresh!!!

        try {
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprix(rs.getFloat("Prixv"));

                w.setqua(rs.getInt("quantite"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total

                w.calculerprix();
                w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");
                w.setdate(dt);
                w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("MethPayementV"));



                w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("categ"));
                w.setmed(rs.getString("namemed"));


                float total=w.gettotal();
                totprice+=total;





                //w.setcateg(rs.getString("categ"));
                //w.setcode(rs.getString("code"));
                //w.setmed(rs.getString("med"));
                //int qua=rs.getInt("qua");
                //SimpleIntegerProperty quant = new SimpleIntegerProperty();
                //quant.set(qua);
                //w.setqua(quant);



                shcate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());



                ventes.add(w);
                listPurchases.setItems(ventes);






            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    int li=0;
    int idp=0;

    @FXML
    public void addpurchases(ActionEvent event) throws IOException {


        //hta iconfirme
        String sql="INSERT INTO `vente`(`Prixv`, `Datev`,`IDu`,`IDca`,`IDc`,`MethPayementV`) VALUES (?,?,?,?,?,?)";


        for (int j=0;j<li;j++){
            int quanti = 0;
            try{





                PreparedStatement statement =getConnection().prepareStatement(sql);
                statement.setFloat(1,ventee.get(j).getprix());
                statement.setDate(2,ventee.get(j).getdate());

                // pour l essaie
                //LoginController LOGIN = new LoginController();
                statement.setInt(3,LoginController.id);
                System.out.println(LoginController.id);
                statement.setInt(4,1);
                statement.setInt(5,1);


                statement.setString(6,ventee.get(j).getMethod());

                //tweli jointure m3a contenir bach njibo namemed




                try{



                    String x="SELECT Qte " +
                            "FROM produit " +
                            "WHERE Libellép = '"+ventee.get(j).getmed()+"';";
                    PreparedStatement statementa =getConnection().prepareStatement(x);
                    ResultSet rs =statementa.executeQuery();
                    rs.next();
                    quanti=rs.getInt("Qte");

                    if(quanti>0){
                        quanti -= ventee.get(j).getqua();
                    }else{
                        System.out.println("quantite est pas suffisant");
                    }






                    statementa.executeQuery();




                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("no quantite");
                    System.out.println(e.getMessage());


                }
                try{



                    String x="UPDATE produit" +
                            " SET Qte =  "+ quanti+
                            " WHERE IDp = '"+idp+ "';";
                    PreparedStatement statementa =getConnection().prepareStatement(x);


                    int Affected = statementa.executeUpdate();

                    if (Affected > 0) {
                        System.out.println("quantite est diminue");
                    } else {
                        System.out.println("diminution failed");
                    }




                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("change quantite failed");
                    System.out.println(e.getMessage());


                }




             //   statement.setString(7,ventee.get(j).getcateg());


                try{



                    String x="select IDp" +
                            " from produit "+
                            " WHERE Libellép = '"+ventee.get(j).getmed()+ "';";
                    PreparedStatement statementa =getConnection().prepareStatement(x);


                    ResultSet rs = statementa.executeQuery();

                    if(rs.next()){
                        System.out.println("we have this product to add to contenir table");
                        idp=rs.getInt("IDp");

                    }else{
                        System.out.println("we dont have this product to add to table contenir");

                    }


                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("getting id from product failled");
                    System.out.println(e.getMessage());


                }


                statement.executeUpdate();
                statement.close();



            } catch (SQLException e) {

                System.out.println("connection failed");
                System.out.println(e.getMessage());


            }



            int idv=0;

            try{



                //ye3ni akhir haja tzadet
                String x="SELECT IDv FROM vente ORDER BY idv DESC LIMIT 1;";
                PreparedStatement statementa =getConnection().prepareStatement(x);


                ResultSet rs = statementa.executeQuery();


                if(rs.next()){
                    System.out.println("id of vente has been selected");
                    idv=rs.getInt("IDv");
                    System.out.println(idv+"oooooooo");

                }else{
                    System.out.println("err in selecting the last id in table vente");

                }



                statementa.close();


            } catch (SQLException e) {

                System.out.println("getting id from vente failled");
                System.out.println(e.getMessage());


            }


            try{



                String x="insert into contenir (IDp,IDv,Quantpr) values ('"+idp+"','"+idv+"','"+quint+"');";

                PreparedStatement statementa =getConnection().prepareStatement(x);


                int Affected = statementa.executeUpdate();

                if (Affected > 0) {
                    System.out.println("contenir fait");
                } else {
                    System.out.println("contenir failed");
                }




                statementa.close();


            } catch (SQLException e) {

                System.out.println("add to contenir failed");
                System.out.println(e.getMessage());


            }






        }

        li=0;


        afficher();

        trye.clear();
        ventes.clear();


    }

    ObservableList<vente> trye;

    float price=0;
    int quint;



    @FXML
    void trynewone(ActionEvent event) {
        //matzadetch l base de donne hta tconfirma

        float prixf=0;



        String qua=addqte.getText();


        quint=Integer.parseInt(qua);

        String categ=addcat.getValue();

        String selectedItem = searchli.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Do something with the selected item, e.g., print it
            System.out.println("Selected Item: " + selectedItem);
        }
        String med=selectedItem;



        try{



            String x="select Prixv" +
                    " from produit where Libellép= '"+med+"'";
            PreparedStatement statementa =getConnection().prepareStatement(x);


            ResultSet rs = statementa.executeQuery();


            if(rs.next()){
                System.out.println("we have the price");
                prixf=rs.getFloat("Prixv");

            }else{
                System.out.println("the price is not in the table");

            }

            statementa.close();


        } catch (SQLException e) {

            System.out.println("getting price from produit failed");
            System.out.println(e.getMessage());


        }


        LocalDate dat=adddate.getValue();
        Date dt=Date.valueOf(dat);

        String methd=addmethod.getValue();


        System.out.println(addcl.getText());

        vente venta =new vente();
        venta.setmed(med);
        venta.setdate(dt);
        venta.setprix(prixf);
        venta.setcateg(categ);
        venta.setqua(quint);
        venta.setmethod(methd);

        //tehseb quantite x prix unitaire
        venta.calculerprix();
        tabcat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
        tabdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
        tabmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
        metho.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethod()));
        tabprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
        tabquan.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());




        trye=tabresult.getItems();

        trye.add(venta);
        tabresult.setItems(trye);

        price+=venta.gettotal();
        totalaj.setText(Float.toString(price));

        ventee.add(venta);

        li++;






    }


}