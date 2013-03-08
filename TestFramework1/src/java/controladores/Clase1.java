/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.mw.framework.anotaciones.Controller;
import com.mw.framework.anotaciones.MetodoAccion;
import com.mw.framework.controlador.Controlador;

/**
 *
 * @author cc
 */
@Controller(nombreControlador="cont1")
public class Clase1 extends Controlador{
    @MetodoAccion(nombreAccion="met1")
    public String holas(){
        return "comprar.jsp";
    }
}
