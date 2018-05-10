/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.DB;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class ScriptDB {
    
    public static String getTblInfoGeneral(){
        
        String query = "create table IF NOT EXISTS informacion_general(\n" +
                "id_info int AUTO_INCREMENT,\n" +
                "nombre_empresa varchar(50) NOT NULL,\n" +
                "direccion varchar(120) NOT NULL,\n" +
                "cod_pos int NOT NULL,\n" +
                "rfc varchar(16) NOT NULL,\n" +
                "iva double NOT NULL,\n" +
                "\n" +
                "PRIMARY KEY (id_info)\n" +
                ");";
        
        return query;
    }
    
    public static String getTblUsuarios(){
        String query = "create table IF NOT EXISTS usuarios(\n" +
                "id_usuario int AUTO_INCREMENT,\n" +
                "cve_usuario varchar(10) NOT NULL,\n" +
                "nombre_usuario varchar(60) NOT NULL,\n" +
                "psw_usuario varchar(20) NOT NULL,\n" +
                "rol int NOT NULL,\n" +
                "activo int NOT NULL,\n" +
                "\n" +
                "PRIMARY KEY (id_usuario)\n" +
                ");";
        
        return query;
    }
    
    public static String getTblRoles(){
        String query = "create table IF NOT EXISTS roles(\n" +
                "id_rol int AUTO_INCREMENT,\n" +
                "cve_rol int NOT NULL,\n" +
                "rol varchar(20) NOT NULL,\n" +
                "PRIMARY KEY (id_rol)\n" +
                ");";
        
        return query;
    }
    
    public static String getDefaultUser(){
        String query = "MERGE INTO usuarios "
                + " VALUES (null,'admin','Administrador del sistema.','a1b2c3',1,1)";
        return query;
    }
    
    public static String getDefaultRoles(){
        String query = "MERGE INTO roles VALUES (null,1,'admin'),(null,2,'gral')";
        return query;
    }

}
