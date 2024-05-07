package example.controller;
import com.sun.javafx.charts.Legend;
import example.Services.Produit;
import example.Services.Utilisateur;
import example.model.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.FileChooser;

import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Produit_controller extends Controller implements Initializable {

    @FXML
    public AnchorPane Connected ;

    @FXML
    private ComboBox<String> category,ordon;
    @FXML
    private  ComboBox<Integer> depot;

    @FXML
    private TextField nompro,codeba,prix,qantite;
    @FXML
    private DatePicker dateexpir;
    @FXML
    private ImageView imageprod;

    @FXML
    private TextField searchTextField;


    @FXML private TableView<Produit> table;
    private ObservableList<Produit> produits;

    public void initialize(URL url , ResourceBundle resourceBundle){


        if(category !=null){
            initializeCategoryComboBox();
        }
        if(depot !=null){
            initializeDepotComboBox();
        }

        if(ordon != null){
            ordon.setItems(FXCollections.observableArrayList("Oui", "Non"));
        }

        Online(ConnectionStat(),main,Connected);
        new Thread(this::table).start();


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
        Online(ConnectionStat(),main,Connected);

    }

    /*@FXML
    private AnchorPane Connected;*/
    public void start(Stage primaryStage) {

    }
    @FXML
    private ImageView image;
    @FXML
    private Text main;
    int c=0;
    int d=0;
    Stage stage = new Stage();

    /*public void Onclose(ActionEvent event) throws IOException , SQLException {
        FermerFentere(event);
    }*/

    public void Addimage(ActionEvent event){
        File file = ParcourirFichier(event);
        if (file != null) {
            Image img=new Image(file.toURI().toString());
            imageprod.setImage(img);
        }
    }
    public void addproduct(ActionEvent event) throws IOException {
        NouveauFenetre("ajouter-produit");
    }
    public void fichproduct(ActionEvent event) throws IOException {
        NouveauFenetre("fich-produit");
    }

    public void initializeCategoryComboBox() {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();

            String sql = "SELECT Libelléca FROM catégorie";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            ObservableList<String> categories = FXCollections.observableArrayList();
            while (rs.next()) {
                String category = rs.getString("libelléca");
                categories.add(category);
            }

            category.setItems(categories);

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void initializeDepotComboBox() {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();

            String sql = "SELECT IDdep FROM depot";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            ObservableList<Integer> depotIds = FXCollections.observableArrayList();
            while (rs.next()) {
                int depotId = rs.getInt("IDdep");
                depotIds.add(depotId);
            }

            depot.setItems(depotIds);

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public InputStream convertImageInputStream(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }



    public boolean validateFields() {
        if (nompro.getText().isEmpty() || prix.getText().isEmpty() || qantite.getText().isEmpty() || codeba.getText().isEmpty() || dateexpir.getValue() == null) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs.");
            return false;
        }


        if (!nompro.getText().matches("[a-zA-ZÀ-ÿ\\s-]+")) {
            showAlert("Erreur de validation", "Le nom doit contenir uniquement des lettres.");
            return false;
        }

        if (!prix.getText().matches("\\d*\\.?\\d*")) {
            showAlert("Erreur de validation", "Le prix doit être un nombre valide.");
            return false;
        }

        if (!qantite.getText().matches("\\d+")) {
            showAlert("Erreur de validation", "La quantité doit contenir uniquement des chiffres.");
            return false;
        }

        if (!codeba.getText().matches("\\d{8,14}")) {
            showAlert("Erreur de validation", "Le code barre doit contenir des chiffres entre 8 et 14.");
            return false;
        }


        LocalDate selectedDate = dateexpir.getValue();


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




    LocalDate selectedDate;
    public void addProduct(ActionEvent event) throws IOException {
        if(validateFields()){
            String productName = nompro.getText();
            String category = this.category.getValue();
            String code = codeba.getText();
            float price = Float.parseFloat(prix.getText());
            int initialQuantity = Integer.parseInt(qantite.getText());
            selectedDate = dateexpir.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = selectedDate.format(formatter);
            Image productImage = imageprod.getImage();
            int depotId = depot.getValue();
            String ordonValue = ordon.getValue();

            boolean besOrdon = false;
            if ( ordonValue.equalsIgnoreCase("oui")) {
                besOrdon = true;
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {

                int categoryId = getCategoryId(category);
                if (categoryId == -1) {
                    System.err.println("La catégorie n'a pas été trouvée.");
                    return;
                }

                String sql = "INSERT INTO produit (Libellép, IDcat, Codebr, Prixv, Qte, Datepp, Imagep, IDdep, BesoinORD ) VALUES (?, ?, ?, ?, ?, ?,?, ?,?)";
                PreparedStatement statement = getConnection().prepareStatement(sql);

                statement.setString(1, productName);
                statement.setInt(2, categoryId);
                statement.setString(3, code);
                statement.setFloat(4, price);
                statement.setInt(5, initialQuantity);
                statement.setString(6, formattedDate);

                InputStream inputStream = convertImageInputStream(productImage);
                statement.setBlob(7, inputStream);
                statement.setInt(8,depotId);
                statement.setBoolean(9,besOrdon);

                statement.executeUpdate();

                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le produit a été ajouté avec succès !");
                alert.showAndWait();
                FermerFentere(event);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
                alert.setTitle("Échec");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de l'ajout du produit !");
                alert.showAndWait();
            }
        }

    }

    private int getCategoryId(String categoryName) throws SQLException {
        int categoryId = -1; // Par défaut, si la catégorie n'est pas trouvée, retourne -1

        String sql = "SELECT IDcat FROM catégorie WHERE Libelléca = ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, categoryName);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            categoryId = rs.getInt("IDcat");
        }

        rs.close();
        statement.close();

        return categoryId;
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


            String sql ="SELECT p.IDp, p.Libellép, p.Prixv, p.Qte, p.Datepp, p.Codebr, p.IDdep, c.Libelléca " +
                    "FROM produit p " +
                    "JOIN catégorie c ON p.IDcat = c.IDcat";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            produits = FXCollections.observableArrayList();


            if (!rs.isBeforeFirst()) {
                System.out.println("Aucun produit trouvé");
            }

            while (rs.next()) {
                Produit produit = new Produit(
                        rs.getInt("IDp"),
                        rs.getString("Libellép"),
                        rs.getFloat("Prixv"),
                        rs.getInt("Qte"),
                        rs.getString("Datepp"),
                        rs.getString("CodeBr"),
                        rs.getInt("IDdep")
                );
                produit.setCategorie(rs.getString("Libelléca"));
                produits.add(produit);
            }

            System.out.println("Nombre de produits chargés: " + produits.size());

            table.setItems(produits);

            TableColumn<Produit, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getIdp()).asObject());
            idCol.setMinWidth(50);

            TableColumn<Produit, String> libCol = new TableColumn<>("Nom");
            libCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getLibp()));
            libCol.setMinWidth(100);
            applyTextAlignment(libCol);

            TableColumn<Produit, Float> priceCol = new TableColumn<>("Prix");
            priceCol.setCellValueFactory(f -> new javafx.beans.property.SimpleFloatProperty(f.getValue().getPrixv()).asObject());
            priceCol.setMinWidth(150);

            TableColumn<Produit, Integer> quantityCol = new TableColumn<>("Quantité");
            quantityCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getQuantite()).asObject());
            quantityCol.setMinWidth(150);

            TableColumn<Produit, String> expirationCol = new TableColumn<>("Date d'expiration");
            expirationCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getExpDate()));
            expirationCol.setMinWidth(150);
            applyTextAlignment(expirationCol);

            TableColumn<Produit, String> codeCol = new TableColumn<>("CodeBr");
            codeCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getCode()));
            codeCol.setMinWidth(110);
            applyTextAlignment(codeCol);

            TableColumn<Produit, String> categCol = new TableColumn<>("Catégorie");
            categCol.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getCateg()));
            categCol.setMinWidth(150);
            applyTextAlignment(categCol);

            /*TableColumn<Produit, Integer> iddeCol = new TableColumn<>("IDdep");
            iddeCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getIdp()).asObject());
            iddeCol.setMinWidth(20);*/

            TableColumn<Produit, Integer> iddeCol = new TableColumn<>("IDdep");
            iddeCol.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getIddep()).asObject());
            iddeCol.setMinWidth(20);

            table.getColumns().setAll(idCol, libCol, priceCol, quantityCol, expirationCol,codeCol,categCol,iddeCol);


            TableColumn<Produit, Void> actionCol = new TableColumn<>("Actions");
            actionCol.setMinWidth(100);

            actionCol.setCellFactory(col -> {
                return new TableCell<Produit, Void>() {
                    private final SplitMenuButton splitMenuButton = new SplitMenuButton();

                    {
                        MenuItem editMenuItem = new MenuItem("Modifier");
                        MenuItem deleteMenuItem = new MenuItem("Supprimer");

                        splitMenuButton.getItems().addAll(editMenuItem, deleteMenuItem);

                        editMenuItem.setOnAction(e -> {
                            Produit p = getTableView().getItems().get(getIndex());
                            editProduit(p);
                        });
                        deleteMenuItem.setOnAction(e -> {
                            Produit p = getTableView().getItems().get(getIndex());
                            deleteProduit(p);
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


    private void applyTextAlignment(TableColumn<Produit, String> column) {
        column.setCellFactory(tc -> {
            TableCell<Produit, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }



    public void deleteProduit(Produit produit) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DatabaseManager dbManager = new DatabaseManager();

            try (Connection conn = dbManager.getConnection()) {
                String sql = "DELETE FROM produit WHERE IDp = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, produit.getIdp());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Produit supprimé avec succès : " + produit.getLibp());

                    // Supprimer le produit de la liste attachée au TableView
                    table.getItems().remove(produit);
                } else {
                    System.out.println("Aucun produit supprimé.");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getErrorCode());

                if(ex.getErrorCode() == 1451 ) {
                    showAlert("Erreur ", "Imposible de suprimer ce produit car il apartient a d'autre ventes ");
                      }
                System.out.println("Erreur lors de la suppression du produit.");
            }

            }
        }





    public void setupsearchtextfield(javafx.scene.input.KeyEvent event){
        FilteredList<Produit> filteredListProduit = new FilteredList<>(produits, b -> true);

        if ( searchTextField!= null) {

            searchTextField.textProperty().addListener((observable, OldValue, NewValue) -> {
                filteredListProduit.setPredicate(produit -> {
                    // Aucune valeur de recherche
                    if (NewValue == null || NewValue.isEmpty() || NewValue.isBlank()) {
                        return true;
                    }
                    String searchedProduit = NewValue.toLowerCase();

                    // Vérifie si le texte recherché est présent dans chaque propriété du produit
                    if (produit.getLibp().toLowerCase().contains(searchedProduit)) {
                        return true;
                    }
                    if (produit.getCode().toLowerCase().contains(searchedProduit)) {
                        return true;
                    }
                    if (String.valueOf(produit.getPrixv()).toLowerCase().contains(searchedProduit)) {
                        return true;
                    }
                    if (produit.getExpDate() != null && produit.getExpDate().toString().toLowerCase().contains(searchedProduit)) {
                        return true;
                    }
                    // Ajoutez d'autres conditions pour d'autres propriétés du produit si nécessaire

                    // Aucun résultat trouvé
                    return false;
                });

                // Création d'une SortedList à partir de la FilteredList
                SortedList<Produit> sortedList = new SortedList<>(filteredListProduit);

                // Lier le comparateur de la SortedList avec le comparateur du TableView Commandes (remplacez Commandes par le nom de votre TableView)
                sortedList.comparatorProperty().bind(table.comparatorProperty());

                // Définir les éléments du TableView avec la SortedList filtrée et triée
                table.setItems(sortedList);
            });
        }

    }





    public void editProduit(Produit produit) {
        TextField nomField = new TextField(produit.getLibp());
        TextField prixField = new TextField(String.valueOf(produit.getPrixv()));
        TextField quantiteField = new TextField(String.valueOf(produit.getQuantite()));



        TextField codeField = new TextField(String.valueOf(produit.getCode()));

        // Remplir les ComboBox pour le jour, le mois et l'année
        DatePicker datePicker = new DatePicker();

        LocalDate date = LocalDate.parse(produit.getExpDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);


        date = LocalDate.parse((formattedDate));

        datePicker.setValue(date);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier le produit");
        dialog.setHeaderText("Modifier les détails du produit : " + produit.getLibp());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("Nom :"), nomField,
                new Label("Prix :"), prixField,
                new Label("Quantité :"), quantiteField,
                new Label("Date d'expiration :"),datePicker,
                new Label("Code-barres :"), codeField
        );

        dialog.getDialogPane().setContent(vbox);

        ButtonType okButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Vérification pour le libellé pour autoriser seulement les alphabets et les espaces
        UnaryOperator<TextFormatter.Change> libelleFilter = change -> change.getText().matches("[a-zA-ZÀ-ÿ\\s-]*") ? change : null;
        nomField.setTextFormatter(new TextFormatter<>(libelleFilter));

        // Vérification pour le prix pour autoriser seulement les chiffres et les décimaux
        UnaryOperator<TextFormatter.Change> prixFilter = change -> change.getText().matches("\\d*\\.?\\d*") ? change : null;
        prixField.setTextFormatter(new TextFormatter<>(prixFilter));

        // Vérification pour la quantité pour autoriser seulement les chiffres
        UnaryOperator<TextFormatter.Change> quantiteFilter = change -> change.getText().matches("\\d*") ? change : null;
        quantiteField.setTextFormatter(new TextFormatter<>(quantiteFilter));

        // Vérification pour le code-barres pour autoriser seulement les chiffres
        codeField.setTextFormatter(new TextFormatter<>(quantiteFilter));


        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {


            try {
                String sql = "UPDATE produit SET Libellép = ?, Prixv = ?, Qte = ?, Datepp = ?, Codebr = ? WHERE IDp = ?";
                PreparedStatement statement = getConnection().prepareStatement(sql);

                selectedDate = datePicker.getValue();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formattedDate = selectedDate.format(formatter);

                statement.setString(4, formattedDate);
                statement.setString(1, nomField.getText());
                statement.setFloat(2, Float.parseFloat(prixField.getText()));
                statement.setInt(3, Integer.parseInt(quantiteField.getText()));

                // Concaténer les valeurs sélectionnées pour former la date d'expiration au format "année-mois-jour"



                statement.setString(5, codeField.getText());
                statement.setInt(6, produit.getIdp());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Produit mis à jour avec succès.");
                    table();
                } else {
                    System.out.println("Aucune modification apportée.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Erreur de format de nombre.");
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors de la modification du produit.");
            }
        }
    }







}