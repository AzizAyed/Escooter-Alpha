/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aweld
 */
public class HomeController implements Initializable {

    @FXML
    private Button EXIT;
    private Button Reclamation;
    @FXML
    private Button GDS;
    @FXML
    private Button OFFRES;
    @FXML
    private Button LOGOUT;
    @FXML
    private Button BACKRECL;
    @FXML
    private Button GDC;

   
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

    void logout(ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("signin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    public void Reclamation(ActionEvent event) throws IOException {
        if (Reclamation.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjouterReclamation.fxml"));
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
    private void GDC(ActionEvent event) throws IOException {
         if (GDC.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }
}
}