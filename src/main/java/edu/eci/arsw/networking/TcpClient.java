package edu.eci.arsw.networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 35000;

        Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to " + host + ":" + port);
        System.out.println("Type messages (or 'Bye.' to quit):");

        Thread reader = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("<< " + line);
                }
            } catch (Exception ignored) {}
        });
        reader.start();

        while (true) {
            String input = scanner.nextLine();
            out.println(input);
            if ("Bye.".equals(input)) break;
        }

        socket.shutdownOutput();
        Thread.sleep(300);
        socket.close();
        reader.join(2000);
        scanner.close();
        System.out.println("Disconnected.");
    }
}
