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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @FXML
    private Button likesButton;

    @FXML
    private Label FechaHora;


    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    private Vendedor usuarioV = (Vendedor) usuario;
    private Producto producto;
    Utilities logger = Utilities.getInstance();
    private boolean esLike = false;



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
        likesButton.setOnAction(event -> {
            try {
                likear(producto);
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
        chat.setOnAction(event -> {
            try{
                handleChatButtonAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buscar.setOnAction(event -> {
            try {
                handleBuscarButtonAction();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
        cerrarButton.setOnAction(event -> {
            try{
                handleCerrarButtonAction();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
        perfil.setOnAction(event -> {
            try{
                handlePerfilButtonAction();
            }catch(IOException e){
                throw new RuntimeException(e);
            }
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
    private void handleChatButtonAction() throws IOException {
        logger.logInfo("El vendedor " + ((Vendedor) usuario).getNombre() + " está accediendo a la vista de chat.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método que se ejecuta al hacer clic en el botón de perfil
    @FXML
    private void handlePerfilButtonAction() throws IOException {
        logger.logInfo("El vendedor " + ((Vendedor) usuario).getNombre() + " está accediendo a la vista de perfil.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) perfil.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método que se ejecuta al hacer clic en el botón de buscar
    @FXML
    private void handleBuscarButtonAction() throws IOException {
        String cedula = inputBuscar.getText();

        for (Vendedor vendedor : marketplace.getVendedores()) {
            if (vendedor.getCedula().equals(cedula)) {
                marketplace.setVendedorPorAgregar(vendedor);
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PerfilDeOtro.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) buscar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    // Método que se ejecuta al hacer clic en el botón de cerrar sesión
    @FXML
    private void handleCerrarButtonAction() throws IOException {
        if (usuario instanceof Vendedor) {
            logger.logInfo("El vendedor " + ((Vendedor) usuario).getNombre() + " está cerrando sesión y navegando a Inicio.fxml.");
        } else {
            logger.logWarning("El usuario actual no es un vendedor al cerrar sesión.");
        }

        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        LocalDateTime fechaPublicacion = producto.getFechaPublicacion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaComoString = fechaPublicacion.format(formatter);

        FechaHora.setText(fechaComoString);
        configurarProducto();
    }

    public void likear(Producto producto) throws IOException {
        if (esLike) {
            usuarioV.quitarMeGusta(producto);
            esLike = false;
        } else {
            usuarioV.darMeGusta(producto);
            esLike = true;
        }

        likesLabel.setText("Likes: " + producto.getLikes());
    }

}
