/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mw.framework.nucleo;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author LoBo
 */
public class MapeadorXML {
    
    HashMap<String, String> listaControladores=new HashMap<String,String>();//mapea controladores rutas controladores
    HashMap<String, HashMap> listaMetodosControladores=new HashMap<String, HashMap>();//mapear controladores metodos de controladores
    
    public void mapear(){
        String xml;
        xml=obtenerContenidoXML();
        this.mapear(xml);
    }
    
    public void mapear(String xml) {
        
        try {
            /*
             * primero parseamos los xml luego obtenemos la lista de
             * controladores una vez tenemos la lista de co0ntroladores
             * obtenemos sus metodos una vez tenemos los metodos obtenemos sus
             * rutas
             *
             */

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource archivo = new InputSource();
            archivo.setCharacterStream(new StringReader(xml));

            Document documento = db.parse(archivo);
            documento.getDocumentElement().normalize();

            NodeList nodoControladores = documento.getElementsByTagName("controlador");

            for (int s = 0; s < nodoControladores.getLength(); s++) {

                Node primerNodo = nodoControladores.item(s);
                String nombreControlador;
                String rutaControlador;
                String accion;
                String rutaAccion;

                if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
                    
                    ///capturamos el nombre del controlador 
                    Element primerElemento = (Element) primerNodo;

                    NodeList primerNombreElementoLista =
                    primerElemento.getElementsByTagName("nombre-controlador");
                    
                    Element primerNombreElemento =(Element) primerNombreElementoLista.item(0);
                    NodeList listaNombresControladores = primerNombreElemento.getChildNodes();
                  
                    nombreControlador = ((Node) listaNombresControladores.item(0)).getNodeValue().toString();
                    //System.out.println("nombre controlador : " + nombreControlador);
                    ///////////////////////////////
                    //capturamos la ruta del controlador
                    
                    Element segundoElemento = (Element) primerNodo;

                    NodeList segundoNombreElementoLista =
                    primerElemento.getElementsByTagName("nombre-controlador-servlet");
                    
                    Element segundoNombreElemento =(Element) segundoNombreElementoLista.item(0);
                    NodeList listaRutaControladores = segundoNombreElemento.getChildNodes();
                  
                    rutaControlador = ((Node) listaRutaControladores.item(0)).getNodeValue().toString();
                    //System.out.println("ruta controlador : " + nombreControlador);
                    
                    //fin/////////////////////////////
                    
                    HashMap<String,String> listaAccionesControladores=new HashMap<String, String>();
                    this.listaControladores.put(nombreControlador,rutaControlador);
                    this.listaMetodosControladores.put(nombreControlador, listaAccionesControladores);
                    
                    NodeList listaAcciones =primerElemento.getElementsByTagName("accion");
                    for (int i = 0; i < listaAcciones.getLength(); i++) {
                        Element elementoAccion = (Element) listaAcciones.item(i);    
                        NodeList listaNombreAccion = elementoAccion.getElementsByTagName("nombre-accion");
                        Element elna =(Element) listaNombreAccion.item(0);
                        NodeList primerNombre = elna.getChildNodes();
                        accion = ((Node) primerNombre.item(0)).getNodeValue().toString();
                       // Element elementLNA = (Element) listaNombreAccion.item(0);
                        //accion = ((Node) listaNombreAccion.item(0)).getNodeValue().toString();
                        //System.out.println("nombre accion : " + accion);                       
                        
                        ////ruta de la accion
                         NodeList listaRutaAccion = elementoAccion.getElementsByTagName("nombre-accion-servlet");
                        Element elra =(Element) listaRutaAccion.item(0);
                        NodeList segundoRuta = elra.getChildNodes();
                         rutaAccion= ((Node) segundoRuta.item(0)).getNodeValue().toString();
                       // Element elementLNA = (Element) listaNombreAccion.item(0);
                        //accion = ((Node) listaNombreAccion.item(0)).getNodeValue().toString();
                        //System.out.println("ruta accion : " + accion);
                        listaAccionesControladores.put(accion, rutaAccion);
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MapeadorXML() {
        //System.out.println("leyendo el archivo");
        //String xml=this.obtenerContenidoXML();
        //this.mapear(xml);
        //System.out.println("   "+listaControladores);
        //System.out.println(" "+listaMetodosControladores);
    }
    
    public String obtenerContenidoXML(){
//        return "<xml>"+           
//"	<controlador>"+
//"	<nombre-controlador>controlador1</nombre-controlador>"+
//"        <nombre-controlador-servlet>com.mw.framework.controladores.Controlador1</nombre-controlador-servlet>"+
//"		<accion>"+
//"			<nombre-accion>comprar</nombre-accion>"+
//"                        <nombre-accion-servlet>comprarProducto</nombre-accion-servlet>"+
//"			<vista-accion>vista1.html</vista-accion>"+
//"		</accion>"+
//"		"+
//"	</controlador>"+
//"	<controlador>"+
//""+
//"	<nombre-controlador>controlador2</nombre-controlador>"+
//"        <nombre-controlador-servlet>com.mw.framework.controladores.Controlador2</nombre-controlador-servlet>"+
//"		<accion>"+
//"			<nombre-accion>regalar</nombre-accion>"+
//"			<nombre-accion-servlet>regalarProducto</nombre-accion-servlet>"+
//"			<vista-accion>vista2.html</vista-accion>"+
//"		</accion>"+
//"		<accion>"+
//"			<nombre-accion>vender</nombre-accion>"+
//"			<nombre-accion-servlet>venderProducto</nombre-accion-servlet>"+
//"			<vista-accion>vista3.html</vista-accion>"+
//"		</accion>"+		
//"	</controlador>"+
//
//"</xml>";
        BufferedReader br = null;
        String entrada;
        String cadena="";
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("com.mw.framework.nucleo.ejemploXML.xml"))));
            InputStream is=null;
            String ruta = "/WEB-INF/ejemploXML.xml";
            
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ruta))));
            
            while ((entrada = br.readLine()) != null){
                cadena = cadena + entrada;
            }
        } catch (Exception ex) {
            Logger.getLogger(MapeadorXML.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return cadena;
    }
    
     public String obtenerContenidoXML(InputStream link){

        BufferedReader br = null;
        String entrada;
        String cadena="";
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("com.mw.framework.nucleo.ejemploXML.xml"))));
            InputStream is=link;
            String ruta = "/WEB-INF/ejemploXML.xml";
            
            br = new BufferedReader(new InputStreamReader(is));
            
            while ((entrada = br.readLine()) != null){
                cadena = cadena + entrada;
            }
        } catch (Exception ex) {
            Logger.getLogger(MapeadorXML.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return cadena;
    }
    
    
//    public static void main(String[] args) {
//        MapeadorXML mapXML =new MapeadorXML();
//        
//    }

    HashMap<String, HashMap> obtenerMapeoControladoresMetodos() {
        return this.listaMetodosControladores;
    }
    
    HashMap <String,String> obtenerControladores(){
        return this.listaControladores;
    }
}
