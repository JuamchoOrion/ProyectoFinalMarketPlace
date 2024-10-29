package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerfilController {

    @FXML
    private ImageView imageProduct1, imageProduct2, imageProduct3;

    @FXML
    private Label nameProduct1, nameProduct2, nameProduct3;

    @FXML
    private Label priceProduct1, priceProduct2, priceProduct3;

    @FXML
    private Button AggProducto;

    private Vendedor vendedor;

    @FXML
    public void initialize() {
        AggProducto.setOnAction(event -> {
            try {
                crearProducto();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        cargarProductosQuemados(); // Llama a la función que inicializa los productos
        cargarProductos();

    }
    private void crearProducto() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) AggProducto.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Método para crear productos "quemados" y asignarlos al vendedor
    private void cargarProductosQuemados() {
        vendedor = new Vendedor("Juan Pérez","3" ,"Vendedor confiable", "contrasenia123", "Calle Falsa 123");

        // Crear una lista de productos quemados con Estado y Categoria en null
        List<Producto> productosQuemados = new ArrayList<>();

        productosQuemados.add(new Producto(
                "Camiseta Deportiva",
                "001",
                "/imagenes/logo.png", // Ruta de imagen dentro del paquete resources
                "25.99",
                LocalDate.now().minusDays(10),
                null, // Categoria nula
                150,
                null)); // Estado nulo

        productosQuemados.add(new Producto(
                "Zapatos Running",
                "002",
                "/imagenes/logo.png", // Ruta de imagen dentro del paquete resources
                "45.00",
                LocalDate.now().minusDays(5),
                null, // Categoria nula
                200,
                null)); // Estado nulo

        productosQuemados.add(new Producto(
                "Mochila Escolar",
                "003",
                "/imagenes/logo.png", // Ruta de imagen dentro del paquete resources
                "30.50",
                LocalDate.now().minusDays(2),
                null, // Categoria nula
                120,
                null)); // Estado nulo

        // Asignar la lista de productos al vendedor
        vendedor.getListaProductos().addAll(productosQuemados);
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

            // Producto 2
            nameProduct2.setText(producto2.getNombre());
            priceProduct2.setText("Precio: €" + producto2.getPrecio());
            imageProduct2.setImage(new Image(getClass().getResourceAsStream(producto2.getImagen())));

            // Producto 3
            nameProduct3.setText(producto3.getNombre());
            priceProduct3.setText("Precio: €" + producto3.getPrecio());
            imageProduct3.setImage(new Image(getClass().getResourceAsStream(producto3.getImagen())));
        }
    }
}