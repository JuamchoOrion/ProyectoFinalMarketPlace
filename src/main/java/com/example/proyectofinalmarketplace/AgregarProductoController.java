package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AgregarProductoController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField urlField;

    @FXML
    private TextField precioField;

    @FXML
    private ComboBox<Categoria> categoriaComboBox;

    @FXML
    private Button aniadirProducto; // Asegúrate de que este nombre coincida con el FXML

    @FXML
    private Button volverBtn;

    @FXML
    private Label tituloLabel;

    private Marketplace marketplace = MarketplaceManager.getMarketplaceInstance();
    private Vendedor usuario = (Vendedor) marketplace.getUsuarioActual();
    Utilities logger = Utilities.getInstance();

    // Método para inicializar el controlador
    @FXML
    public void initialize() {
        List<Categoria> categorias = marketplace.getCategorias();
        categoriaComboBox.getItems().addAll(categorias);

        // Validación para que solo se ingresen números en el campo precio
        precioField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!character.matches("\\d")) {
                event.consume(); // Ignora el ingreso si no es un número
            }
        });
    }

    // Acción del botón Añadir Producto
    @FXML
    private void aniadirProducto() throws ProductoYaExisteException, ProductoInvalidoException, IOException {
        String nombreProducto = nombreField.getText();
        String codigoProducto = codigoField.getText();
        String urlImagen = urlField.getText();
        Categoria categoriaSeleccionada = categoriaComboBox.getValue();
        String precio = precioField.getText();

        // Crear y agregar el producto
        Producto producto = new Producto(nombreProducto, codigoProducto, urlImagen, precio, LocalDateTime.now(), categoriaSeleccionada, 0, Estado.PUBLICADO);
        usuario.agregarProducto(marketplace, producto);

        // Confirmación de la acción (puedes agregar un mensaje en la interfaz si lo deseas)
        System.out.println("Producto agregado: " + producto);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) volverBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    // Acción del botón Volver
    @FXML
    private void volver() throws IOException {
        logger.logInfo("Navegando de regreso a Perfil.fxml.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Perfil.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) volverBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
