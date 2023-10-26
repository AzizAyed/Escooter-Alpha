package reclamGui;

import java.awt.Desktop;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Etat;
import services.EmailService;
import entities.Reclamation;
import entities.Reponse;
import entities.TypeRec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServiceReclamation;
import services.ServiceReponse;
import tools.DataSource;

public class BackreclController implements Initializable {
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button boutonmodifier;

    @FXML
    private TableColumn<Reponse, Integer> idrep;

    @FXML
    private Button boutonsupprimer;
    
    @FXML
    private TableView<Reclamation> table;
    @FXML
    private Button navigate3;
    @FXML
    private Button Ajout;
    @FXML
    private TextField filtreReclamations;
    @FXML
    private Button exit;

    @FXML
    private TextArea contenutext;

    @FXML
    private AnchorPane Ajoutreponse;

    @FXML
    private Button repondre;

    @FXML
    private TableColumn<Reponse, String> colonnecontenu;

    @FXML
    private TableColumn<Reponse, String> colonnedescr;

    @FXML
    private TableColumn<Reponse, String> colonnetitre;


    @FXML
    private AnchorPane modifierreponse;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<Reponse> tablereponse;

    @FXML
    private Button navigate;


    @FXML
    private TableColumn<Reclamation, Date> date;
    @FXML
    private ComboBox<String> ComboboxTri;
    @FXML
    private TableColumn<Reclamation, Etat> etat;
    @FXML
    private TableColumn<Reclamation, String> description;


    @FXML
    private TableColumn<Reclamation, String> email;

    @FXML
    private Button retour1;
 @FXML
    private Button navigate2;
    @FXML
    private Button retour2;

    @FXML
    private AnchorPane repond;

    @FXML
    private TableColumn<Reclamation, String> titre;
    private Connection cnx;
    @FXML
    private Button pdf;
    @FXML
    private Button Home;
    @FXML
    private Button GSD;
    @FXML
    private Button OFFRES;
    @FXML
    private Button LOGOUT;
    @FXML
    private Button DASHBOARD;

   @FXML
public void supprimer(ActionEvent event) {
    Reclamation selectedReclamation = table.getSelectionModel().getSelectedItem();

    if (selectedReclamation != null) {
        // Créez une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la réclamation ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réclamation ?");

        // Ajoutez des boutons "OK" et "Annuler" à la boîte de dialogue
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOK) {
            // L'utilisateur a cliqué sur "OK", supprimez la réclamation
            try {
                int id = selectedReclamation.getId();
                ServiceReclamation.getInstance().supprimer(id);
                table.getItems().remove(selectedReclamation);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // L'utilisateur a cliqué sur "Annuler", aucune action de suppression n'est effectuée
        }
    } else {
        // Aucune réclamation n'est sélectionnée, affichez une alerte pour informer l'utilisateur
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune réclamation sélectionnée");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
        alert.showAndWait();
    }
}


    

    @FXML
    public void EXIT() {
        System.exit(0);
    }

    @FXML
public void supprimerReponse(ActionEvent event) {
        this.cnx = DataSource.getInstance().getConnection();

    Reponse selectedReponse = tablereponse.getSelectionModel().getSelectedItem();

    if (selectedReponse != null) {
        // Créez une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la réponse ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réponse ?");

        // Ajoutez des boutons "OK" et "Annuler" à la boîte de dialogue
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOK) {
            // L'utilisateur a cliqué sur "OK", supprimez la réponse
            try {
                int id = selectedReponse.getIdrep();
                String sql = "DELETE FROM reponse WHERE idrep = ?";
                try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    int lignesModifiees = preparedStatement.executeUpdate();

                    if (lignesModifiees > 0) {
                        System.out.println("La réponse a été supprimée avec succès de la base de données.");
                    } else {
                        System.out.println("Échec de la suppression de la réponse dans la base de données.");
                    }
                }

                tablereponse.getItems().remove(selectedReponse);
            } catch (SQLException ex) {
            }
        } else {
            // L'utilisateur a cliqué sur "Annuler", aucune action de suppression n'est effectuée
        }
    } else {
        // Aucune réponse n'est sélectionnée, affichez une alerte pour informer l'utilisateur
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune réponse sélectionnée");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réponse à supprimer.");
        alert.showAndWait();
    }
}

    

    int idReclamationSelectionnee;

    @FXML
    public void selectionnerReclamation() {
        Reclamation reclamationSelectionnee = table.getSelectionModel().getSelectedItem();

        if (reclamationSelectionnee != null) {
            idReclamationSelectionnee = reclamationSelectionnee.getId();
            System.out.println("ID de la réclamation sélectionnée : " + idReclamationSelectionnee);
        }
    }

    private EmailService emailService;

    @FXML
    public void ajouterReponse(ActionEvent event) throws SQLException {
    String contenuReponse = contenutext.getText();

    // Vérifiez si le champ de réponse est vide
    if (contenuReponse.isEmpty()) {
        // Affichez une alerte si le champ est vide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'Ajout de Réponse");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer du contenu pour la réponse.");
        alert.showAndWait();
    } else {
        this.cnx = DataSource.getInstance().getConnection();

        String sql = "INSERT INTO reponse (contenu, idrec) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, contenuReponse);
            preparedStatement.setInt(2, idReclamationSelectionnee);

            int lignesModifiees = preparedStatement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("La réponse a été ajoutée avec succès à la base de données.");

                // Affichez une alerte pour informer l'utilisateur que la réponse a été ajoutée avec succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("La réponse a été envoyée avec succès !");
                successAlert.showAndWait();

                afficherReponse(null);
                marquerReclamationCommeTraitee(idReclamationSelectionnee);
                emailService.envoyerEmail("medaziz.ayed@esprit.tn", "Nouvelle Réponse à Votre Réclamation", "Une nouvelle réponse a été ajoutée à votre réclamation : " + contenuReponse);
            } else {
                System.out.println("Échec de l'envoi de la réponse .");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de l'envoie de la réponse.");
        }
    }
}


    @FXML
public void repondre() {
    Reclamation reclamationSelectionnee = table.getSelectionModel().getSelectedItem();

    if (reclamationSelectionnee == null) {
        // Afficher une alerte si aucune réclamation n'est sélectionnée
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de Réponse");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réclamation avant de répondre.");
        alert.showAndWait();
    } else {
        // Le code actuel pour passer de Ajoutreponse à repond
        Ajoutreponse.setVisible(true);
        repond.setVisible(false);
    }
}


    @FXML
    public void retour2() {
        if (retour2.isFocused()) {
            repond.setVisible(true);
            modifierreponse.setVisible(false);
        }
    }

    @FXML
    public void retour1() {
        if (retour1.isFocused()) {
            repond.setVisible(true);
            Ajoutreponse.setVisible(false);
        }
    }
@FXML
    public void navigate2() {
        if (navigate2.isFocused()) {
            repond.setVisible(false);
            modifierreponse.setVisible(true);
        }
    }
    @FXML
    public void navigate3() {
        if (navigate3.isFocused()) {
            repond.setVisible(true);
            modifierreponse.setVisible(false);
        }
    }
    @FXML
    public void affiche() {
        if (navigate.isFocused()) {
            modifierreponse.setVisible(true);
            Ajoutreponse.setVisible(false);
        }
    }

    @FXML
    public void modifierReponse(ActionEvent event) throws SQLException {
        Reponse selectedReponse = tablereponse.getSelectionModel().getSelectedItem();

        if (selectedReponse != null) {
            int idReponseSelectionnee = selectedReponse.getIdrep();

            if (idReponseSelectionnee > 0) {
                TextInputDialog dialog = new TextInputDialog(selectedReponse.getContenu());
                dialog.setTitle("Modifier la Réponse");
                dialog.setHeaderText("Modifier la réponse :");
                dialog.setContentText("Nouveau contenu :");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String nouveauContenu = result.get();
                    selectedReponse.setContenu(nouveauContenu);

                    try {
                        this.cnx = DataSource.getInstance().getConnection();
                        String sql = "UPDATE reponse SET contenu = ? WHERE idrep = ?";
                        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                            preparedStatement.setString(1, nouveauContenu);
                            preparedStatement.setInt(2, idReponseSelectionnee);

                            int lignesModifiees = preparedStatement.executeUpdate();

                            if (lignesModifiees > 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Modification Réussie");
                                alert.setHeaderText(null);
                                alert.setContentText("La réponse a été mise à jour avec succès dans la base de données.");
                                alert.showAndWait();

                                afficherReponse(null);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Échec de la Modification");
                                alert.setHeaderText(null);
                                alert.setContentText("Aucune réponse mise à jour.");
                                alert.showAndWait();
                            }
                        }
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de Modification");
                        alert.setHeaderText(null);
                        alert.setContentText("Erreur lors de la mise à jour de la réponse : " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ID de Réponse Invalide");
                alert.setHeaderText(null);
                alert.setContentText("ID de réponse invalide : " + idReponseSelectionnee);
                alert.showAndWait();
            }
        }
    }

    private void marquerReclamationCommeTraitee(int idReclamation) {
        this.cnx = DataSource.getInstance().getConnection();

        try {
            String sql = "UPDATE reclamation SET etat = 'TRAITE' WHERE id = ?";
            PreparedStatement prepare = cnx.prepareStatement(sql);
            prepare.setInt(1, idReclamation);
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public void filtrerReclamations() {
    String texteFiltre = filtreReclamations.getText();
    String triSelectionne = ComboboxTri.getValue(); // Obtenir la valeur sélectionnée dans le ComboBox de tri

    List<Reclamation> reclamationList = new ArrayList<>();

    // Obtenez la liste complète des réclamations (non triées)
    reclamationList = ServiceReclamation.getInstance().getAll();

    if (triSelectionne != null) {
        if (triSelectionne.equals("traitée")) {
            // Tri par état "traitée"
            reclamationList = trierReclamationsParEtat(reclamationList, Etat.TRAITE);
        } else if (triSelectionne.equals("non traitée")) {
            // Tri par état "non traitée"
            reclamationList = trierReclamationsParEtat(reclamationList, Etat.NON_TRAITE);
        } else {
            // Tri par date
            reclamationList = trierReclamations(reclamationList, triSelectionne);
        }
    }

    if (texteFiltre.isEmpty()) {
        // Si le champ de filtre est vide, affichez la liste triée complète
        ObservableList<Reclamation> observableList = FXCollections.observableArrayList(reclamationList);
        table.setItems(observableList);
    } else {
        // Si le champ de filtre n'est pas vide, filtrez les réclamations triées en fonction du texte
        List<Reclamation> reclamationsFiltrees = new ArrayList<>();

        for (Reclamation reclamation : reclamationList) {
            if (reclamation.getTitre().toLowerCase().contains(texteFiltre.toLowerCase()) || reclamation.getDescription().toLowerCase().contains(texteFiltre.toLowerCase())) {
                // Si le titre ou la description de la réclamation contient le texte de filtre, ajoutez-la à la liste filtrée
                reclamationsFiltrees.add(reclamation);
            }
        }

        // Convertissez la liste filtrée en ObservableList
        ObservableList<Reclamation> observableReclamationsFiltrees = FXCollections.observableArrayList(reclamationsFiltrees);

        // Affichez les réclamations filtrées dans la table
        table.setItems(observableReclamationsFiltrees);
    }
}

public void initialiserComboBox() {
    List<String> list = new ArrayList<>();

    // Ajoutez ici les valeurs que vous souhaitez afficher dans le ComboBox
    list.add("Date croissante");
    list.add("Date décroissante");
    list.add("traitée");
    list.add("non traitée");
    
    ObservableList<String> dataList = FXCollections.observableArrayList(list);
    ComboboxTri.setItems(dataList);
}

private List<Reclamation> trierReclamationsParEtat(List<Reclamation> reclamations, Etat etatRecherche) {
    List<Reclamation> reclamationsFiltrees = new ArrayList<>();

    for (Reclamation reclamation : reclamations) {
        if (reclamation.getEtat() == etatRecherche) {
            reclamationsFiltrees.add(reclamation);
        }
    }

    return reclamationsFiltrees;
}

private List<Reclamation> trierReclamations(List<Reclamation> reclamations, String triSelectionne) {
    if (triSelectionne != null) {
        if (triSelectionne.equals("Date croissante")) {
            // Trier la liste de réclamations par date croissante
            reclamations.sort((r1, r2) -> r1.getDate().compareTo(r2.getDate()));
        } else if (triSelectionne.equals("Date décroissante")) {
            // Trier la liste de réclamations par date décroissante
            reclamations.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate()));
        }
    }
    return reclamations;
}


    public void afficherReponse(ActionEvent event) {
        try {
            this.cnx = DataSource.getInstance().getConnection();
            String sql = "SELECT r.titre, r.description, reponse.contenu, reponse.idrep, reponse.email " +
                 "FROM reclamation r " +
                 "JOIN reponse reponse ON r.id = reponse.idrec";

            PreparedStatement preparedStatement = cnx.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Reponse> observableList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String contenu = resultSet.getString("contenu");
                String email = resultSet.getString("email");

                int idrep = resultSet.getInt("idrep");
                Reponse reponse = new Reponse();
                Reclamation reclamation = new Reclamation();
                reclamation.setTitre(titre);
                reclamation.setDescription(description);
                reponse.setContenu(contenu);
                reponse.setR(reclamation);
                reponse.setIdrep(idrep);
                reponse.setEmail(email);

                observableList.add(reponse);
            }

            tablereponse.setItems(observableList);

            colonnecontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            idrep.setCellValueFactory(new PropertyValueFactory<>("idrep"));
            colonnetitre.setCellValueFactory(data -> {
                Reclamation reclamation = data.getValue().getR();
                if (reclamation != null) {
                    return new SimpleStringProperty(reclamation.getTitre());
                }
                return new SimpleStringProperty("");
            });

            colonnedescr.setCellValueFactory(data -> {
                Reclamation reclamation = data.getValue().getR();
                if (reclamation != null) {
                    return new SimpleStringProperty(reclamation.getDescription());
                }
                return new SimpleStringProperty("");
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialiserComboBox();
            ComboboxTri.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    filtrerReclamations();
});

            filtreReclamations.textProperty().addListener((observable, oldValue, newValue) -> {
        filtrerReclamations();
    });
            
            emailService = new EmailService("escooterbyalpha@gmail.com", "kaix gxfe ctur qkqd");
            List<Reclamation> reclamation = ServiceReclamation.getInstance().getAll();
            ObservableList<Reclamation> observableList = FXCollections.observableArrayList(reclamation);
            table.setItems(observableList);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            afficherReponse(null);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
public void genererPDF(ActionEvent ignored) throws FileNotFoundException {
    try {
        List<Reclamation> reclamations = table.getItems();

        if (reclamations != null && !reclamations.isEmpty()) {
            String filePath = "liste_reclamations.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Liste des Réclamations :"));

            for (Reclamation reclamation : reclamations) {
                document.add(new Paragraph("Titre : " + reclamation.getTitre()));
                document.add(new Paragraph("Description : " + reclamation.getDescription()));
                document.add(new Paragraph("Date : " + reclamation.getDate()));
                document.add(new Paragraph("Type : " + reclamation.getTypeRec())); // Utilisez la méthode appropriée pour obtenir le type
                document.add(new Paragraph("État : " + reclamation.getEtat().name())); // Utilisez la méthode pour obtenir l'état
                document.add(new Paragraph("\n"));
            }

            document.close();
            System.out.println("Le PDF a été généré avec succès.");

            // Ouvrez automatiquement le fichier PDF généré avec l'application par défaut
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(filePath));
            } else {
                System.out.println("L'ouverture automatique n'est pas prise en charge sur ce système.");
            }

        } else {
            System.out.println("La liste des réclamations est vide.");
        }
    } catch (DocumentException | IOException e) {
    }
}
@FXML
    public void Home(ActionEvent event) throws IOException {
        if (Home.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }

    @FXML
    private void GSD(ActionEvent event) throws IOException {
        if (GSD.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjoutSta.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }

    @FXML
    private void OFFRES(ActionEvent event) throws IOException {
        if (OFFRES.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjoutOff.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }

}

    @FXML
    private void LOGOUT(ActionEvent event) throws IOException {
        if (LOGOUT.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Signin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }

    @FXML
    private void DASHBOARD(ActionEvent event) throws IOException {
        if (DASHBOARD.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("DASHBOARDRECL.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }
}
}