/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

import animatefx.animation.BounceIn;
import entities.Station;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceStation;

/**
 * FXML Controller class
 *
 * @author aweld
 */
public class AjoutStaController implements Initializable {

    @FXML
    private Button EXIT;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private Button HOME;
    @FXML
    private Button OFFRES;
    @FXML
    private Button BACKRECL;
    @FXML
    private Button LOGOUT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    

    @FXML
    public void EXIT (){
        System.exit(0);   
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void textfieldDesign1(MouseEvent event) {
    }

    @FXML
    private void AjouterStation(ActionEvent event) {
        if (txtNom.getText().isEmpty()) {
            new BounceIn(txtNom).play();
        } else {
            if (txtAdresse.getText().isEmpty()) {
                new BounceIn(txtAdresse).play();
            } else {
                try {
                    String nom = txtNom.getText();
                    String adresse = txtAdresse.getText();
                    
                    // Add the station to the list (assuming your ServiceStation class works correctly)
                    ServiceStation ss = new ServiceStation();
                    Station st = new Station(nom, adresse);
                    ss.ajouter(st);
                    showAlert("Reussi", "La station a ete ajoutee.");
                    System.out.println("Station Ajoutée");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }       
        }
    }

    @FXML
    private void fenAff(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("ModSta.fxml"));

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
    private void HOME(ActionEvent event) throws IOException {
        if (HOME.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Home.fxml"));
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
