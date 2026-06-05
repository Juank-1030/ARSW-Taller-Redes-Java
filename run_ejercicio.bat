@echo off
setlocal
set ROOT=%~dp0

rem --- compile ---
echo [INFO] Compiling ...
call mvn compile -f "%ROOT%pom.xml"
if ERRORLEVEL 1 (
    echo [ERROR] Compilation failed.
    exit /b %ERRORLEVEL%
)

set CPATH=%ROOT%target\classes

rem --- no args: interactive launcher ---
if "%1"=="" (
    echo.
    echo Starting interactive launcher ...
    java -cp "%CPATH%" edu.eci.arsw.networking.WorkshopLauncher
    exit /b %ERRORLEVEL%
)

rem --- port mapping for client mode ---
set PORT=35000
if "%1"=="4_3_2" set PORT=35000

rem --- second arg "client": run generic TCP client ---
if "%2"=="client" (
    echo.
    echo [INFO] Connecting to localhost:%PORT% ...
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
if "%1"=="5_2_1" set CLASS_NAME=DatagramTimeClient
if "%1"=="6_4_1" set CLASS_NAME=RmiChat

if "%CLASS_NAME%"=="" (
    echo [ERROR] Unknown exercise number '%1'
    exit /b 1
)

echo.
echo [INFO] Running ejercicio%1/%CLASS_NAME% ...
java -cp "%CPATH%" edu.eci.arsw.networking.ejercicio%1.%CLASS_NAME%
