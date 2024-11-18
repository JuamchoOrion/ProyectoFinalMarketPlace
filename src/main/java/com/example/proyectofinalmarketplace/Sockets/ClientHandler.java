package com.example.proyectofinalmarketplace.Sockets;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ChatServer server;
    private PrintWriter out;
    private BufferedReader in;
    private final String identifier; // Identificador único para este cliente
    private boolean connected = true; // Estado de la conexión

    public ClientHandler(Socket socket, ChatServer server, String identifier) {
        this.socket = socket;
        this.server = server;
        this.identifier = identifier;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println(identifier + " conectado.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(identifier + ": " + message);
                server.broadcast(identifier + ": " + message, this);
            }
        } catch (IOException e) {
            System.err.println("Error en " + identifier + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public boolean isConnected() {
        return connected && !socket.isClosed();
    }

    public String getIdentifier() {
        return identifier;
    }

    private void closeConnection() {
        connected = false;
        server.removeClient(this);

        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error cerrando conexión de " + identifier + ": " + e.getMessage());
        }
    }
}


/**package com.example.proyectofinalmarketplace.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket socket;
    private final ChatServer server;
    private PrintWriter out;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + message);
                server.broadcast(message);
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}**/