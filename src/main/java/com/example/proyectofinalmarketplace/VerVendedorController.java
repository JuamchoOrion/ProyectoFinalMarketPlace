package com.example.proyectofinalmarketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import java.util.Optional;

public class VerVendedorController {

    @FXML
    private TableView<Vendedor> tablaVendedores;

    @FXML
    private TableColumn<Vendedor, String> vendedorNombre;

    @FXML
    private TableColumn<Vendedor, String> vendedorCedula;

    @FXML
    private ObservableList<Vendedor> listaVendedores;

    @FXML
    private void initialize() {
        // Inicializa las columnas
        vendedorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        vendedorCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        // Cargar datos de prueba
        listaVendedores = FXCollections.observableArrayList(
                new Vendedor("Carlos Pérez", "12345678", "Descripción", "password", "Calle 123"),
                new Vendedor("Lucía Gómez", "87654321", "Descripción", "password", "Calle 456")
        );

        // Asignar la lista de vendedores a la tabla
        tablaVendedores.setItems(listaVendedores);
    }

    // Función para ver los detalles del vendedor
    @FXML
    private void verVendedor() {
        // Obtener el vendedor seleccionado en la tabla
        Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();

        // Verificar que se ha seleccionado un vendedor
        if (vendedorSeleccionado != null) {
            // Mostrar información del vendedor en un JOptionPane
            StringBuilder detalles = new StringBuilder();
            detalles.append("Nombre: ").append(vendedorSeleccionado.getNombre()).append("\n");
            detalles.append("Cédula: ").append(vendedorSeleccionado.getCedula()).append("\n");
            detalles.append("Descripción: ").append(vendedorSeleccionado.getDescripcion()).append("\n");
            detalles.append("Dirección: ").append(vendedorSeleccionado.getDireccion()).append("\n");


            // Mostrar el JOptionPane
            JOptionPane.showMessageDialog(null, detalles.toString(), "Detalles del Vendedor", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Si no se ha seleccionado ningún vendedor, mostrar un mensaje
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un vendedor.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Función para eliminar un vendedor
    @FXML
    private void eliminarVendedor() {
        Vendedor vendedorSeleccionado = tablaVendedores.getSelectionModel().getSelectedItem();

        if (vendedorSeleccionado != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText("¿Está seguro de que desea eliminar este vendedor?");
            alerta.setContentText("Nombre: " + vendedorSeleccionado.getNombre() + "\nCédula: " + vendedorSeleccionado.getCedula());

            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                listaVendedores.remove(vendedorSeleccionado);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un vendedor para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
