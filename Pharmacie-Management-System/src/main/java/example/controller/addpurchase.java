package example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


class purchaseproduct{
    private String client;
    private LocalDate dt;
    private int Amount;
    private String product;

    purchaseproduct(String client, LocalDate dt, int amount, String product){

        this.client=client;
        this.dt=dt;
        this.Amount=amount;

        this.product=product;
    }

    public String getproduct(){
        return this.product;
    }


    public int getamount(){
        return this.Amount;
    }

    public LocalDate getdate(){
        return this.dt;
    }

    public String getclient(){
        return this.client;
    }
}

public class addpurchase {



    @FXML
    private DatePicker date;



    @FXML
    private TextField clientname;

    @FXML
    private TextField productname;

    @FXML
    private TextField amount;






    @FXML
    protected void addpurchases(ActionEvent event) {
        String amounttxt=amount.getText();
        int amountint = Integer.parseInt(amounttxt);
        purchaseproduct x=new purchaseproduct(clientname.getText(),date.getValue(),amountint,productname.getText());


        //VentesController.listPurchases.getItems().add();



        System.out.println(x.getclient());
        System.out.println(x.getdate());
        System.out.println(x.getamount());
        System.out.println(x.getproduct());







    }

}
