package edu.eci.arsw.networking.ejercicio5_2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class DatagramTimeClient {

    public static void main(String[] args) {

        // se guarda la última hora recibida por si el servidor se cae
        // al inicio se muestra un mensaje mientras se espera la primera respuesta
        String lastTime = "Waiting for server...";

        try {
            // se crea un socket UDP sin vincularlo a un puerto específico
            // el sistema operativo asigna un puerto automáticamente
            DatagramSocket socket = new DatagramSocket();

            // se establece un tiempo máximo de espera de 3 segundos
            // si el servidor no responde en ese tiempo se lanza una excepción
            socket.setSoTimeout(3000);

            // se resuelve la dirección del servidor, en este caso localhost
            InetAddress address =
                    InetAddress.getByName("127.0.0.1");

            // el cliente se ejecuta para siempre actualizando la hora cada 5 segundos
            while (true) {

                try {
                    // se prepara un buffer vacío para enviar al servidor
                    byte[] buf = new byte[256];

                    // se crea un paquete con dirección al servidor en el puerto 4445
                    DatagramPacket packet =
                            new DatagramPacket(
                                    buf,
                                    buf.length,
                                    address,
                                    4445);

                    // se envía el datagrama vacío al servidor pidiendo la hora
                    socket.send(packet);

                    // se crea un nuevo paquete para recibir la respuesta
                    packet =
                            new DatagramPacket(
                                    buf,
                                    buf.length);

                    // se espera la respuesta del servidor con un máximo de 3 segundos
                    socket.receive(packet);

                    // se extrae el string de hora del paquete recibido
                    String received =
                            new String(
                                    packet.getData(),
                                    0,
                                    packet.getLength());

                    // se actualiza la última hora recibida
                    lastTime = received;
                    // se imprime la hora en la consola
                    System.out.println(
                            "Date: " + received);

                } catch (SocketTimeoutException ex) {
                    // si pasan 3 segundos y el servidor no responde
                    // se muestra la última hora conocida y se indica que está caído
                    System.out.println(
                            "Date: " + lastTime
                                    + " (server offline)");
                }

                // se espera 5 segundos antes de hacer la siguiente petición
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    // si el hilo es interrumpido se muestra un error y se sale
                    System.err.println(
                            "Client interrupted.");
                    break;
                }
            }

        } catch (SocketException ex) {
            // si no se puede crear el socket UDP se muestra el error
            System.err.println(
                    "Could not create socket.");

        } catch (UnknownHostException ex) {
            // si no se puede resolver la dirección del servidor
            System.err.println(
                    "Unknown host: 127.0.0.1");

        } catch (IOException ex) {
            // si hay algún error de entrada o salida
            System.err.println(
                    "IO error: " + ex.getMessage());
        }
    }
}
