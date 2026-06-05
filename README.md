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

**Run the interactive launcher** (shows a menu to pick any exercise):
```bash
# Using the unified script (Windows) — no argument
run_ejercicio.bat

# Using Maven directly
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.WorkshopLauncher"
```

**Run a specific exercise directly**:
```bash
# Using the unified script with exercise number (Windows)
run_ejercicio.bat 1

# Using Maven directly
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio1.URLInfo"
```

**Available commands:**

| Command | Runs |
|---------|------|
| `run_ejercicio.bat` | Interactive menu — pick any exercise |
| `run_ejercicio.bat 1` | Exercise 1 — URL Info Printer |
| `run_ejercicio.bat 2` | Exercise 2 — URL Browser |
| `run_ejercicio.bat 4_3_1` | Exercise 4.3.1 — Square Server |
| `run_ejercicio.bat 4_3_2` | Exercise 4.3.2 — Trig Server |
| `run_ejercicio.bat 4_4` | Exercise 4.4 — Basic HTTP Server |
| `run_ejercicio.bat 4_5_1` | Exercise 4.5.1 — Multi-Request Web Server |
| `run_ejercicio.bat 5_2_1` | Exercise 5.2.1 — Datagram Time Client |
| `run_ejercicio.bat 6_4_1` | Exercise 6.4.1 — RMI Chat |

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
run_ejercicio.bat 1
```

Or using Maven directly:
```bash
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
run_ejercicio.bat 2
```

Or using Maven directly:
```bash
mvn compile exec:java "-Dexec.mainClass=edu.eci.arsw.networking.ejercicio2.URLBrowser"
```

---

### 4.3.1. Square Server ✅

**Package:** `edu.eci.arsw.networking.ejercicio4_3_1` — **Completed**  
**File:** `src/main/java/edu/eci/arsw/networking/ejercicio4_3_1/SquareServer.java`

#### What it does

A TCP server that listens on port `35000`, receives a number from a client, and responds with the square of that number. This teaches the server side of socket programming — how to accept connections, read data from a client, and send a response back.

#### Step-by-step implementation

| Step | What we did | Why |
|------|-------------|-----|
| 1 | Import `java.net.ServerSocket` and `java.net.Socket` | `ServerSocket` waits for incoming connections; `Socket` represents the established connection |
| 2 | Import `java.io.BufferedReader`, `InputStreamReader`, `PrintWriter`, `IOException` | These handle reading from and writing to the socket's input/output streams |
| 3 | Create a `ServerSocket` on port `35000` inside a `try-catch` | `IOException` is thrown if the port is already in use or inaccessible |
| 4 | Call `serverSocket.accept()` and wait for a client | `accept()` blocks until a client connects; returns a `Socket` for communication |
| 5 | Wrap `socket.getOutputStream()` in a `PrintWriter` with auto-flush enabled | `println()` sends data to the client; auto-flush ensures each message is sent immediately |
| 6 | Wrap `socket.getInputStream()` in `BufferedReader` via `InputStreamReader` | `readLine()` reads the client's message one line at a time |
| 7 | Enter a loop: `while ((inputLine = in.readLine()) != null)` | Keeps the connection alive to handle multiple requests from the same client |
| 8 | Parse the input as a `double` with `Double.parseDouble()` | Supports integer and decimal numbers; `NumberFormatException` is caught for invalid input |
| 9 | Compute `number * number` and send it back with `out.println()` | The square operation is the core requirement of the exercise |
| 10 | Check if input equals `"Bye."` to exit the loop | Provides a clean way for the client to terminate the connection |
| 11 | Close all resources (`out`, `in`, `clientSocket`, `serverSocket`) | Frees system resources and port bindings |

#### Why this matters

This is the foundation of all TCP server applications. Every web server, database server, and chat server follows the same pattern:
- Bind to a port → wait for connections → read client data → send a response → repeat.
- The sequential single-client pattern is exactly how early HTTP/1.0 web servers worked.

#### Key classes used

| Class | Role |
|-------|------|
| `java.net.ServerSocket` | Listens for incoming TCP connections on a specific port |
| `java.net.Socket` | Represents the communication endpoint after a connection is established |
| `BufferedReader` | Reads text from the socket's input stream line by line |
| `PrintWriter` | Writes text to the socket's output stream |
| `Double.parseDouble()` | Converts a string to a numeric value for calculation |

#### Run

Start the server:
```bash
run_ejercicio.bat 4_3_1
```

Then connect with a TCP client (e.g., `ncat`, `telnet`, or another terminal):
```bash
ncat localhost 35000
```

Example interaction:
```
Client sends: 5     → Server responds: 25.0
Client sends: Bye.  → Connection closes
```

---

### 4.3.2. Trig Server ✅

**Package:** `edu.eci.arsw.networking.ejercicio4_3_2` — **Completed**  
**File:** `src/main/java/edu/eci/arsw/networking/ejercicio4_3_2/TrigServer.java`

#### What it does

A TCP server that receives a number and responds with the result of a trigonometric function (cosine by default). The user can dynamically switch between sine, cosine, and tangent at runtime by sending `fun:sin`, `fun:cos`, or `fun:tan`. This teaches stateful server design — the server remembers the current operation between requests.

#### Step-by-step implementation

| Step | What we did | Why |
|------|-------------|-----|
| 1 | Import `java.util.function.DoubleUnaryOperator` | This functional interface stores the current operation as a function reference, avoiding verbose `if/else` chains on every evaluation |
| 2 | Declare `private static DoubleUnaryOperator currentOp = Math::cos` | The default operation is cosine; `Math::cos` is a method reference assigned to the variable |
| 3 | Create `ServerSocket` on port `35000` inside a `try-catch` | Same pattern as Exercise 4.3.1 — handle port conflicts |
| 4 | Accept a client and set up `PrintWriter` / `BufferedReader` | Standard socket I/O setup for reading and writing text |
| 5 | Read input line and check `startsWith("fun:")` | The `fun:` prefix is the protocol command to switch operations |
| 6 | Extract the function name with `substring(4)`, trim and lowercase it | Normalize user input so `fun:SIN`, `Fun:Sin`, etc. all work |
| 7 | Use a `switch` to assign `Math::sin`, `Math::cos`, or `Math::tan` to `currentOp` | The variable now points to the selected function for future calculations |
| 8 | If input does NOT start with `fun:`, parse as `double` with `Double.parseDouble()` | The input is treated as a numeric operand |
| 9 | Call `currentOp.applyAsDouble(number)` and send the result back | Applies whichever function is currently selected (sin, cos, or tan) |
| 10 | Handle `NumberFormatException` if the input is neither `fun:...` nor a valid number | Prevents the server from crashing on invalid input |
| 11 | Check for `"Bye."` to exit the loop and close resources | Clean shutdown sequence |

#### Why this matters

This exercise introduces **stateful protocol design** — the server maintains state (the current operation) across multiple client requests. This is a fundamental concept in:
- **FTP servers** that remember the current directory
- **Database connections** that remember transaction state
- **Game servers** that track player state between moves

Using `DoubleUnaryOperator` demonstrates Java's functional programming capabilities — storing and swapping behavior at runtime without conditionals.

#### Key classes used

| Class / Interface | Role |
|-------------------|------|
| `java.net.ServerSocket` | Listens for incoming connections |
| `java.net.Socket` | Communication endpoint |
| `DoubleUnaryOperator` | Functional interface that stores the current trig function |
| `Math::sin / cos / tan` | Method references assigned dynamically |
| `String.startsWith()` | Detects `fun:` commands |
| `String.substring()` | Extracts the function name from the command |

#### Example interaction

```
Client sends: 0                  → Server responds: 1.0           (cos(0))
Client sends: 1.5707963267948966 → Server responds: 6.12E-17      (cos(π/2) ≈ 0)
Client sends: fun:sin            → Server responds: Operation changed to sin
Client sends: 0                  → Server responds: 0.0           (sin(0))
Client sends: fun:tan            → Server responds: Operation changed to tan
Client sends: 0                  → Server responds: 0.0           (tan(0))
Client sends: Bye.               → Connection closes
```

#### Run

Start the server:
```bash
run_ejercicio.bat 4_3_2
```

Then connect with a TCP client (port **35001**):
```bash
ncat localhost 35001
```

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
