/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.DB;

import java.sql.SQLException;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class initialConfigDB {
    
    public static void main(String args[]){ 
        initialConfigDB icdb = new initialConfigDB();
        icdb.loadInitialConfig();
    }
    
    public void loadInitialConfig(){
            try{
                DBConnection dbc = new DBConnection();
                dbc.EstablecerConexion();
                
                dbc.ejecutarSQL(ScriptDB.getTblInfoGeneral());
                System.out.println("[OK]    Información general.");
                dbc.ejecutarSQL(ScriptDB.getTblUsuarios());
                System.out.println("[OK]    Usuarios.");
                dbc.ejecutarSQL(ScriptDB.getTblRoles());
                System.out.println("[OK]    Roles.");
                dbc.ejecutarSQL(ScriptDB.getDefaultUser());
                System.out.println("[OK]    Default user.");
                dbc.ejecutarSQL(ScriptDB.getDefaultRoles());
                System.out.println("[OK]    Default rol.");
                
            } catch (SQLException ex) {
                
            System.out.println("[ERROR]    SQL ERROR: "+ex);
            }

        
        
    
    }

}
