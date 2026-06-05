package edu.eci.arsw.networking.ejercicio5_2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class DatagramTimeServer {

    // el socket UDP que escuchará en el puerto 4445
    private DatagramSocket socket;

    public DatagramTimeServer() {
        try {
            // se crea el socket UDP y se vincula al puerto 4445
            // cualquier datagrama enviado a localhost:4445 llegará aquí
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            // si el puerto está ocupado se muestra un error y se sale
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }
    }

    public void startServer() {
        // el servidor se ejecuta para siempre, solo se detiene con Ctrl+C
        while (true) {
            try {
                // se prepara un buffer de 256 bytes para recibir datos
                byte[] buf = new byte[256];
                // se crea un paquete vacío que usará ese buffer
                DatagramPacket packet =
                        new DatagramPacket(buf, buf.length);

                // el servidor se bloquea aquí hasta que llegue un datagrama
                // de algún cliente preguntando la hora
                socket.receive(packet);

                // se obtiene la fecha y hora actual del sistema
                String dString = new Date().toString();

                // se convierte el string de la hora a un arreglo de bytes
                buf = dString.getBytes();

                // se obtiene la dirección IP del cliente que envió el datagrama
                InetAddress address = packet.getAddress();
                // se obtiene el puerto del cliente que envió el datagrama
                int port = packet.getPort();

                // se crea un nuevo paquete con la hora, la IP del cliente
                // y el puerto del cliente para enviarle la respuesta
                packet =
                        new DatagramPacket(
                                buf,
                                buf.length,
                                address,
                                port);

                // se envía el datagrama con la hora al cliente
                socket.send(packet);

            } catch (IOException ex) {
                // si hay algún error de entrada o salida se muestra en consola
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // se crea el servidor y se inicia el bucle de atención
        DatagramTimeServer ds =
                new DatagramTimeServer();
        ds.startServer();
    }
}
