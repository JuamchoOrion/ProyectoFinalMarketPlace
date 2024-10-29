package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioNoExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private PasswordField inputContrasenia;

    @FXML
    private TextField inputGmail;

    @FXML
    private Button registrarse;

    @FXML
    private Button ingresoLogin;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
        ingresoLogin.setOnAction(event -> login());
        registrarse.setOnAction(event -> {
            try {
                register();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void register() throws IOException {
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) registrarse.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    private void login() {
        // Obtener Inputs
        String nombre = inputGmail.getText().trim();
        String contrasenia = inputContrasenia.getText().trim();

        // Verificar que no estén vacíos
        if (nombre.isEmpty() || contrasenia.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Por favor, complete todos los campos.");
            logger.logInfo("Intento de login con campos vacíos.");
            return;
        }

        try {
            Usuario usuario = marketplace.autenticacionUsuario(nombre, contrasenia);
            cargarVistaUsuario(usuario);
        } catch (UsuarioNoExisteException e) {
            showAlert(Alert.AlertType.ERROR, "Acceso Denegado", "El nombre o contraseña es incorrecto.");
        }
    }

    private void cargarVistaUsuario(Usuario usuario) {
        try {
            FXMLLoader loader;
            Scene scene;

            if (usuario instanceof Admin) {
                loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());

                AdminController controller = loader.getController();


            } else if (usuario instanceof Vendedor) {
                loader = new FXMLLoader(getClass().getResource("Muro.fxml"));
                scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());

                MuroController controller = loader.getController();
                controller.setMarketplace(marketplace);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Tipo de usuario desconocido.");
                logger.logSevere("Tipo de usuario desconocido para el usuario: " + usuario.getNombre());
                return;
            }

            Stage stage = (Stage) ingresoLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error de Carga", "No se pudo cargar la vista correspondiente.");
            logger.logSevere("Error al cargar la vista para el usuario: " + usuario.getNombre());
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
