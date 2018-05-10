/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Rol {
    private final IntegerProperty id_rol;
    private final IntegerProperty cve_rol;
    private final StringProperty rol;
    
    public Rol(int id_rol, int cve_rol, String rol){
        this.id_rol = new SimpleIntegerProperty(id_rol);
        this.cve_rol = new SimpleIntegerProperty(cve_rol);
        this.rol = new SimpleStringProperty(rol);
    }
    
    public void setId_rol(int id_rol){
        this.id_rol.set(id_rol);
    }
    
     public void setCve_rol(int cve_rol){
        this.cve_rol.set(cve_rol);
    }
     
      public void setRol(String rol){
        this.rol.set(rol);
    }

    public IntegerProperty getId_rol() {
        return id_rol;
    }

    public IntegerProperty getCve_rol() {
        return cve_rol;
    }

    public StringProperty getRol() {
        return rol;
    }
    
    

}
