package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppConIniciales extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private Marketplace marketplace;

    public static void main(String[] args) {
        launch(); // Lanza la aplicación JavaFX desde aquí
    }

    @Override
    public void start(Stage stage) {
        try {
            // Inicializar datos del marketplace
            inicializarDatos();

            // Configurar el singleton de Marketplace para que otras clases puedan acceder a él
            MarketplaceManager.setMarketplaceInstance(marketplace);

            // Cargar la interfaz de usuario desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(AppConIniciales.class.getResource("Inicio.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

            // Configurar y mostrar la ventana principal
            stage.setTitle("Marketplace - JavaDictos");
            stage.setScene(scene);
            stage.show();

        } catch (IOException | ProductoYaExisteException | ProductoInvalidoException | UsuarioYaExisteException e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

    /**
     * Inicializa los datos del Marketplace usando DatosIniciales.
     */
    private void inicializarDatos() throws ProductoYaExisteException, ProductoInvalidoException, UsuarioYaExisteException {
        // Crear un Marketplace con datos iniciales
        marketplace = DatosIniciales.crearMarketplaceConDatosIniciales();

        // Añadir un vendedor adicional con productos
        Vendedor vendedorExtra = DatosIniciales.crearVendedorConProductos(marketplace);
        marketplace.getVendedores().add(vendedorExtra);
        marketplace.getUsuarios().add(vendedorExtra);
        marketplace.getProductos().addAll(vendedorExtra.getListaProductos());
    }
}
