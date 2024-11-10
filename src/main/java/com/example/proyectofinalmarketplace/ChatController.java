package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class ChatController {

    @FXML
    private ComboBox<Usuario> comboContactos;

    @FXML
    private Button iniciarBtn;
    @FXML
    private Button chatsBtn;

    @FXML
    private Button chat;

    @FXML
    private Button perfil;

    @FXML
    private Label LabelNombreUsuario;

    @FXML
    private TextField inputBuscar;

    @FXML
    private Button buscar;

    @FXML
    private Button cerrarButton;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuarioActual = marketplace.getUsuarioActual();
    private Vendedor vendedorActual = (Vendedor) usuarioActual;
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
    List<Vendedor> contactos = vendedorActual.getListaContactos();
    comboContactos.getItems().addAll(contactos);
    }

    // Método para manejar el clic en el botón "Iniciar Chat"
    @FXML
    private void iniciarChat() {

    }

    // Método para manejar el clic en el botón "Chats Existentes"
    @FXML
    private void mostrarChatsExistentes() {

    }

    // Método para manejar el clic en el botón "Chat" de navegación
    @FXML
    private void navegarChat() {
        // Código para cambiar a la vista de chat
    }

    // Método para manejar el clic en el botón "Perfil" de navegación
    @FXML
    private void navegarPerfil() {
        // Código para cambiar a la vista de perfil
    }

    // Método para manejar el clic en el botón "Buscar"
    @FXML
    private void buscarProducto() {
        String query = inputBuscar.getText();
        // Código para buscar el producto en base al texto ingresado
    }

    // Método para manejar el clic en el botón "Cerrar sesión"
    @FXML
    private void cerrarSesion() {
        // Código para cerrar la sesión del usuario y volver a la pantalla de login
    }
}
