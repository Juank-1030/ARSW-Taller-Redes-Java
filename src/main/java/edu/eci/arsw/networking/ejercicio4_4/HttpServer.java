package edu.eci.arsw.networking.ejercicio4_4;

import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        // se crea el servidor HTTP en el puerto 35000
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        // se espera a que un navegador web se conecte al servidor
        Socket clientSocket = null;

        try {
            // se muestra un mensaje indicando que el servidor está listo
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();

        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        // se prepara el PrintWriter para enviar la respuesta HTTP al navegador
        PrintWriter out =
                new PrintWriter(
                        clientSocket.getOutputStream(),
                        true);

        // se prepara el BufferedReader para leer los encabezados
        // que el navegador envía en la petición HTTP
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

        String inputLine, outputLine;

        // se leen todas las líneas de la petición HTTP del navegador
        while ((inputLine = in.readLine()) != null) {

            // se imprime cada línea recibida para ver qué envía el navegador
            System.out.println("Received: " + inputLine);

            // cuando in.ready() devuelve false significa que el navegador
            // ya terminó de enviar los encabezados y se sale del bucle
            if (!in.ready()) {
                break;
            }
        }

        // se construye la respuesta HTTP completa con encabezados y HTML
        outputLine =
                // la primera línea debe ser el código de estado HTTP 200 OK
                "HTTP/1.1 200 OK\r\n"
                        // se especifica que el contenido es HTML
                        + "Content-Type: text/html\r\n"
                        // línea en blanco obligatoria entre encabezados y cuerpo
                        + "\r\n"
                        // a partir de aquí va el contenido HTML que verá el navegador
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "My Web Site"
                        + "</body>"
                        + "</html>"
                        // se agrega la última línea recibida como curiosidad
                        + inputLine;

        // se envía la respuesta completa al navegador
        out.println(outputLine);

        // se cierran todos los recursos
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
