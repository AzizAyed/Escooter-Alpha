package Interface;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.edu.esprit.entities.Personne;
import tn.edu.esprit.services.ServicePersonne;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminController implements Initializable {

    @FXML
    private TableView<Personne> tableview;
    @FXML
    private TableColumn<Personne, String> nomview;
    @FXML
    private TableColumn<Personne, String> prenomview;
    @FXML
    private TableColumn<Personne, String> emailview;
    @FXML
    private TextField nomsearch;
    @FXML
    private TextField prenomsearch;
    @FXML
    private Button deletebtn;
    @FXML
    private Button backbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the cell value factories for the columns
        nomview.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomview.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailview.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set the delete button event handler
        deletebtn.setOnAction(this::deletebtn);

        // Add listeners to the text fields
        nomsearch.textProperty().addListener((observable, oldValue, newValue) -> updateTableView());
        prenomsearch.textProperty().addListener((observable, oldValue, newValue) -> updateTableView());

        // Load and display data from the "personne" table
        initializeTableView();
    }

    private void initializeTableView() {
        // Load and display data from the "personne" table
        tableview.setItems(loadPersonneData());
    }

    private ObservableList<Personne> loadPersonneData() {
        try {
            ServicePersonne service = new ServicePersonne();
            List<Personne> personnes = service.getAll();
            return FXCollections.observableArrayList(personnes);
        } catch (Exception e) {
            showAlert("Error loading data: " + e.getMessage(), "Aucune ligne sélectionnée.");
            return FXCollections.observableArrayList();
        }
    }

    @FXML
    private void deletebtn(ActionEvent event) {
        Personne clickedPersonne = tableview.getSelectionModel().getSelectedItem();
        if (clickedPersonne == null) {
            showAlert("Erreur", "Aucune ligne sélectionnée.");
        } else {
            try {
                String nom = clickedPersonne.getNom();
                String prenom = clickedPersonne.getPrenom();

                ServicePersonne service = new ServicePersonne();
                service.supprimer(nom, prenom);
                
                // Refresh the TableView with the updated data
                updateTableView();

                showAlert("Réussi", "Personne supprimée");
                System.out.println("Personne Supprimée");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                Parent root = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void updateTableView() {
        String nom = nomsearch.getText().trim();
        String prenom = prenomsearch.getText().trim();

        if (!nom.isEmpty() || !prenom.isEmpty()) {
            try {
                ServicePersonne service = new ServicePersonne();
                List<Personne> searchResults = service.searchByNomAndPrenom(nom, prenom);
                tableview.setItems(FXCollections.observableArrayList(searchResults));
            } catch (Exception e) {
                showAlert("Error searching for Personnes: " + e.getMessage(), "Aucune ligne sélectionnée.");
            }
        } else {
            initializeTableView(); // Reload all records
        }
    }

    private void showAlert(String message, String aucune_ligne_selectionnee) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
   /* @FXML
    void backbtn(ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("Signin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }*/
}
