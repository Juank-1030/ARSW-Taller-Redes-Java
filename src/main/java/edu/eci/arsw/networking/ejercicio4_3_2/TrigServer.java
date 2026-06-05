package edu.eci.arsw.networking.ejercicio4_3_2;

import java.net.*;
import java.io.*;

public class TrigServer {

    // esta variable guarda la operación trigonométrica actual
    // por defecto empieza en coseno, el cliente puede cambiarla
    // enviando "fun:sin", "fun:cos" o "fun:tan"
    private static String currentOp = "cos";

    public static void main(String[] args) throws IOException {
        // se crea el servidor en el puerto 35000 para escuchar clientes
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        // se espera a que un cliente se conecte al servidor
        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        // se prepara el canal de salida para responder al cliente
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

        // se prepara el canal de entrada para leer lo que envía el cliente
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine, outputLine;

        // se leen los mensajes del cliente uno por uno
        while ((inputLine = in.readLine()) != null) {
            // se muestra en la consola del servidor lo que llegó
            System.out.println("Mensaje: " + inputLine);

            // si el cliente escribe "Bye." se termina la conexión
            if ("Bye.".equals(inputLine))
                break;

            // se verifica si el mensaje empieza con "fun:" lo que significa
            // que el cliente quiere cambiar la operación matemática
            if (inputLine.startsWith("fun:")) {
                // se extrae el nombre de la función después de "fun:"
                String func = inputLine.substring(4).trim().toLowerCase();
                // solo se aceptan sin, cos o tan, cualquier otra cosa da error
                if ("sin".equals(func) || "cos".equals(func) || "tan".equals(func)) {
                    currentOp = func;
                    outputLine = "Operacion cambiada a " + func;
                } else {
                    outputLine = "Error: funcion desconocida '" + func + "'";
                }
            } else {
                // si no es un comando fun: se asume que es un número
                try {
                    double number = Double.parseDouble(inputLine);
                    double result;
                    // se aplica la función trigonométrica que esté seleccionada
                    switch (currentOp) {
                        case "sin": result = Math.sin(number); break;
                        case "tan": result = Math.tan(number); break;
                        // por defecto se calcula el coseno
                        default:    result = Math.cos(number); break;
                    }
                    outputLine = String.valueOf(result);
                } catch (NumberFormatException e) {
                    // si no es un número válido se envía un mensaje de error
                    outputLine = "Error: '" + inputLine + "' no es un numero valido.";
                }
            }

            // se envía la respuesta al cliente
            out.println(outputLine);
        }

        // se cierran todos los recursos al terminar
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
