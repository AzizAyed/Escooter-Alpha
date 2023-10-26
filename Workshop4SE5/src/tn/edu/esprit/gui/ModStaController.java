/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import animatefx.animation.BounceIn;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Station;
import tn.edu.esprit.services.ServiceStation;

/**
 * FXML Controller class
 *
 * @author Nazih
 */
public class ModStaController implements Initializable {

    @FXML
    private Button exit;
    @FXML
    private TableView<Station> tabSta;
    @FXML
    private TableColumn<Station, String> colNom;
    @FXML
    private TableColumn<Station, String> colAdresse;
    @FXML
    private TextField txtNomAff;
    @FXML
    private TextField txtAdrAff;
    @FXML
    private TextField txtRechSta;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNom.setCellValueFactory(new PropertyValueFactory <> ("nom"));
        colAdresse.setCellValueFactory(new PropertyValueFactory <> ("adresse"));
        ServiceStation ss = new ServiceStation();
        List<Station> ls = ss.getAll();
        ObservableList<Station> ols = FXCollections.observableArrayList(ls);
        tabSta.setItems(ols);
        
        txtRechSta.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Rechercher(null);
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
    private void Rechercher(ActionEvent event) {
        String rc = txtRechSta.getText();
        ServiceStation service = new ServiceStation();
        List<Station> st = service.getAll();
        ObservableList<Station> ols = FXCollections.observableArrayList(); // Assuming you want to create an ObservableList

        for (Station a : st) {
            if (a.getNom().toLowerCase().contains(rc) || a.getAdresse().toLowerCase().contains(rc)) {
                ols.add(a); // Add the matching Station to the ObservableList
            }
        }
        
        tabSta.setItems(ols);
        tabSta.refresh();
        
    }

    @FXML
    private void buttModSta(ActionEvent event) {
        Station clickedStation = tabSta.getSelectionModel().getSelectedItem();
        if (clickedStation==null) {   
        new BounceIn(tabSta).play();
        } else {
            if (txtNomAff.getText().isEmpty()) {
            new BounceIn(txtNomAff).play();
        } else {
                if (txtAdrAff.getText().isEmpty()) {
                new BounceIn(txtAdrAff).play();
        } else {
            try{
                String idString = String.valueOf(clickedStation.getId());
                int id = Integer.parseInt(idString);
                String nom=txtNomAff.getText();
                String adresse=txtAdrAff.getText();
                ServiceStation ss = new ServiceStation();
                Station st = new Station (id,nom,adresse);
                ss.modifier(st);
                List<Station> ls = ss.getAll();
                ObservableList<Station> ols = FXCollections.observableArrayList(ls);
                tabSta.setItems(ols);
                tabSta.refresh();
                showAlert("Reussi", "Station modifiée");
                System.out.println("Station Modifiée");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                Parent root = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }}}}

    @FXML
    private void buttSupSta(ActionEvent event) {
        Station clickedStation = tabSta.getSelectionModel().getSelectedItem();
        if (clickedStation==null) {
            new BounceIn(tabSta).play();
        } else {
            try{
                String idString = String.valueOf(clickedStation.getId());
                int id = Integer.parseInt(idString);
                ServiceStation ss = new ServiceStation();
                ss.supprimer(id);
                List<Station> ls = ss.getAll();
                ObservableList<Station> ols = FXCollections.observableArrayList(ls);
                tabSta.setItems(ols);
                tabSta.refresh();
                txtNomAff.setText("");
                txtAdrAff.setText("");
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
    private void fenAjou(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("AjoutSta.fxml"));

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
    private void SelectAff(MouseEvent event) {
        Station clickedStation = tabSta.getSelectionModel().getSelectedItem();
        txtNomAff.setText(String.valueOf(clickedStation.getNom()));
        txtAdrAff.setText(String.valueOf(clickedStation.getAdresse()));
    }

    @FXML
    private void fenAjoutOff(ActionEvent event) {
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
    
}
