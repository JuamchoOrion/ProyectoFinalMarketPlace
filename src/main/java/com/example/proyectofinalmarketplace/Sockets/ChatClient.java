
package com.example.proyectofinalmarketplace.Sockets;

import com.example.proyectofinalmarketplace.ChatController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private final String serverAddress;
    private final int serverPort;

    public ChatClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor. Escribe tus mensajes...");

            // Hilo para recibir mensajes del servidor
            Thread receiver = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Mensaje recibido: " + message);
                    }
                } catch (IOException e) {
                    System.err.println("Error al recibir mensaje: " + e.getMessage());
                }
            });
            receiver.start();

            // Enviar mensajes al servidor
            while (true) {
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Desconectado del chat.");
                    break;
                }
                out.println(userInput);
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
    public void ejecutar() {
        ChatClient client = new ChatClient("192.168.198.172", 12345);
        client.start();
    }
    public static void main(String[] args) {
        ChatClient client = new ChatClient("192.168.198.172", 12345);
        client.start();
    }
}