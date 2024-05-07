package example.controller;

import example.Services.Produit;
import example.Services.Utilisateur;
import example.model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;


public class Dashboard_controller extends Controller implements Initializable {
    public DatabaseManager DataConnexion = new DatabaseManager();
    @FXML
    private AnchorPane Connected;
    @FXML
    private Text main;
    float Totalvente;
    int Totalproduits;
    int Totalclient;
    int Produitperim;
    @FXML
    private Text income;
    @FXML
    private Text prod;
    @FXML
    private Text client;
    @FXML
    private Text perim;
    @FXML
    private LineChart<?, ?> Capital;
    @FXML
    private BarChart<?, ?> barChart;



    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            DataConnexion.setConnectionStat(true);
            Online(ConnectionStat(), main, Connected);
        } catch (Exception e1) {
            DataConnexion.setConnectionStat(false);
            e1.printStackTrace();
        }

        String sqlSelect = "SELECT v.Prixv FROM `vente` v WHERE Datev = CURDATE();";
        PreparedStatement stat = null;

        try {
            stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                Totalvente += result.getFloat("Prixv");
            }
            income.setText(String.valueOf(Totalvente));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sqlSelect = "SELECT COUNT(*) FROM produit";

        try {
            stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                Totalproduits = result.getInt(1);
            }
            prod.setText(String.valueOf(Totalproduits));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sqlSelect = "SELECT COUNT(*) FROM client";

        try {
            stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                Totalclient = result.getInt(1);
            }
            client.setText(String.valueOf(Totalclient));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sqlSelect = "SELECT COUNT(*) FROM `produit` WHERE Datepp < CURRENT_DATE;";

        try {
            stat = getConnection().prepareStatement(sqlSelect);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                Produitperim = result.getInt(1);
            }
            perim.setText(String.valueOf(Produitperim));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        XYChart.Series series = new XYChart.Series();

        //Date :
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 10, j = 9; i >= 1 && j >= 0; i--, j--) {

            LocalDate date = currentDate.minusMonths(i);
            LocalDate date1 = currentDate.minusMonths(j);
            String formattedCurrentDate = date1.format(formatter);
            String formattedPrevieusDate = date.format(formatter);

            System.out.println(formattedCurrentDate + " la walo " + formattedPrevieusDate);
            sqlSelect = "SELECT COUNT(*) FROM `vente` v WHERE v.Datev <= '" + formattedCurrentDate + "' AND v.Datev >= '" + formattedPrevieusDate + "';";
            System.out.println(sqlSelect);
            try {
                stat = getConnection().prepareStatement(sqlSelect);
                ResultSet result = stat.executeQuery();

                while (result.next()) {
                    System.out.println(result.getInt(1));
                    series.getData().add(new XYChart.Data<>(formattedCurrentDate, result.getInt(1)));
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        Capital.getData().addAll(series);


        XYChart.Series series2 = new XYChart.Series();

            sqlSelect = "SELECT IDp, COUNT(IDp) AS count\n" +
                    "FROM contenir\n" +
                    "GROUP BY IDp\n" +
                    "ORDER BY count DESC\n" +
                    "LIMIT 5;";

            String sqlSelect2 ;
            System.out.println(sqlSelect);

            try {
                stat = getConnection().prepareStatement(sqlSelect);
                ResultSet result = stat.executeQuery();

                while (result.next()) {
                    sqlSelect2 = "SELECT Libellép FROM produit WHERE IDp = "+result.getInt("IDp")+";";
                    stat = getConnection().prepareStatement(sqlSelect2);
                    ResultSet result2 = stat.executeQuery();

                    while (result2.next()){
                        System.out.print(result2.getString("Libellép"));
                        System.out.println( "l ID du produits est : "+result.getInt("IDp")+" count "+ result.getInt("count"));
                        series2.getData().add(new XYChart.Data<>(result2.getString("Libellép"),  result.getInt("count")));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }


        barChart.getData().addAll(series2);
    }}