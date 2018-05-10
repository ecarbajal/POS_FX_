/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_fx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pos_fx.Controller.LoginController;

/**
 *
 * @author Eduardo Carbajal 
 */
public class POS_FX extends Application {
    
    @Override
    public void start(Stage stage) throws IOException{
        loadLoginWindows(stage);
      
    }
    
    public void loadLoginWindows(Stage stage) throws IOException{
          FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("view/Login.fxml").openStream());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        LoginController lc = loader.getController();
        lc.instance(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
