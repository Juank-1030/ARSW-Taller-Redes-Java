package edu.eci.arsw.networking;

import java.lang.reflect.Method;
import java.util.Scanner;

public class WorkshopLauncher {

    private static final String BASE = "edu.eci.arsw.networking.ejercicio";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Select an exercise (0 to exit): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            String pkg = input.replace(".", "_").replace("-", "_");
            if (pkg.endsWith("_c")) {
                pkg = pkg.substring(0, pkg.length() - 2) + "_client";
            }
            String className;

            if (pkg.endsWith("_client")) {
                String serverPkg = pkg.substring(0, pkg.length() - "_client".length());
                className = resolveClassName(serverPkg);
                if (className == null) {
                    System.out.println("Unknown exercise.\n");
                    continue;
                }
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

            className = resolveClassName(pkg);
            if (className == null) {
                System.out.println("Unknown exercise number.\n");
                continue;
            }

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
        System.out.println("  5_2_1 - Datagram Time Client");
        System.out.println("  6_4_1 - RMI Chat");
        System.out.println("  0   - Exit");
        System.out.println("==============================");
    }

    private static String resolveClassName(String pkg) {
        switch (pkg) {
            case "1":     return "URLInfo";
            case "2":     return "URLBrowser";
            case "4_3_1": return "SquareServer";
            case "4_3_2": return "TrigServer";
            case "4_4":   return "HttpServer";
            case "4_5_1": return "MultiServer";
            case "5_2_1": return "DatagramTimeClient";
            case "6_4_1": return "RmiChat";
            default:      return null;
        }
    }

    private static int getPort(String pkg) {
        switch (pkg) {
            case "4_3_2": return 35001;
            default:      return 35000;
        }
    }
}
