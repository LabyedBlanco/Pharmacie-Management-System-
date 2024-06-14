package example.Services;

import javafx.beans.property.*;


public class Avoir {

    public SimpleStringProperty Fournissuer;
    public SimpleStringProperty Date;
    public SimpleStringProperty Utilisateur;
    public SimpleStringProperty Status;
    public SimpleStringProperty Prix;
    public SimpleStringProperty CommandeID;

    public Avoir(SimpleStringProperty fournissuer, SimpleStringProperty date, SimpleStringProperty utilisateur, SimpleStringProperty status, SimpleStringProperty prix, SimpleStringProperty commandeID) {
        Fournissuer = fournissuer;
        Date = date;
        Utilisateur = utilisateur;
        Status = status;
        Prix = prix;
        CommandeID = commandeID;
    }
}
