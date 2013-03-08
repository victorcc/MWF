/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mw.testframework1.controladores;

import com.mw.framework.controlador.Controlador;
import com.mw.testframework1.modelos.Producto;

/**
 *
 * @author USUARIO
 */
public class Controlador1 extends Controlador{


    public String comprarProducto() {
        return "comprar.jsp";
    }
    
    public String detalleProducto() {
        String nombreProducto = request.getParameter("nombreProducto");
        Producto producto = new Producto(nombreProducto);
        request.setAttribute("producto", producto);
        return "detalleProducto.jsp";
    }
    
}
