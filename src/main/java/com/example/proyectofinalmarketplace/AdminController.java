package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button cerrarButton;

    @FXML
    private Button crearCategoButton;

    @FXML
    private Button crearVendButton;
    @FXML
    private Button crearAdmButton;

    @FXML
    private Button editarVendButton;

    @FXML
    private Button removerVendButton;
    @FXML
    private TextField inputNombre;
    @FXML
    private TextField inputDescripcion;

    @FXML
    private Button verVendButton;


    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Usuario admin = marketplace.getUsuarioActual();
    Utilities logger = Utilities.getInstance();

    @FXML
    public void initialize() {
        cerrarButton.setOnAction(event -> {
            try {
                cerrarSesion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        crearAdmButton.setOnAction(event -> {
            try {
                crearAdmin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        removerVendButton.setOnAction(event -> {
            try {
                remover();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        editarVendButton.setOnAction(event -> {
            try {
                editarVendedor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        crearVendButton.setOnAction(event -> {
            try {
                crearVendedor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        );
        crearCategoButton.setOnAction(event -> {
                    try {
                        crearCategoria();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        verVendButton.setOnAction(event -> {
            try {
                verVendedor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void crearCategoria() throws IOException {
        String nombre = inputNombre.getText().trim();
        String descripcion = inputDescripcion.getText().trim();

        // Verificar que no estén vacíos
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Por favor, complete todos los campos.");
            logger.logInfo("Intento de registrarse con campos vacíos.");
            return;
        }
        Categoria categoria = new Categoria(nombre, descripcion);
        if (admin instanceof Admin) {
            ((Admin) admin).crearCategoria(marketplace, categoria);
            MarketplaceManager.setMarketplaceInstance(marketplace);
        }

    }

    private void cerrarSesion() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está cerrando sesión y navegando a Inicio.fxml.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void remover() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a RemVend.fxml para remover un vendedor.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("RemVend.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) removerVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void crearVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a CrearVend.fxml para crear un nuevo vendedor.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("CrearVend.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void crearAdmin() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a CrearVend.fxml para crear un nuevo administrador.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("CrearAdm.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void editarVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a EditarVend.fxml para editar un vendedor.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("EditarVend.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) editarVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void verVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a VerVend.fxml para ver detalles del vendedor.");
        FXMLLoader loader;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("VerVendedores.fxml"));
        scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) verVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        logger.logInfo("Mostrando alerta: " + title + " - " + content);
    }
}