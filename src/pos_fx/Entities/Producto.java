/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_fx.model.Model;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Producto {
    
    public Producto(){
        
    }

    /**
     * @return the clave_producto
     */
    public String getClave_producto() {
        return clave_producto;
    }

    /**
     * @param clave_producto the clave_producto to set
     */
    public void setClave_producto(String clave_producto) {
        this.clave_producto = clave_producto;
    }
    
    Model m = new Model();

    /**
     * @return the id_inventario
     */
    public int getId_inventario() {
        return id_inventario;
    }

    /**
     * @param id_inventario the id_inventario to set
     */
    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    /**
     * @return the descripcion_producto
     */
    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    /**
     * @param descripcion_producto the descripcion_producto to set
     */
    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    /**
     * @return the costo_producto
     */
    public Double getCosto_producto() {
        return costo_producto;
    }

    /**
     * @param costo_producto the costo_producto to set
     */
    public void setCosto_producto(Double costo_producto) {
        this.costo_producto = costo_producto;
    }

    /**
     * @return the precio1
     */
    public Double getPrecio1() {
        return precio1;
    }

    /**
     * @param precio1 the precio1 to set
     */
    public void setPrecio1(Double precio1) {
        this.precio1 = precio1;
    }

    /**
     * @return the precio2
     */
    public Double getPrecio2() {
        return precio2;
    }

    /**
     * @param precio2 the precio2 to set
     */
    public void setPrecio2(Double precio2) {
        this.precio2 = precio2;
    }

    /**
     * @return the precio3
     */
    public Double getPrecio3() {
        return precio3;
    }

    /**
     * @param precio3 the precio3 to set
     */
    public void setPrecio3(Double precio3) {
        this.precio3 = precio3;
    }

    /**
     * @return the existencia
     */
    public Integer getExistencia() {
        return existencia;
    }

    /**
     * @param existencia the existencia to set
     */
    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    /**
     * @return the activo
     */
    public Integer getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Integer activo) {
        this.activo = activo;
    }
    
    private int id_inventario;
    private String clave_producto;
    private String descripcion_producto;
    private double costo_producto;
    private Double precio1;
    private Double precio2;
    private Double precio3;
    private Integer existencia;
    private Integer activo;

    public Producto(int id_inventario, String clave_producto, String descripcion_producto, double costo_producto, Double precio1, Double precio2, Double precio3, Integer existencia, Integer activo) {
        this.id_inventario = id_inventario;
        this.clave_producto = clave_producto;
        this.descripcion_producto = descripcion_producto;
        this.costo_producto = costo_producto;
        this.precio1 = precio1;
        this.precio2 = precio2;
        this.precio3 = precio3;
        this.existencia = existencia;
        this.activo = activo;
    }

    public Producto CreateProducto(String codigo){
        
        ResultSet rs = m.getQueryResults("select * from inventario where clave_producto='"+codigo+"'");
        Producto p = null;
        
        try {
            while(rs.next()){
                 p = new Producto(
                         rs.getInt(1),
                         rs.getString(2),
                         rs.getString(3),
                         rs.getDouble(4),
                         rs.getDouble(5),
                         rs.getDouble(6),
                         rs.getDouble(7),
                         rs.getInt(8),
                         rs.getInt(9));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    


}
