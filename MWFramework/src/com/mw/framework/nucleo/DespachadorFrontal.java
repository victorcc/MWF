package com.mw.framework.nucleo;

import com.mw.framework.anotaciones.Controller;
import com.mw.framework.anotaciones.MetodoAccion;
import com.mw.framework.anotaciones.MotorAnotaciones;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LoBo
 */
public class DespachadorFrontal extends HttpServlet {

    private InputStream inputStream;
    private final String ruta = "/WEB-INF/conf.xml";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.manejarPeticion(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        this.manejarPeticion(request, response);
    }

    private void manejarPeticion(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String sp = request.getServletPath();
        PrintWriter out = response.getWriter();
        MapeadorXML mxml = new MapeadorXML();

        inputStream = getServletContext().getResourceAsStream(ruta);
        String xml = mxml.obtenerContenidoXML(inputStream);
        mxml.mapear(xml);

        HashMap<String, String> controladoresRutas = mxml.obtenerControladores();
        HashMap<String, HashMap> controladoresMetodos = mxml.obtenerMapeoControladoresMetodos();


        //aqui se inicia convencion sobre configuracion
        // la convencion es que el los controladores se agreguen a la carpeta "controladores"
        //y cada vista se agregre a una carpeta con el nombre del controlador en WEB-INF
        //System.out.println("se incia la busqueda de clases ");
        //out.write("buscando ");
        ArrayList<String> listaClases = MotorConvencion.revisarListaControladores();
        for (String nombreClase : listaClases) {
            //out.write("clase "+nombreClase);
            //obtenemos la lista de clases controladores del paquete "controladores"
            ArrayList<String> listaMetodos = MotorConvencion.revisarListaMetodos(nombreClase);
            //agregamos los controladores de la carpeta "controladores" a la lista actual
            controladoresRutas.put(nombreClase, "controladores." + nombreClase);

            HashMap listaMetodosControladores = new HashMap();
            for (String nombreMetodo : listaMetodos) {
                //cada nuevo metodo se ira agregando a la lista
                listaMetodosControladores.put(nombreMetodo, nombreMetodo);
            }
            //agregamos una lista de metodos al controlador
            controladoresMetodos.put(nombreClase, listaMetodosControladores);
        }

        //fin COC

        //aqui se inicia motor de anotaciones
        try {
            for (String nombreClase : listaClases) {
                //se agrega el directorio de controladores
                String directorioControladores="controladores.";
                nombreClase=nombreClase;
                System.out.println("clase "+nombreClase);
                if (MotorAnotaciones.esControladorAnotado(directorioControladores+nombreClase)) {
                    //Debemos obtener el contenido de la anotacion de una clase
                    Controller cntrllrAnot = (Controller) MotorAnotaciones.getAnotacionController(directorioControladores+nombreClase);
                    String nombreCortoControlador = cntrllrAnot.nombreControlador(); // "cont1"

                    controladoresRutas.put(nombreCortoControlador, directorioControladores+nombreClase);
                    ArrayList<String> listaMetodos = MotorConvencion.revisarListaMetodos(nombreClase);

                    HashMap listaMetodosControladores = new HashMap();
                    for (String nombreMetodo : listaMetodos) {
                        if (MotorAnotaciones.esMetodoAnotado(directorioControladores+nombreClase, nombreMetodo)) {
                            //Debo obtener el nombre dentro de la anotacion del metodo de la clase
                            MetodoAccion mtdAnot = (MetodoAccion) MotorAnotaciones.getAnotacionMetodoAccion(directorioControladores+nombreClase, nombreMetodo);
                            String nombreCortoAccion = mtdAnot.nombreAccion(); //"met1"    
                            System.out.println(" nom "+nombreCortoAccion);
                            //cada nuevo metodo se ira agregando a la lista
                            listaMetodosControladores.put(nombreCortoAccion,nombreMetodo );
                        }
                    }
                    //agregamos una lista de metodos al controlador
                    controladoresMetodos.put(nombreCortoControlador, listaMetodosControladores);
                }
            }
            System.out.println(" clas  "+controladoresRutas +" met "+controladoresMetodos);
        } catch (Exception e) {
        }

        //fin motor de anotaciones

        String nombreControlador = this.getNombreControlador(sp);
        String nombreAccion = this.getNombreAccion(sp);

        try {
            String nombreRealControlador = controladoresRutas.get(nombreControlador);
            String nombreRealMetodo = controladoresMetodos.get(nombreControlador).get(nombreAccion).toString();

//            if (request == null || response == null) {
//                //out.write("NULO!");
//                System.out.println("NULO!");
//            }
            //out.write("NOT NULL!");
//            System.out.println("NOT NULL!");
            Object controlador = (Object) Class.forName(nombreRealControlador).newInstance();
            /*
             * se setea el request
             */
            Method setRequest = controlador.getClass().getMethod("setRequest", new Class[]{HttpServletRequest.class});
            setRequest.invoke(controlador, new Object[]{request});

            /*
             * se setea el response
             */
            Method setResponse = controlador.getClass().getMethod("setResponse", new Class[]{HttpServletResponse.class});
            setResponse.invoke(controlador, new Object[]{response});


            Method m = controlador.getClass().getMethod(nombreRealMetodo, new Class[]{});

            Object rpta = m.invoke(controlador, new Object[]{});
            //out.write(rpta.toString());
            request.getRequestDispatcher(rpta.toString()).forward(request, response);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            //out.write("Excepcion");
        }
    }

    public String getNombreControlador(String ruta) {
        String subRuta = ruta;
        StringTokenizer tokens = new StringTokenizer(subRuta, "/");

        return tokens.nextToken();
    }

    public String getNombreAccion(String ruta) {
        String subRuta = ruta;
        StringTokenizer tokens = new StringTokenizer(subRuta, "/");
        tokens.nextToken();
        String aux = tokens.nextToken();
        return aux.substring(0, aux.length() - 5);
    }
}
