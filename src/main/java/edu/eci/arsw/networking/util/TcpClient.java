package edu.eci.arsw.networking.util;

import java.io.*;
import java.net.*;
import java.util.Scanner;

// esta clase es un cliente TCP genérico que sirve para probar
// los servidores TCP de los ejercicios 4.3.1 y 4.3.2
public class TcpClient {

    public static void main(String[] args) throws Exception {
        // se obtiene el host y el puerto desde los argumentos
        // si no se proporcionan se usan localhost y 35000 por defecto
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 35000;

        // se crea el socket TCP y se conecta al servidor
        Socket socket = new Socket(host, port);
        // se prepara el canal de salida para enviar mensajes al servidor
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // se prepara el canal de entrada para leer respuestas del servidor
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // se prepara el lector de la consola para lo que escriba el usuario
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to " + host + ":" + port);
        System.out.println("Type messages (or 'Bye.' to quit):");

        // se crea un hilo separado que se encarga de leer las respuestas
        // del servidor y mostrarlas en pantalla mientras el usuario escribe
        Thread reader = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("<< " + line);
                }
            } catch (Exception ignored) {}
        });
        reader.start();

        // el hilo principal lee lo que el usuario escribe y lo envía al servidor
        while (true) {
            String input = scanner.nextLine();
            out.println(input);
            if ("Bye.".equals(input)) break;
        }

        // se cierra la conexión de forma ordenada
        socket.shutdownOutput();
        // se espera un momento para que lleguen las últimas respuestas
        Thread.sleep(300);
        socket.close();
        reader.join(2000);
        scanner.close();
        System.out.println("Disconnected.");
    }
}
