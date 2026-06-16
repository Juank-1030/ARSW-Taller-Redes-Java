# Plan de Trabajo — Taller ARSW Redes Java

## Índice

1. [Ejercicio 1 — URL Info Printer](#ejercicio-1--url-info-printer)
   - [Clase: `URLInfo`](#clase-urlinfo)
   - 🌐 [Caso real — Validador de enlaces](#-caso-de-uso-real--validador-de-enlaces-link-checker)
2. [Ejercicio 2 — URL Browser](#ejercicio-2--url-browser)
   - [Clase: `URLBrowser`](#clase-urlbrowser)
   - 🌐 [Caso real — Descargador de documentación offline](#-caso-de-uso-real--descargador-de-documentación-técnica-offline)
3. [Ejercicio 4.3.1 — Square Server](#ejercicio-431--square-server)
   - [Clase: `SquareServer`](#clase-squareserver)
   - 🌐 [Caso real — Validador de tarjetas de crédito](#-caso-de-uso-real--servicio-de-validación-de-tarjetas-de-crédito-módulo-luhn)
4. [Ejercicio 4.3.2 — Trig Server](#ejercicio-432--trig-server)
   - [Clase: `TrigServer`](#clase-trigserver)
   - 🌐 [Caso real — Cálculos estructurales](#-caso-de-uso-real--servidor-de-cálculos-estructurales-para-ingeniería-civil)
5. [Ejercicio 4.4 — HttpServer básico](#ejercicio-44--httpserver-basico)
   - [Clase: `HttpServer`](#clase-httpserver)
   - 🌐 [Caso real — Health Dashboard](#-caso-de-uso-real--página-de-estado-de-servicios-internos-health-check-dashboard)
6. [Ejercicio 4.5.1 — Web Server multi-solicitud](#ejercicio-451--web-server-multi-solicitud)
   - [Clase: `MultiServer`](#clase-multiserver)
   - 🌐 [Caso real — Servidor de assets para dev](#-caso-de-uso-real--servidor-de-assets-estáticos-para-una-aplicación-web-corporativa)
7. [Ejercicio 5.2.1 — Datagram Time Client](#ejercicio-521--datagram-time-client)
   - [Clase: `DatagramTimeServer`](#clase-datagramtimeserver)
   - [Clase: `DatagramTimeClient`](#clase-datagramtimeclient)
   - 🌐 [Caso real — Monitoreo de sensores IoT](#-caso-de-uso-real--sistema-de-monitoreo-de-sensores-iot-temperatura-y-humedad)
8. [Ejercicio 6.4.1 — RMI Chat](#ejercicio-641--rmi-chat)
   - [Clase: `Chat` (Interfaz Remota)](#clase-chat-interfaz-remota)
   - [Clase: `RmiChat`](#clase-rmichat)
   - 🌐 [Caso real — Notificaciones bancarias](#-caso-de-uso-real--sistema-de-notificaciones-push-para-cajeros-bancarios)

---

## Ejercicio 1 — URL Info Printer ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio1`
**Archivo:** `URLInfo.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio1/URLInfo.java`

### Requisito

> Escriba un programa en el cual usted cree un objeto URL e imprima en pantalla cada uno de los datos que retornan los 8 métodos de la sección anterior.

### Métodos de `java.net.URL` a utilizar

| # | Método | Retorna | Ejemplo |
|---|--------|---------|---------|
| 1 | `getProtocol()` | Protocolo | `"http"` |
| 2 | `getAuthority()` | Autoridad (host:puerto) | `"ldbn.escuelaing.edu.co:80"` |
| 3 | `getHost()` | Host | `"ldbn.escuelaing.edu.co"` |
| 4 | `getPort()` | Puerto (`-1` si no está explícito) | `80` |
| 5 | `getPath()` | Ruta | `"/index.html"` |
| 6 | `getQuery()` | Query string | `"query=value"` |
| 7 | `getFile()` | Path + Query | `"/index.html?query=value"` |
| 8 | `getRef()` | Fragmento/ancla | `"seccion"` |

### URL de prueba sugerida

```
http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion
```

### Clase `URLInfo`

```java
package edu.eci.arsw.networking.ejercicio1;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL(
                "http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion"
            );

            System.out.println("URL: " + url);
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Authority: " + url.getAuthority());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("File: " + url.getFile());
            System.out.println("Ref: " + url.getRef());

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        }
    }
}
```

### Explicación detallada

**`URL url = new URL(String)`** — El constructor de `java.net.URL` parsea una cadena de texto en sus componentes individuales. Si la cadena no tiene formato URL válido, lanza `MalformedURLException`. Internamente, el constructor separa cada parte usando las reglas del estándar RFC 2396.

Cada método `getXxx()` extrae una parte específica de la URL parseada:
- `getProtocol()` → el esquema (http, https, ftp, etc.)
- `getAuthority()` → combinación de usuario (si existe), host y puerto
- `getHost()` → solo el nombre del host (resuelve si es necesario)
- `getPort()` → el número de puerto, o `-1` si no se especificó
- `getPath()` → la ruta del recurso sin query ni fragmento
- `getQuery()` → todo lo que va después de `?` (antes de `#`), o `null` si no hay
- `getFile()` → `getPath()` + `"?"` + `getQuery()` (o solo el path si no hay query)
- `getRef()` → todo lo que va después de `#`, o `null` si no hay fragmento

### Salida esperada

```
URL: http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion
Protocol: http
Authority: ldbn.escuelaing.edu.co:80
Host: ldbn.escuelaing.edu.co
Port: 80
Path: /index.html
Query: query=value
File: /index.html?query=value
Ref: seccion
```

### Verificación

- Ejecutar `URLInfo.main()` desde IDE
- O con Maven: `mvn compile exec:java -Dexec.mainClass="edu.eci.arsw.networking.ejercicio1.URLInfo"`

### Notas

- `getPort()` retorna `-1` si el puerto no está explícito en la URL
- `getQuery()` retorna `null` si la URL no tiene query string
- `getRef()` retorna `null` si la URL no tiene fragmento `#`
- `getFile()` es la combinación de `getPath()` + `"?"` + `getQuery()`

---

### 🌐 Caso de uso real — Validador de enlaces (Link Checker)

**Escenario:** Un equipo de contenido web necesita verificar que todos los enlaces en sus artículos no estén rotos antes de publicar. Cada semana revisan cientos de URLs. En lugar de hacerlo manualmente, usan un programa que parsea cada URL, la valida e intenta conectarse para verificar que existe.

```java
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;

public class LinkChecker {

    public static void checkLink(String urlString) {
        try {
            URL url = new URL(urlString);

            // Verifica que el protocolo sea soportado
            String protocol = url.getProtocol();
            if (!"http".equals(protocol) && !"https".equals(protocol)) {
                System.out.println("[SKIP] Protocolo no soportado: " + protocol);
                return;
            }

            // Intenta conectar para verificar que el host existe
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(5000);
            conn.connect();

            int code = conn.getResponseCode();
            String status = (code >= 200 && code < 400) ? "[OK]" : "[BROKEN]";
            System.out.println(status + " " + code + " " + urlString);

            conn.disconnect();

        } catch (MalformedURLException e) {
            System.out.println("[INVALID] URL mal formada: " + urlString);
        } catch (IOException e) {
            System.out.println("[BROKEN] No se pudo conectar: " + urlString);
        }
    }

    public static void main(String[] args) {
        List<String> links = List.of(
            "https://www.escuelaing.edu.co",
            "https://www.google.com/noexiste",
            "htp://malformada",
            "https://www.wikipedia.org"
        );

        System.out.println("=== Verificando enlaces ===\n");
        for (String link : links) {
            checkLink(link);
        }
    }
}
```

**Cómo funciona:**
- Toma una lista de URLs y para cada una extrae el protocolo, el host y el puerto usando los métodos de `java.net.URL`
- Valida que el protocolo sea HTTP/HTTPS antes de proseguir
- Abre una conexión `HttpURLConnection` con método `HEAD` (solo obtiene cabeceras, no el cuerpo) para verificar que el recurso existe sin descargarlo
- Clasifica cada enlace como `[OK]` (código 2xx/3xx), `[BROKEN]` (4xx/5xx o error de conexión) o `[INVALID]` (URL mal formada)
- Útil en integración continua (CI/CD) para detectar enlaces rotos automáticamente antes de cada despliegue

---

## Ejercicio 2 — URL Browser ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio2`
**Archivo:** `URLBrowser.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio2/URLBrowser.java`

### Requisito

> Escriba una aplicación browser que pregunte una dirección URL al usuario y que lea datos de esa dirección y que los almacene en un archivo con el nombre `resultado.html`. Luego intente ver este archivo en el navegador.

### Clase `URLBrowser`

```java
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

                System.out.println("Content saved to resultado.html");
            }

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());

        } catch (IOException e) {
            System.err.println("Error reading URL: " + e.getMessage());
        }
    }
}
```

### Explicación detallada

**Concepto:** Este programa actúa como un "cliente HTTP mínimo". Descarga el contenido de una página web usando la clase `URL` de Java y lo guarda en un archivo local.

**Flujo paso a paso:**

1. **`Scanner`** — Lee la URL ingresada por el usuario desde la consola.

2. **`new URL(urlString)`** — Construye un objeto `URL`. Si la cadena no es una URL válida, se lanza `MalformedURLException`.

3. **`url.openStream()`** — Abre una conexión a la URL y devuelve un `InputStream` para leer los datos del servidor. Por debajo, `URL.openStream()` hace lo siguiente:
   - Determina el protocolo (http, https, ftp, etc.)
   - Abre una conexión TCP al servidor (resuelve el DNS si es necesario)
   - Envía una petición HTTP GET automáticamente
   - Devuelve el stream del cuerpo de la respuesta

4. **`BufferedReader`** — Envuelve el `InputStreamReader` para leer línea por línea de forma eficiente.

5. **`PrintWriter` con `FileWriter`** — Escribe cada línea leída en un archivo `resultado.html` en el directorio actual.

6. **`try-with-resources`** — Las líneas 25-28 usan la sintaxis try-with-resources, lo que garantiza que tanto el `BufferedReader` como el `PrintWriter` se cierren automáticamente al salir del bloque, incluso si ocurre una excepción.

**Manejo de errores:**
- `MalformedURLException` — la URL ingresada no tiene el formato correcto (faltan `://`, caracteres ilegales, etc.)
- `IOException` — problemas de red (servidor caído, DNS no resuelve, timeout) o problemas de escritura (disco lleno, permisos)

### Ejecución

```bash
run_ejercicio2.bat
```

O con Maven:

```bash
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio2.URLBrowser"
```

### Verificación

1. Ejecutar el programa
2. Ingresar: `http://example.com`
3. Abrir `resultado.html` en el navegador — debe mostrar la página de Example Domain

---

### 🌐 Caso de uso real — Descargador de documentación técnica offline

**Escenario:** Un desarrollador viaja frecuentemente en avión sin internet. Quiere descargar páginas completas de documentación técnica (JavaDoc, MDN, etc.) para consultarlas offline. Construye una herramienta que descarga recursivamente páginas y las guarda localmente.

```java
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

public class DocOfflineDownloader {

    public static void downloadPage(String urlString, String outputDir) {
        try {
            URL url = new URL(urlString);

            // Genera un nombre de archivo a partir de la URL
            String fileName = url.getHost() + url.getPath()
                    .replace("/", "_")
                    .replaceAll("[^a-zA-Z0-9._-]", "_");
            if (fileName.isEmpty()) fileName = "index";
            if (!fileName.endsWith(".html")) fileName += ".html";

            Path outputPath = Path.of(outputDir, fileName);
            Files.createDirectories(outputPath.getParent());

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
                 PrintWriter writer = new PrintWriter(
                         new FileWriter(outputPath.toFile()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.println(line);
                }
            }

            System.out.println("[OK] " + urlString + " → " + outputPath);

        } catch (MalformedURLException e) {
            System.err.println("[INVALID] " + urlString);
        } catch (IOException e) {
            System.err.println("[ERROR] " + urlString + " — " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String outputDir = "offline_docs";
        List<String> docs = List.of(
            "https://docs.oracle.com/en/java/javase/21/",
            "https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/URL.html"
        );

        System.out.println("=== Descargando documentacion offline ===\n");
        for (String doc : docs) {
            downloadPage(doc, outputDir);
        }
        System.out.println("\nDocumentos guardados en: " + outputDir);
    }
}
```

**Cómo funciona:**
- Itera sobre una lista de URLs de documentación técnica
- Para cada URL, genera un nombre de archivo único basado en el host y la ruta
- Usa `url.openStream()` para leer el contenido igual que el URLBrowser
- Guarda cada página en una carpeta local organizada
- Podría extenderse para descargar también CSS, JS e imágenes referenciadas en las páginas (parsing HTML con expresiones regulares o Jsoup)
- El desarrollador puede abrir los archivos HTML localmente en su navegador sin conexión a internet

---

## Ejercicio 4.3.1 — Square Server ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_1`
**Archivo:** `SquareServer.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_3_1/SquareServer.java`

### Requisito

> Escriba un servidor que reciba un número y responda el cuadrado de este número.

### Clase `SquareServer`

```java
package edu.eci.arsw.networking.ejercicio4_3_1;

import java.net.*;
import java.io.*;

public class SquareServer {

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

            try {
                double number = Double.parseDouble(inputLine);
                outputLine = String.valueOf(number * number);
            } catch (NumberFormatException e) {
                outputLine = "Error: '" + inputLine + "' no es un numero valido.";
            }

            out.println(outputLine);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
```

### Explicación detallada

**Concepto:** Este es un servidor TCP que recibe números a través de un socket y devuelve su cuadrado. Es la versión más básica de una arquitectura cliente-servidor.

**Flujo paso a paso:**

1. **`new ServerSocket(35000)`** — Crea un socket de servidor vinculado al puerto 35000. El sistema operativo reserva este puerto para que solo esta aplicación pueda recibir conexiones entrantes. Si el puerto ya está ocupado, se lanza `IOException`.

2. **`serverSocket.accept()`** — El servidor se **bloquea** (se queda esperando) hasta que un cliente se conecte. Cuando un cliente establece conexión, `accept()` devuelve un objeto `Socket` que representa la conexión con ese cliente específico.

3. **`PrintWriter` con auto-flush** — `new PrintWriter(socket.getOutputStream(), true)` crea un flujo de salida para enviar datos al cliente. El parámetro `true` activa el auto-flush: cada vez que se llama a `println()`, los datos se envían inmediatamente por la red.

4. **`BufferedReader`** — `new BufferedReader(new InputStreamReader(socket.getInputStream()))` crea un flujo de entrada para leer los datos que envía el cliente línea por línea.

5. **Bucle `while ((inputLine = in.readLine()) != null)`** — Lee una línea de texto enviada por el cliente. `readLine()` se bloquea hasta que recibe un `\n` o el cliente cierra la conexión (en cuyo caso retorna `null`).

6. **`Double.parseDouble(inputLine)`** — Convierte el texto recibido a un número de tipo `double`. Si el texto no es un número válido, lanza `NumberFormatException`.

7. **`number * number`** — Calcula el cuadrado del número.

8. **`out.println(outputLine)`** — Envía la respuesta al cliente.

9. **Condición de salida `"Bye.".equals(inputLine)`** — Si el cliente envía exactamente `"Bye."`, el servidor sale del bucle y cierra la conexión.

10. **Cierre de recursos** — Se cierran `out`, `in`, `clientSocket` y `serverSocket` en ese orden para liberar los recursos del sistema.

**Flujo de datos:**
```
Cliente: envía "5\n"
Servidor: recibe "5", calcula 5*5 = 25.0
Servidor: envía "25.0\n"
```

### Ejecución

```bash
run_ejercicio.bat 4_3_1
```

O con Maven:

```bash
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio4_3_1.SquareServer"
```

### Verificación

```bash
# Desde otra terminal
echo 5 | ncat localhost 35000
# Esperado: 25.0
```

---

### 🌐 Caso de uso real — Servicio de validación de tarjetas de crédito (módulo Luhn)

**Escenario:** Una pasarela de pagos necesita validar números de tarjetas de crédito en tiempo real. Cada vez que un comercio ingresa un número de tarjeta, un microservicio TCP verifica si el número es válido según el algoritmo de Luhn antes de enviarlo al banco. Esto permite detectar errores de tipeo al instante.

```java
import java.net.*;
import java.io.*;

public class LuhnValidatorServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(36000);
        System.out.println("Luhn Validator corriendo en puerto 36000...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);

            if ("Bye.".equals(inputLine))
                break;

            // Limpia el número: solo dígitos
            String digits = inputLine.replaceAll("[^0-9]", "");

            if (digits.length() < 13 || digits.length() > 19) {
                outputLine = "INVALIDO: longitud incorrecta";
            } else if (!luhnCheck(digits)) {
                outputLine = "INVALIDO: no pasa algoritmo Luhn";
            } else {
                String brand = detectBrand(digits);
                outputLine = "VALIDO: " + brand;
            }

            out.println(outputLine);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    // Algoritmo de Luhn: duplica cada segundo dígito desde la derecha,
    // suma los dígitos resultantes, verifica que el total sea múltiplo de 10
    private static boolean luhnCheck(String digits) {
        int sum = 0;
        boolean alternate = false;

        for (int i = digits.length() - 1; i >= 0; i--) {
            int n = digits.charAt(i) - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    private static String detectBrand(String digits) {
        if (digits.startsWith("4")) return "Visa";
        if (digits.startsWith("5")) return "Mastercard";
        if (digits.startsWith("34") || digits.startsWith("37")) return "Amex";
        if (digits.startsWith("6")) return "Discover";
        return "Desconocida";
    }
}
```

**Cómo funciona:**
- Similar al SquareServer, pero en lugar de elevar al cuadrado, ejecuta el algoritmo de Luhn
- **Paso 1:** Limpia el número eliminando espacios y guiones (`"4532 1234 5678 9012"` → `"4532123456789012"`)
- **Paso 2:** Verifica que tenga entre 13 y 19 dígitos (estándar ISO/IEC 7812)
- **Paso 3:** Aplica el algoritmo de Luhn: desde el último dígito hacia atrás, duplica cada segundo dígito y suma todos; el total debe ser múltiplo de 10
- **Paso 4:** Detecta la marca (Visa, Mastercard, Amex, Discover) según el prefijo
- Responde al cliente con `"VALIDO: Visa"` o `"INVALIDO: no pasa algoritmo Luhn"`

**Ejemplo de uso con ncat:**
```bash
echo "4532 1234 5678 9012" | ncat localhost 36000
# RESPUESTA: VALIDO: Visa
```

---

## Ejercicio 4.3.2 — Trig Server ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_2`
**Archivo:** `TrigServer.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_3_2/TrigServer.java`

### Requisito

> Escriba un servidor que pueda recibir un número y responda con una operación trigonométrica sobre este número. Puede recibir `fun:sin`, `fun:cos`, `fun:tan` para cambiar la operación. Por defecto empieza calculando el coseno.

### Clase `TrigServer`

```java
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
```

### Explicación detallada

**Concepto:** Extensión del SquareServer que ahora permite cambiar la operación matemática en tiempo de ejecución mediante comandos de texto. El servidor mantiene un estado (`currentOp`) que determina qué función trigonométrica aplicar.

**Diferencias clave con SquareServer:**

1. **Variable de estado `currentOp`** — Es una variable estática que guarda la operación actual. Empieza en `"cos"` por defecto. El cliente puede cambiarla en cualquier momento.

2. **Comando `fun:X`** — El servidor detecta si el mensaje entrante empieza con `"fun:"`. Si es así, extrae el nombre de la función con `substring(4)` y lo valida (solo acepta `sin`, `cos`, `tan`).

3. **Switch de operaciones** — Cuando el mensaje no es un comando, se asume que es un número. Se usa un `switch` sobre `currentOp` para elegir entre `Math.sin()`, `Math.cos()`, o `Math.tan()`.

4. **`Math.sin/cos/tan`** — Estas funciones de `java.lang.Math` reciben el ángulo en **radianes** y devuelven el resultado como `double`.

**Flujo de datos con cambio de operación:**
```
Cliente: envía "0\n"
Servidor: calcula cos(0) = 1.0, envía "1.0\n"

Cliente: envía "fun:sin\n"
Servidor: cambia operación a sin, envía "Operacion cambiada a sin\n"

Cliente: envía "0\n"
Servidor: calcula sin(0) = 0.0, envía "0.0\n"
```

### Verificación esperada

| Cliente envía | Servidor responde |
|---------------|-------------------|
| `0` | `1.0` (cos(0)) |
| `1.5707963267948966` | `~0.0` (cos(π/2)) |
| `fun:sin` | `Operation changed to sin` |
| `0` | `0.0` (sin(0)) |
| `fun:tan` | `Operation changed to tan` |
| `0` | `0.0` (tan(0)) |
| `Bye.` | _(cierra conexión)_ |

### Ejecución

```bash
run_ejercicio.bat 4_3_2
```

O con Maven:

```bash
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio4_3_2.TrigServer"
```

---

### 🌐 Caso de uso real — Servidor de cálculos estructurales para ingeniería civil

**Escenario:** Una empresa de construcción usa sensores en puentes que miden ángulos de inclinación. Los ingenieros necesitan calcular fuerzas de tensión, compresión y carga máxima en tiempo real. Un servidor central recibe el ángulo en grados y el tipo de cálculo requerido, y responde con el resultado.

```java
import java.net.*;
import java.io.*;

public class StructuralCalcServer {

    private static String currentCalc = "tension";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(37000);
        System.out.println("Servidor de calculos estructurales en puerto 37000...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);

            if ("Bye.".equals(inputLine))
                break;

            if (inputLine.startsWith("calc:")) {
                // Cambia el tipo de cálculo: tension, compresion, carga_max
                String calc = inputLine.substring(5).trim().toLowerCase();
                if ("tension".equals(calc) || "compresion".equals(calc)
                        || "carga_max".equals(calc)) {
                    currentCalc = calc;
                    outputLine = "Calculo cambiado a " + calc;
                } else {
                    outputLine = "Error: calculo desconocido '" + calc + "'";
                }
            } else {
                // Se espera: "angulo_en_grados,masa_kg"
                try {
                    String[] parts = inputLine.split(",");
                    double anguloGrados = Double.parseDouble(parts[0].trim());
                    double masa = Double.parseDouble(parts[1].trim());
                    double radianes = Math.toRadians(anguloGrados);

                    double resultado;
                    switch (currentCalc) {
                        case "tension":
                            // T = m * g * sin(θ)
                            resultado = masa * 9.81 * Math.sin(radianes);
                            outputLine = "Tension: " + String.format("%.2f", resultado) + " N";
                            break;
                        case "compresion":
                            // C = m * g * cos(θ)
                            resultado = masa * 9.81 * Math.cos(radianes);
                            outputLine = "Compresion: " + String.format("%.2f", resultado) + " N";
                            break;
                        case "carga_max":
                            // Carga máxima = m * g * (sin(θ) + cos(θ))
                            resultado = masa * 9.81 * (Math.sin(radianes) + Math.cos(radianes));
                            outputLine = "Carga max: " + String.format("%.2f", resultado) + " N";
                            break;
                        default:
                            outputLine = "Error: calculo no definido";
                    }

                } catch (Exception e) {
                    outputLine = "Error: formato esperado 'angulo,masa'";
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
```

**Cómo funciona:**
- Extiende el concepto del TrigServer a un dominio real de ingeniería civil
- Recibe comandos `calc:tension`, `calc:compresion` o `calc:carga_max` para cambiar el tipo de cálculo
- Recibe datos en formato `"angulo_en_grados,masa_en_kg"` y parsea ambos valores
- Convierte grados a radianes con `Math.toRadians()` antes de usar las funciones trigonométricas
- Aplica fórmulas físicas reales:
  - **Tensión:** `T = m * g * sin(θ)` — fuerza a lo largo del cable/puente
  - **Compresión:** `C = m * g * cos(θ)` — fuerza perpendicular a la superficie
  - **Carga máxima:** Combinación de ambas fuerzas
- Responde con el resultado en Newtons (N) con dos decimales

**Ejemplo de uso con ncat:**
```bash
echo "calc:tension" | ncat localhost 37000
# RESPUESTA: Calculo cambiado a tension

echo "30,100" | ncat localhost 37000
# RESPUESTA: Tension: 490.50 N (100kg * 9.81 * sin(30°))
```

---

## Ejercicio 4.4 — HttpServer básico ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_4`
**Archivo:** `HttpServer.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_4/HttpServer.java`

### Requisito

> Implemente el servidor web del código provisto e intente conectarse desde el browser.

### Clase `HttpServer`

```java
package edu.eci.arsw.networking.ejercicio4_4;

import java.net.*;
import java.io.*;

public class HttpServer {

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
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();

        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out =
                new PrintWriter(
                        clientSocket.getOutputStream(),
                        true);

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {

            System.out.println("Received: " + inputLine);

            if (!in.ready()) {
                break;
            }
        }

        outputLine =
                "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "My Web Site"
                        + "</body>"
                        + "</html>"
                        + inputLine;

        out.println(outputLine);

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
```

### Explicación detallada

**Concepto:** Este programa implementa un servidor HTTP mínimo que entiende el protocolo de la web. Cuando un navegador se conecta, el servidor lee la petición HTTP, la imprime en consola, y responde con una página HTML hardcodeada.

**¿Qué es HTTP?** HTTP (HyperText Transfer Protocol) es el protocolo que usa la web. Un cliente (navegador) envía una petición como:
```
GET / HTTP/1.1
Host: localhost:35000
User-Agent: Mozilla/5.0 ...
```
Y el servidor responde con:
```
HTTP/1.1 200 OK
Content-Type: text/html

<!DOCTYPE html>...
```

**Flujo paso a paso:**

1. **`ServerSocket(35000)`** — Escucha conexiones HTTP entrantes en el puerto 35000.

2. **Lectura de la petición HTTP** — El servidor lee todas las líneas que envía el navegador usando `readLine()`. La condición `in.ready()` detecta cuándo el navegador ha terminado de enviar encabezados. `ready()` retorna `false` cuando el buffer de entrada está vacío (el navegador está esperando la respuesta).

3. **Construcción de la respuesta HTTP** — La respuesta debe cumplir con el formato del protocolo:
   - **Primera línea:** `HTTP/1.1 200 OK` — versión del protocolo + código de estado + mensaje
   - **Encabezados:** `Content-Type: text/html\r\n` — indica al navegador que el contenido es HTML
   - **Línea en blanco:** `\r\n` — marca el final de los encabezados y el inicio del cuerpo
   - **Cuerpo:** El HTML que se mostrará en el navegador

4. **`out.println(outputLine)`** — Envía la respuesta completa al navegador.

5. **`inputLine` al final del HTML** — La última línea leída de la petición se incluye en el HTML como curiosidad.

**Formato de una respuesta HTTP:**
```
HTTP/1.1 200 OK\r\n
Content-Type: text/html\r\n
Content-Length: 123\r\n
\r\n
<!DOCTYPE html>
<html>...
```

### Verificación

Abrir `http://localhost:35000` en el navegador → debe mostrar "My Web Site" con título "Title of the document". En la consola del servidor se imprimirán todas las líneas de la petición HTTP que envió el navegador.

---

### 🌐 Caso de uso real — Página de estado de servicios internos (Health Check Dashboard)

**Escenario:** Un equipo de DevOps necesita un dashboard interno que muestre el estado de todos los microservicios de la empresa (base de datos, API de autenticación, servidor de archivos, etc.). Cada servicio expone un endpoint de health check, y el dashboard consolida toda la información en una página web.

```java
import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HealthCheckServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(38000);
        System.out.println("Health Dashboard en http://localhost:38000");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            while ((in.readLine()) != null) {
                if (!in.ready()) break;
            }

            // Simula verificación de servicios internos
            String timestamp = LocalTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String dbStatus = checkService("Base de Datos", true);
            String apiStatus = checkService("API Auth", true);
            String filesStatus = checkService("Servidor Archivos", false);

            String html = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Refresh: 10\r\n"  // Recarga automática cada 10s
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    + "<html><head><meta charset='UTF-8'>"
                    + "<title>Health Dashboard</title>"
                    + "<style>"
                    + "body{font-family:Arial;padding:40px;background:#1a1a2e;color:#eee}"
                    + ".ok{color:#4ecca3;font-weight:bold}"
                    + ".fail{color:#ff6b6b;font-weight:bold}"
                    + ".card{background:#16213e;padding:20px;margin:10px 0;border-radius:8px}"
                    + "h1{color:#e94560}</style>"
                    + "</head><body>"
                    + "<h1>🔍 Health Dashboard</h1>"
                    + "<p>Ultima actualizacion: " + timestamp + "</p>"
                    + "<div class='card'>" + dbStatus + "</div>"
                    + "<div class='card'>" + apiStatus + "</div>"
                    + "<div class='card'>" + filesStatus + "</div>"
                    + "<p><small>Recarga automatica cada 10s</small></p>"
                    + "</body></html>";

            out.println(html);
            out.close();
            in.close();
            clientSocket.close();
        }
    }

    private static String checkService(String name, boolean isUp) {
        String status = isUp ? "<span class='ok'>● ONLINE</span>"
                             : "<span class='fail'>● OFFLINE</span>";
        return "<h3>" + name + "</h3><p>Estado: " + status + "</p>";
    }
}
```

**Cómo funciona:**
- Similar al HttpServer básico, pero con un bucle `while (true)` para atender múltiples clientes
- Cada vez que un navegador solicita la página, el servidor verifica el estado simulado de cada servicio
- Incluye la cabecera `Refresh: 10` que hace que el navegador recargue automáticamente la página cada 10 segundos
- Genera una página HTML con estilo dark-mode moderna usando CSS inline
- En un escenario real, `checkService()` haría peticiones HTTP reales a cada microservicio o ejecutaría `curl`/`ping` para verificar disponibilidad
- Muestra la hora de la última actualización para que el equipo DevOps sepa qué tan reciente es la información

---

## Ejercicio 4.5.1 — Web Server multi-solicitud ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_5_1`
**Archivo:** `MultiServer.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_5_1/MultiServer.java`

### Requisito

> Escriba un servidor web que soporte múltiples solicitudes seguidas (no concurrentes). El servidor debe retornar todos los archivos solicitados, incluyendo páginas HTML e imágenes.

### Clase `MultiServer`

```java
package edu.eci.arsw.networking.ejercicio4_5_1;

import java.net.*;
import java.io.*;
import java.nio.file.*;

public class MultiServer {

    private static final String WWW_ROOT = "www";

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        System.out.println("Listo para recibir ...");

        while (true) {

            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true);

            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()));

            BufferedOutputStream dataOut =
                    new BufferedOutputStream(
                            clientSocket.getOutputStream());

            String inputLine;
            String requestPath = null;

            while ((inputLine = in.readLine()) != null) {

                System.out.println("Received: " + inputLine);

                if (inputLine.startsWith("GET ")) {
                    requestPath = inputLine.split(" ")[1];
                }

                if (!in.ready()) {

                    if (requestPath == null) {
                        String body = "<!DOCTYPE html>"
                                + "<html><body><h1>400 Bad Request</h1></body></html>";
                        byte[] bodyData = body.getBytes();
                        out.write("HTTP/1.1 400 Bad Request\r\n"
                                + "Content-Type: text/html\r\n"
                                + "Content-Length: " + bodyData.length + "\r\n"
                                + "\r\n");
                        out.flush();
                        dataOut.write(bodyData);
                        dataOut.flush();

                    } else {

                        String filePath = requestPath.equals("/")
                                ? "/index.html" : requestPath;
                        File file = new File(WWW_ROOT + filePath);

                        if (file.exists() && !file.isDirectory()) {

                            byte[] fileData = Files.readAllBytes(file.toPath());
                            String contentType = getContentType(file.getName());

                            out.write("HTTP/1.1 200 OK\r\n"
                                    + "Content-Type: " + contentType + "\r\n"
                                    + "Content-Length: " + fileData.length + "\r\n"
                                    + "Connection: keep-alive\r\n"
                                    + "\r\n");
                            out.flush();
                            dataOut.write(fileData);
                            dataOut.flush();

                            System.out.println("Served: " + file.getName());

                        } else {
                            String body = "<!DOCTYPE html>"
                                    + "<html><body><h1>404 Not Found</h1>"
                                    + "<p>" + requestPath + "</p></body></html>";
                            byte[] bodyData = body.getBytes();
                            out.write("HTTP/1.1 404 Not Found\r\n"
                                    + "Content-Type: text/html\r\n"
                                    + "Content-Length: " + bodyData.length + "\r\n"
                                    + "\r\n");
                            out.flush();
                            dataOut.write(bodyData);
                            dataOut.flush();
                        }
                    }

                    requestPath = null;
                }
            }

            out.close();
            in.close();
            dataOut.close();
            clientSocket.close();
        }
    }

    private static String getContentType(String fileName) {
        String ext = fileName.substring(
                fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "html": case "htm":  return "text/html";
            case "css":               return "text/css";
            case "js":                return "application/javascript";
            case "json":              return "application/json";
            case "png":               return "image/png";
            case "jpg": case "jpeg":  return "image/jpeg";
            case "gif":               return "image/gif";
            case "svg":               return "image/svg+xml";
            case "ico":               return "image/x-icon";
            default:                  return "application/octet-stream";
        }
    }
}
```

### Explicación detallada

**Concepto:** Este servidor web mejora el ejercicio 4.4 para servir archivos estáticos reales (HTML, imágenes, CSS) desde una carpeta `www/`. Soporta múltiples solicitudes HTTP en la misma conexión (keep-alive) y múltiples clientes de forma secuencial.

**Diferencias clave con `HttpServer`:**

1. **Bucle exterior `while (true)`** — Permite que el servidor acepte múltiples clientes uno tras otro sin reiniciar. Cada vez que un navegador se conecta, `accept()` desbloquea y se procesa la conexión. Cuando el navegador se desconecta, el bucle vuelve a `accept()`.

2. **Bucle interior para keep-alive** — Lee múltiples peticiones HTTP del mismo cliente mientras mantenga la conexión abierta. El navegador moderno envía varias peticiones (una para el HTML, otra para el CSS, otra para las imágenes) por la misma conexión.

3. **Parseo de la línea `GET`** — Cuando el navegador solicita `GET /logo.svg HTTP/1.1`, se extrae la ruta `/logo.svg` para saber qué archivo servir.

4. **`BufferedOutputStream`** — Se usa un `BufferedOutputStream` además del `PrintWriter` porque los archivos binarios (imágenes) no pueden enviarse con `PrintWriter` (que maneja texto y puede corromper bytes). Los encabezados HTTP se envían con `PrintWriter` (texto), y el cuerpo del archivo se envía con `dataOut.write()` (bytes).

5. **Mapeo de rutas** — `"/"` se convierte en `"/index.html"` para que la raíz del servidor sirva la página principal.

6. **`Files.readAllBytes()`** — Lee todo el archivo en memoria de una sola vez. Esto funciona bien para archivos pequeños (típicos de una página web).

7. **Detección de tipo de contenido** — El método `getContentType()` determina el `Content-Type` (MIME type) según la extensión del archivo. Esto es esencial para que el navegador sepa cómo interpretar el contenido (HTML, imagen PNG, SVG, etc.).

8. **Códigos de estado HTTP:**
   - `200 OK` — el archivo existe y se sirve correctamente
   - `400 Bad Request` — no se pudo parsear la solicitud
   - `404 Not Found` — el archivo solicitado no existe

**Cabecera `Content-Length`:** Se calcula el tamaño exacto del archivo en bytes y se incluye en la respuesta. Esto permite al navegador saber cuándo ha terminado de recibir el contenido.

**Cabecera `Connection: keep-alive`:** Indica al navegador que puede reutilizar la misma conexión TCP para enviar más peticiones.

### Archivos de prueba

- `www/index.html` — página HTML principal
- `www/logo.svg` — imagen SVG de prueba

### Verificación

Abrir `http://localhost:35000` en el navegador → debe mostrar la página con el logo. El servidor queda vivo para múltiples solicitudes. Probar también `http://localhost:35000/noexiste.html` para ver la página 404.

---

### 🌐 Caso de uso real — Servidor de assets estáticos para una aplicación web corporativa

**Escenario:** Una empresa desarrolla una aplicación web React/Vue que en producción se sirve desde un CDN (CloudFront, Cloudflare). En desarrollo local, los programadores necesitan un servidor que sirva los archivos estáticos (HTML, JS, CSS, imágenes) exactamente como lo haría el CDN, para detectar problemas de ruta o MIME types antes de desplegar.

```java
import java.net.*;
import java.io.*;
import java.nio.file.*;

public class DevStaticServer {

    private static final String WWW_ROOT = "dist";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor de desarrollo en http://localhost:8080");
        System.out.println("Sirviendo archivos desde: " + WWW_ROOT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            BufferedOutputStream dataOut = new BufferedOutputStream(
                    clientSocket.getOutputStream());

            String inputLine;
            String requestPath = "/index.html";

            while ((inputLine = in.readLine()) != null) {
                System.out.println("  ← " + inputLine);
                if (inputLine.startsWith("GET ")) {
                    requestPath = inputLine.split(" ")[1];
                }
                if (!in.ready()) break;
            }

            // Soporte para SPA: si no tiene extensión, sirve index.html
            if (!requestPath.contains(".")) {
                requestPath = "/index.html";
            }

            File file = new File(WWW_ROOT + requestPath);

            if (file.exists() && !file.isDirectory()) {
                byte[] fileData = Files.readAllBytes(file.toPath());
                String contentType = getMimeType(file.getName());

                out.write("HTTP/1.1 200 OK\r\n"
                        + "Content-Type: " + contentType + "\r\n"
                        + "Content-Length: " + fileData.length + "\r\n"
                        + "Cache-Control: no-cache\r\n"  // Sin caché en desarrollo
                        + "\r\n");
                out.flush();
                dataOut.write(fileData);
                dataOut.flush();
                System.out.println("  → 200 " + requestPath + " (" + fileData.length + " bytes)");
            } else {
                String body = "<!DOCTYPE html><html><body>"
                        + "<h1>404 - Archivo no encontrado</h1>"
                        + "<p>Ruta solicitada: " + requestPath + "</p>"
                        + "<p>Raiz del servidor: " + new File(WWW_ROOT).getAbsolutePath() + "</p>"
                        + "</body></html>";
                byte[] bodyData = body.getBytes();
                out.write("HTTP/1.1 404 Not Found\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: " + bodyData.length + "\r\n"
                        + "\r\n");
                out.flush();
                dataOut.write(bodyData);
                dataOut.flush();
                System.out.println("  → 404 " + requestPath);
            }

            out.close();
            in.close();
            dataOut.close();
            clientSocket.close();
        }
    }

    private static String getMimeType(String fileName) {
        String ext = fileName.substring(
                fileName.lastIndexOf('.') + 1).toLowerCase();
        return switch (ext) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "json" -> "application/json";
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "svg" -> "image/svg+xml";
            case "ico" -> "image/x-icon";
            case "woff2" -> "font/woff2";
            case "ttf" -> "font/ttf";
            default -> "application/octet-stream";
        };
    }
}
```

**Cómo funciona:**
- Similar al MultiServer, pero adaptado específicamente para desarrollo web moderno
- Sirve archivos desde la carpeta `dist/` (carpeta típica de build de React/Vue/Angular)
- **Soporte para SPA (Single Page Application):** si la ruta solicitada no tiene extensión (como `/dashboard` o `/users`), sirve `index.html` para que el router del frontend maneje la navegación
- Incluye cabecera `Cache-Control: no-cache` para forzar al navegador a siempre pedir la versión más reciente durante desarrollo
- Soporta tipos MIME adicionales como `font/woff2` y `font/ttf` para tipografías web
- Muestra logs detallados de cada petición: método, ruta, código de estado y tamaño en bytes
- En producción, este servidor se reemplazaría por Nginx, Apache o un CDN, pero en desarrollo es rápido y portátil (no requiere instalar nada más que Java)

---

## Ejercicio 5.2.1 — Datagram Time Client

**Paquete:** `edu.eci.arsw.networking.ejercicio5_2_1`
**Archivos:**
- `DatagramTimeServer.java`
- `DatagramTimeClient.java`
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio5_2_1/`

### Requisito

> Utilizando datagramas escriba un programa que se conecte a un servidor que responde la hora actual en el servidor. El programa debe actualizar la hora cada 5 segundos según los datos del servidor. Si una hora no es recibida debe mantener la hora que tenía. Para la prueba se apagará el servidor y después de unos segundos se reactivará. El cliente debe seguir funcionando y actualizarse cuando el servidor esté nuevamente funcionando.

### Conceptos clave: UDP vs TCP

| Característica | TCP | UDP |
|----------------|-----|-----|
| Conexión | Orientado a conexión (handshake 3 vías) | Sin conexión — se envía directamente |
| Garantía de entrega | Sí (ACK + retransmisión) | No — el paquete puede perderse |
| Orden | Los datos llegan en orden | Sin orden garantizado |
| Estado | El servidor mantiene estado por cliente | Sin estado — cada datagrama es independiente |
| Overhead | Alto (cabeceras de 20+ bytes, ACKs) | Bajo (cabecera de 8 bytes) |
| Uso típico | Web (HTTP), email, transferencia de archivos | DNS, VoIP, gaming, streaming en vivo |

### Clase `DatagramTimeServer`

```java
package edu.eci.arsw.networking.ejercicio5_2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class DatagramTimeServer {

    private DatagramSocket socket;

    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }
    }

    public void startServer() {
        while (true) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet =
                        new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                String dString = new Date().toString();
                buf = dString.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                packet = new DatagramPacket(
                        buf, buf.length, address, port);

                socket.send(packet);

            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        DatagramTimeServer ds = new DatagramTimeServer();
        ds.startServer();
    }
}
```

**Explicación detallada:**

1. **`new DatagramSocket(4445)`** — Crea un socket UDP vinculado al puerto 4445. A diferencia de TCP (`ServerSocket`), un `DatagramSocket` no "escucha conexiones" sino que simplemente recibe paquetes que lleguen a ese puerto.

2. **`while (true)`** — El servidor corre indefinidamente hasta que se detenga manualmente (Ctrl+C).

3. **`socket.receive(packet)`** — Se bloquea hasta que llega un datagrama UDP. Cuando llega, llena el `DatagramPacket` con los datos y automáticamente guarda la dirección IP y puerto del remitente.

4. **`new Date().toString()`** — Genera la hora actual del sistema en formato texto (ej: `"Fri Jun 05 14:23:10 COT 2026"`).

5. **`dString.getBytes()`** — Convierte el String a bytes para enviarlo por la red.

6. **`packet.getAddress()` y `packet.getPort()`** — Obtiene la IP y puerto del cliente que envió la solicitud original.

7. **Nuevo `DatagramPacket` de respuesta** — Se construye con los bytes de la hora, más la dirección IP y puerto destino (el cliente).

8. **`socket.send(packet)`** — Envía el datagrama de respuesta. No hay confirmación de recepción; si el paquete se pierde, el servidor nunca lo sabrá.

### Clase `DatagramTimeClient`

```java
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

        String lastTime = "Waiting for server...";

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(3000);

            InetAddress address =
                    InetAddress.getByName("127.0.0.1");

            while (true) {

                try {
                    byte[] buf = new byte[256];

                    DatagramPacket packet =
                            new DatagramPacket(
                                    buf, buf.length,
                                    address, 4445);

                    socket.send(packet);

                    packet = new DatagramPacket(
                            buf, buf.length);

                    socket.receive(packet);

                    String received = new String(
                            packet.getData(), 0,
                            packet.getLength());

                    lastTime = received;
                    System.out.println("Date: " + received);

                } catch (SocketTimeoutException ex) {
                    System.out.println(
                            "Date: " + lastTime +
                            " (server offline)");
                }

                Thread.sleep(5000);
            }

        } catch (SocketException ex) {
            System.err.println("Could not create socket.");
        } catch (UnknownHostException ex) {
            System.err.println("Unknown host: 127.0.0.1");
        } catch (IOException ex) {
            System.err.println("IO error: " + ex.getMessage());
        }
    }
}
```

**Explicación detallada:**

1. **`new DatagramSocket()`** — Crea un socket UDP sin vincular a un puerto específico. El sistema operativo asigna un puerto efímero automáticamente.

2. **`socket.setSoTimeout(3000)`** — Establece un timeout de 3 segundos para `receive()`. Sin esto, si el servidor está caído, el cliente se quedaría bloqueado para siempre esperando una respuesta que nunca llegará.

3. **`InetAddress.getByName("127.0.0.1")`** — Resuelve la dirección del servidor (localhost). Como es una IP numérica, no requiere DNS.

4. **Bucle infinito** — El cliente envía una solicitud cada 5 segundos para siempre.

5. **Paquete de solicitud** — Se crea un `DatagramPacket` apuntando al servidor (`127.0.0.1:4445`). El buffer está vacío; el servidor no necesita datos en la solicitud.

6. **`socket.send(packet)`** — Envía el datagrama de solicitud. Como UDP no tiene conexión, esto siempre "funciona" incluso si el servidor no existe (el paquete se pierde silenciosamente).

7. **`socket.receive(packet)`** — Espera la respuesta del servidor. Si el servidor responde antes de 3 segundos, el paquete se llena con los datos. Si pasan 3 segundos sin respuesta, se lanza `SocketTimeoutException`.

8. **`new String(packet.getData(), 0, packet.getLength())`** — Convierte los bytes recibidos a String. Se usa `getLength()` en lugar de `getData().length` porque el buffer es de 256 bytes pero solo los primeros `getLength()` bytes contienen datos reales (el resto es basura del buffer).

9. **`SocketTimeoutException`** — Si el servidor no responde, se atrapa esta excepción y se imprime la última hora conocida con `"(server offline)"`.

10. **`Thread.sleep(5000)`** — Espera 5 segundos entre solicitudes.

### Entradas y salidas

| Componente | Entrada | Procesamiento | Salida |
|------------|---------|---------------|--------|
| **Servidor** | Datagrama UDP vacío desde cualquier cliente | `new Date().toString()` → hora actual | Datagrama UDP con string de hora |
| **Cliente** | Datagrama UDP con hora (o timeout 3s) | Extrae bytes → String; guarda en `lastTime` | Consola: `"Date: ..."` o `"Date: ... (server offline)"` |

### Diagrama de flujo temporal

```
Tiempo  CLIENTE                    SERVIDOR (puerto 4445)
  │       │                            │
  ├─ 0s ──┼── send(datagrama) ───────> │ receive() → desbloquea
  │       │                            │ new Date().toString()
  │       │<── send(datagrama+hora) ───│
  │       ├── ¡Recibió! → imprime hora │
  ├─ 1s ──┤                            │
  ├─ 2s ──┤                            │
  ├─ 3s ──┤                            │
  ├─ 4s ──┤                            │
  ├─ 5s ──┼── send(datagrama) ───────> │ receive()
  │       │<── send(datagrama+hora) ───│
  │       ├── ¡Recibió! → imprime hora │
  │       │                            │
  │  (Ctrl+C en servidor)             (servidor muere)
  │       │                            │
  ├─ 10s ─┼── send(datagrama) ───XXX   │ (nadie responde)
  │       ├── Timeout 3s
  │       ├── "server offline"
  ├─ 15s ─┼── send(datagrama) ───XXX   │
  │       ├── Timeout 3s
  │       ├── "server offline"
  │       │                            │
  │  (Reiniciar servidor)             (servidor vivo otra vez)
  │       │                            │
  ├─ 20s ─┼── send(datagrama) ───────> │ receive()
  │       │<── send(datagrama+hora) ───│
  │       ├── ¡Recibió! → imprime hora │
  │       ├── Se recuperó solo         │
```

### Ejecución

**Paso 1 — Compilar:**
```bash
mvn clean compile
```

**Paso 2 — Abrir dos terminales.**

Terminal 1 — Servidor:
```bash
run_ejercicio.bat 5_2_1
# Output: DatagramTimeServer listening on port 4445 ...
```

Terminal 2 — Cliente:
```bash
run_ejercicio.bat 5_2_1 client
# Output: Date: Fri Jun 05 14:23:10 COT 2026  (cada 5s)
```

**Paso 3 — Probar tolerancia a fallos:**
1. Servidor online → cliente recibe hora cada 5s
2. Ctrl+C en servidor → cliente muestra `(server offline)` pero sigue vivo
3. Reiniciar servidor → cliente se recupera solo en el próximo ciclo de 5s

### Verificación esperada

| Escenario | Cliente muestra |
|-----------|----------------|
| Servidor corriendo | `Date: Fri Jun 05 14:23:10 COT 2026` |
| Servidor apagado | `Date: Fri Jun 05 14:23:30 COT 2026 (server offline)` |
| Servidor reactivado | `Date: Fri Jun 05 14:23:47 COT 2026` (se recupera solo) |

### Notas

- El servidor usa `new Date().toString()` que devuelve la hora local del sistema.
- El timeout de 3s del cliente (`setSoTimeout`) es independiente del intervalo de 5s (`Thread.sleep`).
- `DatagramPacket.getData()` devuelve el buffer completo de 256 bytes, pero solo los primeros `getLength()` bytes contienen datos reales.
- El puerto 4445 es el estándar para este ejercicio. Si está ocupado, usar `netstat -ano | findstr :4445` y `taskkill /F /PID <PID>` para liberarlo.

---

### 🌐 Caso de uso real — Sistema de monitoreo de sensores IoT (temperatura y humedad)

**Escenario:** Un invernadero inteligente tiene decenas de sensores de temperatura y humedad distribuidos en diferentes zonas. Cada sensor envía lecturas cada 5 segundos por UDP a un servidor central. UDP es ideal aquí porque:
- Los sensores son dispositivos de bajo costo que no pueden mantener conexiones TCP largas
- Si un paquete se pierde, la siguiente lectura llegará 5s después (no hay datos críticos)
- Se minimiza el consumo de batería al no requerir handshake ni ACKs

```java
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IoTSensorMonitor {

    // Almacena la última lectura de cada sensor por su ID
    private static Map<String, String> sensorData = new HashMap<>();
    private static final int TIMEOUT_MS = 10000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(4545)) {
            System.out.println("=== MONITOR DE SENSORES IoT ===");
            System.out.println("Escuchando sensores en puerto UDP 4545...\n");

            // Hilo aparte para imprimir el dashboard cada 3 segundos
            Thread dashboardThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        printDashboard();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            dashboardThread.setDaemon(true);
            dashboardThread.start();

            byte[] buf = new byte[256];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String data = new String(packet.getData(), 0, packet.getLength());
                String timestamp = LocalTime.now()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                // Formato esperado: "sensor_id:temperatura:humedad"
                // Ejemplo: "sensor_A:25.3:68"
                String[] parts = data.split(":");
                if (parts.length == 3) {
                    String sensorId = parts[0];
                    String temp = parts[1];
                    String hum = parts[2];

                    sensorData.put(sensorId, timestamp + "|" + temp + "|" + hum);
                    System.out.println("[" + timestamp + "] " + sensorId
                            + " → Temp: " + temp + "°C, Hum: " + hum + "%");
                } else {
                    System.out.println("[" + timestamp + "] Dato mal formado: " + data);
                }
            }

        } catch (SocketException e) {
            System.err.println("No se pudo abrir el puerto 4545");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }

    private static void printDashboard() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         DASHBOARD DE SENSORES         ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (sensorData.isEmpty()) {
            System.out.println("  (esperando datos de sensores...)");
            return;
        }

        for (Map.Entry<String, String> entry : sensorData.entrySet()) {
            String[] parts = entry.getValue().split("\\|");
            String time = parts[0];
            String temp = parts[1];
            String hum = parts[2];

            String status = Double.parseDouble(temp) > 30 ? "⚠ ALTA" : "✓ normal";
            System.out.println("  " + entry.getKey()
                    + " | Temp: " + temp + "°C " + status
                    + " | Hum: " + hum + "%"
                    + " | Ultima lectura: " + time);
        }
        System.out.println();
    }
}
```

**Cliente simulado de sensor IoT:**

```java
import java.io.IOException;
import java.net.*;
import java.util.Random;

public class IoTSensorSimulator {

    public static void main(String[] args) throws Exception {
        String sensorId = args.length > 0 ? args[0] : "sensor_A";
        Random rand = new Random();

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");

            while (true) {
                // Simula lecturas de temperatura (15-35°C) y humedad (40-90%)
                double temp = 15 + rand.nextDouble() * 20;
                double hum = 40 + rand.nextDouble() * 50;

                String data = String.format("%s:%.1f:%.0f", sensorId, temp, hum);
                byte[] buf = data.getBytes();

                DatagramPacket packet = new DatagramPacket(
                        buf, buf.length, serverAddr, 4545);
                socket.send(packet);

                System.out.println("[" + sensorId + "] Enviado: " + data);
                Thread.sleep(4000 + rand.nextInt(2000)); // 4-6s entre lecturas
            }
        }
    }
}
```

**Cómo funciona:**
- **Servidor (`IoTSensorMonitor`):** Recibe datagramas UDP en el puerto 4545. Cada datagrama contiene `"sensor_id:temperatura:humedad"`. Almacena la última lectura de cada sensor y cada 3 segundos imprime un dashboard en consola
- **Cliente simulado (`IoTSensorSimulator`):** Genera datos aleatorios de temperatura y humedad y los envía por UDP cada 4-6 segundos. Se puede ejecutar múltiples instancias con diferentes IDs (`sensor_A`, `sensor_B`, etc.)
- **Tolerancia a fallos:** Si un sensor deja de enviar datos (se quedó sin batería), el dashboard muestra la última lectura conocida en lugar de dejar de funcionar. Cuando el sensor se reactiva, los datos se actualizan automáticamente
- **Por qué UDP:** Los sensores IoT tienen recursos limitados. UDP evita el overhead del handshake TCP (3 paquetes antes de enviar datos) y no requiere mantener estado de conexión. La pérdida ocasional de un paquete es aceptable porque el próximo llegará segundos después

**Ejecución:**
```bash
# Terminal 1 - Servidor
java edu.eci.arsw.networking.ejercicio5_2_1.IoTSensorMonitor

# Terminal 2 - Sensor A
java edu.eci.arsw.networking.ejercicio5_2_1.IoTSensorSimulator sensor_A

# Terminal 3 - Sensor B
java edu.eci.arsw.networking.ejercicio5_2_1.IoTSensorSimulator sensor_B
```

---

## Ejercicio 6.4.1 — RMI Chat

**Paquete:** `edu.eci.arsw.networking.ejercicio6_4_1`
**Archivos:**
- `Chat.java` — interfaz remota
- `RmiChat.java` — implementación (cliente + servidor)
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio6_4_1/`

### Requisito

> Utilizando RMI, escriba un aplicativo que pueda conectarse a otro aplicativo del mismo tipo en un servidor remoto para comenzar un chat. El aplicativo debe solicitar una dirección IP y un puerto antes de conectarse con el cliente que se desea. Igualmente, debe solicitar un puerto antes de iniciar para que publique el objeto que recibe los llamados remotos en dicho puerto.

### Conceptos clave: RMI vs Sockets

| Característica | Sockets (TCP/UDP) | RMI |
|----------------|-------------------|-----|
| Abstracción | Enviar/recibir bytes | Llamar métodos en objetos remotos |
| Estado | El programador maneja el protocolo | El stub/skeleton maneja la comunicación |
| Serialización | Manual (bytes) | Automática (objetos Java) |
| Descubrimiento | Conexión directa a IP:puerto | Registry + lookup por nombre |
| Complejidad | Baja (raw sockets) | Alta pero abstraída |

### Cómo funciona RMI

RMI (Remote Method Invocation) permite que un objeto en una JVM invoque métodos de un objeto en otra JVM como si fueran locales. Por debajo, RMI usa TCP para la comunicación, pero toda la complejidad está oculta:

```
PEER A                          PEER B
  |                               |
  | 1. createRegistry(puertoA)    | 1. createRegistry(puertoB)
  | 2. exportObject(stubA)        | 2. exportObject(stubB)
  | 3. rebind("chat", stubA)      | 3. rebind("chat", stubB)
  |                               |
  | 4. lookup("chat") → stubB     |
  | 5. stubB.sendMessage("Hola") ──> 6. Imprime "Hola"
  |                               |
  |                               | 7. lookup("chat") → stubA
  | 9. Imprime "Bien!"          <── 8. stubA.sendMessage("Bien!")
```

Cada peer ejecuta:
1. `LocateRegistry.createRegistry(puerto)` — inicia un registry RMI en el puerto local
2. `UnicastRemoteObject.exportObject(objeto, 0)` — exporta el objeto y genera un stub
3. `registry.rebind("chat", stub)` — publica el stub en el registry
4. `LocateRegistry.getRegistry(ip, puerto).lookup("chat")` — busca el stub del otro peer

### Clase `Chat` (Interfaz Remota)

```java
package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    void sendMessage(String message) throws RemoteException;
}
```

**Explicación detallada:**

La interfaz `Chat` define el **contrato remoto** — los métodos que pueden ser invocados desde otra JVM.

- **`extends Remote`** — Obligatorio para todas las interfaces remotas RMI. Marca la interfaz como capaz de ser invocada remotamente.
- **`throws RemoteException`** — Cada método debe declarar que lanza `RemoteException` porque la comunicación remota puede fallar (red caída, servidor no disponible, timeout, etc.).
- **`sendMessage(String message)`** — El único método del chat. Un peer llama a este método en el stub del otro peer para enviarle un mensaje.

### Clase `RmiChat`

```java
package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiChat implements Chat {

    private static Chat remotePeer;
    private static int localPort;

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("\n[Peer " + localPort + "]: " + message);
        System.out.print("You: ");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== RMI Chat ===");
        System.out.print("Enter your local port: ");
        localPort = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter remote IP: ");
        String remoteIp = scanner.nextLine().trim();

        System.out.print("Enter remote port: ");
        int remotePort = Integer.parseInt(scanner.nextLine().trim());

        try {
            Registry registry = LocateRegistry.createRegistry(localPort);

            RmiChat chatImpl = new RmiChat();
            Chat stub = (Chat) UnicastRemoteObject.exportObject(chatImpl, 0);

            registry.rebind("chat", stub);
            System.out.println("Chat service published on port " + localPort);

            while (true) {
                try {
                    Registry remoteRegistry =
                            LocateRegistry.getRegistry(remoteIp, remotePort);
                    remotePeer = (Chat) remoteRegistry.lookup("chat");
                    System.out.println(
                            "Connected to " + remoteIp + ":" + remotePort);
                    System.out.println(
                            "Type your messages (type 'exit' to quit):\n");
                    break;
                } catch (Exception e) {
                    System.out.println(
                            "Waiting for remote peer on "
                            + remoteIp + ":" + remotePort + " ...");
                    Thread.sleep(3000);
                }
            }

            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) break;

                remotePeer.sendMessage(message);
            }

        } catch (RemoteException e) {
            System.err.println("RMI error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Chat ended.");
    }
}
```

**Explicación detallada:**

1. **`implements Chat`** — `RmiChat` implementa la interfaz remota. El método `sendMessage()` se ejecutará en esta JVM cuando el otro peer lo invoque remotamente.

2. **`sendMessage()`** — Imprime el mensaje recibido con el prefijo `[Peer puerto]` para identificar quién envió el mensaje. Luego restaura el prompt `"You: "` para que el usuario pueda seguir escribiendo.

3. **`LocateRegistry.createRegistry(localPort)`** — Inicia el servicio de nombres RMI (como un DNS local) en el puerto especificado. Esto reemplaza al comando externo `rmiregistry`. Otros peers consultarán este registry para encontrar el stub.

4. **`UnicastRemoteObject.exportObject(chatImpl, 0)`** — Convierte el objeto `chatImpl` en un objeto remoto accesible desde otras JVM. Internamente:
   - Crea un **skeleton** en el servidor que escucha invocaciones remotas
   - Genera un **stub** (proxy) que implementa la interfaz `Chat`
   - El puerto `0` significa que el sistema asigna un puerto TCP anónimo
   - Retorna el stub que se publica en el registry

5. **`registry.rebind("chat", stub)`** — Publica el stub en el registry bajo el nombre `"chat"`. El otro peer usará este nombre para hacer `lookup()`.

6. **Bucle de conexión con reintentos** — El programa intenta conectarse al otro peer cada 3 segundos. Esto es necesario porque los dos peers pueden iniciar en cualquier orden:
   - Si Peer A arranca primero, intenta conectarse a Peer B que aún no existe → reintenta
   - Cuando Peer B arranca, también intenta conectarse a Peer A → si encuentra el registry de A, la conexión se establece
   - El bucle `while (true)` con `Thread.sleep(3000)` asegura que ambos peers se conecten eventualmente

7. **`LocateRegistry.getRegistry(remoteIp, remotePort).lookup("chat")`** — Se conecta al registry del peer remoto y busca el stub con nombre `"chat"`. Si el registry no está disponible, lanza excepción (se reintenta).

8. **`remotePeer.sendMessage(message)`** — Esta línea parece una llamada local, pero es una **invocación remota**:
   - RMI serializa el objeto `String` message a bytes
   - Crea una conexión TCP al peer remoto
   - Envía el nombre del método (`sendMessage`) y los parámetros serializados
   - El skeleton del peer remoto deserializa, invoca el método real
   - El método `sendMessage()` se ejecuta en la otra JVM
   - Si hay algún error de red, se lanza `RemoteException`

### Entradas y salidas

| Componente | Entrada | Procesamiento | Salida |
|------------|---------|---------------|--------|
| **Peer A** | Consola: mensajes de texto. Red: `sendMessage()` desde Peer B | RMI serializa/deserializa automáticamente | Consola: mensajes con prefijo `[Peer B_port]`. Red: stub invoca `sendMessage()` en Peer B |
| **Peer B** | Consola: mensajes de texto. Red: `sendMessage()` desde Peer A | RMI serializa/deserializa automáticamente | Consola: mensajes con prefijo `[Peer A_port]`. Red: stub invoca `sendMessage()` en Peer A |

### Diagrama de flujo temporal

```
PEER A (10990)                 PEER B (10991)
  |                               |
  | createRegistry(10990)         |
  | exportObject()                |
  | rebind("chat")                |
  |                               | createRegistry(10991)
  |                               | exportObject()
  |                               | rebind("chat")
  | lookup(10991) → waiting...    |
  |                               | lookup(10990) → OK!
  |                               | remotePeer = stubA
  |                               | "Connected to 10990"
  | lookup(10991) → OK!           |
  | remotePeer = stubB            |
  | "Connected to 10991"         |
  |                               |
  | You: Hola                     |
  | stubB.sendMessage("Hola") ───>| [Peer 10990]: Hola
  |                               | You: :)
  | [Peer 10991]: :)           <──| stubA.sendMessage(":)")
  | You: exit                     |
  | (termina)                     |
```

### Ejecución

**Paso 1 — Compilar:**
```bash
mvn clean compile
```

**Paso 2 — Abrir DOS terminales.**

Terminal 1 — Peer A (puerto local 10990, se conecta a Peer B en 10991):
```bash
run_ejercicio.bat 6_4_1
# Ingresar: 10990, 127.0.0.1, 10991
```

Terminal 2 — Peer B (puerto local 10991, se conecta a Peer A en 10990):
```bash
run_ejercicio.bat 6_4_1
# Ingresar: 10991, 127.0.0.1, 10990
```

**Paso 3 — Escribir mensajes en cualquier terminal.**

```
Peer A:                         Peer B:
You: Hola desde A               [Peer 10990]: Hola desde A
                                You: Hola desde B
[Peer 10991]: Hola desde B
You: exit                       (Peer A se desconecta)
```

### Verificación esperada

| Escenario | Comportamiento |
|-----------|----------------|
| Peer A arranca primero | Espera con "Waiting for remote peer..." cada 3s |
| Peer B arranca después | Ambos se conectan automáticamente |
| Peer A escribe mensaje | Aparece en consola de Peer B con prefijo `[Peer 10990]` |
| Peer B escribe mensaje | Aparece en consola de Peer A con prefijo `[Peer 10991]` |
| Cualquier peer escribe "exit" | Ese peer se desconecta; el otro recibe `RemoteException` |

### Notas

- **`LocateRegistry.createRegistry()`** evita tener que ejecutar `rmiregistry` externamente.
- **`UnicastRemoteObject.exportObject(obj, 0)`** exporta el objeto en un puerto TCP anónimo.
- **`registry.rebind("chat", stub)`** — el nombre debe coincidir en ambos peers.
- **El reintento con `Thread.sleep(3000)`** resuelve el problema de sincronización: un peer arranca primero y espera al otro.
- **Java 21** tiene `SecurityManager` deprecado, por lo que no se usa. En versiones anteriores de Java habría que configurar un archivo `policy`.
- **RMI usa TCP por debajo**, pero toda la complejidad del socket está oculta por el stub/skeleton.

---

### 🌐 Caso de uso real — Sistema de notificaciones push para cajeros bancarios

**Escenario:** Un banco tiene múltiples cajeros en sus sucursales. Cuando un supervisor autoriza una transacción grande (ej: apertura de cuenta, préstamo), el sistema debe notificar instantáneamente al cajero correspondiente. RMI es ideal aquí porque:
- Cada cajero es un "peer" que publica un objeto remoto para recibir notificaciones
- El supervisor invoca un método remoto en el cajero específico
- No se necesita un servidor central de mensajes como RabbitMQ o Kafka para esta comunicación punto a punto
- La serialización automática de objetos Java simplifica el envío de datos estructurados (cliente, monto, tipo de transacción)

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

// Contrato remoto para recibir notificaciones
public interface NotificationReceiver extends Remote {
    void notify(String title, String message, int priority) throws RemoteException;
}
```

```java
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BankTellerAgent implements NotificationReceiver {

    private static final String SERVICE_NAME = "bankNotification";
    private int tellerId;

    public BankTellerAgent(int tellerId) {
        this.tellerId = tellerId;
    }

    @Override
    public void notify(String title, String message, int priority) throws RemoteException {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String priorityLabel = priority >= 8 ? "🔴 URGENTE"
                             : priority >= 5 ? "🟡 IMPORTANTE"
                             : "🟢 INFORMATIVO";

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      NOTIFICACION DEL SISTEMA       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Cajero #" + tellerId);
        System.out.println("  Fecha: " + timestamp);
        System.out.println("  Prioridad: " + priorityLabel);
        System.out.println("  Titulo: " + title);
        System.out.println("  Mensaje: " + message);
        System.out.println("  ─────────────────────────────");
        System.out.print("  Accion (1=aceptar, 2=rechazar): ");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese ID del cajero: ");
        int tellerId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Ingrese puerto local: ");
        int localPort = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Ingrese IP del supervisor: ");
        String supervisorIp = scanner.nextLine().trim();

        System.out.print("Ingrese puerto del supervisor: ");
        int supervisorPort = Integer.parseInt(scanner.nextLine().trim());

        try {
            // Publicar este cajero como receptor de notificaciones
            Registry localRegistry = LocateRegistry.createRegistry(localPort);
            BankTellerAgent agent = new BankTellerAgent(tellerId);
            NotificationReceiver stub = (NotificationReceiver)
                    UnicastRemoteObject.exportObject(agent, 0);
            localRegistry.rebind(SERVICE_NAME, stub);
            System.out.println("Cajero #" + tellerId + " registrado en puerto " + localPort);

            // Esperar conexión al supervisor (o a que el supervisor se conecte)
            System.out.println("Esperando notificaciones del supervisor...");

            // Mantener el programa vivo
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

```java
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class BankSupervisor {

    private static final String SERVICE_NAME = "bankNotification";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese puerto local del supervisor: ");
        int localPort = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Ingrese IP del cajero destino: ");
        String tellerIp = scanner.nextLine().trim();

        System.out.print("Ingrese puerto del cajero destino: ");
        int tellerPort = Integer.parseInt(scanner.nextLine().trim());

        try {
            // Publicar el supervisor (por si el cajero necesita enviar confirmación)
            Registry localRegistry = LocateRegistry.createRegistry(localPort);

            // Conectarse al cajero
            Registry tellerRegistry = LocateRegistry.getRegistry(tellerIp, tellerPort);
            NotificationReceiver teller = (NotificationReceiver)
                    tellerRegistry.lookup(SERVICE_NAME);

            System.out.println("Conectado al cajero en " + tellerIp + ":" + tellerPort);
            System.out.println("=== Envio de notificaciones ===");

            while (true) {
                System.out.print("\nTitulo de la notificacion: ");
                String title = scanner.nextLine();
                if ("exit".equalsIgnoreCase(title)) break;

                System.out.print("Mensaje: ");
                String message = scanner.nextLine();

                System.out.print("Prioridad (1-10): ");
                int priority = Integer.parseInt(scanner.nextLine().trim());

                // Invocación remota → el mensaje aparece en la pantalla del cajero
                teller.notify(title, message, priority);
                System.out.println("✓ Notificacion enviada al cajero");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Supervisor desconectado.");
    }
}
```

**Cómo funciona:**
- **Interfaz remota `NotificationReceiver`** — Define el contrato para recibir notificaciones con título, mensaje y prioridad
- **Cajero (`BankTellerAgent`)** — Cada cajero publica su objeto remoto en un registry local. Cuando el supervisor invoca `notify()`, la notificación aparece directamente en la consola del cajero con formato visual (colores, íconos según prioridad)
- **Supervisor (`BankSupervisor`)** — Se conecta al registry del cajero destino, obtiene su stub y envía notificaciones invocando `teller.notify()` como si fuera una llamada local
- **Prioridades:** Las notificaciones se clasifican visualmente: 🔴 URGENTE (8-10), 🟡 IMPORTANTE (5-7), 🟢 INFORMATIVO (1-4)
- **Por qué RMI en lugar de sockets:** RMI abstrae toda la complejidad de serialización y protocolo. El supervisor solo necesita hacer `teller.notify(titulo, mensaje, prioridad)` sin preocuparse de codificar/decodificar bytes, manejar delimitadores de mensaje o reconexiones. Además, si en el futuro se agregan más campos a la notificación (ej: `fecha`, `idTransaccion`), solo se modifica la interfaz remota y los parámetros se serializan automáticamente

**Ejecución:**
```bash
# Terminal 1 - Cajero (puerto 11000)
java BankTellerAgent
# Ingresar: ID=101, Puerto=11000, IP_Supervisor=127.0.0.1, Puerto_Supervisor=12000

# Terminal 2 - Supervisor (puerto 12000)
java BankSupervisor
# Ingresar: Puerto=12000, IP_Cajero=127.0.0.1, Puerto_Cajero=11000

# El supervisor escribe notificaciones y aparecen instantáneamente en el cajero
```
