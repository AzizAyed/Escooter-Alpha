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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceOffre;

/**
 * FXML Controller class
 *
 * @author aweld
 */
public class AjoutOffController implements Initializable {

    @FXML
    private Button EXIT;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPri;
    @FXML
    private Spinner<Integer> spiDurOff;
    @FXML
    private Button Home;
    @FXML
    private Button BACKRECL;
    @FXML
    private Button LOGOUT;
    @FXML
    private Button GDS;

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
    private void textfieldDesign1(MouseEvent event) {
    }

    @FXML
    private void AjouterOffre(ActionEvent event) {
        if (txtNom.getText().isEmpty()) {
            new BounceIn(txtNom).play();
        } else {
            if (txtPri.getText().isEmpty()) {
                new BounceIn(txtPri).play();
        } else {
            if (!txtPri.getText().matches("\\d{1,5}(\\.\\d{1,3})?")) {
                new BounceIn(txtPri).play();
                showAlert("Erreur", "Le prix doit être un nombre valide separé par un '.'");
            } else {
                try {
                    String nom = txtNom.getText();
                    float prix = Float.parseFloat(txtPri.getText());
                    int duree = spiDurOff.getValue();
                    ServiceOffre so = new ServiceOffre();
                    Offre of = new Offre(nom, prix, duree);
                    so.ajouter(of);
                    showAlert("Reussi", "L'offre a ete ajoutee.");
                    System.out.println("Offre Ajoutée");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }}       
        }
    }

    @FXML
    private void fenAffOff(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("ModOff.fxml"));

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
    private void Home(ActionEvent event) throws IOException {
                if (Home.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Home.fxml"));
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
}

    