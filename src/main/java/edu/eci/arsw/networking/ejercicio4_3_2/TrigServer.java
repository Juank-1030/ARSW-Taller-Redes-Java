package edu.eci.arsw.networking.ejercicio4_3_2;

import java.net.*;
import java.io.*;

public class TrigServer {

    private static String currentOp = "cos";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje: " + inputLine);

            if ("Bye.".equals(inputLine))
                break;

            if (inputLine.startsWith("fun:")) {
                String func = inputLine.substring(4).trim().toLowerCase();
                if ("sin".equals(func) || "cos".equals(func) || "tan".equals(func)) {
                    currentOp = func;
                    outputLine = "Operacion cambiada a " + func;
                } else {
                    outputLine = "Error: funcion desconocida '" + func + "'";
                }
            } else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result;
                    switch (currentOp) {
                        case "sin": result = Math.sin(number); break;
                        case "tan": result = Math.tan(number); break;
                        default:    result = Math.cos(number); break;
                    }
                    outputLine = String.valueOf(result);
                } catch (NumberFormatException e) {
                    outputLine = "Error: '" + inputLine + "' no es un numero valido.";
                }
            }

            out.println(outputLine);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
