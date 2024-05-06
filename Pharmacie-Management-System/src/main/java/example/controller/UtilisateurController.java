package example.controller;

import eu.hansolo.tilesfx.Alarm;
import example.Services.Fournisseur;
import example.model.DatabaseManager;
import example.Services.Utilisateur;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtilisateurController extends Controller implements Initializable {
    @FXML
    public AnchorPane Connected;

    @FXML
    private Button bottonadd;
    @FXML
    private Button buttonModifier;
    @FXML
    private ImageView image;
    @FXML
    private TextField nom, prenom, tel, Email, salary, cin,searchUser;
    @FXML
    private PasswordField passwd;
    @FXML
    private ComboBox<String> Role;
    @FXML
    private SplitMenuButton Action;

    private ObservableList<Utilisateur> utilisateurs;


    @FXML
    private DatePicker birthday;
    @FXML
    private TableView<Utilisateur> table;

    int c = 0;
    public Pane imagePane;
    @FXML
    private Text main;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if (Role != null) {
            Role.setItems(FXCollections.observableArrayList("Gérant", "Pharmacien", "Admin"));
        }


        Online(ConnectionStat(), main, Connected);
        new Thread(this::table).start();
    }


    public void start(Stage primaryStage) {
        primaryStage.setOnShown(event -> {


        });

    }


    public void switchToHello(ActionEvent event) throws IOException {
        NouveauFenetre("Profile_utilisateur");
    }

    public boolean validateFields() {
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || tel.getText().isEmpty() || salary.getText().isEmpty() || cin.getText().isEmpty() || birthday.getValue() == null) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs.");
            return false;
        }



        if (!nom.getText().matches("[a-zA-ZÀ-ÿ\\s-]+")) {
            showAlert("Erreur de validation", "Le nom doit contenir uniquement des lettres.");
            return false;
        }

        if (!prenom.getText().matches("[a-zA-ZÀ-ÿ\\s-]+")) {
            showAlert("Erreur de validation", "Le prénom doit contenir uniquement des lettres.");
            return false;
        }

        if (!tel.getText().matches("0\\d+")) {
            showAlert("Erreur de validation", "Le téléphone doit contenir uniquement 10 chiffres et il doit commencer par 0.");
            return false;
        }

        if (!salary.getText().matches("\\d*\\.?\\d*")) {
            showAlert("Erreur de validation", "Le salaire doit être composé par des chiffre.");
            return false;
        }

        if (!cin.getText().matches("[A-Z0-9]{7,15}")) {
            showAlert("Erreur de validation", "Le CIN doit contenir entre 7 et 15 caractères avec des lettres majuscules et des chiffres.");
            return false;
        }


        LocalDate selectedDate = birthday.getValue();

        if (selectedDate == null) {
            showAlert("Erreur de validation", "Veuillez sélectionner une date.");
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = selectedDate.format(formatter);

        if (!formattedDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showAlert("Erreur de validation", "La date doit être au format dd/MM/yyyy.");
            return false;
        }


        return true;
    }


    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }








    public void onaddimageUser(ActionEvent event) {
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
    public void addu(ActionEvent event) throws IOException {

        if(validateFields()){
            String name = nom.getText();
            String pren = prenom.getText();
            String email = Email.getText();
            String phone = tel.getText();
            String CIN = cin.getText();
            String role = Role.getValue();
            String mpass = passwd.getText();
            String salaire = salary.getText();
            String Date = birthday.getEditor().getText();
            Image imageView = this.image.getImage();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {

                String sql = "INSERT INTO utilisateur (Nom,Prénom,DN, Tel,Role,Mpasse,Email,Salaire,CIN,IMAGE) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
                PreparedStatement statement = getConnection().prepareStatement(sql);

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
                FermerFentere(event);

            } catch (Exception e) {
                System.out.println(e);
                System.err.println("Erreur lors de l'ajout de données : " + e.getMessage());
                alert.setTitle("Failed");
                alert.setHeaderText(null);
                alert.setContentText("probleme d'insertion vérifie à nouveau vos coordonnées!");
                alert.showAndWait();
            }
        }


    }


    public InputStream convertImageToInputStream(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }


    public void table() {
        if (table == null) {
            System.err.println("Erreur : tableViewActif est null");
            return;
        }
        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = null;

        try {
            conn = dbManager.getConnection();

            String sql = "SELECT IDu, Nom, Prénom, Email, Tel, Role, DN, Salaire FROM utilisateur";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            utilisateurs = FXCollections.observableArrayList();

            if (!rs.isBeforeFirst()) {
                System.out.println("Aucun résultat trouvé");
            }

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("IDu"),
                        rs.getString("Nom"),
                        rs.getString("Prénom"),
                        rs.getString("Email"),
                        rs.getString("Tel"),
                        rs.getString("Role"),
                        rs.getString("DN"),
                        rs.getFloat("Salaire")
                );
                utilisateurs.add(utilisateur);
            }

            System.out.println("Nombre d'utilisateurs chargés: " + utilisateurs.size());

            table.setItems(utilisateurs);

            TableColumn<Utilisateur, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getId()).asObject());
            idCol.setMinWidth(50);

            TableColumn<Utilisateur, String> nameCol = new TableColumn<>("Nom");
            nameCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getNom()));
            nameCol.setMinWidth(100);
            applyTextAlignment(nameCol);

            TableColumn<Utilisateur, String> firstnameCol = new TableColumn<>("Prénom");
            firstnameCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getPrenom()));
            firstnameCol.setMinWidth(100);
            applyTextAlignment(firstnameCol);

            TableColumn<Utilisateur, String> emailCol = new TableColumn<>("Email");
            emailCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getEmail()));
            emailCol.setMinWidth(150);
            applyTextAlignment(emailCol);

            TableColumn<Utilisateur, String> phoneCol = new TableColumn<>("Téléphone");
            phoneCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getTel()));
            phoneCol.setMinWidth(120);
            applyTextAlignment(phoneCol);

            TableColumn<Utilisateur, String> roleCol = new TableColumn<>("Role");
            roleCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getRole()));
            roleCol.setMinWidth(120);
            applyTextAlignment(roleCol);

            TableColumn<Utilisateur, String> Dncol = new TableColumn<>("Date");
            Dncol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getDN()));
            Dncol.setMinWidth(170);
            applyTextAlignment(Dncol);

            TableColumn<Utilisateur, String> salairecol = new TableColumn<>("Salaire");
            salairecol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(Float.toString(f.getValue().getSalaire())));
            salairecol.setMinWidth(100);
            applyTextAlignment(salairecol);


            table.getColumns().setAll(idCol, nameCol, firstnameCol, emailCol, phoneCol, roleCol, Dncol, salairecol);

            TableColumn<Utilisateur, Void> actionCol = new TableColumn<>("Actions");
            actionCol.setMinWidth(100);

            actionCol.setCellFactory(col -> {
                return new TableCell<Utilisateur, Void>() {
                    private final SplitMenuButton splitMenuButton = new SplitMenuButton();

                    {
                        MenuItem editMenuItem = new MenuItem("Modifier");
                        MenuItem deleteMenuItem = new MenuItem("Supprimer");
                        splitMenuButton.getItems().addAll(editMenuItem, deleteMenuItem);

                        editMenuItem.setOnAction(e -> {
                            Utilisateur u = getTableView().getItems().get(getIndex());
                            editUtilisateur(u);
                        });
                        deleteMenuItem.setOnAction(e -> {
                            Utilisateur u = getTableView().getItems().get(getIndex());
                            deleteUtilisateur(u);
                        });
                    }

                    HBox pane = new HBox(10, splitMenuButton);

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : pane);
                    }
                };
            });

            table.getColumns().addAll(actionCol);

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void applyTextAlignment(TableColumn<Utilisateur, String> column) {
        column.setCellFactory(tc -> {
            TableCell<Utilisateur, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }





    public void deleteUtilisateur(Utilisateur utilisateur) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ? Cette action supprimera également toutes les ventes et contenus associés.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DatabaseManager dbManager = new DatabaseManager();

            try (Connection conn = dbManager.getConnection()) {
                conn.setAutoCommit(false); // Désactiver l'autocommit pour gérer la transaction

                // Supprimer les contenus associés aux ventes associées à cet utilisateur
                String deleteContenirSql = "DELETE c FROM contenir c INNER JOIN vente v ON c.IDv = v.IDv WHERE v.IDu = ?";
                PreparedStatement deleteContenirStatement = conn.prepareStatement(deleteContenirSql);
                deleteContenirStatement.setInt(1, utilisateur.getId());
                deleteContenirStatement.executeUpdate();

                // Supprimer les ventes associées à cet utilisateur
                String deleteVentesSql = "DELETE FROM vente WHERE IDu = ?";
                PreparedStatement deleteVentesStatement = conn.prepareStatement(deleteVentesSql);
                deleteVentesStatement.setInt(1, utilisateur.getId());
                deleteVentesStatement.executeUpdate();

                // Supprimer l'utilisateur
                String deleteUtilisateurSql = "DELETE FROM utilisateur WHERE IDu = ?";
                PreparedStatement deleteUtilisateurStatement = conn.prepareStatement(deleteUtilisateurSql);
                deleteUtilisateurStatement.setInt(1, utilisateur.getId());
                int rowsAffected = deleteUtilisateurStatement.executeUpdate();

                conn.commit(); // Valider la transaction

                if (rowsAffected > 0) {
                    System.out.println("Utilisateur supprimé avec succès : " + utilisateur.getNom());

                    // Supprimer l'utilisateur de la liste et rafraîchir la table
                    utilisateurs.remove(utilisateur);
                    table.setItems(utilisateurs);
                } else {
                    System.out.println("Aucun utilisateur supprimé.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors de la suppression de l'utilisateur.");
                System.out.println(ex);

                try {
                    getConnection().rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                    System.out.println("Erreur lors de l'annulation de la suppression de l'utilisateur.");
                    System.out.println(rollbackEx);
                }
            }
        }
    }





    public void setupSearchUser(javafx.scene.input.KeyEvent event) {
        FilteredList<Utilisateur> filteredListUtilisateur = new FilteredList<>(utilisateurs, b -> true);

        if (searchUser != null) {

            searchUser.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredListUtilisateur.setPredicate(utilisateur -> {
                    // Aucune valeur de recherche
                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String searchedUtilisateur = newValue.toLowerCase();

                    // Vérifie si le texte recherché est présent dans chaque propriété de l'utilisateur
                    if (utilisateur.getNom().toLowerCase().contains(searchedUtilisateur)) {
                        return true;
                    }
                    if (utilisateur.getPrenom().toLowerCase().contains(searchedUtilisateur)) {
                        return true;
                    }
                    // Ajoutez d'autres conditions pour d'autres propriétés de l'utilisateur si nécessaire

                    // Aucun résultat trouvé
                    return false;
                });

                // Création d'une SortedList à partir de la FilteredList
                SortedList<Utilisateur> sortedList = new SortedList<>(filteredListUtilisateur);

                // Lier le comparateur de la SortedList avec le comparateur du TableView Utilisateurs
                sortedList.comparatorProperty().bind(table.comparatorProperty());

                // Définir les éléments du TableView avec la SortedList filtrée et triée
                table.setItems(sortedList);
            });
        }
    }






    public void editUtilisateur(Utilisateur utilisateur) {
        TextField nomField = new TextField(utilisateur.getNom());
        TextField prenomField = new TextField(utilisateur.getPrenom());
        TextField emailField = new TextField(utilisateur.getEmail());
        TextField phoneField = new TextField(utilisateur.getTel());
        ComboBox<String> roleComboBox = new ComboBox<>(FXCollections.observableArrayList("Pharmacien", "Admin", "Gérant"));
        roleComboBox.setValue(utilisateur.getRole());
        ComboBox<String> dayComboBox = new ComboBox<>();
        ComboBox<String> monthComboBox = new ComboBox<>();
        ComboBox<String> yearComboBox = new ComboBox<>();
        TextField salaireField = new TextField(String.valueOf(utilisateur.getSalaire()));

        // Remplir les ComboBox pour le jour, le mois et l'année
        for (int i = 1; i <= 31; i++) {
            dayComboBox.getItems().add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            monthComboBox.getItems().add(String.valueOf(i));
        }
        // Supposons que nous voulons couvrir les années de 1900 à 2025
        for (int i = 1900; i <= 2025; i++) {
            yearComboBox.getItems().add(String.valueOf(i));
        }

        // Sélectionner la date de l'utilisateur actuel
        String[] dateParts = utilisateur.getDN().split("/");
        dayComboBox.setValue(dateParts[0]);
        monthComboBox.setValue(dateParts[1]);
        yearComboBox.setValue(dateParts[2]);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'utilisateur");
        dialog.setHeaderText("Modifier les détails de l'utilisateur : " + utilisateur.getNom());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("Nom :"), nomField,
                new Label("Prénom :"), prenomField,
                new Label("Email :"), emailField,
                new Label("Téléphone :"), phoneField,
                new Label("Rôle :"), roleComboBox,
                new Label("Date de Naissance :"),
                new HBox(dayComboBox, new Label("/"), monthComboBox, new Label("/"), yearComboBox),
                new Label("Salaire :"), salaireField
        );

        dialog.getDialogPane().setContent(vbox);

        ButtonType okButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Filtrer le salaire uniquement pour les nombres
        UnaryOperator<TextFormatter.Change> salaryFilter = change -> change.getText().matches("\\d*\\.?\\d*") ? change : null;
        salaireField.setTextFormatter(new TextFormatter<>(salaryFilter));



        // Vérification pour le nom et le prénom pour autoriser seulement les alphabets et les espaces
        UnaryOperator<TextFormatter.Change> nameFilter = change -> change.getText().matches("[a-zA-ZÀ-ÿ\\s-]*") ? change : null;
        nomField.setTextFormatter(new TextFormatter<>(nameFilter));
        prenomField.setTextFormatter(new TextFormatter<>(nameFilter));

        // Vérification pour le téléphone pour autoriser seulement les chiffres
        UnaryOperator<TextFormatter.Change> phoneFilter = change -> change.getText().matches("\\d*") ? change : null;
        phoneField.setTextFormatter(new TextFormatter<>(phoneFilter));

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            DatabaseManager dbManager = new DatabaseManager();

            try (Connection conn = dbManager.getConnection()) {
                String sql = "UPDATE utilisateur SET Nom = ?, Prénom = ?, Email = ?, Tel = ?, Role = ?, Salaire = ?, DN = ? WHERE IDu = ?";
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(1, nomField.getText());
                statement.setString(2, prenomField.getText());
                statement.setString(3, emailField.getText());
                statement.setString(4, phoneField.getText());
                statement.setString(5, roleComboBox.getValue());
                statement.setString(6, salaireField.getText());

                // Concaténer les valeurs sélectionnées pour former la date de naissance au format "jour/mois/année"
                String dateNaissance = dayComboBox.getValue() + "/" + monthComboBox.getValue() + "/" + yearComboBox.getValue();
                statement.setString(7, dateNaissance);

                statement.setInt(8, utilisateur.getId());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Utilisateur mis à jour avec succès.");

                    utilisateur.setNom(nomField.getText());
                    utilisateur.setPrenom(prenomField.getText());
                    utilisateur.setEmail(emailField.getText());
                    utilisateur.setTel(phoneField.getText());
                    utilisateur.setRole(roleComboBox.getValue());
                    utilisateur.setSalaire(Float.parseFloat(salaireField.getText()));
                    utilisateur.setDN(dateNaissance);

                    table.refresh();
                } else {
                    System.out.println("Aucune modification apportée.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors de la modification de l'utilisateur.");
            }
        }
    }























}




