package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class RegisterController {

    @FXML
    private PasswordField inputContrasenia;
    @FXML
    private TextField inputCedula;
    @FXML
    private TextField inputDireccion;
    @FXML
    private TextField inputDescripcion;
    @FXML
    private TextField inputNombre;
    @FXML
    private Button registrar;
    String logFilePath = "C:\\td\\persistencia\\log\\log.txt";
    Utilities logger = Utilities.getInstance(logFilePath);

    @FXML
    public void initialize() {
        registrar.setOnAction(event -> register());
    }
    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();

    private void register() {
        // Obtener Inputs
        String nombre = inputNombre.getText().trim();
        String contrasenia = inputContrasenia.getText().trim();
        String direccion = inputDireccion.getText().trim();
        String descripcion = inputDescripcion.getText().trim();
        String cedula = inputCedula.getText().trim();

        // Verificar que no estén vacíos
        if (nombre.isEmpty() || contrasenia.isEmpty() || direccion.isEmpty() || descripcion.isEmpty() || cedula.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Por favor, complete todos los campos.");
            logger.logInfo("Intento de registrarse con campos vacíos.");
            return;
        }
        try {
            Vendedor vendedor = new Vendedor(nombre,cedula,descripcion,contrasenia,direccion);
            marketplace.aniadirVendedor(vendedor);
            MarketplaceManager.setMarketplaceInstance(marketplace);
            FXMLLoader loader;
            Scene scene;
            loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
            Stage stage = (Stage) registrar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (UsuarioYaExisteException e) {
            showAlert(Alert.AlertType.ERROR, "Creación Denegada", "La contraseña ya está tomada por otro usuario.");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
