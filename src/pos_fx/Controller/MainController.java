package pos_fx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pos_fx.Generic.Utils;
import pos_fx.model.Model;

/**
 *
 * @author Eduardo Carbajal Reyes
 */
public class MainController implements Initializable {
    
    @FXML private Label lblFecha;
    @FXML private Label txtUsuario;
    @FXML private Label txtNombreUsuario;
    @FXML private Button btnAdmUsuarios;
    
    @FXML private Tab tabAdmon;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtIva;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCp;
    @FXML private TextField txtRfc;
    
    @FXML private RadioButton rdCancelarSCve;
    @FXML private RadioButton rdVerTotales;
    @FXML private RadioButton rdReimpresion;
    @FXML private RadioButton rdUsaExistVta;
    
    @FXML private RadioButton rdCambioCosto;
    @FXML private RadioButton rdCambioPrecios;
    @FXML private RadioButton rdCambioStatus;
    @FXML private RadioButton rdCargaMasiva;

    private String usuario, nombreUsuario;
    private Stage vtnMainInstance;
            
    private Model m = new Model();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
             lblFecha.setText(Utils.getDiaFecha());        
    }
    
    @FXML private void loadWindowAdmUsers() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/pos_fx/view/AdmUsuarios.fxml").openStream());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

     public void instance(Stage stage){
        vtnMainInstance = stage;
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        txtNombreUsuario.setText(getNombreUsuario());
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
        txtUsuario.setText(getUsuario());
    }
    
    @FXML public void cambiarUsuario() throws IOException{
        LoginController lc = new LoginController();
        lc.cambiarUsuario(vtnMainInstance);
    }
    
    @FXML public void loadInformation(Event e){
        if(e.getSource() == tabAdmon){
            try {
                ResultSet rs = m.getQueryResults("select * from informacion_general");
                while(rs.next()){
                    txtNombreEmpresa.setText(rs.getString(2));
                    txtDireccion.setText(rs.getString(3));
                    txtCp.setText(rs.getString(5));
                    txtRfc.setText(rs.getString(4));
                    txtIva.setText(rs.getString(6));
                    
                    
                }
                
                ResultSet rsig = m.getQueryResults("select * from permisos_globales");
                String cve_permiso, permitido;
                while(rsig.next()){
                    cve_permiso = rsig.getString(2).trim();
                    permitido = rsig.getString(4).trim();
                    
                    if(cve_permiso.contains("POS_cncl_sin_cve") && permitido.contains("1")){
                        rdCancelarSCve.setSelected(true);
                    }else if(cve_permiso.equals("POS_ver_totales") && permitido.equals("1")){
                        rdVerTotales.setSelected(true);
                    }else if(cve_permiso.equals("POS_permt_reimpresion") && permitido.equals("1")){
                        rdReimpresion.setSelected(true);
                    }else if(cve_permiso.equals("POS_usa_exist_vta") && permitido.equals("1")){
                        rdUsaExistVta.setSelected(true);
                    }else if(cve_permiso.contains("INV_camb_costo_usu") && permitido.contains("1")){
                        rdCambioCosto.setSelected(true);
                    }else if(cve_permiso.equals("INV_camb_prec_usu") && permitido.equals("1")){
                        rdCambioPrecios.setSelected(true);
                    }else if(cve_permiso.equals("INV_camb_status_prod_usu")&& permitido.equals("1")){
                        rdCambioStatus.setSelected(true);
                    }else if(cve_permiso.equals("INV_carga_masiva_usu") && permitido.equals("1")){
                        rdCargaMasiva.setSelected(true);
                    }
                    
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    
    @FXML public void activaPermiso(Event e){
        if(e.getSource() == rdCancelarSCve){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdCancelarSCve.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'POS_cncl_sin_cve'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdVerTotales){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdVerTotales.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'POS_ver_totales'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdReimpresion){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdReimpresion.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'POS_permt_reimpresion'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdUsaExistVta){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdUsaExistVta.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'POS_usa_exist_vta'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdCambioCosto){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdCambioCosto.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'INV_camb_costo_usu'";
            System.out.println(m.executeSQL(queryUpdate));
        }
    }
 
}
