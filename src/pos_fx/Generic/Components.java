/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.Generic;

import javafx.scene.control.Alert;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Components {
    
    public static void Alert(Alert.AlertType alertType, String title, String headerText, String bodyContent){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(bodyContent);
        alert.showAndWait();
    }

}
