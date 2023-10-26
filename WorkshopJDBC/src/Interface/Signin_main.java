package Interface; 




import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.StageStyle;

public class Signin_main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Button login_signup = new Button("Sign Up");
        login_signup.setOnAction((ActionEvent event) -> {
            try {
                Parent signupRoot = FXMLLoader.load(getClass().getResource("signup.fxml"));
                Scene signupScene = new Scene(signupRoot);
                stage.setScene(signupScene);
                
            } catch (IOException e) {
            }
        });


        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
