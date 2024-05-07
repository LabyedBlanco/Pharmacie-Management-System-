package example.Services;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Produit {


    private  String libp;
    private  float prixv;
    private  int idp;
    private int Qte;
    private String Datep;
    private String codBr;
    private String categ;
    private int Iddep;
    public SimpleStringProperty libeller;
    public SimpleStringProperty PrixProduit;
    public SimpleStringProperty Quantite;
    public SimpleStringProperty Idp;

    public static int ord = 0;

    public Produit(SimpleStringProperty libeller, SimpleStringProperty prixProduit, SimpleStringProperty quantite, SimpleStringProperty idp) {
        this.libeller = libeller;
        PrixProduit = prixProduit;
        Quantite = quantite;
        Idp = idp;
        ord++;
    }

    public void Afficher(){
        System.out.println("libeller : " +libeller+ " Prix " +PrixProduit+ " Quantite : " +Quantite+ " idp : " +Idp);
    }

    public Produit(int idp,String libp,float prixv,int Qte,String Datep,String codBr,int Iddep){
        this.idp=idp;
        this.libp=libp;
        this.codBr=codBr;
        this.Datep=Datep;
        this.Qte=Qte;
        this.prixv=prixv;
        this.Iddep=Iddep;

    }



    public int getIdp(){return idp;}
    public void setIdp(int idp) {
        this.idp = idp;
    }

    public float getPrixv() {return prixv;}
    public void setPrixv(float prixv) {
        this.prixv = prixv;}

    public String getLibp(){return libp;}

    public void setLibp(String libp){
        this.libp=libp;
    }


    public int getQuantite() {
        return Qte;
    }

    public String getExpDate() {
        return Datep;
    }

    public String getCode() {
        return codBr;
    }


    public void setCategorie(String libelleca) {
        this.categ=libelleca;
    }

    public String getCateg() {
        return categ;
    }
    public int getIddep(){
        return Iddep;
    }
}