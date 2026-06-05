@echo off
setlocal
set ROOT=%~dp0

echo ========================================
echo   ARSW Networking Workshop - Launcher
echo ========================================

rem --- compile all Java sources ---
echo [1/2] Compiling ...
if not exist "%ROOT%target\classes" mkdir "%ROOT%target\classes"

dir /s /b "%ROOT%src\main\java\*.java" 2>nul | find /V "NetworkingWorkshopApplication" > "%ROOT%target\sources.txt"
if ERRORLEVEL 1 (
    echo [ERROR] No Java source files found in %ROOT%src\main\java\
    pause
    exit /b 1
)

javac -d "%ROOT%target\classes" @"%ROOT%target\sources.txt" 2> "%ROOT%target\errors.txt"
if ERRORLEVEL 1 (
    echo [ERROR] Compilation failed:
    type "%ROOT%target\errors.txt"
    pause
    exit /b %ERRORLEVEL%
)
echo [OK] Compilation successful.

set CPATH=%ROOT%target\classes

rem --- no args: interactive launcher ---
if "%1"=="" (
    echo.
    echo [2/2] Starting interactive launcher ...
    echo.
    java -cp "%CPATH%" edu.eci.arsw.networking.WorkshopLauncher
    exit /b %ERRORLEVEL%
)

rem --- second arg "client": run generic TCP client (or UDP client for 5.2.1) ---
set PORT=35000
if "%2"=="client" (
    if "%1"=="5_2_1" (
        echo.
        echo [2/2] Running DatagramTimeClient ...
        java -cp "%CPATH%" edu.eci.arsw.networking.ejercicio5_2_1.DatagramTimeClient
        exit /b %ERRORLEVEL%
    )
    echo.
    echo [2/2] Connecting to localhost:%PORT% ...
    java -cp "%CPATH%" edu.eci.arsw.networking.util.TcpClient localhost %PORT%
    exit /b %ERRORLEVEL%
)

rem --- map exercise number to class name ---
if "%1"=="1" set CLASS_NAME=URLInfo
if "%1"=="2" set CLASS_NAME=URLBrowser
if "%1"=="4_3_1" set CLASS_NAME=SquareServer
if "%1"=="4_3_2" set CLASS_NAME=TrigServer
if "%1"=="4_4" set CLASS_NAME=HttpServer
if "%1"=="4_5_1" set CLASS_NAME=MultiServer
if "%1"=="5_2_1" set CLASS_NAME=DatagramTimeServer
if "%1"=="6_4_1" set CLASS_NAME=RmiChat

if "%CLASS_NAME%"=="" (
    echo [ERROR] Unknown exercise number '%1'
    pause
    exit /b 1
)

echo.
echo [2/2] Running ejercicio%1/%CLASS_NAME% ...
echo.
if "%1"=="4_4" (
    echo The server is now running. Open your browser and go to:
    echo   http://localhost:35000
    echo.
    echo Waiting for browser connection ...
)
if "%1"=="5_2_1" (
    echo DatagramTimeServer listening on port 4445 ...
    echo Press Ctrl+C to stop.
)
java -cp "%CPATH%" edu.eci.arsw.networking.ejercicio%1.%CLASS_NAME%

if ERRORLEVEL 1 (
    echo [ERROR] Execution failed.
    pause
)
