package edu.eci.arsw.networking.ejercicio2;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLBrowser {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a URL: ");
        String urlString = scanner.nextLine();

        try {
            URL url = new URL(urlString);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
                 PrintWriter writer = new PrintWriter(
                         new FileWriter("resultado.html"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.println(line);
                }

                System.out.println(
                        "Content saved to resultado.html");
            }

        } catch (MalformedURLException e) {
            System.err.println(
                    "Malformed URL: " + e.getMessage());

        } catch (IOException e) {
            System.err.println(
                    "Error reading URL: " + e.getMessage());
        }
    }
}
