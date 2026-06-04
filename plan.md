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

## Ejercicio 4.3.1 — Square Server

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_1`  
**Estado:** Pendiente

---

## Ejercicio 4.3.2 — Trig Server

**Paquete:** `edu.eci.arsw.networking.ejercicio4_3_2`  
**Estado:** Pendiente

---

## Ejercicio 4.4 — HttpServer básico

**Paquete:** `edu.eci.arsw.networking.ejercicio4_4`  
**Estado:** Pendiente

---

## Ejercicio 4.5.1 — Web Server multi-solicitud

**Paquete:** `edu.eci.arsw.networking.ejercicio4_5_1`  
**Estado:** Pendiente

---

## Ejercicio 5.2.1 — Datagram Time Client

**Paquete:** `edu.eci.arsw.networking.ejercicio5_2_1`  
**Estado:** Pendiente

---

## Ejercicio 6.4.1 — RMI Chat

**Paquete:** `edu.eci.arsw.networking.ejercicio6_4_1`  
**Estado:** Pendiente
