package com.example.proyectofinalmarketplace.Sockets;

import com.example.proyectofinalmarketplace.MensajeriaController;
import javafx.application.Platform;
import java.io.*;
import java.net.*;

public class ChatClient {

    private String serverIP;
    private int serverPort;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private MensajeriaController chatController; // El controlador para mostrar mensajes en la vista

    public ChatClient(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    // Método para iniciar la conexión y escuchar los mensajes
    public void start() {
        try {
            socket = new Socket(serverIP, serverPort);  // Establece la conexión con el servidor
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Leer mensajes del servidor
            out = new PrintWriter(socket.getOutputStream(), true); // Enviar mensajes al servidor

            System.out.println("Conectado al servidor en " + serverIP + ":" + serverPort);

            // Crear un hilo para escuchar los mensajes del servidor
            new Thread(() -> listenForMessages()).start();
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    // Método para escuchar los mensajes del servidor
    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                // Imprimir en la consola cada mensaje recibido
                System.out.println("Mensaje recibido: " + message);

                // Usar Platform.runLater() para actualizar la vista en el hilo de JavaFX
                if (chatController != null) {
                    String finalMessage = message;
                    Platform.runLater(() -> chatController.mostrarMensaje(finalMessage));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al recibir mensaje: " + e.getMessage());
        }
    }

    // Método para enviar un mensaje al servidor
    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                out.println(message);  // Enviar mensaje al servidor
                out.flush();           // Asegúrate de que el mensaje se envíe de inmediato
                System.out.println("Mensaje enviado: " + message); // Confirmación en consola
            } catch (Exception e) {
                System.err.println("Error al enviar mensaje: " + e.getMessage());
            }
        }).start();
    }

    // Establecer el controlador de la vista para mostrar mensajes
    public void setChatController(MensajeriaController chatController) {
        this.chatController = chatController;
    }
    public static void main(String[] args) {
        ChatClient client = new ChatClient("192.168.198.172", 12345);
        client.start();

    }
    // Ejemplo de cierre ordenado en el cliente
    public void closeConnection() {
        try {
            if (out != null) {
                out.println("DISCONNECT"); // Mensaje para el servidor
                out.close();
            }
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error cerrando conexión del cliente: " + e.getMessage());
        }
    }

}


/**package com.example.proyectofinalmarketplace.Sockets;

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
    private PrintWriter out;
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
        ChatClient client = new ChatClient("192.168.1.7", 12345);
        client.start();
    }
    public static void main(String[] args) {
        ChatClient client = new ChatClient("192.168.1.7", 12345);
        client.start();
    }
    // Método para enviar mensajes
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

}**/
