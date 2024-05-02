package example.controller;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import example.model.DatabaseManager;
import example.model.Fournisseur;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;



public class FournisseurController extends Controller implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Text main;
    @FXML
    private AnchorPane Connected;

    @FXML
    private TableView<Fournisseur>tableViewActif;

    @FXML
    private ImageView OnBack;

    @FXML
    private TableView<?> tableViewArchived;
    @FXML
    private Text main2;
    @FXML
    private TextField cityfor;

    @FXML
    private TextField countryfor;

    @FXML
    private TextField emailfor;
    private DatabaseManager dbManager;

    @FXML
    private ComboBox<String> genderfor;


    @FXML
    private TextField namefor;

    @FXML
    private TextField phonefor;
    @FXML
    private TableColumn<?, ?> action;

    @FXML
    private TableColumn<Fournisseur, String> address;

    @FXML
    private TableColumn<Fournisseur, String> email;
    @FXML
    private TableColumn<Fournisseur, String> nom;

    @FXML
    private TableColumn<Fournisseur, String> phone;

    private ObservableList<Fournisseur> fournisseurs;
    @FXML
    private TableColumn<Fournisseur, Integer> id;
    @FXML
    private Label A;
    @FXML
    private Label E;

    @FXML
    private Label N;

    @FXML
    private Label T;
    @FXML
    private TextField ad;

    @FXML
    private TextField em;
    @FXML
    private ListView<Fournisseur> fournisseurListView;

    private Fournisseur selectedFournisseur;
    @FXML
    private TextField no;

    @FXML
    private TextField te;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseManager Data = new DatabaseManager();
            boolean isConnected = Data.ConnectionStat();
            if (isConnected) {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: green; -fx-background-radius: 100px");
            } else {
                System.out.print(isConnected);
                Connected.setStyle("-fx-background-color: red; -fx-background-radius: 100px");
            }
        } catch (Exception e) {
            System.err.println("Error initializing connection: " + e.getMessage());
        }

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
        } else {
            System.err.println("TableView is null");
        }
        Online(ConnectionStat(), main, Connected);
        affiche();

    }

    @FXML
    public void Addfor() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void AddforA(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ArchiverF.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void afficherFournisseursActifs() {

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(true);
            tableViewArchived.setVisible(false);
            main2.setText("Fournisseur Actif");
            OnBack.setVisible(false);
        } else {
            System.err.println("TableView actif is null");
        }
    }

    @FXML
    void afficherFournisseursArchives() {

        if (tableViewActif != null && tableViewArchived != null) {
            tableViewActif.setVisible(false);
            tableViewArchived.setVisible(true);
            main2.setText("Fournisseur Archivé");
            OnBack.setVisible(true);

        } else {
            System.err.println("TableView  archive is null");
        }
    }

    @FXML
    public void addf() {
        String name = namefor.getText();
        String email = emailfor.getText();
        String phone = phonefor.getText();
        String gender = genderfor.getValue();
        String city = cityfor.getText();
        String country = countryfor.getText();

        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            conn = dbManager.getConnection();

            String sql = "INSERT INTO fournisseur (Nomf, Telf, Emailf,country,city, gender,etat) VALUES (?, ?, ?, ?,?,?,'A')";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, country);
            statement.setString(5, city);
            statement.setString(6, gender);

            statement.executeUpdate();


            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été insérées avec succès !");
            alert.showAndWait();
            Addfor();

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de données : " + e.getMessage());
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("probleme d'insertion !");
            alert.showAndWait();
        } finally {
            if (conn != null) {
                try {
                    dbManager.closeConnection();
                } catch (SQLException sqle) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + sqle.getMessage());
                }
            }
        }
    }

    public void affiche() {
        if (tableViewActif == null) {
            System.err.println("Erreur : tableViewActif est null");
            return;
        }
        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = null;

        try {
            conn = dbManager.getConnection();

            String sql = "SELECT IDF, Nomf, Telf, Emailf, country, city FROM fournisseur WHERE etat='A'";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();


            fournisseurs = FXCollections.observableArrayList();


            if (!rs.isBeforeFirst()) {
                System.out.println("Aucun résultat trouvé");
            }

            while (rs.next()) {
                Fournisseur fournisseur = new Fournisseur(
                        rs.getInt("IDF"),
                        rs.getString("Nomf"),
                        rs.getString("Emailf"),
                        rs.getString("Telf"),
                        rs.getString("country") + ", " + rs.getString("city")
                );
                fournisseurs.add(fournisseur);
            }

            System.out.println("Nombre de fournisseurs chargés: " + fournisseurs.size());


            tableViewActif.setItems(fournisseurs);


            TableColumn<Fournisseur, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getId()).asObject());
            idCol.setMinWidth(138);

            TableColumn<Fournisseur, String> nameCol = new TableColumn<>("Nom");
            nameCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getNom()));
            nameCol.setMinWidth(150);
            applyTextAlignment(nameCol);

            TableColumn<Fournisseur, String> emailCol = new TableColumn<>("Email");
            emailCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getEmail()));
            emailCol.setMinWidth(189);
            applyTextAlignment(emailCol);

            TableColumn<Fournisseur, String> phoneCol = new TableColumn<>("Téléphone");
            phoneCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getPhone()));
            phoneCol.setMinWidth(120);
            applyTextAlignment(phoneCol);
            TableColumn<Fournisseur, String> addressCol = new TableColumn<>("Adresse");
            addressCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getAdresse()));
            addressCol.setMinWidth(250);
            applyTextAlignment(addressCol);

            tableViewActif.getColumns().setAll(idCol, nameCol, emailCol, phoneCol, addressCol);
            TableColumn<Fournisseur, Void> actionCol = new TableColumn<>("Actions");
            actionCol.setMinWidth(200);

            actionCol.setCellFactory(col -> {
                        return new TableCell<Fournisseur, Void>() {
                            private final Button btnDelete = new Button("Supprimer");
                            private final Button btnEdit = new Button("Modifier");

                            {
                                btnDelete.setOnAction(e -> {
                                    Fournisseur f = getTableView().getItems().get(getIndex());
                                    deleteFournisseur(f);
                                });
                                btnEdit.setOnAction(e -> {
                                    Fournisseur f = getTableView().getItems().get(getIndex());
                                    editFournisseur(f);
                                });
                            }
                            HBox pane = new HBox(10, btnDelete, btnEdit);

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                setGraphic(empty ? null : pane);
                            }
                        };
            });


        tableViewActif.getColumns().addAll(actionCol);

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void applyTextAlignment(TableColumn<Fournisseur, String> column) {
        column.setCellFactory(tc -> {
            TableCell<Fournisseur, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }
    private void editFournisseur(Fournisseur fournisseur) {

        TextField nomField = new TextField(fournisseur.getNom());
        TextField emailField = new TextField(fournisseur.getEmail());
        TextField phoneField = new TextField(fournisseur.getPhone());
        TextField addressField = new TextField(fournisseur.getAdresse());


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier le fournisseur");
        dialog.setHeaderText("Modifier les détails du fournisseur : " + fournisseur.getNom());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("Nom :"), nomField,
                new Label("Email :"), emailField,
                new Label("Téléphone :"), phoneField,
                new Label("Adresse :"), addressField
        );

        dialog.getDialogPane().setContent(vbox);

        ButtonType okButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {

            DatabaseManager dbManager = new DatabaseManager();

            try (Connection conn = dbManager.getConnection()) {
                String sql = "UPDATE fournisseur SET Nomf = ?, Emailf = ?, Telf = ?, country = ?, city = ? WHERE IDF = ?";
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(1, nomField.getText());
                statement.setString(2, emailField.getText());
                statement.setString(3, phoneField.getText());
                statement.setString(4, addressField.getText().split(",")[0].trim());
                statement.setString(5, addressField.getText().split(",")[1].trim());
                statement.setInt(6, fournisseur.getId());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Fournisseur mis à jour avec succès.");

                    fournisseur.setNom(nomField.getText());
                    fournisseur.setEmail(emailField.getText());
                    fournisseur.setPhone(phoneField.getText());
                    fournisseur.setAdresse(addressField.getText());
                    // Actualiser le TableView
                    tableViewActif.refresh();
                } else {
                    System.out.println("Aucune modification apportée.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors de la modification du fournisseur.");
            }
        }
    }

    private void deleteFournisseur(Fournisseur fournisseur) {
        DatabaseManager dbManager = new DatabaseManager();

        try (Connection conn = dbManager.getConnection()) {
            String sql = "DELETE FROM fournisseur WHERE IDF = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, fournisseur.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fournisseur supprimé avec succès : " + fournisseur.getNom());

                fournisseurs.remove(fournisseur);
                tableViewActif.setItems(fournisseurs);
            } else {
                System.out.println("Aucun fournisseur supprimé.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erreur lors de la suppression du fournisseur.");
        }
    }
    private void displayFournisseurDetails(Fournisseur fournisseur) {
        no.setText(fournisseur.getNom());
        em.setText(fournisseur.getEmail());
        te.setText(fournisseur.getPhone());
        ad.setText(fournisseur.getAdresse());
    }
    private void archiveFournisseur(Fournisseur fournisseur) {
        DatabaseManager dbManager = new DatabaseManager();

        try (Connection conn = dbManager.getConnection()) {
            String sql = "UPDATE fournisseur SET etat = 'D' WHERE IDF = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, fournisseur.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Fournisseur archivé avec succès.");
            } else {
                System.out.println("Aucun fournisseur archivé.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erreur lors de l'archivage du fournisseur.");
        }
    }
    @FXML
    public void onSelect() {
        selectedFournisseur = fournisseurListView.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) fournisseurListView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onCancel() {
        selectedFournisseur = null;

        Stage stage = (Stage) fournisseurListView.getScene().getWindow();
        stage.close();
    }

    public Fournisseur getSelectedFournisseur() {
        return selectedFournisseur;
    }
}