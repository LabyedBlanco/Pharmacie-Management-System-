package example.Services;

import javafx.beans.property.SimpleStringProperty;

public class Produit {
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
}

