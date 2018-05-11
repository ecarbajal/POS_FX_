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
public class Vendedor {
    
    private final IntegerProperty id_vendedor;
    private final StringProperty clave;
    private final StringProperty nombre;
    private final IntegerProperty activo;
    

    
    public Vendedor(int id_vendedor, String cve_vendedor, String nombre_vendedor, int activo){
        this.id_vendedor = new SimpleIntegerProperty(id_vendedor);
        this.clave = new SimpleStringProperty(cve_vendedor);
        this.nombre = new SimpleStringProperty(nombre_vendedor);
        this.activo = new SimpleIntegerProperty(activo);
    }

    public void setId_vendedor(int id_vendedor){
        this.id_vendedor.set(id_vendedor);
    }
    
    public void setCve_vendedor(String cve_vendedor){
        this.clave.set(cve_vendedor);
    }
    
    public void setNombre_vendedor(String nombre_vendedor){
        this.nombre.set(nombre_vendedor);
    }


    
    
    
    
    public int getId_vendedor() {
        return id_vendedor.get();
    }

    public String getCve_vendedor() {
        return clave.get();
    }

    public String getNombre_vendedor() {
        return nombre.get();
    }

    public int getActivo() {
        return activo.get();
    }
    
    

}
