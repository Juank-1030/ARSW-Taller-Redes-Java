# Plan de Trabajo — Taller ARSW Redes Java

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

### Implementación

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

## Ejercicio 2 — URL Browser ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio2`  
**Archivo:** `URLBrowser.java`  
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio2/URLBrowser.java`

### Requisito

> Escriba una aplicación browser que pregunte una dirección URL al usuario y que lea datos de esa dirección y que los almacene en un archivo con el nombre `resultado.html`. Luego intente ver este archivo en el navegador.

### Implementación

1. Solicitar al usuario una dirección URL por consola (`Scanner`)
2. Crear un objeto `URL` con la dirección ingresada
3. Abrir un stream de lectura con `openStream()`
4. Leer línea por línea con `BufferedReader`
5. Escribir cada línea en un archivo `resultado.html` usando `FileWriter` / `PrintWriter`
6. Manejar `MalformedURLException` e `IOException`
7. Cerrar todos los recursos (try-with-resources)

### Ejecución

```bash
run_ejercicio2.bat
```

O con Maven:

```bash
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio2.URLBrowser"
```

---

## Ejercicio 4.3.1 — Square Server ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_1`  
**Archivo:** `SquareServer.java`  
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_3_1/SquareServer.java`

### Requisito

> Escriba un servidor que reciba un número y responda el cuadrado de este número.

### Implementación

1. Crear `ServerSocket` en el puerto `35000`
2. Aceptar una conexión cliente con `serverSocket.accept()`
3. Obtener `PrintWriter` (salida) y `BufferedReader` (entrada) del socket
4. Bucle: leer línea → parsear como `double` → calcular cuadrado → responder
5. Manejar `NumberFormatException` si la entrada no es un número válido
6. Salir del bucle si el cliente envía `"Bye."`
7. Cerrar todos los recursos al finalizar

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

## Ejercicio 4.3.2 — Trig Server ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_2`  
**Archivo:** `TrigServer.java`  
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_3_2/TrigServer.java`

### Requisito

> Escriba un servidor que pueda recibir un número y responda con una operación trigonométrica sobre este número. Puede recibir `fun:sin`, `fun:cos`, `fun:tan` para cambiar la operación. Por defecto empieza calculando el coseno.

### Implementación

1. Crear `ServerSocket` en puerto `35000`
2. Estado inicial: operación = `Math::cos`
3. Bucle de lectura:
   - Si el mensaje empieza con `fun:` → cambiar operación a sin/cos/tan
   - Sino → parsear como `double`, aplicar operación actual, responder
4. Usar `DoubleUnaryOperator` como variable de función para evitar `if/switch` en cada evaluación
5. Manejar `NumberFormatException` para entradas inválidas
6. Cerrar recursos al finalizar

### Verificación esperada (puerto 35001)

| Cliente envía | Servidor responde |
|---------------|-------------------|
| `0` | `1.0` (cos(0)) |
| `1.5707963267948966` | `~0.0` (cos(π/2)) |
| `fun:sin` | `Operation changed to sin` |
| `0` | `0.0` (sin(0)) |
| `fun:tan` | `Operation changed to tan` |
| `0` | `0.0` (tan(0)) |
| `Bye.` | _(cierra conexión)_ |

---

## Ejercicio 4.4 — HttpServer básico ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_4`  
**Archivo:** `HttpServer.java`  
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_4/HttpServer.java`

### Requisito

> Implemente el servidor web del código provisto e intente conectarse desde el browser.

### Implementación

1. Crear `ServerSocket` en puerto `35000`
2. Aceptar una conexión cliente con `serverSocket.accept()`
3. Bucle: leer líneas del request HTTP (`in.readLine()`) hasta que `!in.ready()` (no más datos)
4. Imprimir cada línea recibida en consola con `"Received: "`
5. Construir respuesta HTTP con HTML hardcodeado (`<!DOCTYPE html>...`)
6. Enviar respuesta con `out.println()`
7. Cerrar todos los recursos

### Verificación

Abrir `http://localhost:35000` en el navegador → debe mostrar "My Web Site" con título "Title of the document".

---

## Ejercicio 4.5.1 — Web Server multi-solicitud ✅

**Paquete:** `edu.eci.arsw.networking.ejercicio4_5_1`  
**Archivo:** `MultiServer.java`  
**Estado:** Completado — ver `src/main/java/edu/eci/arsw/networking/ejercicio4_5_1/MultiServer.java`

### Requisito

> Escriba un servidor web que soporte múltiples solicitudes seguidas (no concurrentes). El servidor debe retornar todos los archivos solicitados, incluyendo páginas HTML e imágenes.

### Implementación

1. Crear `ServerSocket` en puerto `35000`
2. Bucle externo: aceptar conexiones de clientes
3. Bucle interno: leer requests HTTP del cliente (keep-alive)
4. Parsear la línea `GET /ruta HTTP/1.1` para obtener el archivo solicitado
5. Mapear `/` → `/index.html`
6. Buscar el archivo en la carpeta `www/`
7. Determinar `Content-Type` según la extensión del archivo
8. Leer el archivo y enviarlo con cabeceras HTTP completas (200 OK)
9. Si el archivo no existe, devolver 404
10. Cuando el cliente se desconecta, volver al bucle externo (accept)

### Archivos de prueba

- `www/index.html` — página HTML principal
- `www/logo.svg` — imagen SVG de prueba

### Verificación

Abrir `http://localhost:35000` en el navegador → debe mostrar la página con el logo. El servidor queda vivo para múltiples solicitudes.

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

### Implementación: Servidor (`DatagramTimeServer`)

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
            // 1. Vincular socket al puerto 4445
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }
    }

    public void startServer() {
        while (true) {                // 2. Bucle infinito
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet =
                        new DatagramPacket(buf, buf.length);

                socket.receive(packet);  // 3. Bloquear hasta recibir datagrama

                String dString = new Date().toString();  // 4. Hora actual
                buf = dString.getBytes();                 // 5. String → bytes

                InetAddress address = packet.getAddress(); // 6. IP del cliente
                int port = packet.getPort();               // 7. Puerto del cliente

                packet = new DatagramPacket(              // 8. Paquete de respuesta
                        buf, buf.length, address, port);

                socket.send(packet);                      // 9. Enviar respuesta

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

**Flujo del servidor:**
1. `new DatagramSocket(4445)` — crea un socket UDP y lo vincula al puerto `4445`. Si el puerto está ocupado, imprime error y sale con `System.exit(1)`.
2. `while (true)` — el servidor nunca termina por sí mismo.
3. `socket.receive(packet)` — **se bloquea** hasta que llega un datagrama. Cuando llega, el `DatagramPacket` se llena con los datos recibidos y automáticamente obtiene la IP y puerto de origen.
4. `new Date().toString()` — genera un string con la fecha/hora actual del sistema.
5. `dString.getBytes()` — convierte el string a un arreglo de bytes para enviarlo por la red.
6. `packet.getAddress()` — obtiene la dirección IP del cliente que envió el datagrama.
7. `packet.getPort()` — obtiene el puerto del cliente.
8. Crea un nuevo `DatagramPacket` con los bytes de la hora, la IP destino y el puerto destino.
9. `socket.send(packet)` — envía el datagrama de respuesta al cliente.

### Implementación: Cliente (`DatagramTimeClient`)

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
            DatagramSocket socket = new DatagramSocket(); // 1. Socket sin vinculo
            socket.setSoTimeout(3000);                     // 2. Timeout 3s

            InetAddress address =
                    InetAddress.getByName("127.0.0.1");    // 3. IP del servidor

            while (true) {                                 // 4. Bucle infinito

                try {
                    byte[] buf = new byte[256];

                    DatagramPacket packet =                // 5. Paquete de solicitud
                            new DatagramPacket(
                                    buf, buf.length,
                                    address, 4445);

                    socket.send(packet);                   // 6. Enviar solicitud

                    packet = new DatagramPacket(           // 7. Paquete para recibir
                            buf, buf.length);

                    socket.receive(packet);                // 8. Esperar respuesta

                    String received = new String(          // 9. Extraer hora
                            packet.getData(), 0,
                            packet.getLength());

                    lastTime = received;
                    System.out.println("Date: " + received);

                } catch (SocketTimeoutException ex) {      // 10. Timeout
                    System.out.println(
                            "Date: " + lastTime +
                            " (server offline)");
                }

                Thread.sleep(5000);                        // 11. Esperar 5s
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

**Flujo del cliente:**
1. `new DatagramSocket()` — crea un socket UDP sin vincular a un puerto específico. El SO asigna un puerto efímero automáticamente.
2. `socket.setSoTimeout(3000)` — establece un timeout de 3 segundos para `receive()`. Sin esto, si el servidor está caído, el cliente se quedaría bloqueado para siempre.
3. `InetAddress.getByName("127.0.0.1")` — resuelve la dirección del servidor (localhost).
4. `while (true)` — el cliente nunca termina. Solo se detiene con Ctrl+C.
5. Crea un `DatagramPacket` con buffer vacío apuntando a `127.0.0.1:4445`.
6. `socket.send(packet)` — envía el datagrama vacío al servidor.
7. Crea un nuevo `DatagramPacket` vacío (sin dirección) para recibir la respuesta.
8. `socket.receive(packet)` — espera hasta 3 segundos la respuesta del servidor.
9. Convierte los bytes recibidos a String usando `packet.getData()` (limitado a `packet.getLength()` para evitar basura del buffer).
10. Si pasan 3 segundos sin respuesta, `SocketTimeoutException` — imprime la última hora conocida con `"(server offline)"`.
11. `Thread.sleep(5000)` — espera 5 segundos antes de la siguiente solicitud.

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

- El servidor usa `new Date().toString()` que devuelve la hora local del sistema. Esto significa que el formato y zona horaria dependen de la configuración regional.
- El timeout de 3s del cliente (`setSoTimeout`) es independiente del intervalo de 5s (`Thread.sleep`). Si el timeout fuera mayor a 5s, el cliente podría retrasarse.
- `DatagramPacket.getData()` devuelve el buffer completo de 256 bytes, pero solo los primeros `getLength()` bytes contienen datos reales. Por eso se usa `new String(packet.getData(), 0, packet.getLength())`.
- El puerto 4445 es el estándar para este ejercicio. Si está ocupado, usar `netstat -ano | findstr :4445` y `taskkill /F /PID <PID>` para liberarlo.

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

RMI permite que un objeto en una JVM invoque métodos de un objeto en otra JVM como si fueran locales:

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

### Implementación: Interfaz remota (`Chat.java`)

```java
package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    void sendMessage(String message) throws RemoteException;
}
```

La interfaz define el **contrato remoto**. Solo los métodos declarados aquí pueden ser invocados desde otra JVM. Debe extender `Remote` y cada método debe lanzar `RemoteException`.

### Implementación: Clase principal (`RmiChat.java`)

```java
package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiChat implements Chat {

    private static Chat remotePeer;   // Referencia al otro peer
    private static int localPort;     // Puerto local (para mostrar)

    @Override
    public void sendMessage(String message) throws RemoteException {
        // RMI invoca este método cuando el otro peer envía un mensaje
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
            // 1. Crear registry en el puerto local
            Registry registry = LocateRegistry.createRegistry(localPort);

            // 2. Exportar el objeto local (genera un stub)
            RmiChat chatImpl = new RmiChat();
            Chat stub = (Chat) UnicastRemoteObject.exportObject(chatImpl, 0);

            // 3. Publicar el stub en el registry
            registry.rebind("chat", stub);
            System.out.println("Chat service published on port " + localPort);

            // 4. Conectarse al peer remoto (con reintentos)
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

            // 5. Bucle de lectura de mensajes desde consola
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) break;

                // Llamada remota → el mensaje viaja por la red
                // y se imprime en la consola del otro peer
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

**Flujo detallado:**

1. `LocateRegistry.createRegistry(localPort)` — inicia el servicio de nombres RMI en el puerto local. No es necesario ejecutar `rmiregistry` externamente.

2. `UnicastRemoteObject.exportObject(chatImpl, 0)` — convierte un objeto local en un objeto remoto exportable. Retorna un **stub** (proxy) que implementa la interfaz `Chat`. El puerto `0` significa que el sistema asigna un puerto TCP anónimo para la comunicación RMI subyacente.

3. `registry.rebind("chat", stub)` — asocia el nombre `"chat"` con el stub en el registry. El otro peer buscará por este nombre.

4. `LocateRegistry.getRegistry(remoteIp, remotePort).lookup("chat")` — se conecta al registry del otro peer y obtiene su stub. Esto está envuelto en un bucle con reintentos porque el otro peer puede no estar listo aún (caso típico: Peer A arranca primero y espera a Peer B).

5. `remotePeer.sendMessage(message)` — cuando se invoca este método, RMI serializa el parámetro `String`, lo envía por la red al otro peer, deserializa el parámetro, invoca `sendMessage()` en el objeto `RmiChat` del otro peer, y este imprime el mensaje en su consola.

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

- **`LocateRegistry.createRegistry()`** evita tener que ejecutar `rmiregistry` externamente. Esto simplifica la ejecución.
- **`UnicastRemoteObject.exportObject(obj, 0)`** exporta el objeto en un puerto TCP anónimo. RMI elige el puerto automáticamente.
- **`registry.rebind("chat", stub)`** — el nombre debe coincidir en ambos peers para que `lookup("chat")` funcione.
- **El reintento con `Thread.sleep(3000)`** resuelve el problema de sincronización: un peer arranca primero y espera al otro.
- **Java 21** tiene `SecurityManager` deprecado, por lo que no se usa. En versiones anteriores de Java habría que configurar un archivo `policy`.
- **RMI usa TCP por debajo**, pero toda la complejidad del socket está oculta por el stub/skeleton.
