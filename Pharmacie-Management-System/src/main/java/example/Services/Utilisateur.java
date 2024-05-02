package example.Services;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.SplitMenuButton;

public class Utilisateur {

    private final StringProperty CIN;
    private final StringProperty Mpasse;
    private final SimpleIntegerProperty id;
    private final StringProperty Nom;
    private final StringProperty Prénom;
    private final StringProperty DN;
    private final StringProperty  Role;
    private final StringProperty  Email;
    private final StringProperty  Tel;
    private final StringProperty  Salaire;
    private SplitMenuButton Action;


    public Utilisateur() {
        id = new SimpleIntegerProperty(this,"IDu");
        CIN = new SimpleStringProperty(this, "CIN");
        Nom= new SimpleStringProperty(this, "Nom");
        Prénom = new SimpleStringProperty(this, "Prénom");
        DN = new SimpleStringProperty(this, "DN");
        Role = new SimpleStringProperty(this, "Role");
        Email = new SimpleStringProperty(this, "Email");
        Tel = new SimpleStringProperty(this, "Tel");
        Salaire = new SimpleStringProperty(this, "Salaire");
        Mpasse = new SimpleStringProperty(this, "Mpasse");
        Action = new SplitMenuButton();

    }



    public static void add(Utilisateur util) {
    }

    public StringProperty nomProperty() {
        return Nom;
    }

    public String getNom() {
        return Nom.get();
    }

    public void setNom(String newNom) {
        Nom.set(newNom);
    }

    public StringProperty prenomProperty() {
        return Prénom;
    }

    public String getPrenom() {
        return Prénom.get();
    }

    public void setPrenom(String newPrenom) {
        Prénom.set(newPrenom);
    }

    public StringProperty dateProperty() {
        return DN;
    }

    public String getDate() {
        return DN.get();
    }

    public void setDate(String newDate) {
        DN.set(newDate);
    }

    public StringProperty roleProperty() {
        return Role;
    }

    public String getRole() {
        return Role.get();
    }

    public void setRole(String newRole) {
        Role.set(newRole);
    }

    public StringProperty emailProperty() {
        return Email;
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String newEmail) {
        Email.set(newEmail);
    }

    public StringProperty telephoneProperty() {
        return Tel;
    }

    public String getTelephone() {
        return Tel.get();
    }

    public void setTelephone(String newTelephone) {
        Tel.set(newTelephone);
    }

    public StringProperty salaireProperty() {
        return Salaire;
    }

    public String getSalaire() {
        return Salaire.get();
    }

    public void setSalaire(String newSalaire) {
        Salaire.set(newSalaire);
    }


    public IntegerProperty idProperty() { return id; }
    public int getId() { return id.get(); }

    public void setId(int newId) {
        id.set(newId);
    }

    public SplitMenuButton getActionMenu() {
        return Action;
    }

    // Ajoutez cette méthode pour définir le menu d'actions

    public void setActionMenu(SplitMenuButton Action) {
        this.Action= Action;
    }

    public String getPass() {
        return Mpasse.get();
    }

    public String getCIN() {

        return  CIN.get();
    }
}



