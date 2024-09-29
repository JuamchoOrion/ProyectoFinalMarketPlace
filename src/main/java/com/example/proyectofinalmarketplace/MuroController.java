package com.example.proyectofinalmarketplace;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MuroController {

    @FXML
    private GridPane productGrid;

    public void initialize() {
        List<Producto> productos = obtenerProductos();  // Simulamos obtener los productos

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

    private List<Producto> obtenerProductos() {
        return List.of(
                new Producto("Producto 1", "C001", "/com/example/proyectofinalmarketplace/logo.png", "19.99", LocalDate.now(), null, 100, null),
                new Producto("Producto 2", "C002", "/com/example/proyectofinalmarketplace/logo.png", "29.99", LocalDate.now(), null, 250, null),
                new Producto("Producto 3", "C003", "/com/example/proyectofinalmarketplace/logo.png", "39.99", LocalDate.now(), null, 75, null),
                new Producto("Producto 4", "C001", "/com/example/proyectofinalmarketplace/logo.png", "19.99", LocalDate.now(), null, 100, null),
                new Producto("Producto 5", "C001", "/com/example/proyectofinalmarketplace/logo.png", "19.99", LocalDate.now(), null, 100, null)
                // Puedes agregar más productos aquí
        );
    }

    // Crear la vista de un producto en el GridPane
    private VBox crearVistaProducto(Producto producto) {
        VBox productBox = new VBox();
        productBox.setSpacing(10);
        productBox.getStyleClass().add("container"); // Clase CSS para el contenedor

        // Imagen del producto
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(producto.getImagen())));

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        // Nombre del producto
        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("titulo"); // Aplicar estilo de título

        // Precio del producto
        Label precioLabel = new Label("€" + producto.getPrecio());
        precioLabel.getStyleClass().add("label-style"); // Estilo de etiqueta

        // Recuento de likes
        Label likesLabel = new Label("Likes: " + producto.getLikes());
        likesLabel.getStyleClass().add("label-style"); // Estilo de etiqueta

        // Agregar todos los elementos a la caja del producto
        productBox.getChildren().addAll(imageView, nombreLabel, precioLabel, likesLabel);

        // Cambiar color al pasar el mouse
        productBox.setOnMouseEntered(event -> {
            productBox.setStyle("-fx-background-color: #5d1569;"); // Cambia al color más oscuro
        });

        productBox.setOnMouseExited(event -> {
            productBox.setStyle(""); // Vuelve al estilo original
        });

        // Hacer clic en el producto
        productBox.setOnMouseClicked(event -> {
            // Aquí puedes manejar el evento de clic (sin controlador)
            System.out.println("Clic en: " + producto.getNombre()); // Simple mensaje en la consola
            // Aquí puedes abrir otra interfaz si lo deseas
        });

        return productBox;
    }


}
