package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PerfilController {

    @FXML
    private ImageView imageProduct1, imageProduct2, imageProduct3;

    @FXML
    private Label nameProduct1, nameProduct2, nameProduct3;

    @FXML
    private Label priceProduct1, priceProduct2, priceProduct3;

    @FXML
    private Label nombreUsuario;  // Etiqueta para mostrar el nombre de usuario

    @FXML
    private VBox topProductosVBox; // VBox para los 10 productos más vendidos

    @FXML
    private Button AggProducto;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuario = marketplace.getUsuarioActual();
    private Vendedor vendedor;
    Utilities logger = Utilities.getInstance(); // Instancia del logger

    @FXML
    public void initialize() {
        if (usuario instanceof Vendedor) {
            vendedor = (Vendedor) usuario;
            nombreUsuario.setText("ID: " + vendedor.getCedula() + ", Nombre: " + vendedor.getNombre()); // Configura el nombre de usuario
            logger.logInfo("Perfil cargado para el vendedor: " + vendedor.getNombre());
            cargarProductos();
            mostrarTopProductos();
        } else {
            logger.logWarning("El usuario actual no es un vendedor.");
        }

        AggProducto.setOnAction(event -> {
            try {
                crearProducto();
            } catch (IOException e) {
                logger.logWarning("Error al intentar crear un producto: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    private void crearProducto() throws IOException {
        logger.logInfo("El vendedor " + vendedor.getNombre() + " está intentando agregar un nuevo producto.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) AggProducto.getScene().getWindow();
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
}
