package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PerfilController {


    @FXML
    private ComboBox<Vendedor> comboSolicitudes;

    @FXML
    private Button homeButton;
    @FXML
    private Button chulitoButton;

    @FXML
    private Button cancelarButton;
    @FXML
    private VBox vboxComentarios;
    @FXML
    private Label nombreUsuario;  // Etiqueta para mostrar el nombre de usuario

    @FXML
    private VBox topProductosVBox; // VBox para los 10 productos más vendidos

    @FXML
    private Button chatButton;

    @FXML
    private Button AggProducto;

    @FXML
    private TextField inputCedula;

    @FXML
    private Button buscarVendedor;

    @FXML
    private GridPane productGrid;



    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    Vendedor vendedor = (Vendedor) usuario;
    Utilities logger = Utilities.getInstance(); // Instancia del logger

    @FXML
    public void initialize() {
        nombreUsuario.setText("ID: " + vendedor.getCedula() + ", Nombre: " + vendedor.getNombre()); // Configura el nombre de usuario
        logger.logInfo("Perfil cargado para el vendedor: " + vendedor.getNombre());
        mostrarTopProductos();
        cargarComentarios();
        homeButton.setOnAction(event -> {
            try {
                volverHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            });
        chatButton.setOnAction(event -> {
            try {
                chat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Vendedor> solicitudesPendientes = vendedor.getSolicitudesPendientes();
        comboSolicitudes.getItems().addAll(solicitudesPendientes);


        buscarVendedor.setOnAction(event -> {
            try {
               buscar();
            } catch (IOException e) {
                logger.logWarning("Error al intentar buscar un vendedor: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        AggProducto.setOnAction(event -> {
            try {
                crearProducto();
            } catch (IOException e) {
                logger.logWarning("Error al intentar crear un producto: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        chulitoButton.setOnAction(event -> {
            try {
                aceptar();
            } catch (IOException e) {
                logger.logWarning("Error al intentar crear un producto: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        cancelarButton.setOnAction(event -> {
            try {
                rechazar();
            } catch (IOException e) {
                logger.logWarning("Error al intentar crear un producto: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        List<Producto> productos = new ArrayList<>();

// Agregar productos del vendedor actual
        for (Producto producto : vendedor.getListaProductos()) {
            productos.add(producto);
            logger.logInfo("Producto " + producto.getNombre() + " del vendedor " + vendedor.getNombre() + " añadido a la lista.");
        }


// Ordenar la lista de productos desde el más nuevo al más viejo
        productos.sort(Comparator.comparing(Producto::getFechaPublicacion).reversed());

        int column = 0;
        int row = 0;
        for (Producto producto : productos) {
            VBox productBox = crearVistaProducto(producto);
            productGrid.add(productBox, column++, row);

            if (column == 2) {  // Muestra 3 productos por fila
                column = 0;
                row++;

        }
        }
    }

    private void aceptar() throws IOException {
        Vendedor vendedorAAceptar = comboSolicitudes.getValue();
        vendedor.aceptarSolicitud(vendedorAAceptar);
        System.out.println(vendedor.getListaContactos());
    }
    private void rechazar() throws IOException {
        Vendedor vendedorAAceptar = (Vendedor) comboSolicitudes.getValue();
        vendedor.rechazarSolicitud(vendedorAAceptar);
    }
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
    private void chat() throws IOException {
        logger.logInfo("El vendedor " + vendedor.getNombre() + " está ingresando al chat.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chatButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

            Stage currentStage = (Stage) chatButton.getScene().getWindow();
            Scene currentScene = new Scene(root);
            currentStage.setScene(currentScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista FXML.");
            e.printStackTrace();  // Imprimir el stack trace completo
        }}
    private void crearProducto() throws IOException {
        logger.logInfo("El vendedor " + vendedor.getNombre() + " está intentando agregar un nuevo producto.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("AgregarProducto.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) AggProducto.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void volverHome() throws IOException {
        logger.logInfo("El usuario " + usuario.getNombre() + " está regresando al home ");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Muro.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarTopProductos() {
        List<Producto> productosTop = vendedor.getListaProductos();
        for (Producto producto : productosTop) {
            Label productoLabel = new Label(producto.getNombre());
            topProductosVBox.getChildren().add(productoLabel);
            logger.logInfo("Producto añadido a la lista de top productos: " + producto.getNombre());
        }
    }
    private void cargarComentarios() {
        // Limpia el VBox para evitar duplicados al recargar los comentarios
        vboxComentarios.getChildren().clear();

        // Itera sobre cada producto en la lista de productos del vendedor
        for (Producto producto : vendedor.getListaProductos()) {
            // Label para mostrar el nombre del producto
            Label nombreProductoLabel = new Label("Producto: " + producto.getNombre());// Estilo opcional

            vboxComentarios.getChildren().add(nombreProductoLabel); // Agrega el nombre del producto

            // Itera sobre cada comentario del producto
            for (Comentario comentario : producto.getComentarios()) {
                // HBox para contener el comentario y una fecha (opcional)
                HBox comentarioBox = new HBox();
                comentarioBox.setSpacing(10);

                // Label para el comentario
                Label comentarioLabel = new Label(comentario.getTexto());
                comentarioLabel.setWrapText(true); // Permite el ajuste del texto en varias líneas


                // Agrega el comentario y la fecha a la HBox
                comentarioBox.getChildren().addAll(comentarioLabel);


                vboxComentarios.getChildren().add(comentarioBox);
            }

            // Añade una separación visual entre productos
            vboxComentarios.getChildren().add(new Label("----------"));
        }
    }
}
