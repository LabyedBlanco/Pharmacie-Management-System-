package example.controller;

import example.model.DatabaseManager;
import example.model.Utilisateur;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtilisateurController extends Controller implements Initializable {
    @FXML
    public AnchorPane Connected ;

    @FXML
    private Button bottommost,bottonadd;
    @FXML
    private ImageView image;
    @FXML
    private TextField  nom,prenom,tel,Email,salary,cin;
    @FXML
    private PasswordField passwd;
    @FXML
    private ComboBox<String> Role;
    @FXML
    private SplitMenuButton Action;


    @FXML
    private DatePicker birthday;
    @FXML private TableView<Utilisateur> table;

    @FXML   private TableColumn<Utilisateur,String>NOM;
    @FXML   private TableColumn<Utilisateur,String>PRENOM;
    @FXML   private TableColumn<Utilisateur,String>TELE;
    @FXML   private TableColumn<Utilisateur,String>EMAIL;
    @FXML   private TableColumn<Utilisateur,String>SALAIRE;
    @FXML   private TableColumn<Utilisateur,String>ROLE;
    @FXML   private TableColumn<Utilisateur,String>BIRTH;
    @FXML   private TableColumn<Utilisateur,SplitMenuButton>ACTION;
    @FXML private TableColumn<Utilisateur,Integer> ID;






    int c = 0;
    public Pane imagePane;
    @FXML
    private Text main;



    @Override
    public void initialize(URL url , ResourceBundle resourceBundle){


        if (Role!= null) {
            Role.setItems(FXCollections.observableArrayList("Gérant", "Pharmacien", "Admin"));
        }





        Online(ConnectionStat(),main,Connected);
        try {
            DatabaseManager Data = new DatabaseManager();
            boolean isConnected = Data.ConnectionStat();
            if (isConnected) {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: green; -fx-background-radius: 100px");

            } else {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
                main.setText("Offline");
            }
        } catch (Exception e) {
            System.err.println("Error initializing connection: " + e.getMessage());
        }
        new Thread(this::table).start();
    }


    public void start(Stage primaryStage) {
        primaryStage.setOnShown(event -> {
            table();
        });

    }



    public void switchToHello(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        NouveauFenetre("Profile_utilisateur");
        bottommost.setVisible(false);


    }

    public void switchToHello() throws IOException {

        Stage stage = new Stage();
        stage.setResizable(false);
        NouveauFenetre("Profile_utilisateur");
        bottonadd.setVisible(false);


    }




    public void onaddimageUser(ActionEvent event){
        File file = ParcourirFichier(event);
        if (file != null) {
            String cheminPhoto = file.getAbsolutePath();
            System.out.println("Chemin de la photo sélectionnée : " + cheminPhoto);

            Image imageview = new Image(file.toURI().toString());
            image.setImage(imageview);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }

    }

    @FXML
    public void addu() throws IOException {



        String name = nom.getText();
        String pren = prenom.getText();
        String email = Email.getText();
        String phone = tel.getText();
        String CIN= cin.getText();
        String role = Role.getValue();
        String mpass=passwd.getText();
        String salaire=salary.getText();
        String Date = birthday.getEditor().getText();
        Image imageView = this.image.getImage();




        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            conn = dbManager.getConnection();

            String sql = "INSERT INTO utilisateur (Nom,Prénom,DN, Tel,Role,Mpasse,Email,Salaire,CIN,IMAGE) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, pren);
            statement.setString(3, Date);
            statement.setString(4, phone);
            statement.setString(5, role);
            statement.setString(6, mpass);
            statement.setString(7, email);
            statement.setString(8, salaire);
            statement.setString(9, CIN);

            InputStream inputStream = convertImageToInputStream(imageView);
            statement.setBlob(10, inputStream);

            statement.executeUpdate();




            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été insérées avec succès !");
            alert.showAndWait();
            ActionEvent evenement = null;
            FermerFentere(evenement);
            




        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de données : " + e.getMessage());
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("probleme d'insertion !");
            alert.showAndWait();
        }



        finally {
            if (conn != null) {
                try {
                    dbManager.closeConnection();
                } catch (SQLException sqle) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + sqle.getMessage());
                }
            }
        }

    }


    private InputStream convertImageToInputStream(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }


public void table(){
    ObservableList<Utilisateur> dta = FXCollections.observableArrayList();
    Connection conn = null;
    try{
        DatabaseManager dbManager = new DatabaseManager();
        conn = dbManager.getConnection();
        String sqlSelect="SELECT IDu,Nom,Prénom,DN,Role,Tel,Email,Salaire FROM utilisateur";

        PreparedStatement stat = conn.prepareStatement(sqlSelect);
        ResultSet result = stat.executeQuery();


        while (result.next()){
         Utilisateur util=new Utilisateur();

         util.setId(result.getInt("IDu"));
         util.setNom(result.getString("Nom"));
         util.setPrenom(result.getString("Prénom"));
         util.setDate(result.getString("DN"));
         util.setRole(result.getString("Role"));
         util.setTelephone(result.getString("Tel"));
         util.setEmail(result.getString("Email"));
         util.setSalaire(result.getString("Salaire"));
            SplitMenuButton actionMenu = new SplitMenuButton();
            MenuItem modifierItem = new MenuItem("Modifier");
           modifierItem.setOnAction(event -> {
               try {
                   switchToHello();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }


           });
            MenuItem supprimerItem = new MenuItem("Supprimer");
            supprimerItem.setOnAction(event -> {
                delete();
            });

            actionMenu.getItems().addAll(modifierItem, supprimerItem);
            util.setActionMenu(actionMenu);

            dta.add(util);


        }
        ID.setCellValueFactory(f -> f.getValue().idProperty().asObject());
        NOM.setCellValueFactory(f -> f.getValue().nomProperty());
        PRENOM.setCellValueFactory(f -> f.getValue().prenomProperty());
        BIRTH.setCellValueFactory(f -> f.getValue().dateProperty());
        ROLE.setCellValueFactory(f -> f.getValue().roleProperty());
        EMAIL.setCellValueFactory(f -> f.getValue().emailProperty());
        TELE.setCellValueFactory(f -> f.getValue().telephoneProperty());
        SALAIRE.setCellValueFactory(f -> f.getValue().salaireProperty());
        ACTION.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().getActionMenu()));


        table.setItems(dta);





    }

    catch (SQLException e) {
        e.printStackTrace();
    }

}



   public void Update() throws IOException {


        String name,lastname,tele,dat,role,email,salair,pass,Cin;


       int selectedIndex = table.getSelectionModel().getSelectedIndex();
       Utilisateur selectedUser = table.getItems().get(selectedIndex);



        /*name = nom.getText();
        tele =tel.getText();
        lastname = prenom.getText();
        email = Email.getText();
        salair=salary.getText();
        Cin=cin.getText();
        dat=birthday.getEditor().getText();
        role=Role.getValue();
        pass=passwd.getText();*/

         name = selectedUser.getNom();
         lastname = selectedUser.getPrenom();
         dat = selectedUser.getDate();
         tele = selectedUser.getTelephone();
         role = selectedUser.getRole();
         pass = selectedUser.getPass();
        email = selectedUser.getEmail();
         salair = selectedUser.getSalaire();
         Cin = selectedUser.getCIN();
        int selectedUserId = selectedUser.getId();

        try
        {
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();

            PreparedStatement pst = conn.prepareStatement("UPDATE utilisateur SET Nom = ?, Prénom = ?, DN = ?, Tel = ?, Role = ?, Mpasse = ?, Email = ?, Salaire = ?, CIN = ? IMAGE = ? WHERE IDu = ?");
            pst.setString(1, name);
            pst.setString(2, lastname);
            pst.setString(3, dat);
            pst.setString(4, tele);
            pst.setString(5, role);
            pst.setString(6, pass);
            pst.setString(7, email);
            pst.setString(8, salair);
            pst.setString(9, Cin);
            pst.setInt(10, selectedUserId);


            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour");
            alert.setHeaderText("Mise à jour utilisateur");
            alert.setContentText("Utilisateur mis à jour avec succès !");
            alert.showAndWait();

            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, "An SQL exception occurred", ex);
            ex.printStackTrace();
        }
    }

    public void delete() {

        int selectedIndex = table.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            int id = table.getItems().get(selectedIndex).getId();
            try {

                DatabaseManager dbManager = new DatabaseManager();
                Connection conn = dbManager.getConnection();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM utilisateur WHERE IDu = ?");
                pst.setInt(1, id);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Registration");
                alert.setHeaderText("User Registration");
                alert.setContentText("Deleted!");
                alert.showAndWait();

                table();
            } catch (SQLException ex) {
                Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No user Selected");
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }
    




}




