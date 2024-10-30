package com.example.proyectofinalmarketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class VerVendedoresController {

    @FXML
    private TableView<Vendedor> tablaVendedores;

    @FXML
    private TableColumn<Vendedor, String> vendedorNombre;

    @FXML
    private TableColumn<Vendedor, String> vendedorCedula;

    @FXML
    private Button btnEliminarVendedor;

    @FXML
    private Button btnVerVendedor;

    @FXML
    private Button btnDevolverVendedor;

    private ObservableList<Vendedor> vendedoresList = FXCollections.observableArrayList();
    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario admin = marketplace.getUsuarioActual();
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
        btnEliminarVendedor.setOnAction(event -> eliminarVendedor());
        btnVerVendedor.setOnAction(event -> verVendedor());
        btnDevolverVendedor.setOnAction(actionEvent -> {
            try {
                devolver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Inicializa la tabla de vendedores
        vendedorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        vendedorCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        vendedoresList = FXCollections.observableArrayList(marketplace.getVendedores());
        tablaVendedores.setItems(vendedoresList);
    }

    @FXML
    private void eliminarVendedor() {
        Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();
        if (vendedorSeleccionado != null) {
            // Eliminar el vendedor de la lista
            vendedoresList.remove(vendedorSeleccionado);
            // Mostrar mensaje de confirmación
            showAlert(Alert.AlertType.INFORMATION, "Exito", "Vendedor eliminado", "El vendedor ha sido eliminado correctamente.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "No se ha seleccionado ningún vendedor", "Por favor selecciona un vendedor para eliminar.");
        }
    }

    @FXML
private void verVendedor() {
    Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();
    if (vendedorSeleccionado != null) {
        // mostrar al vendedor seleccionado
        String detalles = "Nombre: " + vendedorSeleccionado.getNombre() +
                          "\nCedula: " + vendedorSeleccionado.getCedula() +
                          "\nDescripcion: " + vendedorSeleccionado.getDescripcion() +
                          "\nDireccion: " + vendedorSeleccionado.getDireccion();
        
        // Mostrar detalles del vendedor en un alert
        showAlert(Alert.AlertType.INFORMATION, "Detalles del Vendedor", null, detalles);
    } else {
        showAlert(Alert.AlertType.WARNING, "Advertencia", "No se ha seleccionado ningún vendedor", "Por favor selecciona un vendedor para ver sus detalles.");
    }
}

    @FXML
    private void devolver() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " esta devolviendose desde verVendedores a la ventana admin.fxml.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) btnDevolverVendedor.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
