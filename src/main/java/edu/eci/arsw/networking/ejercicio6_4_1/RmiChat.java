package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

// esta clase implementa el chat peer-to-peer usando RMI
// cada instancia actúa como servidor (publica su objeto remoto)
// y como cliente (se conecta al objeto remoto del otro peer)
public class RmiChat implements Chat {

    // referencia al objeto remoto del otro peer para enviarle mensajes
    private static Chat remotePeer;
    // guarda el puerto local para mostrarlo cuando llegue un mensaje
    private static int localPort;

    @Override
    public void sendMessage(String message) throws RemoteException {
        // cuando el otro peer envía un mensaje, RMI llama este método
        // se imprime el mensaje recibido con el puerto del remitente
        System.out.println("\n[Peer " + localPort + "]: " + message);
        System.out.print("You: ");
        System.out.flush();
    }

    public static void main(String[] args) {
        // se lee desde la consola la configuración del chat
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== RMI Chat ===");
        // se pide el puerto en el que se publicará el servicio local
        System.out.print("Enter your local port: ");
        localPort = Integer.parseInt(scanner.nextLine().trim());

        // se pide la IP del otro peer al que se quiere conectar
        System.out.print("Enter remote IP: ");
        String remoteIp = scanner.nextLine().trim();

        // se pide el puerto del otro peer
        System.out.print("Enter remote port: ");
        int remotePort = Integer.parseInt(scanner.nextLine().trim());

        try {
            // se crea un registro RMI en el puerto local para que el
            // otro peer pueda encontrar nuestro objeto remoto
            Registry registry = LocateRegistry.createRegistry(localPort);

            // se crea una instancia del chat y se exporta como objeto remoto
            // exportObject genera un stub que el otro peer puede invocar
            RmiChat chatImpl = new RmiChat();
            Chat stub = (Chat) UnicastRemoteObject.exportObject(chatImpl, 0);

            // se publica el stub en el registro con el nombre "chat"
            registry.rebind("chat", stub);
            System.out.println("Chat service published on port " + localPort);

            // se intenta conectar al otro peer, si no está disponible
            // se espera 3 segundos y se vuelve a intentar
            while (true) {
                try {
                    Registry remoteRegistry = LocateRegistry.getRegistry(remoteIp, remotePort);
                    remotePeer = (Chat) remoteRegistry.lookup("chat");
                    System.out.println("Connected to " + remoteIp + ":" + remotePort);
                    System.out.println("Type your messages (type 'exit' to quit):\n");
                    break;
                } catch (Exception e) {
                    System.out.println("Waiting for remote peer on "
                            + remoteIp + ":" + remotePort + " ...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
            }

            // una vez conectado, se leen mensajes del usuario y se envían
            // al otro peer llamando el método remoto sendMessage
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    // esta llamada viaja por la red hasta el otro peer
                    remotePeer.sendMessage(message);
                } catch (RemoteException e) {
                    System.out.println("Lost connection to remote peer.");
                    break;
                }
            }

        } catch (RemoteException e) {
            System.err.println("RMI error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Chat ended.");
        System.exit(0);
    }
}
