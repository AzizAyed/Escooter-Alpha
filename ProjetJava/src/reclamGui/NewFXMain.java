package reclamGui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewFXMain extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    
public void start(Stage stage) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);

        // Make the stage transparent
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED); // Set the undecorated style
        stage.setTitle("mybike!");
        stage.setScene(scene);

        // Set event handlers for moving the window
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.show();
    } catch (IOException e) {
    }
}


    public static void main(String[] args) {
        launch(args);
    }
}
