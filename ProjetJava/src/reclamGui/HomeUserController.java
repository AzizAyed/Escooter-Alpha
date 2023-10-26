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
public class HomeUserController implements Initializable {

    @FXML
    private Button EXIT;
    @FXML
    private Button OFFRES;
    @FXML
    private Button RECLA;
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
    private void EXIT(ActionEvent event) {
        System.exit(0);
    }

    @FXML
  private void OFFRES(ActionEvent event) throws IOException {
        if (OFFRES.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("ChoixOffres.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    
}

    @FXML
    private void RECLA(ActionEvent event) throws IOException {
        if (RECLA.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjouterReclamation.fxml"));
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
