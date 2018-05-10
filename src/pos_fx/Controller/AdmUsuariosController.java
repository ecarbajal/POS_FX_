/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_fx.Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import pos_fx.Entities.Rol;
import pos_fx.Entities.Usuario;
import pos_fx.model.Model;

/**
 * FXML Controller class
 *
 * @author Eduardo Carbajal Reyes
 */
public class AdmUsuariosController implements Initializable {
    final Model m = new Model();
    private final ObservableList<Usuario> usersData = FXCollections.observableArrayList();
    private final ObservableList<Rol> RoleData = FXCollections.observableArrayList();

    @FXML private TableView<Usuario> tblUsers;
    @FXML private TableColumn<Usuario, Integer> clId;
    @FXML private TableColumn<Usuario, String> clCveAcceso;
    @FXML private TableColumn<Usuario, String> clNombre;
    @FXML private TableColumn<Usuario, String> clContrasena;
    @FXML private TableColumn<Usuario, Integer> clRol;
    @FXML private TableColumn<Usuario, Integer> clActivo;
    
    @FXML private ComboBox<Rol> cmbRoles;
    @FXML private TextField txtId;
    @FXML private TextField txtCveAcceso;
    @FXML private TextField txtNombre;
    @FXML private TextField txtContrasena;
    @FXML private CheckBox chkActivo;
    
    @FXML private Button btnAceptar;
    @FXML private Button btnNuevoUsuario;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clId.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        clCveAcceso.setCellValueFactory(new PropertyValueFactory<>("cve_usuario"));
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_usuario"));
        clContrasena.setCellValueFactory(new PropertyValueFactory<>("psw_usuario"));
        clRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        clActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        
        tblUsers.setItems(usersData);
        cargarTablaUsuarios();
        
        btnAceptar.setDisable(true);
        
        cmbRoles.setItems(RoleData);
        cmbRoles.setConverter(new StringConverter<Rol>() {
            @Override
            public String toString(Rol o) {
                return o.getRol().get();
            }

            @Override
            public Rol fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        cargarComboRoles();
        
        tblUsers.setRowFactory(tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Usuario usuario = row.getItem();
                    txtId.setText(usuario.getId_usuario() + "");
                    txtCveAcceso.setText(usuario.getCve_usuario());
                    txtNombre.setText(usuario.getNombre_usuario());
                    txtContrasena.setText(usuario.getPsw_usuario());
                    chkActivo.setSelected(usuario.getActivo() == 1);
                    cmbRoles.getSelectionModel().select(usuario.getRol());
                    btnAceptar.setDisable(false);
                }
            });
            return row;
        });
    }    

    private void cargarTablaUsuarios() {
        try {
            ResultSet cdrUsers = m.getQueryResults("select * from usuarios;");
            while(cdrUsers.next()){
                usersData.add(new Usuario(
                        cdrUsers.getInt(1), 
                        cdrUsers.getString(2), 
                        cdrUsers.getString(3), 
                        cdrUsers.getString(4), 
                        cdrUsers.getInt(5), 
                        cdrUsers.getInt(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdmUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarComboRoles() {
        try {
            ResultSet cdrRole = m.getQueryResults("select * from roles");
            RoleData.add(new Rol(0, 0, "Seleccione un elemento"));
            while (cdrRole.next()) {
                RoleData.add(new Rol(
                        cdrRole.getInt(1), 
                        cdrRole.getInt(2),
                        cdrRole.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdmUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void agregarUsuario(){
        
        int activo = chkActivo.isSelected() ? 1 : 0; 
        String cveAcceso = txtCveAcceso.getText().trim();
        String nombre = txtNombre.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        int rol = cmbRoles.getSelectionModel().getSelectedItem().getCve_rol().getValue();
        
        if((activo != 1 || activo != 0) && !cveAcceso.equals("") && !nombre.equals("") && !contrasena.equals("") && rol != 0){
            String query = "INSERT INTO usuarios values "
                    + "(null,'" + cveAcceso + "',"
                    + "'" + nombre + "',"
                    + "'" + contrasena + "',"
                    + "" + rol + ","
                    + activo + ");";
            
//Validar si ya existe el usuario aqui: 
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardar usuario");
            alert.setHeaderText(null);
            alert.setContentText(m.executeSQL(query));
            cleanForm();
            alert.show();
            tblUsers.refresh();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡ERROR!");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar algun valor en todos los campos.");
            alert.show();
        }
    }
    
    @FXML public void cleanForm(){
        txtId.setText("");
        txtCveAcceso.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");
        cmbRoles.getSelectionModel().select(0);
        chkActivo.setSelected(false);
    }
    
    @FXML public void updateUser(){
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String usuario = txtCveAcceso.getText().trim();
        String psw = txtContrasena.getText().trim();
        String cmbRol = cmbRoles.getSelectionModel().getSelectedItem().getCve_rol().getValue().toString();
        String activo = chkActivo.isSelected() ? "1" : "0";
        
        if(!id.equals("") && !nombre.equals("") && !psw.equals("") && !cmbRol.equals("") && !cmbRol.equals("0")){
            String query = "update usuarios set "
                    + "nombre_usuario='"+nombre+"' , "
                    + "cve_usuario = '"+usuario+"' , "
                    + "psw_usuario = '"+psw+"' , "
                    + "rol = '"+cmbRol+"' ,"
                    + "activo = '"+activo+"' "
                    + "where id_usuario='"+id+"';";
            //System.out.println(query);
            
            String res = m.executeSQL(query);
            if (res.contains("correctamente")) {
                btnAceptar.setDisable(true);
                cleanForm();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText(res);
                cleanForm();
                alert.show();
                tblUsers.refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("¡ERROR!");
                alert.setHeaderText(null);
                alert.setContentText(res);
                alert.show();
            }
            
           
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡ERROR!");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar algun valor en todos los campos.");
            alert.show();
        }
        
    }
}
