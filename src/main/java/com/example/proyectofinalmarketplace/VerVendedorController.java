package com.example.proyectofinalmarketplace;

import javafx.beans.property.SimpleStringProperty;
package com.example.proyectofinalmarketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerVendedorController {

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

    @FXML
    public void initialize() {
        // Inicializa la tabla de vendedores
        vendedorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        vendedorCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        // Cargar los vendedores en la tabla
        cargarVendedores();
        tablaVendedores.setItems(vendedoresList);
    }

    private void cargarVendedores() {
        // Agregar vendedores de prueba
        vendedoresList.add(new Vendedor("Juan Pérez", "12345678"));
        vendedoresList.add(new Vendedor("María López", "87654321"));
        vendedoresList.add(new Vendedor("Carlos Gómez", "23456789"));
        vendedoresList.add(new Vendedor("Ana Torres", "98765432"));
    }

    @FXML
    private void eliminarVendedor() {
        Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();
        if (vendedorSeleccionado != null) {
            // Eliminar el vendedor de la lista
            vendedoresList.remove(vendedorSeleccionado);
            // Mostrar mensaje de confirmación
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Vendedor eliminado", "El vendedor ha sido eliminado correctamente.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "No se ha seleccionado ningún vendedor", "Por favor selecciona un vendedor para eliminar.");
        }
    }

    @FXML
    private void verVendedor() {
        Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();
        if (vendedorSeleccionado != null) {
            // Mostrar detalles del vendedor en un alert
            showAlert(Alert.AlertType.INFORMATION, "Detalles del Vendedor", "Nombre: " + vendedorSeleccionado.getNombre(),
                    "Cédula: " + vendedorSeleccionado.getCedula());
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "No se ha seleccionado ningún vendedor", "Por favor selecciona un vendedor para ver sus detalles.");
        }
    }

    @FXML
    private void devolver() {
        // Lógica para regresar a la vista anterior
        System.out.println("Devolviendo a la vista anterior...");
        // Implementar la lógica de retorno
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
