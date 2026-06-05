package edu.eci.arsw.networking.ejercicio1;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {

    public static void main(String[] args) {
        // se crea un objeto URL con una dirección completa que tiene
        // protocolo, host, puerto, ruta, query y fragmento para poder
        // probar todos los métodos getter que tiene la clase URL
        try {
            URL url = new URL(
                    "http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion"
            );

            // se imprime la URL completa tal como fue ingresada
            System.out.println("URL: " + url);
            // se extrae el protocolo de la URL, en este caso "http"
            System.out.println("Protocol: " + url.getProtocol());
            // se obtiene la autoridad que combina host y puerto
            System.out.println("Authority: " + url.getAuthority());
            // se obtiene solo el nombre del host
            System.out.println("Host: " + url.getHost());
            // se obtiene el puerto, devuelve -1 si no está definido explícitamente
            System.out.println("Port: " + url.getPort());
            // se obtiene la ruta del recurso dentro del servidor
            System.out.println("Path: " + url.getPath());
            // se obtiene la cadena de consulta después del signo ?
            System.out.println("Query: " + url.getQuery());
            // se obtiene la combinación de ruta y consulta
            System.out.println("File: " + url.getFile());
            // se obtiene el fragmento o ancla después del #
            System.out.println("Ref: " + url.getRef());

        } catch (MalformedURLException e) {
            // si la URL está mal escrita se atrapa la excepción
            // y se muestra un mensaje de error
            System.err.println("Malformed URL: " + e.getMessage());
        }
    }
}
