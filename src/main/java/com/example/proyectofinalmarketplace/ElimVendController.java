package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ElimVendController {
    @FXML
    private Button eliminar;

    @FXML
    private ComboBox<Vendedor> comboBoxElimVendedor;

    @FXML
    private Button volverButton;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario admin = marketplace.getUsuarioActual();
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
        logger.logInfo("Inicializando ElimVendController.");

        comboBoxElimVendedor.getItems().addAll(marketplace.getListaVendedores());

        eliminar.setOnAction(event -> {
            try {
                remove();
            } catch (IOException e) {
                logger.logWarning("Error al eliminar vendedor: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        volverButton.setOnAction(event -> {
            try {
                volver();
            } catch (IOException e) {
                logger.logWarning("Error al volver a la pantalla anterior: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    private void volver() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está volviendo a la pantalla Admin.fxml.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) volverButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void remove() throws IOException {
        Vendedor vendedorAEliminar = comboBoxElimVendedor.getSelectionModel().getSelectedItem();
        if (vendedorAEliminar != null) {
            logger.logInfo("El administrador " + admin.getNombre() + " está eliminando al vendedor: " + vendedorAEliminar.getNombre());
            if (admin instanceof Admin) {
                ((Admin) admin).removerVendedor(marketplace, vendedorAEliminar);
                MarketplaceManager.setMarketplaceInstance(marketplace);
                logger.logInfo("Vendedor " + vendedorAEliminar.getNombre() + " eliminado exitosamente por el administrador " + admin.getNombre());
            }
            FXMLLoader loader;
            Scene scene;
            loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
            Stage stage = (Stage) eliminar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            logger.logWarning("No se ha seleccionado ningún vendedor para eliminar.");
        }
    }
}
