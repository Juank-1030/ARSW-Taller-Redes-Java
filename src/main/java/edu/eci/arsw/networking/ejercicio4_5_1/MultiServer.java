package edu.eci.arsw.networking.ejercicio4_5_1;

import java.net.*;
import java.io.*;
import java.nio.file.*;

public class MultiServer {

    // se define la carpeta donde estarán los archivos que se van a servir
    private static final String WWW_ROOT = "www";

    public static void main(String[] args) throws IOException {

        // se crea el servidor en el puerto 35000 igual que el ejercicio anterior
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        System.out.println("Listo para recibir ...");

        // este bucle exterior permite que el servidor atienda múltiples
        // clientes uno después de otro sin tener que reiniciar el programa
        while (true) {

            Socket clientSocket = null;

            try {
                // se espera a que un navegador se conecte
                clientSocket = serverSocket.accept();

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            // se prepara el canal de texto para enviar los encabezados HTTP
            PrintWriter out =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true);

            // se prepara el lector para recibir las peticiones del navegador
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()));

            // se prepara un canal de bytes para enviar archivos binarios
            // como imágenes sin que se corrompan
            BufferedOutputStream dataOut =
                    new BufferedOutputStream(
                            clientSocket.getOutputStream());

            String inputLine;
            String requestPath = null;

            // este bucle interior lee todas las peticiones que hace el
            // navegador mientras mantenga la conexión abierta
            while ((inputLine = in.readLine()) != null) {

                System.out.println("Received: " + inputLine);

                // se detecta si la línea es una petición GET y se extrae
                // la ruta del archivo solicitado
                if (inputLine.startsWith("GET ")) {
                    requestPath = inputLine.split(" ")[1];
                }

                // cuando el navegador termina de enviar encabezados se
                // procesa la petición y se envía la respuesta
                if (!in.ready()) {

                    // si no se pudo extraer una ruta se responde con 400
                    if (requestPath == null) {
                        String body = "<!DOCTYPE html>"
                                + "<html><body><h1>400 Bad Request</h1></body></html>";
                        byte[] bodyData = body.getBytes();
                        out.write("HTTP/1.1 400 Bad Request\r\n"
                                + "Content-Type: text/html\r\n"
                                + "Content-Length: " + bodyData.length + "\r\n"
                                + "\r\n");
                        out.flush();
                        dataOut.write(bodyData);
                        dataOut.flush();

                    } else {

                        // si la ruta es / se reemplaza por /index.html
                        String filePath = requestPath.equals("/")
                                ? "/index.html" : requestPath;
                        File file = new File(WWW_ROOT + filePath);

                        // si el archivo existe se envía con código 200
                        if (file.exists() && !file.isDirectory()) {

                            byte[] fileData = Files.readAllBytes(file.toPath());
                            String contentType = getContentType(file.getName());

                            out.write("HTTP/1.1 200 OK\r\n"
                                    + "Content-Type: " + contentType + "\r\n"
                                    + "Content-Length: " + fileData.length + "\r\n"
                                    + "Connection: keep-alive\r\n"
                                    + "\r\n");
                            out.flush();
                            dataOut.write(fileData);
                            dataOut.flush();

                            System.out.println("Served: " + file.getName());

                        } else {
                            // si el archivo no existe se responde con 404
                            String body = "<!DOCTYPE html>"
                                    + "<html><body><h1>404 Not Found</h1>"
                                    + "<p>" + requestPath + "</p></body></html>";
                            byte[] bodyData = body.getBytes();
                            out.write("HTTP/1.1 404 Not Found\r\n"
                                    + "Content-Type: text/html\r\n"
                                    + "Content-Length: " + bodyData.length + "\r\n"
                                    + "\r\n");
                            out.flush();
                            dataOut.write(bodyData);
                            dataOut.flush();
                        }
                    }

                    // se reinicia la ruta para la siguiente petición
                    requestPath = null;
                }
            }

            // cuando el navegador se desconecta se cierran los recursos
            // y el servidor vuelve al bucle exterior a esperar otro cliente
            out.close();
            in.close();
            dataOut.close();
            clientSocket.close();
        }
    }

    // esta función devuelve el tipo MIME según la extensión del archivo
    // para que el navegador sepa cómo interpretar el contenido
    private static String getContentType(String fileName) {
        String ext = fileName.substring(
                fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "html": case "htm":  return "text/html";
            case "css":               return "text/css";
            case "js":                return "application/javascript";
            case "json":              return "application/json";
            case "png":               return "image/png";
            case "jpg": case "jpeg":  return "image/jpeg";
            case "gif":               return "image/gif";
            case "svg":               return "image/svg+xml";
            case "ico":               return "image/x-icon";
            default:                  return "application/octet-stream";
        }
    }
}
