package com.mw.framework.nucleo;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.*;

public class BuscadorClases {

    private static boolean getJar = true;

    public static ArrayList<String> obtenerClasesDePaquete(String packageName) {
        ArrayList<String> names = new ArrayList<String>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL packageURL;
            packageName = packageName.replace(".", "/");
            packageURL = classLoader.getResource(packageName);


            File folder = new File(packageURL.getFile());
            if (folder == null) {
                System.out.println("folder null");
            } else {
                System.out.println("  " + folder);
                File[] contenuti = folder.listFiles();
                if (contenuti == null) {
                    System.out.println("contenido de los folder null ");
                }else{
                String entryName;
                    for (File actual : contenuti) {
                        entryName = actual.getName();
                        entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                        names.add(entryName);
                    }
                }
            }


        } catch (Exception e) {
        }
        return names;
    }
}