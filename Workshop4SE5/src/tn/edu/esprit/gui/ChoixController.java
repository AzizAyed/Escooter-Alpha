/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import tn.edu.esprit.entities.Station;
import tn.edu.esprit.services.ServiceStation;

/**
 * FXML Controller class
 *
 * @author Nazih
 */
public class ChoixController implements Initializable {

    @FXML
    private ChoiceBox comSta;
    @FXML
    private Label labAff;

    /**
     * Initializes the controller class.
     * @param event
     */
    
    @FXML
    public void SelectChoix(ActionEvent event){
        String s = comSta.getSelectionModel().getSelectedItem().toString();
        labAff.setText(s);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceStation service = new ServiceStation();
        List<Station> st = service.getAll();
        ObservableList<Station> ols = FXCollections.observableArrayList(st);
        comSta.setItems(ols);
    }
    
}
