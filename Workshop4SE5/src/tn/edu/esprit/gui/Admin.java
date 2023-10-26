/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nazih
 */
public class Admin extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjoutOff.fxml"));
            Scene scene = new Scene(root, 830, 575);
        
            primaryStage.setTitle("EScooter");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            //System.err.println(ECMAErrors.getMessage(STYLESHEET_MODENA, args));
        }
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
