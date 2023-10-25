package reclamGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Signup_main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        // You may add additional setup code here, if necessary
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Here, you can add methods to handle the sign-up process

    // For example, a method to handle user registration
    private void registerUser(String nom, String prenom, String cin, String email, String password) {
        // Implement the code to create a new user account
        // You might send the data to a server or store it in a database
    }
}
