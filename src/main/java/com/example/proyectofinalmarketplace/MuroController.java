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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MuroController {

    @FXML
    private GridPane productGrid;

    @FXML
    private TextField inputBuscar;

    @FXML
    private Button chat;

    @FXML
    private Button buscar;

    @FXML
    private Button perfil;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario usuarioActual = marketplace.getUsuarioActual();


    public void initialize() {
        chat.setOnAction(event -> {
                    try {chatVista();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        perfil.setOnAction(event -> {
            try {perfilVista();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        //List<Producto> productos = marketplace.getListaProductos();  
        //System.out.println(productos);

        List<Producto> productos = new ArrayList<>();

        // Agregar productos del vendedor actual
        productos.addAll(((Vendedor)usuarioActual).getListaProductos());

        // Agregar productos de la lista de amigos del vendedor principal
        for (Vendedor amigo : ((Vendedor)usuarioActual).getListaContactos()) {
            productos.addAll(amigo.getListaProductos());
        }

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
    private void chatVista() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    private void perfilVista() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) chat.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;}

    //"/com/example/proyectofinalmarketplace/Imagenes/logo.png"

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
