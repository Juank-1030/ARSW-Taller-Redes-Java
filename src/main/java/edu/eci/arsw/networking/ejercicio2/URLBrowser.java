package edu.eci.arsw.networking.ejercicio2;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLBrowser {

    public static void main(String[] args) {
        // se crea un Scanner para leer lo que el usuario escriba en la consola
        Scanner scanner = new Scanner(System.in);

        // se le pide al usuario que ingrese una dirección URL
        System.out.print("Enter a URL: ");
        String urlString = scanner.nextLine();

        try {
            // se crea un objeto URL a partir del texto que escribió el usuario
            URL url = new URL(urlString);

            // se usa try-with-resources para abrir la conexión y el archivo
            // el BufferedReader lee el contenido de la página web línea por línea
            // el PrintWriter escribe ese contenido en un archivo llamado resultado.html
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
                 PrintWriter writer = new PrintWriter(
                         new FileWriter("resultado.html"))) {

                String line;
                // se lee cada línea de la página web hasta que no haya más datos
                while ((line = reader.readLine()) != null) {
                    // cada línea se escribe en el archivo resultado.html
                    writer.println(line);
                }

                // al terminar se informa al usuario que el contenido se guardó
                System.out.println(
                        "Content saved to resultado.html");
            }

        } catch (MalformedURLException e) {
            // si la URL no tiene el formato correcto se muestra un error
            System.err.println(
                    "Malformed URL: " + e.getMessage());

        } catch (IOException e) {
            // si hay problemas de red o de lectura se muestra un error
            System.err.println(
                    "Error reading URL: " + e.getMessage());
        }
    }
}
