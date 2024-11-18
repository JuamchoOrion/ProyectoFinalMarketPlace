package com.example.proyectofinalmarketplace.Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    private int clientCounter = 0; // Contador de clientes para asignar identificadores

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Servidor iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) { // Permitir conexiones continuamente
                // Limpiar clientes desconectados antes de aceptar una nueva conexión
                synchronized (clients) {
                    clients.removeIf(client -> !client.isConnected());
                }

                // Aceptar nuevas conexiones
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                System.out.println("Cliente conectado: " + clientCounter);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this, "Cliente-" + clientCounter);

                synchronized (clients) {
                    clients.add(clientHandler);
                }

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    public synchronized void broadcast(String message, ClientHandler sender) {
        // Eliminar clientes desconectados antes de enviar mensajes
        clients.removeIf(client -> !client.isConnected());

        for (ClientHandler client : clients) {
            if (client != sender) { // No enviar el mensaje al remitente
                client.sendMessage(message);
            }
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println(client.getIdentifier() + " se ha desconectado.");
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(12345);
        server.start();
    }
}
