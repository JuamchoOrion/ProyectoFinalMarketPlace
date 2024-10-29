package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EditarVendController {
    @FXML
    private PasswordField inputNewContrasenia;
    @FXML
    private TextField inputNewCedula;
    @FXML
    private TextField inputNewDireccion;
    @FXML
    private TextField inputNewDescripcion;
    @FXML
    private TextField inputNewNombre;
    @FXML
    private Button editar;
    @FXML
    private Button volverButton;

    @FXML
    private ComboBox<Vendedor> comboBoxVendedor;
    String logFilePath = "C:\\td\\persistencia\\log\\log.txt";
    Utilities logger = Utilities.getInstance(logFilePath);

    @FXML
    public void initialize() {

        comboBoxVendedor.getItems().addAll(marketplace.getListaVendedores());
        editar.setOnAction(event -> edit());
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

    private void edit() {
        // Obtener Inputs
        String nombre = inputNewNombre.getText().trim();
        String contrasenia = inputNewContrasenia.getText().trim();
        String direccion = inputNewDireccion.getText().trim();
        String descripcion = inputNewDescripcion.getText().trim();
        String cedula = inputNewCedula.getText().trim();
        Vendedor vendedorAEditar = comboBoxVendedor.getSelectionModel().getSelectedItem();

        // Verificar que no estén vacíos
        if (nombre.isEmpty() || contrasenia.isEmpty() || direccion.isEmpty() || descripcion.isEmpty() || cedula.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Por favor, complete todos los campos.");
            logger.logInfo("Intento de editar el vendedor con campos vacíos.");
            return;
        }
        if (admin instanceof Admin) {
            try {
                ((Admin) admin).editarVendedor(marketplace,vendedorAEditar, nombre, descripcion, contrasenia, direccion, cedula);
                MarketplaceManager.setMarketplaceInstance(marketplace);
                FXMLLoader loader;
                Scene scene;
                loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
                Stage stage = (Stage) editar.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (UsuarioYaExisteException e) {
                System.out.println("q ravia");
            }
        }



    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

