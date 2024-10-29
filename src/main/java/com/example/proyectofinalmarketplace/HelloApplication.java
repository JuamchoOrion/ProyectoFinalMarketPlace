package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int WIDTH = 600;   // Ancho estándar
    private static final int HEIGHT = 400;   // Alto estándar
    private Marketplace marketplace;

    public static double getWidth() {
        return WIDTH;
    }
    public static double getHeight() {
        return HEIGHT;
    }


    @Override
    public void start(Stage stage) throws IOException, ProductoYaExisteException, ProductoInvalidoException {
        String logFilePath ="C:\\td\\persistencia\\log";
        Utilities logger = Utilities.getInstance(logFilePath);
        marketplace = DatosIniciales.crearMarketplaceConDatosIniciales();
        MarketplaceManager.setMarketplaceInstance(marketplace);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}