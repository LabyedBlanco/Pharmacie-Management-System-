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


    @FXML
    private Button delete= new Button();

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
    private ChoiceBox<String> addcl=new ChoiceBox<>();



    @FXML
    private ChoiceBox<Integer> idcaisse=new ChoiceBox<>();



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
    private TableColumn<vente, String> shmethod=new TableColumn<>("method");



    @FXML
    private TableColumn<vente, Date> shdate=new TableColumn<>("date");


    @FXML
    private TableColumn<vente, Integer> shid=new TableColumn<>("id");

    @FXML
    private TableColumn<vente, Float> shprix=new TableColumn<>("prix");

    @FXML
    private TableColumn<vente, Integer> shqua=new TableColumn<>("quantite");


    float totprice=0;

    ArrayList<vente> ventee=new ArrayList<>();


    ObservableList<vente> ventes;

    float totprix=0;

    void afficher(){
        ventes=listPurchases.getItems();
        ventes.clear();
        //refresh!!!

        try {
            String sql="SELECT vente.IDv as w, vente.MethPayementV as x, vente.Prixv as b, vente.Datev as c, catégorie.Libelléca as d, contenir.Quantpr as e " +
                    "FROM vente" +
                    " JOIN contenir ON vente.IDv = contenir.IDv" +
                    " JOIN produit ON produit.IDp = contenir.IDp" +
                    " JOIN catégorie ON produit.IDcat = catégorie.IDcat;";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){



                vente w=new vente();

                //for showing the table
                w.setprixtot(rs.getFloat("b"));

                w.setqua(rs.getInt("e"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total



                totprix = w.gettotal()+totprix;


                /*w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");*/
                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));



                //w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("d"));

                w.setidv(rs.getInt("w"));

                System.out.println(w.getidv()+"iiiiiiii");
                //float total=w.gettotal();
                //totprice+=total;





                //w.setcateg(rs.getString("categ"));
                //w.setcode(rs.getString("code"));
                //w.setmed(rs.getString("med"));
                //int qua=rs.getInt("qua");
                //SimpleIntegerProperty quant = new SimpleIntegerProperty();
                //quant.set(qua);
                //w.setqua(quant);



                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shid.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getidv()).asObject());
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());
                shmethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethod()));


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



        String frst="";

        try {
            String sql2 = "Select Libelléca from catégorie";
            PreparedStatement stmt = getConnection().prepareStatement(sql2);
            ResultSet rse = stmt.executeQuery();

            int i=0;
            while (rse.next()) {
                if(i==0){
                    frst=rse.getString("Libelléca");

                }

                addcat.getItems().add(rse.getString("Libelléca"));
               i++;
            }

            addcat.setValue(frst);




        } catch (SQLException a) {
            System.out.println(a.getMessage());

        }
        int x=0;
        int caiss=0;

        try {
            String sql2 = "Select Nomc from client";
            PreparedStatement stmt = getConnection().prepareStatement(sql2);
            ResultSet rse = stmt.executeQuery();

            int j=0;
            String frist="";
            while (rse.next()) {
                if(j==0){
                    frist=rse.getString("Nomc");

                }

                addcl.getItems().add(rse.getString("Nomc"));
                j++;
            }

            addcat.setValue(frist);




        } catch (SQLException a) {
            System.out.println(a.getMessage());

        }

        try {
            String requete="select IDca from inventaire";
            PreparedStatement stmt=getConnection().prepareStatement(requete);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                if(x==0){
                    caiss=rs.getInt("IDca");

                }
                x++;

                idcaisse.getItems().add(rs.getInt("IDca"));



            }
            idcaisse.setValue(caiss);



            stmt.close();
        } catch (SQLException e) {
            System.out.println("no id found of that client");
            System.out.println(e.getMessage());
            showAlert("Ereur","Le Client n est pas dans la BD");
        }



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


        delete.setOnAction(this::handleButtonClick);






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

    private void handleButtonClick(ActionEvent event) {
        vente selectedVente = listPurchases.getSelectionModel().getSelectedItem();
        if (selectedVente != null) {
            int idv = selectedVente.getidv();
            System.out.println("Selected vente id: " + idv);


            int idcon = 0;

            try {
                String sql ="select contenir.IDcont  from contenir" +
                        " join vente on vente.IDv  = contenir.IDv " +
                        " where vente.IDv ="+idv+";";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    idcon = rs.getInt("IDcont");
                    System.out.println("IDcont: " + idcon);
                } else {
                    System.out.println("IDcont not found for vente ID: " + idv);
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error retrieving IDcont: " + e.getMessage());
            }

            // Deleting from contenir table
            try {
                String sql = "DELETE FROM contenir WHERE IDcont = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, idcon);
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) deleted from contenir.");
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error deleting from contenir: " + e.getMessage());
            }

            // Deleting from vente table
            try {
                String sql = "DELETE FROM vente WHERE IDv = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, idv);
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) deleted from vente.");
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error deleting from vente: " + e.getMessage());
            }

            afficher();

        } else {
            System.out.println("No vente selected.");
        }
    }



    @FXML
    public void onadd() throws IOException {
        super.NouveauFenetre("Ajouter-vente");
    }

    @FXML
    void month(ActionEvent event) {


        ventes.clear();
        try {

            String sql="SELECT vente.IDv as w, vente.MethPayementV as x, vente.Prixv as b, vente.Datev as c, catégorie.Libelléca as d, contenir.Quantpr as e " +
                    "FROM vente" +
                    " JOIN contenir ON vente.IDv = contenir.IDv" +
                    " JOIN produit ON produit.IDp = contenir.IDp" +
                    " JOIN catégorie ON produit.IDcat = catégorie.IDcat"+
                    " WHERE MONTH(vente.Datev) = MONTH(CURRENT_DATE()) AND YEAR(vente.Datev) = YEAR(CURRENT_DATE());";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprixtot(rs.getFloat("b"));

                w.setqua(rs.getInt("e"));
                w.setidv(rs.getInt("w"));


                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total


                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));

                w.setcateg(rs.getString("d"));



                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shid.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getidv()).asObject());
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());
                shmethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethod()));

                ventes.add(w);

            }
            listPurchases.setItems(ventes);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("err at month show");
        }
    }

    @FXML
    void jour(ActionEvent event) {

        ventes.clear();
        try {

            String sql="SELECT vente.IDv as w, vente.MethPayementV as x, vente.Prixv as b, vente.Datev as c, catégorie.Libelléca as d, contenir.Quantpr as e " +
                    "FROM vente" +
                    " JOIN contenir ON vente.IDv = contenir.IDv" +
                    " JOIN produit ON produit.IDp = contenir.IDp" +
                    " JOIN catégorie ON produit.IDcat = catégorie.IDcat"+
                    " WHERE DATE(vente.Datev) = CURDATE()";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprixtot(rs.getFloat("b"));

                w.setqua(rs.getInt("e"));
                w.setidv(rs.getInt("w"));



                //il faut calculer total par multiplication de prix uni par quantite apres get the total


                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));



                //w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("d"));


                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shid.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getidv()).asObject());
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());
                shmethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethod()));

                ventes.add(w);

            }
            listPurchases.setItems(ventes);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("err at month show");
        }


    }
    @FXML
    void week(ActionEvent event) {

        ventes.clear();
        //refresh!!!

        try {

            String sql="SELECT vente.IDv as w, vente.MethPayementV as x, vente.Prixv as b, vente.Datev as c, catégorie.Libelléca as d, contenir.Quantpr as e " +
                    "FROM vente" +
                    " JOIN contenir ON vente.IDv = contenir.IDv" +
                    " JOIN produit ON produit.IDp = contenir.IDp" +
                    " JOIN catégorie ON produit.IDcat = catégorie.IDcat"+
                    " WHERE WEEK(vente.Datev) = WEEK(CURDATE());";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                vente w=new vente();
                //for showing the table
                w.setprixtot(rs.getFloat("b"));
                w.setidv(rs.getInt("w"));


                w.setqua(rs.getInt("e"));


                //il faut calculer total par multiplication de prix uni par quantite apres get the total


                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));



                //w.setidv(rs.getInt("IDv"));
                w.setcateg(rs.getString("d"));


                shdate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getdate()));
                shid.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getidv()).asObject());
                shprix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().gettotal()).asObject());
                shqua.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getqua()).asObject());
                shmethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethod()));

                ventes.add(w);

            }
            listPurchases.setItems(ventes);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("err at month show");
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
                statement.setFloat(1,price);
                statement.setDate(2,ventee.get(j).getdate());

                // pour l essaie
                //LoginController LOGIN = new LoginController();
                statement.setInt(3,ventee.getFirst().getidu());
                statement.setInt(4,ventee.get(j).getidca());

                statement.setInt(5,ventee.get(j).getidcl());


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

                    if(quanti>quint){
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

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    void trynewone(ActionEvent event) {
        //matzadetch l base de donne hta tconfirma

        float prixf=0;
        int idp=0;
        int oldqua=0;
        vente venta =new vente();

        String qua=addqte.getText();
        quint=Integer.parseInt(qua);





        String selectedItem = searchli.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            System.out.println("Selected Item: " + selectedItem);
        }
        String med=selectedItem;




        venta.setidca(idcaisse.getValue());

        System.out.println(venta.getidca()+"dddfdfdfdfdfd");



        try {
            String requete="select IDp from produit where Libellép = '"+med+"';";
            PreparedStatement stmt=getConnection().prepareStatement(requete);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                idp=rs.getInt("IDp");

            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("no id found of that product");
            System.out.println(e.getMessage());
        }


        try {
            String ss="select Qte from produit where IDp = "+idp+";";
            PreparedStatement stm=getConnection().prepareStatement(ss);

            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                oldqua=rs.getInt("Qte");

            }
            stm.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        if(oldqua>quint){
            System.out.println("enough quantite");
            String categ=addcat.getValue();


            try{



                String x="select Prixv" +
                        " from produit where Libellép = '"+med+"';";
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


            System.out.println(addcl.getValue()+"aaaa");

            int idcl=0;
            try {
                String requete="select IDc from client where Nomc = '"+addcl.getValue()+"';";
                PreparedStatement stmt=getConnection().prepareStatement(requete);
                ResultSet rs=stmt.executeQuery();
                if(rs.next()){
                    idcl=rs.getInt("IDc");

                }
                stmt.close();
            } catch (SQLException e) {
                System.out.println("no id found of that client");
                System.out.println(e.getMessage());
            }


            venta.setidcl(idcl);

            venta.setidca(idcaisse.getValue());
            venta.setmed(med);
            venta.setdate(dt);
            venta.setprixtot(prixf*quint);
            venta.setcateg(categ);
            venta.setqua(quint);
            venta.setmethod(methd);
            venta.setidprod(idp);
            venta.setidu(LoginController.id);


            //tehseb quantite x prix unitaire
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
        else{
            System.out.println("not enough quantite");

            showAlert("Ereur","quantite insuffisant");

        }


    }


}