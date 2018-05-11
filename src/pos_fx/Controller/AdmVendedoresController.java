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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pos_fx.Entities.Rol;
import pos_fx.Entities.Vendedor;
import pos_fx.Generic.Components;
import pos_fx.model.Model;

/**
 * FXML Controller class
 *
 * @author Eduardo Carbajal Reyes
 */
public class AdmVendedoresController implements Initializable {
    final Model m = new Model();
    private final ObservableList<Vendedor> usersData = FXCollections.observableArrayList();
    private final ObservableList<Rol> RoleData = FXCollections.observableArrayList();

    @FXML private TableView<Vendedor> tblUsers;
    @FXML private TableColumn<Vendedor, Integer> clId;
    @FXML private TableColumn<Vendedor, String> clCveAcceso;
    @FXML private TableColumn<Vendedor, String> clNombre;
    @FXML private TableColumn<Vendedor, Integer> clActivo;
    

    @FXML private TextField txtId;
    @FXML private TextField txtCveAcceso;
    @FXML private TextField txtNombre;
    @FXML private CheckBox chkActivo;
    
    @FXML private Button btnAceptar;
    @FXML private Button btnNuevoUsuario;
    @FXML private Button btnEliminarVendedor;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clId.setCellValueFactory(new PropertyValueFactory<>("id_vendedor"));
        clCveAcceso.setCellValueFactory(new PropertyValueFactory<>("cve_vendedor"));
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_vendedor"));
        clActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        
        tblUsers.setItems(usersData);
        cargarTablaUsuarios();
        
        btnAceptar.setDisable(true);
        btnEliminarVendedor.setDisable(true);

        
        tblUsers.setRowFactory(tv -> {
            TableRow<Vendedor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Vendedor vendedor = row.getItem();
                    txtId.setText(vendedor.getId_vendedor() + "");
                    txtCveAcceso.setText(vendedor.getCve_vendedor());
                    txtNombre.setText(vendedor.getNombre_vendedor());
                    chkActivo.setSelected(vendedor.getActivo() == 1);
                    btnAceptar.setDisable(false);
                    btnNuevoUsuario.setDisable(true);
                    btnEliminarVendedor.setDisable(false);
                }
            });
            return row;
        });
    }    

    private void cargarTablaUsuarios() {
        try {
            ResultSet cdrUsers = m.getQueryResults("select * from vendedores;");
            while(cdrUsers.next()){
                usersData.add(new Vendedor(
                        cdrUsers.getInt(1), 
                        cdrUsers.getString(2), 
                        cdrUsers.getString(3), 
                        cdrUsers.getInt(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdmVendedoresController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdmVendedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void agregarUsuario(){
        
        int activo = chkActivo.isSelected() ? 1 : 0; 
        String cveAcceso = txtCveAcceso.getText().trim();
        String nombre = txtNombre.getText().trim();
        
        if(m.validateExistVendedor(cveAcceso)){
            Components.Alert(Alert.AlertType.ERROR, "Registro duplicado", "El vendedor "+cveAcceso+" ya existe.", null);
        }else if((activo != 1 || activo != 0) && !cveAcceso.equals("") && !nombre.equals("")){
            String query = "INSERT INTO vendedores VALUES "
                    + "(null,'" + cveAcceso + "',"
                    + "'" + nombre + "',"
                    + activo + ");";
            //System.out.println(query);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardar vendedor");
            alert.setHeaderText(null);
            alert.setContentText(m.executeSQL(query));
            cleanForm();
            alert.show();

            tblUsers.getItems().clear();
            cargarTablaUsuarios();
            tblUsers.setItems(usersData);

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
        chkActivo.setSelected(false);
        btnNuevoUsuario.setDisable(false);
        btnAceptar.setDisable(true);
        btnEliminarVendedor.setDisable(true);
    }
    
    @FXML public void updateUser(){
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String vendedor = txtCveAcceso.getText().trim();
        String activo = chkActivo.isSelected() ? "1" : "0";
        
        if(!id.equals("") && !nombre.equals("")){
            String query = "update vendedores set "
                    + "nombre='"+nombre+"' , "
                    + "clave = '"+vendedor+"' , "
                    + "activo = '"+activo+"' "
                    + "where id_vendedor='"+id+"';";
            //System.out.println(query);
            
            String res = m.executeSQL(query);
            if (res.contains("correctamente")) {
                btnAceptar.setDisable(true);
                btnNuevoUsuario.setDisable(false);
                btnEliminarVendedor.setDisable(true);
                cleanForm();
                tblUsers.getItems().clear();
                 cargarTablaUsuarios();
                 tblUsers.setItems(usersData);
       

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText(res);
                cleanForm();
                alert.show();
                
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
    
     @FXML public void delete(){
        String id = txtId.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Está seguro de eliminar al usuario "+txtNombre.getText()+"?");
        alert.setContentText(null);
        alert.showAndWait();
        
        
        if (alert.getResult() == ButtonType.OK) {
            String query = "delete from vendedores where id_vendedor="+id;
            String answer = m.executeSQL(query);
            if(answer.contains("correctamente")){
                int selectedUser = tblUsers.getSelectionModel().getSelectedIndex();
                usersData.remove(selectedUser);
                Components.Alert(Alert.AlertType.INFORMATION, answer, "Eliminación de vendedor correcta", null);
                
            }else{
                Components.Alert(Alert.AlertType.ERROR, answer, "Eliminación de vendedor correcta", null);
            }
        } else {
            alert.close();
        }
    }
}
