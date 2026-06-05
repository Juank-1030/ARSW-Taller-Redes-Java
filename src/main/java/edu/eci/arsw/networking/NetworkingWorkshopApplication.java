package edu.eci.arsw.networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetworkingWorkshopApplication {

    // este es el punto de entrada principal de la aplicación Spring Boot
    // aunque en este taller no se usa directamente, está aquí como
    // requisito de la estructura del proyecto Maven
	public static void main(String[] args) {
		SpringApplication.run(NetworkingWorkshopApplication.class, args);
	}

}
