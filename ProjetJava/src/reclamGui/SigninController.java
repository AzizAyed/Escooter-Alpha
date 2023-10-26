package reclamGui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.DataSource;

public class SigninController implements Initializable {

    @FXML
    private Button login_btn;
    @FXML
    private TextField login_username;
    @FXML
    private TextField login_password;
    @FXML
    private Button login_signup;

    private final Connection dbConnection;
    @FXML
    private AnchorPane signin_form;
    @FXML
    private Hyperlink forgetpassword;
    private Button Home;
    @FXML
    private Button EXIT;

    public SigninController() {
        // Initialize the database connection
        dbConnection = DataSource.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String nom = login_username.getText();
        String password = login_password.getText();

        boolean validInput = isValidInput(nom) && isValidInput(password);
        boolean loginSuccess = validInput ? authenticateUser(nom, password) : false;

        if (loginSuccess) {
            if (nom.equals("ramzi") && password.equals("azerqsdf")) {
                openFXML("Home.fxml", event);
            } else {
                openFXML("Homeuser.fxml", event);
            }
        } else {
            showAlert("Login failed. Please check your information.");
        }
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty();
    }

    private boolean authenticateUser(String nom, String password) {
        try {
            String query = "SELECT * FROM personne WHERE nom = ? AND password = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void log(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Forget(ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("Forgetpassword.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    } 
     
    void login_btn (ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

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
    private void EXIT(ActionEvent event) {
        System.exit(0);
    }

    private void openFXML(String fxmlFile, ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
