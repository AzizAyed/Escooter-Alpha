package reclamGui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin_main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load the FXML layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Set the title for the window
            stage.setTitle("Admin Application");

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the window
            stage.show();
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
