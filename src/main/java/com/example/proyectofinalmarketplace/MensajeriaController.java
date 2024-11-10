package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MensajeriaController {

    @FXML
    private VBox messageContainer;  // Contenedor de mensajes en la interfaz

    @FXML
    private TextField messageInput; // Campo de entrada para escribir el mensaje

    @FXML
    private Button enviarBtn;       // Botón para enviar el mensaje

    // Método que se llama para inicializar el controlador
    public void initialize() {
        // Aquí se podrían inicializar las configuraciones necesarias
        // como listeners de interfaz, entre otros.
    }

    // Método para manejar el envío de mensajes
    @FXML
    private void handleSendMessage() {
        String message = messageInput.getText(); // Obtiene el texto del campo de entrada
        if (!message.isEmpty()) {
            // Llama al método para agregar el mensaje a la interfaz como remitente
            addMessage("Vendedor B", message, true);  // Cambia "Vendedor B" por el remitente actual
            messageInput.clear(); // Limpia el campo de entrada
        }
    }

    // Método para agregar mensajes a la interfaz, ya sea como remitente o receptor
    private void addMessage(String sender, String message, boolean isSender) {
        HBox messageBox = new HBox(); // Contenedor horizontal para el mensaje
        messageBox.setSpacing(5);

        // Etiqueta con el nombre del remitente
        Label senderLabel = new Label(sender);
        senderLabel.getStyleClass().add("label-style");

        // Etiqueta con el mensaje
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add(isSender ? "mensaje-texto-emisor" : "mensaje-texto-receptor");

        // Configuración de alineación dependiendo de si es remitente o receptor
        if (isSender) {
            messageBox.getChildren().addAll(messageLabel, senderLabel);
            messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
        } else {
            messageBox.getChildren().addAll(senderLabel, messageLabel);
        }

        // Agrega el mensaje al contenedor de mensajes
        messageContainer.getChildren().add(messageBox);
    }

    // Método que puede utilizarse para cerrar la conexión o liberar recursos al cerrar la ventana
    public void closeConnection() {
        // Cierra conexiones o libera recursos aquí si es necesario
    }
}
