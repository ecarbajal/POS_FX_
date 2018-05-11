package pos_fx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import pos_fx.Entities.Producto;
import pos_fx.Entities.Producto_tabla_venta;
import pos_fx.Entities.Vendedor;
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
    
    @FXML private Tab tabInventarios;
    @FXML private Tab tabReportes;
    @FXML private Tab tabAdministracion;
    
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
    
    @FXML private TextField txtCveVendedor;
    @FXML private Label lblClaveVendedor;
    @FXML private Label lblNombreVendedor;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtDescripcionProducto;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtTotalPrroducto;
    @FXML private TextField txtCantidad;
    
    @FXML private TableView<Producto_tabla_venta> tblProductosVenta;
    @FXML private TableColumn<Producto_tabla_venta, String> clCodigo;
    @FXML private TableColumn<Producto_tabla_venta, String> clDescripcion;
    @FXML private TableColumn<Producto_tabla_venta, Integer> clCantidad;
    @FXML private TableColumn<Producto_tabla_venta, Double> clPrecioUnitario;
    @FXML private TableColumn<Producto_tabla_venta, Double> clTotal;
    
    private final ObservableList<Producto_tabla_venta> prodData = FXCollections.observableArrayList();
    
    

    private String usuario, nombreUsuario;
    private Stage vtnMainInstance;
            
    private Model m = new Model();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
             lblFecha.setText(Utils.getDiaFecha());   
             txtCveVendedor.setOnKeyPressed(k ->{
              final KeyCombination ENTER = new KeyCodeCombination(KeyCode.TAB);
            if (ENTER.match(k)) {
                loadSeller();
            }
        });
             
             txtCodigo.setOnKeyPressed(k ->{
              final KeyCombination ENTER = new KeyCodeCombination(KeyCode.TAB);
            if (ENTER.match(k)) {
                loadProductData();
            }
        });
             
             
        clCodigo.setCellValueFactory(new PropertyValueFactory<>("clave_producto"));
        clDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        clPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precio_unitario"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
             tblProductosVenta.setItems(prodData);
             
    }
    
    @FXML private void loadWindowAdmUsers() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/pos_fx/view/AdmUsuarios.fxml").openStream());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML private void loadWindowAdmSellers() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/pos_fx/view/AdmVendedores.fxml").openStream());
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
    
    public void allowView(){
            tabReportes.setDisable(true);
            tabAdministracion.setDisable(true);
            tabInventarios.setDisable(true);
    }
    
    @FXML public void cambiarUsuario() throws IOException{
        LoginController lc = new LoginController();
        lc.cambiarUsuario(vtnMainInstance);
    }
    
    @FXML public void loadInformation(Event e){
        if(e.getSource() == tabAdministracion){
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
        }else if(e.getSource() == rdCambioPrecios){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdCambioPrecios.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'INV_camb_prec_usu'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdCambioStatus){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdCambioStatus.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'INV_camb_status_prod_usu'";
            System.out.println(m.executeSQL(queryUpdate));
        }else if(e.getSource() == rdCargaMasiva){
            String queryUpdate = "UPDATE permisos_globales SET permitido = '"+((rdCargaMasiva.isSelected())? "1": "0")+
                    "' WHERE cve_valor = 'INV_carga_masiva_usu'";
            System.out.println(m.executeSQL(queryUpdate));
        }
    }
 
    private void loadSeller() {
        String cveVendedor = txtCveVendedor.getText().trim();
        String nombreVendedor = m.getSeller(cveVendedor);
        if(nombreVendedor != null && !nombreVendedor.equals("")){
            lblNombreVendedor.setText("Colaborador - "+nombreVendedor);
            lblClaveVendedor.setText(cveVendedor);
            txtCodigo.requestFocus();
        }else{
            lblNombreVendedor.setText("Colaborador no valido, reintente o consulte al administrador.");
            lblClaveVendedor.setText("Clave");
        }
    }
    
    private void loadProductData(){
        Producto p;
        Producto p2 = new Producto();
        p = p2.CreateProducto(txtCodigo.getText().trim());
        if(p != null){
            txtDescripcionProducto.setText(p.getDescripcion_producto());
            txtPrecioUnitario.setText(p.getPrecio1().toString());
            txtTotalPrroducto.setText(p.getPrecio1().toString());
            txtCantidad.setText("1");
            
             
            try {
                ResultSet cdrUsers = m.getQueryResults("select * from inventario;");
                while(cdrUsers.next()){
                    prodData.add(
                            new Producto_tabla_venta(
                                    p.getClave_producto(), 
                                    p.getDescripcion_producto(), 
                                    Integer.parseInt(txtCantidad.getText()),
                                    Double.parseDouble(txtPrecioUnitario.getText()), 
                                    Double.parseDouble(txtTotalPrroducto.getText())
                            ));
                    break;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AdmVendedoresController.class.getName()).log(Level.SEVERE, null, ex);
            }
    
            
            
        }else{
            txtDescripcionProducto.setText("No existe el producto ingresado");
            txtPrecioUnitario.setText("0");
            txtTotalPrroducto.setText("0");
            txtCantidad.setText("0");
            txtCodigo.requestFocus();
        }
        
    }
    
}
