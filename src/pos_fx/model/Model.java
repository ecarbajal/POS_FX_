/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_fx.DB.DBConnection;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Model {
    
    final DBConnection dbc;
    
    public Model(){
            dbc = new DBConnection();
            dbc.EstablecerConexion();
    }
    
    public ResultSet loadConfig(String cveUsuario, String psw){
        boolean validation = false;
        ResultSet rs = getQueryResults("SELECT cve_usuario, nombre_usuario, psw_usuario, rol, activo FROM usuarios WHERE cve_usuario='"+cveUsuario+"' and psw_usuario='"+psw+"';");

        return rs;
    }
    
    public ResultSet getQueryResults(String query){
        ResultSet rs = null;
        try {
            
            rs = dbc.EjecutarSentencia(query);
            
        } catch (SQLException ex) {
            System.out.println("[ERROR] SQL error: "+ex);
        }
        return rs;
    }
    
    public String executeSQL(String query){
        String result;
        try {
            dbc.ejecutarSQL(query);
            result = "Registro almacenado correctamente.";
        } catch (SQLException ex) {
            result = "Ocurrió un problema al guardar el registro, reintente por favor.";
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
    public boolean validateExistUser(String cveUsuario){
        ResultSet rs = getQueryResults("select cve_usuario from usuarios where cve_usuario='"+cveUsuario+"'");
        String cve = null;
        boolean exist = false;
        try {
            while (rs.next()) {
                cve = rs.getString("cve_usuario");
            }
            
         exist = (!cve.equals("") && cve != null);   
            
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException npe){
            exist = false;
        }
        return exist;
    }
    
     public boolean validateExistVendedor(String cveUsuario){
        ResultSet rs = getQueryResults("select clave from vendedores where clave='"+cveUsuario+"'");
        String cve = null;
        boolean exist = false;
        try {
            while (rs.next()) {
                cve = rs.getString("clave");
            }
            
         exist = (!cve.equals("") && cve != null);   
            
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException npe){
            exist = false;
        }
        return exist;
    }
     
      public String getSeller(String cveVendedor){
        ResultSet rs = getQueryResults("select nombre from vendedores where clave='"+cveVendedor+"'");
        String cve = null;
        try {
            while (rs.next()) {
                cve = rs.getString("nombre");
            }  
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException npe){
        }
        return cve;
    }
    
    

}
