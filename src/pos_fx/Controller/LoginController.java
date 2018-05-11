package pos_fx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import pos_fx.POS_FX;
import pos_fx.model.Model;

/**
 * FXML Controller class
 *
 * @author Eduardo Carbajal
 */
public class LoginController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasena;
    @FXML private Button btnAcceso;
    
    private Model m;
    private Stage vtnLogin;
    private Stage vtnMain;

    public LoginController() {
        this.m = new Model();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }  
    
    public void instance(Stage stage){
        vtnLogin = stage;
    }
    
    public void cambiarUsuario(Stage stage) throws IOException{
        vtnMain = stage;
        vtnMain.close();
        pos_fx.POS_FX p = new POS_FX();
        p.loadLoginWindows(stage);
    }
    
    public void acceder(Event event){
        try {
            String usuario = txtUsuario.getText().trim();
            String psw = txtContrasena.getText().trim();
            if(usuario.equals("") && psw.equals("")){
                JOptionPane.showMessageDialog(null, "Debes llenar los campos");
            }else {
                ResultSet rsu = m.loadConfig(usuario, psw);
                String user = null, role = null, active = null, username = null;
                while (rsu.next()) {
                    user = rsu.getString("cve_usuario");
                    username = rsu.getString("nombre_usuario");
                    role = rsu.getString("rol");
                    active = rsu.getString("activo");
                }
                if(user != null && username != null && role != null && active != null){//Si existe el usuario
                   if(active.equals("1")){//Validar si esta activo
                        if(role.equals("1")){//es administrador
                        FXMLLoader loader = new FXMLLoader();
                        Parent root = loader.load(getClass().getResource("/pos_fx/view/Main.fxml").openStream());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                        MainController mc = loader.getController();
                        mc.setUsuario("Administrador del sistema");
                        mc.setNombreUsuario(username);
                        mc.instance(stage);
                        vtnLogin.close();
                        
                    }else if(role.equals("2")){//Aqui poner los otros roles
                        FXMLLoader loader = new FXMLLoader();
                        Parent root = loader.load(getClass().getResource("/pos_fx/view/Main.fxml").openStream());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                        MainController mc = loader.getController();
                        mc.setUsuario("Usuario general");
                        mc.setNombreUsuario(username);
                        mc.instance(stage);
                        mc.allowView();
                        vtnLogin.close();
                    }
                       
                   }else{//Esta inactivo
                        Alert a = new Alert(Alert.AlertType.ERROR, "El usuario ingresado esta inactivo, contacta al administrador.");
                        a.show();
                        txtUsuario.setText("");
                        txtContrasena.setText("");
                        txtUsuario.requestFocus();
                   }
                    
                       

            }else{
                    Alert a = new Alert(Alert.AlertType.ERROR, "Los datos ingresados son incorrectos");
                    a.show();
                    txtUsuario.setText("");
                    txtContrasena.setText("");
                    txtUsuario.requestFocus();
                }
            }
            
            
            
            
        } catch (IOException ex) {
            System.out.println("[ERROR]    IO ERROR: "+ex);
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[ERROR]    SQL ERROR: "+ex);
        }catch (NullPointerException ex){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[ERROR]    NPE ERROR: "+ex);
        }
    }
    
}
