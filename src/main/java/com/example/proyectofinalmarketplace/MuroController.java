package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MuroController {

    @FXML
    private GridPane productGrid;

    @FXML
    private TextField inputBuscar;

    @FXML
    private Button cerrarButton;

    @FXML
    private Button chat;

    @FXML
    private Button buscar;

    @FXML
    private Button perfil;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuarioActual = marketplace.getUsuarioActual();
    Utilities logger = Utilities.getInstance();

    public void initialize() {
        chat.setOnAction(event -> {
            try {
                chatVista();
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

        perfil.setOnAction(event -> {
            try {
                perfilVista();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buscar.setOnAction(event -> {
            try {
                buscarVendedor();
            } catch (IOException e) {
                logger.logWarning("Error al intentar buscar un vendedor: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        List<Producto> productos = new ArrayList<>();

// Agregar productos del vendedor actual
        if (usuarioActual instanceof Vendedor) {
            for (Producto producto : ((Vendedor) usuarioActual).getListaProductos()) {
                if (producto.getEstado() == Estado.PUBLICADO) { // Verifica el estado del producto
                    productos.add(producto);
                    logger.logInfo("Producto " + producto.getNombre() + " del vendedor " + ((Vendedor) usuarioActual).getNombre() + " añadido a la lista.");
                }
            }
        } else {
            logger.logWarning("El usuario actual no es un vendedor.");
        }

// Agregar productos de la lista de amigos del vendedor principal
        for (Vendedor amigo : ((Vendedor) usuarioActual).getListaContactos()) {
            for (Producto producto : amigo.getListaProductos()) {
                if (producto.getEstado() == Estado.PUBLICADO) { // Verifica el estado del producto
                    productos.add(producto);
                    logger.logInfo("Producto " + producto.getNombre() + " del amigo " + amigo.getNombre() + " añadido a la lista.");
                }
            }
        }

// Ordenar la lista de productos desde el más nuevo al más viejo
        productos.sort(Comparator.comparing(Producto::getFechaPublicacion).reversed());

        int column = 0;
        int row = 0;

        for (Producto producto : productos) {
            VBox productBox = crearVistaProducto(producto);
            productGrid.add(productBox, column++, row);

            if (column == 3) {  // Muestra 3 productos por fila
                column = 0;
                row++;
            }
        }
    }
    private void buscarVendedor() throws IOException {
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

    private void chatVista() throws IOException {
        logger.logInfo("El vendedor " + ((Vendedor) usuarioActual).getNombre() + " está accediendo a la vista de chat.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void perfilVista() throws IOException {
        logger.logInfo("El vendedor " + ((Vendedor) usuarioActual).getNombre() + " está accediendo a la vista de perfil.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) perfil.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void cerrarSesion() throws IOException {
        if (usuarioActual instanceof Vendedor) {
            logger.logInfo("El vendedor " + ((Vendedor) usuarioActual).getNombre() + " está cerrando sesión y navegando a Inicio.fxml.");
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

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    private VBox crearVistaProducto(Producto producto) {
        VBox productBox = new VBox();
        productBox.setSpacing(10);
        productBox.getStyleClass().add("container");

        // Imagen del producto
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(producto.getImagen())));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        // Nombre del producto
        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("titulo");

        // Precio del producto
        Label precioLabel = new Label("€" + producto.getPrecio());
        precioLabel.getStyleClass().add("label-style");

        // Recuento de likes
        Label likesLabel = new Label("Likes: " + producto.getLikes());
        likesLabel.getStyleClass().add("label-style");

        // Agregar elementos a la caja del producto
        productBox.getChildren().addAll(imageView, nombreLabel, precioLabel, likesLabel);

        // Cambiar color al pasar el mouse
        productBox.setOnMouseEntered(event -> {
            productBox.setStyle("-fx-background-color: #5d1569;");
        });
        productBox.setOnMouseExited(event -> {
            productBox.setStyle("");
        });

        productBox.setOnMouseClicked(event -> {
            System.out.println("Clic en el producto: " + producto.getNombre()); // Verifica que el clic se detecte
            logger.logInfo("Clic en el producto: " + producto.getNombre());
            abrirProductoView(producto);
        });

        return productBox;
    }
    private void abrirProductoView(Producto producto) {
        try {
            if (producto == null) {
                System.out.println("Error: El producto es nulo.");
                return;
            } else {
                System.out.println("Producto recibido: " + producto.getNombre());
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductoView.fxml"));
            Parent root = loader.load();

            ProductoViewController productoViewController = loader.getController();
            productoViewController.setProducto(producto);

            Stage currentStage = (Stage) chat.getScene().getWindow();
            Scene currentScene = new Scene(root);
            currentStage.setScene(currentScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista FXML.");
            e.printStackTrace();  // Imprimir el stack trace completo
        }
    }



}
