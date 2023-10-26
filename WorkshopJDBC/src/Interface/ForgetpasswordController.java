package Interface;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.edu.esprit.tools.DataSource;

public class ForgetpasswordController implements Initializable {
    @FXML
    private TextField usernametxt;

    @FXML
    private TextField nametxt;

    @FXML
    private TextField answertxt;

    @FXML
    private TextField passtxt;

    @FXML
    private Button backBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button getpswBtn;

    @FXML
    private AnchorPane Forgetpassword_form;

    public ForgetpasswordController() {
        // Initialize the database connection
        
    }
@FXML
void searchname(ActionEvent event) throws SQLException {
    
        String nom = usernametxt.getText().trim();

        if (!isValidInput(nom)) {
            showAlert("Invalid input. Please check your data.");
            return;
        }

            if (nom.isEmpty()) {
                 showAlert("Please insert username");
                 return;
            }

        String sql = "SELECT nom FROM personne WHERE nom=?";
        Connection con = DataSource.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
                nametxt.setText(rs.getString(1));
            } else {
                showAlert("Error: Username is incorrect");
            return;
        }

    
}


  @FXML
void retrievePsw(ActionEvent event) throws SQLException {
    if (isValidCIN(answertxt.getText())) {
        String cin = answertxt.getText();
        String sql = "SELECT password, nom FROM personne WHERE cin=?";
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
         {
            con = DataSource.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cin);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String password = rs.getString("password");
                String nom = rs.getString("nom");
                if (nom.equals(nametxt.getText())) {
                    passtxt.setText(password);
                } else {
                    showAlert("Answer is wrong."); 
                    return;
                }
            } else {
                showAlert("CIN not found in the database.");
                return;
            }
            
        }  
         
    } else {
        showAlert("CIN is invalid. Please enter an 8-digit number.");
        return;
    }
}


private void closeResource(AutoCloseable resource) {
    if (resource != null) {
        try {
            resource.close();
        } catch (Exception ex) {
            // Handle or log the exception as needed
        }
    }
}



    @FXML
    void backLogin(ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("Signin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty();
    }

    private boolean isValidCIN(String cin) {
        return cin.matches("^\\d{8}$");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code
    }
}
