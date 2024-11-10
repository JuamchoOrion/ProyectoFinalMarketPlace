package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PerfilController {

    @FXML
    private ImageView imageProduct1, imageProduct2, imageProduct3;

    @FXML
    private Label nameProduct1, nameProduct2, nameProduct3;

    @FXML
    private Label priceProduct1, priceProduct2, priceProduct3;

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

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    Vendedor vendedor = (Vendedor) usuario;
    Utilities logger = Utilities.getInstance(); // Instancia del logger

    @FXML
    public void initialize() {
        nombreUsuario.setText("ID: " + vendedor.getCedula() + ", Nombre: " + vendedor.getNombre()); // Configura el nombre de usuario
        logger.logInfo("Perfil cargado para el vendedor: " + vendedor.getNombre());
        cargarProductos();
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

    private void cargarProductos() {
        // Verifica si hay suficientes productos en la lista
        if (vendedor.getListaProductos().size() >= 3) {
            Producto producto1 = vendedor.getListaProductos().get(0);
            Producto producto2 = vendedor.getListaProductos().get(1);
            Producto producto3 = vendedor.getListaProductos().get(2);

            // Producto 1
            nameProduct1.setText(producto1.getNombre());
            priceProduct1.setText("Precio: €" + producto1.getPrecio());
            imageProduct1.setImage(new Image(getClass().getResourceAsStream(producto1.getImagen())));
            logger.logInfo("Producto 1 cargado en el perfil: " + producto1.getNombre());

            // Producto 2
            nameProduct2.setText(producto2.getNombre());
            priceProduct2.setText("Precio: €" + producto2.getPrecio());
            imageProduct2.setImage(new Image(getClass().getResourceAsStream(producto2.getImagen())));
            logger.logInfo("Producto 2 cargado en el perfil: " + producto2.getNombre());

            // Producto 3
            nameProduct3.setText(producto3.getNombre());
            priceProduct3.setText("Precio: €" + producto3.getPrecio());
            imageProduct3.setImage(new Image(getClass().getResourceAsStream(producto3.getImagen())));
            logger.logInfo("Producto 3 cargado en el perfil: " + producto3.getNombre());
        } else {
            logger.logWarning("No hay suficientes productos para mostrar. Productos disponibles: " + vendedor.getListaProductos().size());
        }
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
