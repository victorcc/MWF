<%-- 
    Document   : detalleProducto
    Created on : 01-jul-2012, 19:11:01
    Author     : Bazalar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="producto" scope="request" type="com.mw.testframework1.modelos.Producto" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle producto</title>
    </head>
    <body>
        Producto: <b> <%= producto.getNombre() %></b><br />
        Precio: <b> <%= producto.getPrecio() %></b><br />
        Descripcion: <b> <%= producto.getDescripcion() %></b><br />
    </body>
</html>
