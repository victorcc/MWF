package com.mw.framework.anotaciones;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LoBo
 */
public class MotorAnotaciones {

    public static boolean presentaAnotaciones(Class clase) {
        return clase.isAnnotation();
    }

    public static boolean presentaAnotaciones(Method metodo) {
        return (metodo.getAnnotations().length > 0) ? true : false;
    }
    
    public static boolean esControladorAnotado(String nombreClase) {        
        try {
            return Class.forName(nombreClase).isAnnotationPresent(Controller.class);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean esMetodoAccion(Method metodo) {
        return metodo.isAnnotationPresent(MetodoAccion.class);
    }
    
    public static Annotation getAnotacionController(String nombreClase){
        try {
            return Class.forName(nombreClase).getAnnotation(Controller.class);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean esMetodoAnotado(String nombreClase, String nombreMetodo) {
        try {
            Method metodo = Class.forName(nombreClase).getMethod(nombreMetodo, new Class[]{});
            return metodo.isAnnotationPresent(MetodoAccion.class);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static Annotation getAnotacionMetodoAccion(String nombreClase, String nombreMetodo){
        try {
            Method metodo = Class.forName(nombreClase).getMethod(nombreMetodo, new Class[]{});
            return metodo.getAnnotation(MetodoAccion.class);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
