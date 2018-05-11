/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.Entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Producto_tabla_venta {
    private StringProperty clave_producto;
    private StringProperty descripcion;
    private IntegerProperty cantidad;
    private DoubleProperty precio_unitario;
    private DoubleProperty total;

    public Producto_tabla_venta(String clave_producto, String descripcion, int cantidad, double precio_unitario, double total) {
        this.clave_producto = new SimpleStringProperty(clave_producto);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio_unitario = new SimpleDoubleProperty(precio_unitario);
        this.total = new SimpleDoubleProperty(total);
    }

    /**
     * @return the clave_producto
     */
    public String getClave_producto() {
        return clave_producto.get();
    }

    /**
     * @param clave_producto the clave_producto to set
     */
    public void setClave_producto(StringProperty clave_producto) {
        this.clave_producto = clave_producto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion.get();
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(StringProperty descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad.get();
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(IntegerProperty cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio_unitario
     */
    public Double getPrecio_unitario() {
        return precio_unitario.get();
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(DoubleProperty precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total.get();
    }

    /**
     * @param total the total to set
     */
    public void setTotal(DoubleProperty total) {
        this.total = total;
    }
}