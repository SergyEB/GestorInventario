/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author sergi
 */
public class Producto {

    private int idProducto;
    private int idCategoria;
    private String nombreProducto;
    private String descripcionProducto;
    private int cantidadProducto;
    private double precio;
    private Date fechaVencimiento; // Usamos Date para manejar la fecha

    public Producto() {
    }

    public Producto(int idProducto, int idCategoria, String nombreProducto, String descripcionProducto, int cantidadProducto, double precio, Date fechaVencimiento) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.precio = precio;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "idProducto=" + idProducto
                + ", idCategoria=" + idCategoria
                + ", nombreProducto='" + nombreProducto + '\''
                + ", descripcionProducto='" + descripcionProducto + '\''
                + ", cantidadProducto=" + cantidadProducto
                + ", precio=" + precio
                + ", fechaVencimiento=" + fechaVencimiento
                + '}';
    }
}
