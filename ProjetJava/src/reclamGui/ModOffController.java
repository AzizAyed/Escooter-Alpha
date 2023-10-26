/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

import animatefx.animation.BounceIn;
import entities.Offre;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceOffre;

/**
 * FXML Controller class
 *
 * @author Nazih
 */
public class ModOffController implements Initializable {

    @FXML
    private Button exit;
    @FXML
    private TableView<Offre> tabOff;
    @FXML
    private TableColumn<Offre, String> colNom;
    @FXML
    private TableColumn<Offre, Float> colPrix;
    @FXML
    private TableColumn<Offre, Integer> colDuree;
    @FXML
    private TextField txtNomOff;
    @FXML
    private TextField txtPriOff;
    @FXML
    private TextField txtRecOff;
    @FXML
    private Spinner<Integer> spiDurOff;
    @FXML
    private Button HOME;
    @FXML
    private Button GDS;
    @FXML
    private Button BACKRECL;
    @FXML
    private Button LOGOUT;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        valueFactory.setValue(1);
        spiDurOff.setValueFactory(valueFactory);
    
        colNom.setCellValueFactory(new PropertyValueFactory <> ("nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory <> ("prix"));
        colDuree.setCellValueFactory(new PropertyValueFactory <> ("duree"));
        ServiceOffre of = new ServiceOffre();
        List<Offre> lo = of.getAll();
        ObservableList<Offre> olo = FXCollections.observableArrayList(lo);
        tabOff.setItems(olo);
        
        txtRecOff.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    RechercherOff(null);
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }    

    @FXML
    public void EXIT (){
         System.exit(0);
        
    }

    @FXML
    private void SelectAff(MouseEvent event) {
        
        Offre clickedOffre = tabOff.getSelectionModel().getSelectedItem();
        txtNomOff.setText(String.valueOf(clickedOffre.getNom()));
        txtPriOff.setText(String.valueOf(clickedOffre.getPrix()));
        spiDurOff.getValueFactory().setValue(clickedOffre.getDuree());
    
    }

    @FXML
    private void RechercherOff(ActionEvent event) {
        String rc = txtRecOff.getText();
        ServiceOffre service = new ServiceOffre();
        List<Offre> of = service.getAll();
        ObservableList<Offre> olo = FXCollections.observableArrayList();

        for (Offre a : of) {
            if (a.getNom().toLowerCase().contains(rc) || String.valueOf(a.getPrix()).contains(rc) || String.valueOf(a.getDuree()).contains(rc)) {
                olo.add(a); // Add the matching Station to the ObservableList
            }
        }
        
        tabOff.setItems(olo);
        tabOff.refresh();
        
    }

    @FXML
    private void buttModOff(ActionEvent event) {
        Offre clickedOffre = tabOff.getSelectionModel().getSelectedItem();
        if (clickedOffre==null) {
            new BounceIn(tabOff).play();
        } else {
            if (txtNomOff.getText().isEmpty()) {
            new BounceIn(txtNomOff).play();
        } else {
                if (txtPriOff.getText().isEmpty()) {
            new BounceIn(txtPriOff).play();
        } else {    
                if (spiDurOff.getValue() == null) {
            new BounceIn(spiDurOff).play();
        } else {
                    if (txtPriOff.getText().matches("\\\\d{1,5}(.\\\\d{1,3})?")) {
            showAlert("Erreur", "Prix doit etre des chiffres separes par un '.'");
        } else {
            try{
                String idString = String.valueOf(clickedOffre.getId());
                int id = Integer.parseInt(idString);
                String nom = txtNomOff.getText();
                float prix = Float.parseFloat(txtPriOff.getText());
                int duree = spiDurOff.getValue();
                ServiceOffre so = new ServiceOffre();
                Offre st = new Offre (id,nom,prix,duree);
                so.modifier(st);
                List<Offre> lo = so.getAll();
                ObservableList<Offre> olo = FXCollections.observableArrayList(lo);
                tabOff.setItems(olo);
                tabOff.refresh();
                showAlert("Reussi", "Station modifiée");
                System.out.println("Station Modifiée");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                Parent root = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }}}}}}

    @FXML
    private void buttSupOff(ActionEvent event) {
        Offre clickedOffre = tabOff.getSelectionModel().getSelectedItem();
        if (clickedOffre==null) {
            new BounceIn(tabOff).play();
        } else {
            try{
                String idString = String.valueOf(clickedOffre.getId());
                int id = Integer.parseInt(idString);
                ServiceOffre so = new ServiceOffre();
                so.supprimer(id);
                List<Offre> lo = so.getAll();
                ObservableList<Offre> olo = FXCollections.observableArrayList(lo);
                tabOff.setItems(olo);
                tabOff.refresh();
                txtNomOff.setText("");
                txtPriOff.setText("");
                spiDurOff.getValueFactory().setValue(1);
                showAlert("Reussi", "Station supprimée");
                System.out.println("Station Supprimée");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                Parent root = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void fenAjouOff(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("AjoutOff.fxml"));

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("EScooter");

        currentStage.initModality(Modality.WINDOW_MODAL);


        currentStage.show();

    } catch (IOException e) {
            System.err.println(e.getMessage());
    }
    }

    @FXML
    public void Home(ActionEvent event) throws IOException {
        if (HOME.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }

    @FXML
        private void GDS(ActionEvent event) throws IOException {
        if (GDS.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjoutSta.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }

    @FXML
   public void BACKRECL(ActionEvent event) throws IOException {
        if (BACKRECL.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Backrecl.fxml"));
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
    }