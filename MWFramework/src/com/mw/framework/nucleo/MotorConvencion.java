/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mw.framework.nucleo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cc
 */
public class MotorConvencion {
    public static ArrayList <String>  revisarListaControladores(){
        ArrayList<String> listaControladores = null;
        listaControladores=new ArrayList<>();
        listaControladores=BuscadorClases.obtenerClasesDePaquete("controladores");
        return listaControladores;
    }
    
    public static ArrayList <String> revisarListaMetodos(String nombreClase){
        ArrayList<String> listaMetodos = null;
        listaMetodos=new ArrayList<>();
        Class c;
        try {
            c = Class.forName("controladores."+nombreClase);
            for ( Method m: c.getDeclaredMethods()) {
            listaMetodos.add(m.getName());
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MotorConvencion.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Field[] fields = Entidad.class.getDeclaredFields();   
        return listaMetodos;
    }
    
    
}
