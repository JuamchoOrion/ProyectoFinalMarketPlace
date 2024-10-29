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

public class CrearVendController {
    @FXML
    private PasswordField inputContraseniaa;
    @FXML
    private TextField inputCedulaa;
    @FXML
    private TextField inputDirecciona;
    @FXML
    private TextField inputDescripciona;
    @FXML
    private TextField inputNombrea;
    @FXML
    private Button registrara;
    @FXML
    private Button volverButton;
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
        registrara.setOnAction(event -> register());
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

    private void register() {
        // Obtener Inputs
        String nombre = inputNombrea.getText().trim();
        String contrasenia = inputContraseniaa.getText().trim();
        String direccion = inputDirecciona.getText().trim();
        String descripcion = inputDescripciona.getText().trim();
        String cedula = inputCedulaa.getText().trim();

        // Verificar que no estén vacíos
        if (nombre.isEmpty() || contrasenia.isEmpty() || direccion.isEmpty() || descripcion.isEmpty() || cedula.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Por favor, complete todos los campos.");
            logger.logInfo("Intento de registrarse con campos vacíos.");
            return;
        }
        if (admin instanceof Admin) {
            try {
                Vendedor vendedor = new Vendedor(nombre,cedula,descripcion,contrasenia,direccion);
                ((Admin) admin).addVendedor(marketplace, vendedor);
                MarketplaceManager.setMarketplaceInstance(marketplace);
                FXMLLoader loader;
                Scene scene;
                loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
                Stage stage = (Stage) registrara.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException | UsuarioYaExisteException e) {
                throw new RuntimeException(e);

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
