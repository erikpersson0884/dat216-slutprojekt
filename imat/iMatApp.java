package imat;


import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class iMatApp extends Application {

    @FXML Button nextButton;

    @Override
    public void start(Stage stage) throws Exception {
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("imat/resources/iMat");
        
        Parent root = FXMLLoader.load(getClass().getResource("imat_app.fxml"), bundle);
        
        Scene scene = new Scene(root, 1280, 720);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }




    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
