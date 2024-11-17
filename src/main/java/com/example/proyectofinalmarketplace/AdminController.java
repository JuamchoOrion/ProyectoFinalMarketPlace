package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    @FXML
    private Button GenerarEstadisticas;

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
        });

        crearCategoButton.setOnAction(event -> {
            try {
                crearCategoria();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        verVendButton.setOnAction(event -> {
            try {
                verVendedor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        GenerarEstadisticas.setOnAction(event -> {
            generarTxTEstadistica();
        });
    }

    private void generarTxTEstadistica() {
        String rutaArchivo = "C:/td/persistencia/archivos/estadistica.txt";
        String titulo = "<Título>Reporte de Listado de Clientes";
        String fecha = "<fecha>Fecha: " + LocalDate.now();
        String usuario = "<Usuario>Reporte realizado por: " + admin.getNombre();

        StringBuilder informacionReporte = new StringBuilder();
        List<Vendedor> listaVendedores = marketplace.getListaVendedores();

        for (Vendedor vendedor : listaVendedores) {
            informacionReporte.append(vendedor.getNombre()).append("\n");
            informacionReporte.append("Top 10 productos con más Likes: \n");

            List<Producto> top10ProductosLikes = marketplace.topDiezProdLikeDeVendedor(vendedor);
            for (int i = 0; i < top10ProductosLikes.size(); i++) {
                Producto producto = top10ProductosLikes.get(i);
                informacionReporte.append((i + 1)).append(". ").append(producto.getNombre())
                        .append(".-Likes: ").append(producto.getLikes()).append("\n");
            }

            double totalVentasMes = marketplace.obtenerTotalVentasMes(vendedor);
            informacionReporte.append("\nSuma de valor de precio en el mes:\n")
                    .append(totalVentasMes).append("\n\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(titulo + "\n");
            writer.write(fecha + "\n");
            writer.write(usuario + "\n\n");
            writer.write("Información del reporte:\n");
            writer.write(informacionReporte.toString());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo generar el reporte: " + e.getMessage());
        }
    }

    private void crearCategoria() throws IOException {
        String nombre = inputNombre.getText().trim();
        String descripcion = inputDescripcion.getText().trim();

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void remover() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a RemVend.fxml para remover un vendedor.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RemVend.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) removerVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void crearVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a CrearVend.fxml para crear un nuevo vendedor.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearVend.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void crearAdmin() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a CrearAdm.fxml para crear un nuevo administrador.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearAdm.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) crearAdmButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void editarVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a EditarVend.fxml para editar un vendedor.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarVend.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
        Stage stage = (Stage) editarVendButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void verVendedor() throws IOException {
        logger.logInfo("El administrador " + admin.getNombre() + " está navegando a VerVend.fxml para ver detalles del vendedor.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VerVendedores.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.getWidth(), HelloApplication.getHeight());
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
