/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mw.testframework1.modelos;

/**
 *
 * @author Bazalar
 */
public class Producto {
    private String nombre;
    private double precio;
    private String descripcion;
    
    public Producto(String nombre) {
        this.nombre = nombre;
        this.precio = 12.50;
        this.descripcion = "Un producto cualquiera con un precio cualquiera...";
    }
    
    public Producto(String nombre, double precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
