# Networking Workshop — Java

A hands-on workshop covering fundamental networking concepts in Java: URLs, TCP/UDP sockets, datagrams, and RMI (Remote Method Invocation).

**Author:** Juan Carlos Bohórquez Monroy

---

## Table of Contents

- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [How to Run](#how-to-run)
- [Exercises](#exercises)
  - [1. URL Info Printer](#1-url-info-printer)
  - [2. URL Browser](#2-url-browser)
  - [4.3.1. Square Server](#431-square-server)
  - [4.3.2. Trig Server](#432-trig-server)
  - [4.4. Basic HTTP Server](#44-basic-http-server)
  - [4.5.1. Multi-Request Web Server](#451-multi-request-web-server)
  - [5.2.1. Datagram Time Client](#521-datagram-time-client)
  - [6.4.1. RMI Chat](#641-rmi-chat)

---

## Project Structure

```
src/
├── main/
│   ├── java/edu/eci/arsw/networking/
│   │   ├── NetworkingWorkshopApplication.java   # Spring Boot entry point
│   │   ├── ejercicio1/                           # Exercise 1
│   │   ├── ejercicio2/                           # Exercise 2
│   │   ├── ejercicio4_3_1/                       # Exercise 4.3.1
│   │   ├── ejercicio4_3_2/                       # Exercise 4.3.2
│   │   ├── ejercicio4_4/                         # Exercise 4.4
│   │   ├── ejercicio4_5_1/                       # Exercise 4.5.1
│   │   ├── ejercicio5_2_1/                       # Exercise 5.2.1
│   │   └── ejercicio6_4_1/                       # Exercise 6.4.1
│   └── resources/
│       └── application.properties
└── test/java/edu/eci/arsw/networking/
    └── NetworkingWorkshopApplicationTests.java
```

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Maven | 3.9.16 |

---

## How to Run

**Compile all exercises:**
```bash
mvn compile
```

**Run a specific exercise** (once implemented):
```bash
mvn compile exec:java -Dexec.mainClass="edu.eci.arsw.networking.ejercicio#.URLInfo"
```

**Run tests:**
```bash
mvn test
```

---

## Exercises

### 1. URL Info Printer ✅

**Package:** `edu.eci.arsw.networking.ejercicio1` — **Completed**  
**File:** `src/main/java/edu/eci/arsw/networking/ejercicio1/URLInfo.java`

#### What it does

Decomposes a URL into its 8 structural components and prints each one. This teaches how a URL is parsed by Java — every web address you type in a browser is internally broken into protocol, host, port, path, etc.

#### Step-by-step implementation

| Step | What we did | Why |
|------|-------------|-----|
| 1 | Import `java.net.URL` and `java.net.MalformedURLException` | `URL` is the core class for working with URLs; `MalformedURLException` is thrown when the URL string is invalid |
| 2 | Create a `URL` object with a full URL containing protocol, host, port, path, query and fragment | To test all 8 getter methods we need a URL that has every possible component |
| 3 | Call `url.getProtocol()` and print it | Extracts `http` — identifies the communication protocol |
| 4 | Call `url.getAuthority()` and print it | Extracts `ldbn.escuelaing.edu.co:80` — combines host and port |
| 5 | Call `url.getHost()` and print it | Extracts `ldbn.escuelaing.edu.co` — the server name |
| 6 | Call `url.getPort()` and print it | Extracts `80` — the port number (returns `-1` if not explicitly set) |
| 7 | Call `url.getPath()` and print it | Extracts `/index.html` — the resource path on the server |
| 8 | Call `url.getQuery()` and print it | Extracts `query=value` — the query string after `?` |
| 9 | Call `url.getFile()` and print it | Extracts `/index.html?query=value` — combines path and query |
| 10 | Call `url.getRef()` and print it | Extracts `seccion` — the fragment after `#` |
| 11 | Wrap everything in a `try-catch` block | `MalformedURLException` is a checked exception, the compiler requires us to handle it |

#### Why this matters

Understanding URL decomposition is the foundation for web programming. Every HTTP request, REST API call, or web scraping task relies on being able to construct and parse URLs correctly. The 8 methods map directly to the parts of a URL shown in every browser's address bar.

#### Methods used

| Method | Returns | Example output |
|--------|---------|----------------|
| `getProtocol()` | Protocol | `http` |
| `getAuthority()` | Host:Port | `ldbn.escuelaing.edu.co:80` |
| `getHost()` | Host name | `ldbn.escuelaing.edu.co` |
| `getPort()` | Port number | `80` |
| `getPath()` | Path component | `/index.html` |
| `getQuery()` | Query string | `query=value` |
| `getFile()` | Path + Query | `/index.html?query=value` |
| `getRef()` | Fragment anchor | `seccion` |

#### Test URL

```
http://ldbn.escuelaing.edu.co:80/index.html?query=value#seccion
```

#### Expected output

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

#### Run

```bash
# Option 1 — using the batch script (Windows)
run_ejercicio1.bat

# Option 2 — using Maven directly
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio1.URLInfo"
```

---

### 2. URL Browser ✅

**Package:** `edu.eci.arsw.networking.ejercicio2` — **Completed**  
**File:** `src/main/java/edu/eci/arsw/networking/ejercicio2/URLBrowser.java`

#### What it does

Acts like a minimal web browser: asks the user for a URL, fetches the HTML content from that address, and saves it to a file called `resultado.html`. This teaches how to read data from the internet using Java streams, the same mechanism browsers use under the hood.

#### Step-by-step implementation

| Step | What we did | Why |
|------|-------------|-----|
| 1 | Import `java.util.Scanner` | `Scanner` reads user input from the console |
| 2 | Import `java.net.URL` and `java.net.MalformedURLException` | `URL` represents the remote resource; `MalformedURLException` handles invalid URLs |
| 3 | Import `java.io.BufferedReader`, `InputStreamReader`, `PrintWriter`, `FileWriter`, `IOException` | These handle reading from the network and writing to a file |
| 4 | Prompt the user with `System.out.print("Enter a URL: ")` and read input with `scanner.nextLine()` | The exercise requires the user to enter any URL they want |
| 5 | Create a `URL` object from the user's input inside a `try` block | `new URL(string)` parses and validates the URL format |
| 6 | Call `url.openStream()` to open a connection | `openStream()` returns an `InputStream` connected to the remote server |
| 7 | Wrap the stream in `InputStreamReader` then `BufferedReader` | `BufferedReader` allows reading line-by-line, which is efficient for text content |
| 8 | Create a `PrintWriter` wrapping a `FileWriter("resultado.html")` | `PrintWriter.println()` writes each line to the output file |
| 9 | Loop: `while ((line = reader.readLine()) != null)` | Reads the remote content line by line until the end of the stream |
| 10 | Inside the loop: `writer.println(line)` | Each line is written to `resultado.html`, preserving the original formatting |
| 11 | Use try-with-resources for both reader and writer | Ensures both streams are closed automatically, even if an error occurs |
| 12 | Catch `MalformedURLException` | Handles invalid URL syntax (e.g., missing `http://`) |
| 13 | Catch `IOException` | Handles network errors (e.g., server unreachable, DNS failure) |

#### Why this matters

This is the foundation of web scraping and HTTP clients. The same pattern (open stream → read → process) is used by:
- **Web crawlers** that download pages for indexing
- **REST clients** that consume JSON/XML APIs
- **Download managers** that fetch files from the internet
- **Proxy servers** that read content from one server and forward it to a client

#### Key classes used

| Class | Role |
|-------|------|
| `java.net.URL` | Represents the remote resource and provides `openStream()` |
| `BufferedReader` | Reads text efficiently line by line |
| `InputStreamReader` | Bridge from byte stream to character stream |
| `PrintWriter` | Writes formatted text to the output file |
| `FileWriter` | Creates/appends to `resultado.html` on disk |

#### Run

```bash
# Option 1 — using the batch script (Windows)
run_ejercicio2.bat

# Option 2 — using Maven directly
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio2.URLBrowser"
```

---

### 4.3.1. Square Server

**Package:** `edu.eci.arsw.networking.ejercicio4_3_1`  
**Status:** ⏳ Planned

Build a **TCP server** (using `ServerSocket`) that:
1. Listens on port `35000`
2. Receives a number from a client
3. Responds with the square of that number
4. Stays alive to handle multiple sequential clients

**Key classes:** `java.net.ServerSocket`, `java.net.Socket`

---

### 4.3.2. Trig Server

**Package:** `edu.eci.arsw.networking.ejercicio4_3_2`  
**Status:** ⏳ Planned

Build a **TCP server** that evaluates trigonometric functions:
1. Default operation: **cosine**
2. Receive a number → respond with `cos(number)`, `sin(number)`, or `tan(number)`
3. Command `fun:sin` changes current operation to sine
4. Command `fun:cos` changes current operation to cosine
5. Command `fun:tan` changes current operation to tangent

**Example flow:**
```
Client sends:  0       → Server responds: 1.0    (cos(0))
Client sends:  π/2     → Server responds: 0.0    (cos(π/2))
Client sends:  fun:sin → (operation changes to sine)
Client sends:  0       → Server responds: 0.0    (sin(0))
```

**Key classes:** `java.net.ServerSocket`, `java.net.Socket`, `Math.sin()`, `Math.cos()`, `Math.tan()`

---

### 4.4. Basic HTTP Server

**Package:** `edu.eci.arsw.networking.ejercicio4_4`  
**Status:** ⏳ Planned

Implement a minimal **HTTP server** that:
1. Listens on port `35000`
2. Accepts a single HTTP request
3. Reads the incoming request headers
4. Responds with a hardcoded HTML page
5. Test it by connecting from a real web browser (`http://localhost:35000`)

**Key classes:** `java.net.ServerSocket`, `java.net.Socket`, `PrintWriter`, `BufferedReader`

---

### 4.5.1. Multi-Request Web Server

**Package:** `edu.eci.arsw.networking.ejercicio4_5_1`  
**Status:** ⏳ Planned

Extend the basic HTTP server to support **multiple sequential requests** (non-concurrent). The server must:
1. Stay alive after responding to a request, waiting for the next one
2. Serve static files: HTML pages and images
3. Parse the HTTP request line to determine which file is being requested
4. Read the file from disk and send it as the HTTP response
5. Set the correct `Content-Type` header based on file extension (`.html` → `text/html`, `.jpg` → `image/jpeg`, etc.)

---

### 5.2.1. Datagram Time Client

**Package:** `edu.eci.arsw.networking.ejercicio5_2_1`  
**Status:** ⏳ Planned

Build a **UDP client-server** application for retrieving the current server time:
1. **Server** (`DatagramTimeServer`): listens on port `4445`, responds to any incoming datagram with the current date/time string
2. **Client** (`DatagramTimeClient`): sends a datagram to the server every **5 seconds** and prints the received time
3. If a response is not received (server offline), the client keeps the last known time and continues running
4. The client must recover automatically when the server comes back online

**Key classes:** `java.net.DatagramSocket`, `java.net.DatagramPacket`, `java.net.InetAddress`

---

### 6.4.1. RMI Chat

**Package:** `edu.eci.arsw.networking.ejercicio6_4_1`  
**Status:** ⏳ Planned

Implement a **peer-to-peer chat application** using Java RMI:
1. Each instance acts as both client and server
2. Before starting, the user provides:
   - A **port** to publish their own remote chat object
   - A **remote IP** and **port** to connect to another peer
3. Messages are sent by invoking methods on the remote peer's chat object
4. The chat interface extends `java.rmi.Remote` and methods throw `RemoteException`

**Key classes:** `java.rmi.Remote`, `java.rmi.RemoteException`, `java.rmi.registry.LocateRegistry`, `java.rmi.registry.Registry`, `java.rmi.server.UnicastRemoteObject`
