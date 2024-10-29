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

    @FXML
    public void initialize() {

        comboBoxElimVendedor.getItems().addAll(marketplace.getListaVendedores());
        eliminar.setOnAction(event -> {
            try {
                remove();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        volverButton.setOnAction(event -> {
            try {
                volver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario admin = marketplace.getUsuarioActual();

    private void volver() throws IOException {
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
        if (admin instanceof Admin) {
            ((Admin) admin).removerVendedor(marketplace,vendedorAEliminar);
            MarketplaceManager.setMarketplaceInstance(marketplace);
            FXMLLoader loader;
            Scene scene;
            loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
            Stage stage = (Stage) eliminar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }
}
