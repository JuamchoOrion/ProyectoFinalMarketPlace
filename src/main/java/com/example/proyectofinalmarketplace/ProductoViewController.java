package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductoViewController {

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

    @FXML
    private Label labelNombreProducto;

    @FXML
    private ImageView imagenProducto;

    @FXML
    private Button comprarBtn;

    @FXML
    private Button agregarComentarioBtn;

    @FXML
    private TextField comentario;

    @FXML
    private Button volverBtn;

    @FXML
    private Label likesLabel;

    @FXML
    private Label precioLabel;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    private Vendedor usuarioV = (Vendedor) usuario;
    private Producto producto;

    // Este método se ejecuta al inicializar la vista
    @FXML
    private void initialize() {
        // Verifica si el producto no es nulo y lo configura
        if (producto != null) {
            configurarProducto();
        } else {
            configurarProductoNoDisponible();
        }

        volverBtn.setOnAction(event -> {
            try {
                handleVolverButtonAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        comprarBtn.setOnAction(event -> {

                handleComprarButtonAction();
        });
        agregarComentarioBtn.setOnAction(event -> {
            handleAgregarComentarioButtonAction();
        });
    }

    // Configura los detalles del producto en la vista
    private void configurarProducto() {
        if (producto == null) {
            // Si el producto es nulo, no hacemos nada más y mostramos el mensaje de no disponible
            return;
        }

        // Configuración de los detalles del producto
        labelNombreProducto.setText(producto.getNombre());
        precioLabel.setText("€" + producto.getPrecio());

        // Verifica si la imagen existe antes de asignarla
        if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
            try {
                Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));
                imagenProducto.setImage(image);
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
                imagenProducto.setImage(null);
            }
        } else {
            imagenProducto.setImage(null);  // Si no hay imagen, no mostramos nada
        }

        likesLabel.setText("Likes: " + producto.getLikes());

        if (usuario != null) {
            LabelNombreUsuario.setText(usuario.getNombre());
        }
    }

    // Configura los detalles cuando el producto no está disponible
    private void configurarProductoNoDisponible() {
        labelNombreProducto.setText("Producto no disponible");
        precioLabel.setText("");
        likesLabel.setText("");
        imagenProducto.setImage(null);
        LabelNombreUsuario.setText("");
    }

    // Método que se ejecuta al hacer clic en el botón de chat
    @FXML
    private void handleChatButtonAction() {

    }

    // Método que se ejecuta al hacer clic en el botón de perfil
    @FXML
    private void handlePerfilButtonAction() {

    }

    // Método que se ejecuta al hacer clic en el botón de buscar
    @FXML
    private void handleBuscarButtonAction() {
        System.out.println("Buscando productos...");
    }

    // Método que se ejecuta al hacer clic en el botón de cerrar sesión
    @FXML
    private void handleCerrarButtonAction() {
        System.out.println("Cerrando sesión...");
    }

    // Método que se ejecuta al hacer clic en el botón de comprar
    @FXML
    private void handleComprarButtonAction( ) {
        producto.setEstado(Estado.VENDIDO);
        System.out.println(producto.getEstado().toString());
    }

    // Método que se ejecuta al hacer clic en el botón de agregar comentario
    @FXML
    private void handleAgregarComentarioButtonAction() {
        String comment = comentario.getText();
        producto.agregarComentario(new Comentario(comment, this.usuarioV));
        System.out.println(producto.getComentarios().toString());
    }

    // Método que se ejecuta al hacer clic en el botón de volver
    @FXML
    private void handleVolverButtonAction() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Muro.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) volverBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método para establecer el producto desde el controlador anterior
    public void setProducto(Producto producto) {
        this.producto = producto;
        // Al establecer el producto, volvemos a actualizar la vista
        configurarProducto();
    }
}
