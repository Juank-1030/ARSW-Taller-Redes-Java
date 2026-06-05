package edu.eci.arsw.networking;

import java.lang.reflect.Method;
import java.util.Scanner;
import edu.eci.arsw.networking.util.TcpClient;

// esta clase muestra un menú interactivo que permite al usuario
// seleccionar qué ejercicio ejecutar sin tener que escribir
// la clase completa en la línea de comandos
public class WorkshopLauncher {

    // paquete base donde están todos los ejercicios
    private static final String BASE = "edu.eci.arsw.networking.ejercicio";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // el menú se muestra en un bucle hasta que el usuario elija salir
        while (true) {
            printMenu();
            System.out.print("Select an exercise (0 to exit): ");
            String input = scanner.nextLine().trim();

            // si el usuario ingresa 0 se termina el programa
            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            // se convierten los puntos y guiones a guiones bajos
            // para que coincida con el nombre del paquete
            String pkg = input.replace(".", "_").replace("-", "_");
            // si termina en _c se reemplaza por _client para identificar
            // que se quiere ejecutar el cliente en lugar del servidor
            if (pkg.endsWith("_c")) {
                pkg = pkg.substring(0, pkg.length() - 2) + "_client";
            }
            String className;

            // si el paquete termina en _client se ejecuta el cliente
            if (pkg.endsWith("_client")) {
                String serverPkg = pkg.substring(0, pkg.length() - "_client".length());

                // caso especial para el ejercicio 5.2.1 que usa UDP
                // y no usa el TcpClient genérico
                if (serverPkg.equals("5_2_1")) {
                    className = "DatagramTimeClient";
                    String fullClass = BASE + serverPkg + "." + className;
                    try {
                        Class<?> clazz = Class.forName(fullClass);
                        Method main = clazz.getMethod("main", String[].class);
                        System.out.println("\n--- Running " + fullClass + " ---\n");
                        main.invoke(null, (Object) new String[0]);
                        System.out.println("\n--- Finished ---\n");
                    } catch (Exception e) {
                        System.out.println("Error running exercise: " + e.getMessage() + "\n");
                    }
                    continue;
                }

                // para los ejercicios TCP se obtiene el nombre de la clase
                className = resolveClassName(serverPkg);
                if (className == null) {
                    System.out.println("Unknown exercise.\n");
                    continue;
                }
                // se obtiene el puerto del servidor y se ejecuta el cliente TCP
                int port = getPort(serverPkg);
                System.out.println("\n--- Running TCP client for " + serverPkg + " on port " + port + " ---\n");
                try {
                    TcpClient.main(new String[]{"localhost", String.valueOf(port)});
                } catch (Exception e) {
                    System.out.println("Client error: " + e.getMessage());
                }
                System.out.println("\n--- Finished ---\n");
                continue;
            }

            // si no es cliente se busca la clase del ejercicio
            className = resolveClassName(pkg);
            if (className == null) {
                System.out.println("Unknown exercise number.\n");
                continue;
            }

            // se construye el nombre completo de la clase y se ejecuta
            // usando reflexión para invocar su método main
            String fullClass = BASE + pkg + "." + className;
            try {
                Class<?> clazz = Class.forName(fullClass);
                Method main = clazz.getMethod("main", String[].class);
                System.out.println("\n--- Running " + fullClass + " ---\n");
                main.invoke(null, (Object) new String[0]);
                System.out.println("\n--- Finished ---\n");
            } catch (ClassNotFoundException e) {
                System.out.println("Exercise " + input + " is not implemented yet.\n");
            } catch (NoSuchMethodException e) {
                System.out.println("Error: main method not found in " + fullClass + "\n");
            } catch (Exception e) {
                System.out.println("Error running exercise: " + e.getCause().getMessage() + "\n");
            }
        }
        scanner.close();
    }

    // muestra el menú con todos los ejercicios disponibles
    private static void printMenu() {
        System.out.println("==============================");
        System.out.println("  ARSW Networking Workshop");
        System.out.println("==============================");
        System.out.println("  1   - URL Info Printer");
        System.out.println("  2   - URL Browser");
        System.out.println("  4_3_1   - Square Server");
        System.out.println("  4_3_1_c - Client for Square Server");
        System.out.println("  4_3_2   - Trig Server");
        System.out.println("  4_3_2_c - Client for Trig Server");
        System.out.println("  4_4  - Basic HTTP Server");
        System.out.println("  4_5_1 - Multi-Request Web Server");
        System.out.println("  5_2_1   - Datagram Time Server");
        System.out.println("  5_2_1_c - Datagram Time Client");
        System.out.println("  6_4_1 - RMI Chat");
        System.out.println("  0   - Exit");
        System.out.println("==============================");
    }

    // asocia el número del ejercicio con el nombre de la clase principal
    private static String resolveClassName(String pkg) {
        switch (pkg) {
            case "1":     return "URLInfo";
            case "2":     return "URLBrowser";
            case "4_3_1": return "SquareServer";
            case "4_3_2": return "TrigServer";
            case "4_4":   return "HttpServer";
            case "4_5_1": return "MultiServer";
            case "5_2_1": return "DatagramTimeServer";
            case "6_4_1": return "RmiChat";
            default:      return null;
        }
    }

    // devuelve el puerto en el que escucha cada servidor
    private static int getPort(String pkg) {
        return 35000;
    }
}
