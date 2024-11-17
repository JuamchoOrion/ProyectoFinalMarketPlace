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
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}