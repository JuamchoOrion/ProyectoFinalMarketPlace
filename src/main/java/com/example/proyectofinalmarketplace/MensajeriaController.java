package com.example.proyectofinalmarketplace;



import com.example.proyectofinalmarketplace.Sockets.ChatClient;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;

import javafx.stage.Stage;



import java.io.IOException;



public class MensajeriaController {



    @FXML

    private TextArea areaMensajes;  // Área para mostrar los mensajes



    @FXML

    private TextField inputMensaje; // Campo para escribir el mensaje

    @FXML

    private Button volver;



    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();

    private Usuario usuario = marketplace.getUsuarioActual();

    Vendedor vendedor = (Vendedor) usuario;

    private ChatClient chatClient;

    Utilities logger = Utilities.getInstance();

    ; // Campo para escribir el mensaje





    // Método para inicializar el chat y la conexión

    public void initialize() {

        // Asegúrate de que solo se configure una vez el cliente

        if (chatClient == null) {

            String serverIP = "192.168.198.172"; // Dirección IP del servidor

            int serverPort = 12345; // Puerto del servidor

            chatClient = new ChatClient(serverIP, serverPort);

            chatClient.setChatController(this); // Enlaza el controlador con el cliente

            new Thread(() -> chatClient.start()).start(); // Inicia la escucha de mensajes

        }



    }



    // Método para enviar un mensaje

    @FXML

    private void enviarMensaje() {

        String mensaje = inputMensaje.getText();

        if (!mensaje.isEmpty()) {

            chatClient.sendMessage(mensaje);  // Enviar mensaje al servidor

            areaMensajes.appendText("Tú: " + mensaje + "\n"); // Mostrar mensaje en el área de texto con el prefijo "Tú:"

            inputMensaje.clear(); // Limpiar el campo de entrada

        }

    }



    // Método para mostrar el mensaje recibido en el área de texto

    public void mostrarMensaje(String mensaje) {

        // Solo agrega el mensaje recibido sin el prefijo "Tú:"

        areaMensajes.appendText(mensaje + "\n");

    }

    @FXML

    private void volver() throws IOException {
        logger.logInfo("El vendedor " + vendedor.getNombre() + " está regresando.");

        // Cerrar la conexión del cliente
        if (chatClient != null) {
            chatClient.closeConnection();
        }
        FXMLLoader loader;

        Scene scene;

        loader = new FXMLLoader(getClass().getResource("Chat.fxml"));

        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());

        Stage stage = (Stage) volver.getScene().getWindow();

        stage.setScene(scene);

        stage.show();

    }

}

/**package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.Sockets.ChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MensajeriaController {

    @FXML
    private TextArea areaMensajes;  // Área para mostrar los mensajes

    @FXML
    private TextField inputMensaje; // Campo para escribir el mensaje

    private ChatClient chatClient;

    @FXML
    public void initialize() {
        // Se establece el cliente de chat al iniciar
        String serverIP = "192.168.1.7"; // IP del servidor
        int serverPort = 12345; // Puerto del servidor
        chatClient = new ChatClient(serverIP, serverPort);

        // Hilo para escuchar los mensajes entrantes
        new Thread(() -> chatClient.start()).start();
    }

    // Método para enviar un mensaje
    @FXML
    private void enviarMensaje() {
        String mensaje = inputMensaje.getText();
        if (!mensaje.isEmpty()) {
            chatClient.sendMessage(mensaje);  // Enviar mensaje al servidor
            areaMensajes.appendText("Tú: " + mensaje + "\n"); // Mostrar mensaje en el área de texto
            inputMensaje.clear(); // Limpiar el campo de entrada
        }
    }

    // Método para mostrar mensajes en el área de texto
    public void mostrarMensaje(String mensaje) {
        areaMensajes.appendText(mensaje + "\n");
    }
}**/
