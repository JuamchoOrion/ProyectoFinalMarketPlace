package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.Sockets.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ChatController {

    @FXML
    private ComboBox<Usuario> comboContactos;

    @FXML
    private Button iniciarBtn;
    @FXML
    private Button chatsBtn;

    @FXML
    private Button chatButton;

    @FXML
    private Button perfil;

    @FXML
    private Label LabelNombreUsuario;

    @FXML
    private TextField inputCedula;

    @FXML
    private Button buscarVendedor;

    @FXML
    private Button cerrarButton;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuarioActual = marketplace.getUsuarioActual();
    private Vendedor vendedorActual = (Vendedor) usuarioActual;
    private ChatClient chatClient;
    Utilities logger = Utilities.getInstance();
    @FXML
    public void initialize() {
        List<Vendedor> contactos = vendedorActual.getListaContactos();
        comboContactos.getItems().addAll(contactos);
        perfil.setOnAction(event -> {
            try {
                navegarPerfil();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        iniciarBtn.setOnAction(event -> {
            try {
                iniciarMensajeria();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        cerrarButton.setOnAction(event -> {
            try {
                cerrarSesion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buscarVendedor.setOnAction(event -> {
            try {
                buscar();
            } catch (IOException e) {
                logger.logWarning("Error al intentar buscar un vendedor: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    // Método para manejar el clic en el botón "Iniciar Chat"

    // Método para manejar el clic en el botón "Chats Existentes"
    @FXML
    private void mostrarChatsExistentes() {

    }

    // Método para manejar el clic en el botón "Chat" de navegación
    @FXML
    private void navegarChat() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chatButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método para manejar el clic en el botón "Perfil" de navegación
    @FXML
    private void navegarPerfil() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) perfil.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método para manejar el clic en el botón "Buscar"
    @FXML
    private void buscar() throws IOException {
        String cedula = inputCedula.getText();

        for (Vendedor vendedor : marketplace.getVendedores()) {
            if (vendedor.getCedula().equals(cedula)) {
                marketplace.setVendedorPorAgregar(vendedor);
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PerfilDeOtro.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) buscarVendedor.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarChat() {
        Usuario seleccionado = comboContactos.getValue();
        if (seleccionado == null) {
            System.out.println("Selecciona un usuario para iniciar el chat.");
            return;
        }

        String serverIP = "192.168.198.172"; // IP del servidor
        int serverPort = 12345; // Puerto del servidor

        chatClient = new ChatClient(serverIP, serverPort);
        new Thread(() -> chatClient.start()).start();

        System.out.println("Chat iniciado con: " + seleccionado.getNombre());
    }

    // Método para manejar el clic en el botón "Cerrar sesión"
    @FXML
    private void cerrarSesion() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void iniciarMensajeria() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Mensajeria.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}