package example.controller;
import example.Services.Produit;
import example.Services.vente;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

//


    @FXML
    private ChoiceBox<Integer> modcaiss=new ChoiceBox<>();



    @FXML
    private ChoiceBox<String> modcl=new ChoiceBox<>();

    @FXML
    private DatePicker moddate;

    @FXML
    private TextField modmed = new TextField();

    @FXML
    private ChoiceBox<String> modmetho=new ChoiceBox<>();

    @FXML
    private TextField modqte = new TextField();


//
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


    @FXML
    private TableColumn<vente, Void> action = new TableColumn<>("Menu");



    float totprice=0;

    ArrayList<vente> ventee=new ArrayList<>();


    ObservableList<vente> ventes;

    float totprix=0;
    static int gidv=0;



    public void afficher(){
        ventes=listPurchases.getItems();
        ventes.clear();

        try {
            String sql="select IDv as w, Datev as c, Prixv as b, qtt as e, MethPayementV as x from vente ";
            PreparedStatement stmt =getConnection().prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){



                vente w=new vente();

                //for showing the table
                w.setprixtot(rs.getFloat("b"));

                w.setqua(rs.getInt("e"));

                //pour calculer

                //il faut calculer total par multiplication de prix uni par quantite apres get the total



                //totprix = w.gettotal()+totprix;


                /*w.setidcl(rs.getInt("IDc"));
                w.setidca(rs.getInt("IDca"));

                Date dt=rs.getDate("Datev");*/
                w.setdate(rs.getDate("c"));

                //w.setidu(rs.getInt("IDu"));
                w.setmethod(rs.getString("x"));



                //w.setidv(rs.getInt("IDv"));

                w.setidv(rs.getInt("w"));

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


                action.setCellFactory(col -> new TableCell<vente, Void>() {
                            private final SplitMenuButton menuButton = new SplitMenuButton();

                            {
                                // Define the main action
                                menuButton.setText("Action");
                                menuButton.setOnAction(event -> {
                                    vente rowData = getTableView().getItems().get(getIndex());
                                    System.out.println("Main action clicked for id: " + rowData.getidv());
                                });

                                // Add additional menu items
                                MenuItem option1 = new MenuItem("Modifier");
                                option1.setOnAction(event -> {
                                    vente rowData = getTableView().getItems().get(getIndex());
                                    System.out.println("Option 1 selected for id: " + rowData.getidv());
                                    gidv=rowData.getidv();
                                    System.out.println(gidv);

                                    try {
                                        modifiervent();
                                    } catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                });

                                MenuItem option2 = new MenuItem("Supprimer");
                                option2.setOnAction(event -> {
                                    vente rowData = getTableView().getItems().get(getIndex());
                                    System.out.println("Option 2 selected for id: " + rowData.getidv());
                                    deletev(rowData.getidv());
                                });

                                // Adding options to the SplitMenuButton
                                menuButton.getItems().addAll(option1, option2);
                            }
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getIndex() >= getTableView().getItems().size()) {
                            setGraphic(null);
                        } else {
                            setGraphic(menuButton);
                        }
                    }
                        });



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
    //vente x =new vente();


    /* private void updateSearchResults(String searchText, TableView<vente> searchResultsTableView) {
        ObservableList<vente> searchResults = FXCollections.observableArrayList();
        for (vente item : ventes) {
            if (item.getmed().toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(item);
            }
        }
        searchResultsTableView.setItems(searchResults);
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Online(ConnectionStat(), main, Connected);
        addmethod.getItems().addAll("Check", "Espece", "Virement");
        addmethod.setValue("Espece");

        modmetho.getItems().addAll("Check", "Espece", "Virement");



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

            addcl.setValue(frist);




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
        }



        /*searchprinc.textProperty().addListener((observe, old, neww) -> {
            updateSearchResults(neww, listPurchases);
        });*/




        // Retrieve the sum from the result set



        if (addmed != null) {
            List<String> Suggestions = new ArrayList<>(List.of());
            try {
                String sqlSelect = "SELECT p.Libellép FROM produit p;";
                PreparedStatement stat = getConnection().prepareStatement(sqlSelect);
                ResultSet result = stat.executeQuery();

                while (result.next()) {
                    String libeller = result.getString("Libellép");

                    Suggestions.add(libeller);

                    System.out.println(libeller);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            TextFields.bindAutoCompletion(addmed, Suggestions);

        }



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


        /*addmed.textProperty().addListener((observable, oldValue, newValue) -> {
            searchli.setItems(filterItems(newValue));
        });*/
/*
        vente selectedItem = listPurchases.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            x.setidv(selectedItem.getidv());
            System.out.println("Selected value: " + x.getidv());
        }

*/

        try {
            String sqlsel = "Select Nomc from client";
            PreparedStatement stmt = getConnection().prepareStatement(sqlsel);
            ResultSet rse = stmt.executeQuery();

            while (rse.next()) {


                modcl.getItems().add(rse.getString("Nomc"));
            }


        } catch (SQLException a) {
            System.out.println(a.getMessage()+"err select from client");

        }






        try {
            String requete="select IDca from inventaire";
            PreparedStatement stmt=getConnection().prepareStatement(requete);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){


                modcaiss.getItems().add(rs.getInt("IDca"));

            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    public void modpurchase(ActionEvent event) throws IOException {

        int cl=0;
        System.out.println("modpurchase est marche "+gidv);
        if (modcaiss.getValue() != null) {
            int cais = modcaiss.getValue();
            PreparedStatement stmt = null;
            try {
                String sql = "UPDATE vente SET IDca = ? WHERE IDv = ?";
                stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, cais); // Set the IDca to the value of cais
                stmt.setInt(2, gidv);// Set the IDv to the value of gidv

                System.out.println(gidv);
                int rs = stmt.executeUpdate();
                System.out.println("Done client caiss:  rs " + rs+ " global   "+gidv +"  caiisss "+cais);

            } catch (SQLException e) {
                System.out.println(e.getMessage() + " - Update caiss vente error");
            } finally {
                // Ensure the statement is closed after execution to free resources
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage() + " - Error closing statement");
                    }
                }
            }
        }



        if(modcaiss.getValue()!=null){
            int cais=modcaiss.getValue();
            try {
                String sql ="update vente" +
                        " set IDca = "+cais +
                        " where vente.IDv = "+gidv+";";

                PreparedStatement stmt = getConnection().prepareStatement(sql);
                int rs=stmt.executeUpdate();
                System.out.println("done client caiss"+rs);


                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage()+"update caiss vente err");
            }

        }

        if(moddate.getValue() !=null){
            LocalDate dat=moddate.getValue();
            Date daat=Date.valueOf(dat);
            try {
                String sql ="update vente" +
                        " set Datev = ? "+
                        " where vente.IDv = "+gidv+";";

                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setDate(1,java.sql.Date.valueOf(daat.toLocalDate()));
                int rs=stmt.executeUpdate();
                System.out.println("done date "+rs);


                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage()+"update date err");
            }
        }


        if(modmetho.getValue()!=null){
            String method=modmetho.getValue();

            try {
                String sql ="update vente" +
                        " set MethPayementV = '"+method +"'"+
                        " where vente.IDv = "+gidv+";";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                int rs=stmt.executeUpdate();
                System.out.println("done method "+rs);



                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage()+"update date err");
            }
        }




        if (modcl.getValue() !=null) {

            String clnt=modcl.getValue();

            try {
                String sql2 ="select IDc from client where Nomc = '"+clnt+"'";
                PreparedStatement stmt = getConnection().prepareStatement(sql2);
                ResultSet rse = stmt.executeQuery();
                if (rse.next()) {

                    cl=rse.getInt("IDc");
                }

            } catch (SQLException a) {
                System.out.println(a.getMessage()+"err select from client");

            }
            try {
                String sql = "update vente" +
                        " set IDu = " + cl +
                        " where vente.IDv =" + gidv + ";";


                PreparedStatement stmt = getConnection().prepareStatement(sql);
                int rs=stmt.executeUpdate();
                System.out.println("done client"+rs);


                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage()+" update contenir err");
            }
        }

        super.FermerFentere(event);

    }

    public void modifiervent() throws IOException {
        super.NouveauFenetre("Modifier-vente");

    }

    public void deletev(int idv) {



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


    }



    @FXML
    public void onadd() throws IOException {
        super.NouveauFenetre("Ajouter-vente");








    }

    @FXML
    public void month(ActionEvent event) {


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
    public void jour(ActionEvent event) {

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
    public void week(ActionEvent event) {

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
    Date dt;



    int li=0;
    int idp=0;
    float price=0;
    int idca=-1;
    int idcl=0;
    String methd;

    @FXML
    public void addpurchases(ActionEvent event) throws IOException {


        try {
            //hta iconfirme
            String sql = "INSERT INTO `vente`(`Prixv`, `Datev`,`IDu`,`IDca`,`IDc`,`MethPayementV`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setFloat(1, price);
            statement.setDate(2, dt);


            statement.setInt(3, LoginController.id);
            statement.setInt(4, idca);

            statement.setInt(5, idcl);


            statement.setString(6, methd);

            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {

            System.out.println("add vente failed");
            System.out.println(e.getMessage());


        }


        for (int j = 0; j < li; j++) {
            int quanti = 0;



                try {


                    String x = "SELECT Qte " +
                            "FROM produit " +
                            "WHERE Libellép = '" + ventee.get(j).getmed() + "';";
                    PreparedStatement statementa = getConnection().prepareStatement(x);
                    ResultSet rs = statementa.executeQuery();
                    rs.next();
                    quanti = rs.getInt("Qte");

                    if (quanti >= ventee.get(j).getqua()) {
                        quanti -= ventee.get(j).getqua();

                    } else {
                        System.out.println("quantite est pas suffisant");
                    }


                    statementa.executeQuery();


                    statementa.close();
                } catch (SQLException e) {

                    System.out.println("no quantite");
                    System.out.println(e.getMessage());


                }
                try {

                    String x = "UPDATE produit" +
                            " SET Qte =  " + quanti +
                            " WHERE IDp = '" + ventee.get(j).getidprod() + "';";
                    PreparedStatement statementa = getConnection().prepareStatement(x);


                    int Affected = statementa.executeUpdate();
                    System.out.println(quanti+"li katupdata "+idp+" idp");

                    if (Affected > 0) {
                        System.out.println("quantite est diminue sur bd");
                    } else {
                        System.out.println("diminution failed");
                    }


                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("change quantite failed");
                    System.out.println(e.getMessage());


                }


                int idv = 0;

                try {


                    //ye3ni akhir haja tzadet
                    String x = "SELECT IDv FROM vente ORDER BY idv DESC LIMIT 1;";
                    PreparedStatement statementa = getConnection().prepareStatement(x);


                    ResultSet rs = statementa.executeQuery();


                    if (rs.next()) {
                        System.out.println("id of vente has been selected");
                        idv = rs.getInt("IDv");

                    } else {
                        System.out.println("err in selecting the last id in table vente");

                    }


                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("getting id from vente failled");
                    System.out.println(e.getMessage());


                }


                try {


                    String x = "insert into contenir (IDp,IDv,Quantpr) values ('" + ventee.get(j).getidprod() + "','" + idv + "','" + ventee.get(j).getqua() + "');";

                    PreparedStatement statementa = getConnection().prepareStatement(x);


                    int Affected = statementa.executeUpdate();

                    if (Affected > 0) {
                        System.out.println("contenir fait +++++++"+li);
                    } else {
                        System.out.println("contenir failed");
                    }


                    statementa.close();


                } catch (SQLException e) {

                    System.out.println("add to contenir failed");
                    System.out.println(e.getMessage());

                }


            //afficher();

            trye.clear();
            ventes.clear();


        }
        li = 0;
        super.FermerFentere(event);

    }

    ObservableList<vente> trye;


    int quint;

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    String med;
    String categ;
    String cln;

    public boolean isset(){


        if(addqte.getText().isEmpty()||addmed.getText().isEmpty()||idcaisse.getValue()==null||addcat.getValue().isEmpty()||addmethod.getValue().isEmpty()||addcl.getValue().isEmpty()){
            showAlert("Ereur","replissage de toutes les champs est obligatoire");
            return false;

        }
        String qua=addqte.getText();
        quint=Integer.parseInt(qua);



        med=addmed.getText();

        idca=idcaisse.getValue();
        categ=addcat.getValue();



        methd=addmethod.getValue();

        cln=addcl.getValue();
        LocalDate dat = adddate.getValue();
        try {
            dat = adddate.getValue();
            if (dat != null) {
                dt = Date.valueOf(dat);
            } else {
                // Handle the case of empty input here
                System.out.println("Input is empty");
                showAlert("Ereur","date est vide");
                return false;
            }

        } catch (Exception e) {
            System.out.println("date error occurred: " + e.getMessage());
        }

        return true;
    }

    @FXML
    public void trynewone(ActionEvent event) {


        if(isset()){

        float prixf=0;
        int idp=0;
        int oldqua=0;
        vente venta = new vente();

        try {
            String requete="select IDp from produit where Libellép = '"+med+"';";
            PreparedStatement stmt=getConnection().prepareStatement(requete);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                idp=rs.getInt("IDp");


            stmt.close();
            }
            else{
                showAlert("Ereur","Produit introuvable");
                return;

            }
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






        if(oldqua>=quint){
            System.out.println("enough quantite");


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

            //car dans une vente il y a une seul method

            try {
                String requete="select IDc from client where Nomc = '"+cln+"';";
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



            venta.setmed(med);
            venta.setidprod(idp);
            venta.setprixtot(prixf*quint);
            venta.setcateg(categ);
            venta.setqua(quint);
            venta.setidprod(idp);

            //tehseb quantite x prix unitaire

            tabcat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcateg()));
            tabmed.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getmed()));
            metho.setCellValueFactory(cellData -> new SimpleStringProperty(methd));
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

    public void setupsearchtextfield(javafx.scene.input.KeyEvent event){
        FilteredList<vente> filteredListvente = new FilteredList<>(ventes, b -> true);

        if ( searchprinc!= null) {

            searchprinc.textProperty().addListener((observable, OldValue, NewValue) -> {
                filteredListvente.setPredicate(vente -> {
                    // Aucune valeur de recherche
                    if (NewValue == null || NewValue.isEmpty() || NewValue.isBlank()) {
                        return true;
                    }
                    String searchedvente = NewValue.toLowerCase();

                    // Vérifie si le texte recherché est présent dans chaque propriété du produit
                    if (vente.getMethod().toLowerCase().contains(searchedvente)) {
                        return true;
                    }

                    if (String.valueOf(vente.getqua()).contains(searchedvente)) {
                        return true;
                    }
                    if (String.valueOf(vente.getidv()).contains(searchedvente)) {
                        return true;
                    }
                    if (String.valueOf(vente.gettotal()).contains(searchedvente)) {
                        return true;
                    }
                    if (vente.getdate()!= null && vente.getdate().toString().toLowerCase().contains(searchedvente)) {
                        return true;
                    }
                    // Ajoutez d'autres conditions pour d'autres propriétés du produit si nécessaire

                    // Aucun résultat trouvé
                    return false;
                });

                // Création d'une SortedList à partir de la FilteredList
                SortedList<vente> sortedList = new SortedList<>(filteredListvente);

                // Lier le comparateur de la SortedList avec le comparateur du TableView Commandes (remplacez Commandes par le nom de votre TableView)
                sortedList.comparatorProperty().bind(listPurchases.comparatorProperty());

                // Définir les éléments du TableView avec la SortedList filtrée et triée
                listPurchases.setItems(sortedList);
            });
        }

    }


}