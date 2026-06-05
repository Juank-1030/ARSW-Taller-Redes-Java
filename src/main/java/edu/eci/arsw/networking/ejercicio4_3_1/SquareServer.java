package edu.eci.arsw.networking.ejercicio4_3_1;

import java.net.*;
import java.io.*;

public class SquareServer {

    public static void main(String[] args) throws IOException {
        // se declara el ServerSocket que va a escuchar conexiones entrantes
        ServerSocket serverSocket = null;

        // se intenta crear el servidor en el puerto 35000
        // si el puerto está ocupado se muestra un error y se termina el programa
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        // se declara el socket del cliente que se conectará al servidor
        Socket clientSocket = null;

        // el servidor se queda bloqueado aquí esperando que un cliente se conecte
        // cuando alguien se conecta, accept() devuelve el socket del cliente
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        // se prepara el PrintWriter para enviar datos al cliente
        // el true activa el auto-flush, los mensajes se envían inmediatamente
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

        // se prepara el BufferedReader para leer los datos que envía el cliente
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine, outputLine;

        // se lee cada línea que envía el cliente hasta que se desconecte
        while ((inputLine = in.readLine()) != null) {
            // se muestra en consola el mensaje recibido del cliente
            System.out.println("Mensaje: " + inputLine);

            // si el cliente escribe "Bye." se sale del bucle y se termina la conexión
            if ("Bye.".equals(inputLine))
                break;

            try {
                // se intenta convertir el mensaje a un número decimal
                double number = Double.parseDouble(inputLine);
                // se calcula el cuadrado del número y se convierte a texto
                outputLine = String.valueOf(number * number);
            } catch (NumberFormatException e) {
                // si el mensaje no es un número válido se envía un mensaje de error
                outputLine = "Error: '" + inputLine + "' no es un numero valido.";
            }

            // se envía la respuesta al cliente (el cuadrado o el error)
            out.println(outputLine);
        }

        // se cierran todos los recursos para liberar el puerto y la memoria
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
