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
public class Usuario {
    
    private final IntegerProperty id_usuario;
    private final StringProperty cve_usuario;
    private final StringProperty nombre_usuario;
    private final StringProperty psw_usuario;
    private final IntegerProperty rol;
    private final IntegerProperty activo;
    

    
    public Usuario(int id_usuario, String cve_usuario, String nombre_usuario, String psw_usuario, int rol, int activo){
        this.id_usuario = new SimpleIntegerProperty(id_usuario);
        this.cve_usuario = new SimpleStringProperty(cve_usuario);
        this.nombre_usuario = new SimpleStringProperty(nombre_usuario);
        this.psw_usuario = new SimpleStringProperty(psw_usuario);
        this.rol = new SimpleIntegerProperty(rol);
        this.activo = new SimpleIntegerProperty(activo);
    }

    public void setId_usuario(int id_usuario){
        this.id_usuario.set(id_usuario);
    }
    
    public void setCve_usuario(String cve_usuario){
        this.cve_usuario.set(cve_usuario);
    }
    
    public void setNombre_usuario(String nombre_usuario){
        this.nombre_usuario.set(nombre_usuario);
    }
    
    public void setPsw_usuario(String psw_usuario){
        this.psw_usuario.set(psw_usuario);
    }
    
    public void setRol(int rol) {
        this.rol.set(rol);
    }
    
    public void setActivo(int activo) {
        this.rol.set(activo);
    }



    
    
    
    
    
    public int getId_usuario() {
        return id_usuario.get();
    }

    public String getCve_usuario() {
        return cve_usuario.get();
    }

    public String getNombre_usuario() {
        return nombre_usuario.get();
    }

    public String getPsw_usuario() {
        return psw_usuario.get();
    }

    public int getRol() {
        return rol.get();
    }

    public int getActivo() {
        return activo.get();
    }
    
    

}
