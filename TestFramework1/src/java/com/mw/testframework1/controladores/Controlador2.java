/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mw.testframework1.controladores;

import com.mw.framework.controlador.Controlador;

/**
 *
 * @author USUARIO
 */
public class Controlador2 extends Controlador{

    
    
    public String regalarProducto() {
        System.out.println("el respones es "+this.request);
        return "estoy regalando un producto";
    }
    
    public String venderProducto() {
         System.out.println("el respones es "+this.request);
        return "estoy vendiendo un producto";
    }
}
