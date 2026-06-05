package edu.eci.arsw.networking.ejercicio6_4_1;

import java.rmi.Remote;
import java.rmi.RemoteException;

// esta interfaz define los métodos que pueden ser llamados de forma remota
// desde otra máquina virtual de Java usando RMI
public interface Chat extends Remote {

    // método que se invoca cuando otro usuario envía un mensaje
    // el parámetro message es el texto que escribió el otro usuario
    // RemoteException es obligatoria porque la comunicación puede fallar
    void sendMessage(String message) throws RemoteException;
}
