package example.Services;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.SplitMenuButton;

public class Utilisateur {

    private  String CIN;
    private  String Mpasse;
    private  int id;
    private  String Nom;
    private  String Prénom;
    private  String DN;
    private  String  Role;
    private  String  Email;
    private  String  Tel;
    private  float Salaire;



    public Utilisateur(int id,String Nom,String Prénom,String Email,String Tel,String Role,String DN,float Salaire) {
       this.id=id;
       this.Nom=Nom;
       this.Prénom=Prénom;
       this.Email=Email;
       this.Tel=Tel;
       this.Role=Role;
       this.DN=DN;
       this.Salaire=Salaire;
    }

    public Utilisateur(String CIN,String Mpasse){
        this.Mpasse=Mpasse;
        this.CIN=CIN;
    }

    public int getId() {
        return id;
    }
    public String getNom() {
        return Nom;
    }
    public String getEmail() {
        return Email;
    }
    public String getTel() {
        return Tel;
    }
    public String getRole() {
        return Role;
    }
    public String getPrenom() {
        return Prénom;
    }
    public String getDN() {
        return DN;
    }
    public float getSalaire(){return Salaire; }
    public String getCIN(){return CIN;}

    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String Nom) {
        this.Nom = Nom;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setTel(String Tel) {
        this.Tel= Tel;
    }
    public void setRole(String Role) {
        this.Role = Role;
    }
    public void setPrenom(String Prénom){this.Prénom=Prénom;}
    public void setDN(String DN) {this.DN = DN;}
    public void setSalaire(float Salaire) {this.Salaire = Salaire;}
    public void setCIN(String CIN) {this.CIN = CIN;}

    public void setMpasse(String mpasse) {
        Mpasse = mpasse;
    }
}



