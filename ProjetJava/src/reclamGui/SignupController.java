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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.edu.esprit.tools.DataSource;

public class SignupController implements Initializable {
    @FXML
    private Button signup_btn;
    @FXML
    private TextField signup_nom;
    @FXML
    private TextField signup_prenom;
    @FXML
    private TextField signup_cin;
    @FXML
    private TextField signup_email;
    @FXML
    private TextField signup_password;

    private Connection dbConnection;
    @FXML
    private Button signup_signin;
    @FXML
    private AnchorPane signup_form;

    public SignupController() {
        // Initialize the database connection
        dbConnection = DataSource.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSignupButtonAction() {
        String nom = signup_nom.getText();
        String prenom = signup_prenom.getText();
        String cin = signup_cin.getText();
        String email = signup_email.getText();
        String password = signup_password.getText();

        // Validate user input
        if (isValidInput(nom) && isValidInput(prenom) && isValidCIN(cin) && isValidEmail(email) && isValidPassword(password)) {
            if (userExists(email)) {
                showAlert("User with this email already exists. Please choose a different email.");
            } else if (userExistsByCIN(cin)) {
                showAlert("A user with the same CIN already exists. Please choose a different CIN.");
            } else {
                boolean registrationSuccess = registerUser(nom, prenom, cin, email, password);
                if (registrationSuccess) {
                    showAlert("Registration successful");
                    clearFields();
                } else {
                    showAlert("Registration failed. Please try again later.");
                }
            }
        } else {
            showAlert("Invalid input. Please check your data.");
        }
    }

    private boolean isValidInput(String input) {
        return input.matches("[a-zA-Z]+") && input.length() >= 2;
    }

    private boolean isValidCIN(String cin) {
        return cin.matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean userExists(String email) {
        try {
            String query = "SELECT * FROM personne WHERE email = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("User existence check failed: " + e.getMessage());
            return false;
        }
    }

    private boolean userExistsByCIN(String cin) {
        try {
            String query = "SELECT * FROM personne WHERE cin = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, cin);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("User existence check by CIN failed: " + e.getMessage());
            return false;
        }
    }

    private boolean registerUser(String nom, String prenom, String cin, String email, String password) {
        try {
            // For production, you should use a secure password hashing library (e.g., BCrypt) to hash passwords
            String hashedPassword = hashPassword(password);

            String insertQuery = "INSERT INTO personne (nom, prenom, cin, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, cin);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, hashedPassword);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("User registration failed: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        // In production, use a secure password hashing library (e.g., BCrypt) to hash passwords
        return password;
    }

    private void clearFields() {
        signup_nom.clear();
        signup_prenom.clear();
        signup_cin.clear();
        signup_email.clear();
        signup_password.clear();
    }

    public void switchForm(ActionEvent event) {
        // Implement switching to another form if needed
    }

    @FXML
    private void log(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("Signin.fxml"));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
