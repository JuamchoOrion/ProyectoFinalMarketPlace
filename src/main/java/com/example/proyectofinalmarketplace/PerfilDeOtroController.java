package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PerfilDeOtroController {
    @FXML
    private ImageView imageProduct1, imageProduct2, imageProduct3;

    @FXML
    private Label nameProduct1, nameProduct2, nameProduct3;

    @FXML
    private Label priceProduct1, priceProduct2, priceProduct3;

    @FXML
    private Button homeButton;

    @FXML
    private Label nombreUsuario;  // Etiqueta para mostrar el nombre de usuario

    @FXML
    private Button chatButton;

    @FXML
    private TextField inputCedula;

    @FXML
    private Button buscarVendedor;

    @FXML
    private Button aggContactoButton;

    @FXML
    private VBox vboxComentarios;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    Vendedor vendedor = (Vendedor) usuario;
    private Vendedor vendedorPorAgregar = marketplace.getVendedorPorAgregar();
    Utilities logger = Utilities.getInstance(); // Instancia del logger

    @FXML
    public void initialize() {
        nombreUsuario.setText("ID: " + vendedorPorAgregar.getCedula() + ", Nombre: " + vendedorPorAgregar.getNombre());
        logger.logInfo("Perfil cargado para el vendedor: " + vendedorPorAgregar.getNombre());
        cargarProductos();
        cargarComentarios();

        chatButton.setOnAction(event -> {
            try {
                chat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        homeButton.setOnAction(event -> {
            try {
                volverHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        aggContactoButton.setOnAction(event -> {
            try {
                enviarSolicitud();
            } catch (Exception e) {
                e.printStackTrace();
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
    private void enviarSolicitud() throws Exception {
            vendedor.enviarSolicitudContacto(vendedorPorAgregar);
            System.out.println(vendedor.getNombre());
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
        if (vendedorPorAgregar.getListaProductos().size() >= 3) {
            Producto producto1 = vendedorPorAgregar.getListaProductos().get(0);
            Producto producto2 = vendedorPorAgregar.getListaProductos().get(1);
            Producto producto3 = vendedorPorAgregar.getListaProductos().get(2);

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
            logger.logWarning("No hay suficientes productos para mostrar. Productos disponibles: " + vendedorPorAgregar.getListaProductos().size());
        }
    }
    private void cargarComentarios() {
        if (vendedor.esContacto(vendedorPorAgregar)) {
            // Limpia el VBox para evitar duplicados al recargar los comentarios
            vboxComentarios.getChildren().clear();

            // Itera sobre cada producto en la lista de productos del vendedor
            for (Producto producto : vendedorPorAgregar.getListaProductos()) {
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
    }

