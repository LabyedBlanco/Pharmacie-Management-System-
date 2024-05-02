package example.Services;

import javafx.beans.property.*;


public class Commande {

    public SimpleStringProperty IDcommande;

    public SimpleStringProperty Prixa;
    public SimpleStringProperty DateCommande;
    public SimpleStringProperty IdCaisse;

    public SimpleStringProperty IdFournisseur;
    public SimpleStringProperty IdUtilisateur;
    public SimpleStringProperty MethodePayement;
    public SimpleStringProperty Status;

    public Commande(SimpleStringProperty IDcommande, SimpleStringProperty prixa, SimpleStringProperty dateCommande, SimpleStringProperty idFournisseur, SimpleStringProperty idCaisse,SimpleStringProperty idUtilisateur, SimpleStringProperty methodePayement, SimpleStringProperty status) {
        this.IDcommande = IDcommande;
        Prixa = prixa;
        DateCommande = dateCommande;
        IdFournisseur = idFournisseur;
        IdCaisse = idCaisse;
        IdUtilisateur = idUtilisateur;
        MethodePayement = methodePayement;
        Status = status;
    }
    public void Afficher(){
        System.out.println("La commande de l'id : " +IDcommande+" du Prix : "+Prixa+" dans la date : " +DateCommande+ " avec le fournisseur : " + IdFournisseur + " depuis l'utilisateur : "+IdUtilisateur +" a de status : "+Status+"Methode Payement " + MethodePayement  );
    }
}
