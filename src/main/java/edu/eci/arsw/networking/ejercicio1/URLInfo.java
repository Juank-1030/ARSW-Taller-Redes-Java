package edu.eci.arsw.networking.ejercicio1;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {

    public static void main(String[] args) {
        try {
            URL url = new URL(
                    "http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion"
            );

            System.out.println("URL: " + url);
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Authority: " + url.getAuthority());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("File: " + url.getFile());
            System.out.println("Ref: " + url.getRef());

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        }
    }
}
